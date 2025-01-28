package ec.edu.monster.phoneshop.controller;

import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.common.CommandLine;
import ec.edu.monster.phoneshop.dto.AuthCredentialsDto;
import ec.edu.monster.phoneshop.dto.AuthResponseDto;
import ec.edu.monster.phoneshop.dto.ResponseStatus;
import ec.edu.monster.phoneshop.service.AuthService;

public class AuthController {
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final CommandLine commandLine = CommandLine.getInstance();
    private final AuthService authService = new AuthService();

    public void login() {
        commandLine.print("Autentificaci\u00F3n");
        String username = commandLine.prompt("Usuario: ");
        String password = commandLine.password("Contraseña: ");
        String serverIp = commandLine.prompt("IP del servidor: ");

        applicationContext.setServerIp(serverIp);

        try {
            AuthResponseDto response = authService.login(AuthCredentialsDto.builder()
                    .username(username)
                    .password(password)
                    .build());

            if (response.getStatus() == ResponseStatus.SUCCESS) {
                applicationContext.setToken(response.getToken());
                applicationContext.setIdentificationNumber(response.getUser().getIdentificationNumber());
                commandLine.print("Autentificación exitosa");
            } else {
                commandLine.print(response.getMessage());
                login();
            }
        } catch (Exception e) {
            commandLine.print(e.getMessage());
            login();
        }
    }
}
