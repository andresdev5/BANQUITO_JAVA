package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getProducts();
    ProductDto updateProduct(ProductDto product);
    ProductDto createProduct(ProductDto product);
    void deleteProduct(UUID id) throws Exception;
    ProductDto getProduct(UUID id);
}
