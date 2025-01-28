package ec.edu.monster.phoneshop.config;

import ec.edu.monster.phoneshop.common.AppContext;
import ec.edu.monster.phoneshop.common.AppContextHolder;
import ec.edu.monster.phoneshop.common.ShoppingCart;
import ec.edu.monster.phoneshop.common.ShoppingCartHolder;
import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final ShoppingCartHolder shoppingCartHolder;
    private final AppContextHolder appContextHolder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    @Bean(name = "authenticationContext")
    @Scope(value = WebApplicationContext.SCOPE_SESSION)
    public AuthenticationContext authenticationContext() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        if (attributes instanceof NativeWebRequest) {
            HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attributes).getNativeRequest();
            UserDto user = (UserDto) request.getSession().getAttribute("AUTHENTICATED_USER");
            return AuthenticationContext.builder().user(user).build();
        }

        return AuthenticationContext.builder().build();
    }

    @Bean(name = "shoppingCart")
    @Scope(value = WebApplicationContext.SCOPE_SESSION)
    public ShoppingCart shoppingCart() {
        return shoppingCartHolder.getShoppingCart();
    }

    @Bean(name = "appContext")
    @Scope(value = WebApplicationContext.SCOPE_SESSION)
    public AppContext appContext() {
        return appContextHolder.getAppContext();
    }
}
