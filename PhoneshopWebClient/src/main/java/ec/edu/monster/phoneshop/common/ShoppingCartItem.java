package ec.edu.monster.phoneshop.common;

import ec.edu.monster.phoneshop.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ShoppingCartItem {
    private ProductDto product;
    private int quantity;

    public BigDecimal getTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
