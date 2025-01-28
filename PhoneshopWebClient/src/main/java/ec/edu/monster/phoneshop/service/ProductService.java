package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getProducts();
    ProductDto createProduct(ProductDto product) throws Exception;
    void deleteProduct(UUID id) throws Exception;
    void updateProduct(ProductDto product) throws Exception;
    ProductDto getProduct(UUID id) throws Exception;
}
