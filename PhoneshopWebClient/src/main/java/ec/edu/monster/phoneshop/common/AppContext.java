package ec.edu.monster.phoneshop.common;

import ec.edu.monster.phoneshop.dto.ApiCommunicationType;
import ec.edu.monster.phoneshop.dto.UserDto;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import lombok.Data;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AppContext {
    private String serverApiHost;
    private String serverApiPort;
    private String serverApiUrl;
    private UserDto authenticatedUser;
    private ApiCommunicationType apiCommunicationType;
    private String authToken;

    public ec.edu.monster.phoneshop.ws.AppServicePort buildSoapClient() {
        URL url = null;

        try {
            url = new URL("http://" + ((serverApiHost == null || serverApiHost.trim().isBlank()) ? "localhost" : serverApiHost) + ":"
                    + ((serverApiPort == null || serverApiPort.trim().isBlank()) ? "9001" : serverApiPort) + "/ws/app.wsdl");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        ec.edu.monster.phoneshop.ws.AppServicePortService portService;

        if (url == null) {
            portService = new ec.edu.monster.phoneshop.ws.AppServicePortService();
        } else {
            portService = new ec.edu.monster.phoneshop.ws.AppServicePortService(url);
        }

        Dispatch<SOAPMessage> dispatch = portService.createDispatch(
                new QName("http://monster.edu.ec/app/ws", "AppServicePortSoap11"),
                SOAPMessage.class,
                Service.Mode.MESSAGE
        );

        ec.edu.monster.phoneshop.ws.AppServicePort port = portService.getAppServicePortSoap11();

        if (authToken != null) {
            System.out.println("TOKEN: " + "Bearer " + authToken);

            Map<String, List<String>> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", List.of("Bearer " + authToken));
            dispatch.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

            BindingProvider bindingProvider = (BindingProvider) port;
            bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
        }

        return port;
    }
}
