package ec.edu.monster.phoneshop.common;

import ec.edu.monster.phoneshop.dto.ProductDto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShoppingCart {
    private final Map<UUID, ShoppingCartItem> items = new HashMap<>();

    public void add(ProductDto product) {
        if (items.containsKey(product.getId())) {
            ShoppingCartItem item = items.get(product.getId());
            item.setQuantity(item.getQuantity() + 1);
        } else {
            items.put(product.getId(), ShoppingCartItem.builder()
                    .product(product)
                    .quantity(1)
                    .build());
        }
    }

    public void updateProduct(ProductDto product) {
        if (items.containsKey(product.getId())) {
            ShoppingCartItem item = items.get(product.getId());
            item.setProduct(product);
        }
    }

    public void setProductQuantity(UUID id, int quantity) {
        if (items.containsKey(id)) {
            ShoppingCartItem item = items.get(id);
            item.setQuantity(Math.max(0, quantity));
        }
    }

    public void clear() {
        items.clear();
    }

    public void remove(UUID id, int quantity) {
        if (quantity <= 0) {
            items.remove(id);
        } else {
            if (items.containsKey(id)) {
                ShoppingCartItem item = items.get(id);
                int value = Math.max(0, item.getQuantity() - quantity);

                if (value == 0) {
                    items.remove(id);
                } else {
                    item.setQuantity(value);
                }
            }
        }
    }

    public void remove(UUID id) {
        items.remove(id);
    }

    public int getSize() {
        return items.size();
    }

    public int getTotalProducts() {
        return items.values().stream().mapToInt(ShoppingCartItem::getQuantity).sum();
    }

    public double getTotalPrice() {
        return items.values().stream().mapToDouble(item -> item.getProduct()
                .getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue()).sum();
    }

    public List<ShoppingCartItem> getItems() {
        return List.copyOf(items.values());
    }
}
