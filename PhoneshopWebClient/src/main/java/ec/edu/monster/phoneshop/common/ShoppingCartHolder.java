package ec.edu.monster.phoneshop.common;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
public class ShoppingCartHolder {
    private ShoppingCart shoppingCart = new ShoppingCart();

    @PostConstruct
    public void init() {}
}
