package ec.edu.monster.phoneshop.common;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignAuthInterceptor implements RequestInterceptor
{
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + applicationContext.getToken());
    }
}
