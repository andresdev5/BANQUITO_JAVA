package ec.edu.monster.phoneshop.service.impl;

import ec.edu.monster.phoneshop.client.PhoneShopServerClient;
import ec.edu.monster.phoneshop.common.AppContextHolder;
import ec.edu.monster.phoneshop.common.ShoppingCartHolder;
import ec.edu.monster.phoneshop.dto.*;
import ec.edu.monster.phoneshop.service.AuthService;
import ec.edu.monster.phoneshop.ws.AppServicePort;
import ec.edu.monster.phoneshop.ws.LoginRequest;
import ec.edu.monster.phoneshop.ws.LoginResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final PhoneShopServerClient phoneshopBankServerClient;
    private final HttpSession session;
    private final AppContextHolder appContextHolder;
    private final ShoppingCartHolder shoppingCartHolder;

    @Override
    public AuthResponseDto login(LoginRequestDto credentials) {
        String host = credentials.getServerIp();
        String port = "9001";

        if (host.contains(":")) {
            String[] parts = host.split(":");
            host = parts[0];
            port = parts[1];
        }

        session.setAttribute("EUREKABANK_SERVER_IP", host);
        session.setAttribute("EUREKABANK_SERVER_PORT", port);
        String baseUrl = String.format("http://%s:%s", host, port);

        appContextHolder.getAppContext().setServerApiUrl(baseUrl);
        appContextHolder.getAppContext().setServerApiHost(host);
        appContextHolder.getAppContext().setServerApiPort(port);
        appContextHolder.getAppContext().setApiCommunicationType(credentials.getApiCommunicationType());

        AuthResponseDto response = credentials.getApiCommunicationType() == ApiCommunicationType.REST
                ? loginThroughRest(credentials)
                : loginThroughSoap(credentials);

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            session.setAttribute("EUREKABANK_SERVER_IP", host);
            session.setAttribute("EUREKABANK_SERVER_PORT", port);
            session.setAttribute("IDENTIFICATION_NUMBER", response.getUser().getIdentificationNumber());
            session.setAttribute("AUTH_TOKEN", response.getToken());
            session.setAttribute("AUTHENTICATED_USER", response.getUser());
            appContextHolder.getAppContext().setAuthenticatedUser(response.getUser());
            appContextHolder.getAppContext().setAuthToken(response.getToken());
        }

        return response;
    }

    @Override
    public void logout() {
        session.removeAttribute("AUTH_TOKEN");
        session.removeAttribute("AUTHENTICATED_USER");
        session.removeAttribute("EUREKABANK_SERVER_IP");
        session.removeAttribute("EUREKABANK_SERVER_PORT");
        session.removeAttribute("IDENTIFICATION_NUMBER");
        shoppingCartHolder.getShoppingCart().clear();
    }

    @Override
    public UserDto getCurrentUser() {
        return phoneshopBankServerClient.getCurrentUser();
    }

    private AuthResponseDto loginThroughRest(LoginRequestDto credentials) {
        logger.info("Logging in through REST");

        return phoneshopBankServerClient.login(AuthCredentialsDto.builder()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build());
    }

    private AuthResponseDto loginThroughSoap(LoginRequestDto credentials) {
        logger.info("Logging in through SOAP");

        AppServicePort port = appContextHolder.getAppContext().buildSoapClient();
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
