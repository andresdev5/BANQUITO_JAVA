package ec.edu.monster.phoneshop.client;

import ec.edu.monster.phoneshop.dto.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.UUID;

public interface PhoneShopServerClient {
    @RequestLine("POST /auth/login")
    AuthResponseDto login(AuthCredentialsDto credentials);

    @RequestLine("GET /movements")
    List<MovementDto> getMovements();

    @RequestLine("POST /transactions/transfer")
    void transfer(TransactionDto transaction);

    @RequestLine("POST /public/credit/check")
    CheckCreditResultDto checkCredit(CheckCreditRequestDto request);

    @RequestLine("GET /public/products")
    List<ProductDto> getProducts();

    @RequestLine("GET /public/products/{id}")
    ProductDto getProduct(@Param("id") UUID id) throws Exception;

    @RequestLine("PUT /public/products")
    ProductDto updateProduct(ProductDto product) throws Exception;

    @RequestLine("POST /public/products")
    ProductDto createProduct(ProductDto product) throws Exception;

    @RequestLine("DELETE /public/products/{id}")
    void deleteProduct(@Param("id") UUID id) throws Exception;

    @RequestLine("POST /public/purchase")
    PurchaseResponseDto purchase(PurchaseRequestDto request);
}
