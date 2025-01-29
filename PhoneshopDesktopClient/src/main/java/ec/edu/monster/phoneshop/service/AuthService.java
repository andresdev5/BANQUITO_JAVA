package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.client.PhoneShopServerClient;
import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.*;
import ec.edu.monster.phoneshop.ws.AppServicePort;
import ec.edu.monster.phoneshop.ws.LoginRequest;
import ec.edu.monster.phoneshop.ws.LoginResponse;

import java.util.UUID;
import java.util.logging.Logger;

public class AuthService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();

    public AuthService() {}

    public AuthResponseDto login(LoginRequestDto credentials) {
        String host = credentials.getServerIp();
        String port = "9001";

        if (host.contains(":")) {
            String[] parts = host.split(":");
            host = parts[0];
            port = parts[1];
        }

        String baseUrl = String.format("http://%s:%s", host, port);

        applicationContext.setServerApiUrl(baseUrl);
        applicationContext.setServerApiHost(host);
        applicationContext.setServerApiPort(port);
        applicationContext.setApiCommunicationType(credentials.getApiCommunicationType());

        AuthResponseDto response = credentials.getApiCommunicationType() == ApiCommunicationType.REST
                ? loginThroughRest(credentials)
                : loginThroughSoap(credentials);

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            applicationContext.setAuthenticatedUser(response.getUser());
            applicationContext.setAuthToken(response.getToken());
        }

        return response;
    }

    public void logout() {
        applicationContext.setAuthToken(null);
        applicationContext.setAuthenticatedUser(null);
        shoppingCartService.clear();
    }

    private AuthResponseDto loginThroughRest(LoginRequestDto credentials) {
        logger.info("Logging in through REST");

        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);

        return phoneshopBankServerClient.login(AuthCredentialsDto.builder()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build());
    }

    private AuthResponseDto loginThroughSoap(LoginRequestDto credentials) {
        logger.info("Logging in through SOAP");

        AppServicePort port = applicationContext.buildSoapClient();
        LoginRequest request = new LoginRequest();

        request.setUsername(credentials.getUsername());
        request.setPassword(credentials.getPassword());

        try {
            LoginResponse loginResponse = port.login(request);

            if (loginResponse.getStatus() == ec.edu.monster.phoneshop.ws.ResponseStatus.ERROR) {
                return AuthResponseDto.builder()
                        .status(ResponseStatus.ERROR)
                        .message(loginResponse.getMessage())
                        .build();
            } else {
                String token = loginResponse.getToken();
                UserDto user = UserDto.builder()
                        .id(UUID.fromString(loginResponse.getUser().getId()))
                        .username(loginResponse.getUser().getUsername())
                        .email(loginResponse.getUser().getEmail())
                        .identificationNumber(loginResponse.getUser().getIdentificationNumber())
                        .role(RoleDto.builder()
                                .id(UUID.fromString(loginResponse.getUser().getRole().getId()))
                                .name(loginResponse.getUser().getRole().getName())
                                .build())
                        .profile(UserProfileDto.builder()
                                .id(UUID.fromString(loginResponse.getUser().getProfile().getId()))
                                .firstName(loginResponse.getUser().getProfile().getFirstName())
                                .lastName(loginResponse.getUser().getProfile().getLastName())
                                .build())
                        .build();

                return AuthResponseDto.builder()
                        .token(token)
                        .user(user)
                        .status(ResponseStatus.SUCCESS)
                        .message(loginResponse.getMessage())
                        .build();
            }
        } catch (Exception e) {
            return AuthResponseDto.builder()
                    .status(ResponseStatus.ERROR)
                    .message(e.getMessage())
                    .build();
        }
    }
}
