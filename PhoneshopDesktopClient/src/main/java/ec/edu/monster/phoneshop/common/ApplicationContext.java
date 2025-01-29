package ec.edu.monster.phoneshop.common;

import ec.edu.monster.phoneshop.dto.ApiCommunicationType;
import ec.edu.monster.phoneshop.dto.UserDto;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ApplicationContext {
    private static ApplicationContext instance;

    private String serverApiUrl = "http://localhost:9001";
    private String serverApiHost = "localhost";
    private String serverApiPort = "9001";
    private UserDto authenticatedUser;
    private ApiCommunicationType apiCommunicationType;
    private String authToken;

    private ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }

        return instance;
    }

    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public <T> T buildFeignClient(Class<T> clazz) {
        return Feign.builder()
                .client(new OkHttpClient())
                .decoder(new CustomGsonDecoder())
                .encoder(new GsonEncoder())
                .errorDecoder(new FeignErrorDecoder())
                .requestInterceptor(new FeignInterceptor())
                .logLevel(Logger.Level.FULL)
                .target(clazz, "http://" + ((serverApiHost == null || serverApiHost.trim().isBlank()) ? "localhost" : serverApiHost) + ":"
                        + ((serverApiPort == null || serverApiPort.trim().isBlank()) ? "9001" : serverApiPort));
    }

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
