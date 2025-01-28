package ec.edu.monster.phoneshop.client;

import ec.edu.monster.phoneshop.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "phoneshop-server", url = "${feign.url.phoneshop-server}")
public interface PhoneShopServerClient {
    @PostMapping("/auth/login")
    AuthResponseDto login(AuthCredentialsDto credentials);

    @GetMapping("/auth/me")
    UserDto getCurrentUser();

    @GetMapping("/movements")
    List<MovementDto> getMovements();

    @PostMapping("/transactions/transfer")
    void transfer(TransactionDto transaction);

    @PostMapping("/public/credit/check")
    CheckCreditResultDto checkCredit(CheckCreditRequestDto request);

    @PostMapping("/public/purchase")
    PurchaseResponseDto purchase(PurchaseRequestDto request);

    @GetMapping("/public/products")
    List<ProductDto> getProducts();

    @PutMapping("/public/products")
    ProductDto updateProduct(ProductDto phone)  throws Exception;

    @PostMapping("/public/products")
    ProductDto createProduct(ProductDto product)  throws Exception;

    @DeleteMapping("/public/products/{id}")
    void deleteProduct(@PathVariable UUID id) throws Exception;

    @GetMapping("/public/products/{id}")
    ProductDto getProduct(@PathVariable("id") UUID id) throws Exception;
}
