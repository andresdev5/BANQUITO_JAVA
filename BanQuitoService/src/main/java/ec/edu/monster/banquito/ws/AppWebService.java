package ec.edu.monster.banquito.ws;

import ec.edu.monster.app.ws.*;
import ec.edu.monster.banquito.dto.*;
import ec.edu.monster.banquito.service.AuthService;
import ec.edu.monster.banquito.service.CreditService;
import ec.edu.monster.banquito.service.MovementService;
import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.dto.PurchaseItemDto;
import ec.edu.monster.phoneshop.dto.PurchaseRequestDto;
import ec.edu.monster.phoneshop.dto.PurchaseResponseDto;
import ec.edu.monster.phoneshop.enums.PurchaseMethod;
import ec.edu.monster.phoneshop.service.ProductService;
import ec.edu.monster.phoneshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Endpoint
@RequiredArgsConstructor
public class AppWebService {
    private static final String NAMESPACE_URI = "http://monster.edu.ec/app/ws";
    private final CreditService creditService;
    private final MovementService movementService;
    private final AuthService authService;
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final ModelMapper modelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse loginRequest(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();
        AuthResponseDto authResponse = authService.login(AuthCredentialsDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());

        response.setToken(authResponse.getToken());
        response.setUser(modelMapper.map(authResponse.getUser(), User.class));
        response.setStatus(ec.edu.monster.app.ws.ResponseStatus.valueOf(authResponse.getStatus().name()));
        response.setMessage(authResponse.getMessage());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMovementsByIdentificationRequest")
    @ResponsePayload
    public GetMovementsByIdentificationResponse getMovementsByIdentificationRequest(@RequestPayload GetMovementsByIdentificationRequest request) {
        GetMovementsByIdentificationResponse response = new GetMovementsByIdentificationResponse();
        List<Movement> movements = movementService.getMovementsByIdentificationNumber(request.getIdentification())
                .stream()
                .map(movementDto -> modelMapper.map(movementDto, Movement.class))
                .toList();
        response.getMovements().addAll(movements);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkCreditRequest")
    @ResponsePayload
    public CheckCreditResponse checkCreditRequest(@RequestPayload CheckCreditRequest request) {
        CheckCreditResultDto result = creditService.checkCredit(CheckCreditRequestDto.builder()
                .identificationNumber(request.getIdentification())
                .amount(request.getAmount())
                .build());

        CheckCreditResponse response = new CheckCreditResponse();
        response.setEligible(result.isEligible());
        response.setMessage(result.getMessage());
        response.setMaxAmount(result.getMaxAmount());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "applyForCreditRequest")
    @ResponsePayload
    public ApplyForCreditResponse applyForCreditRequest(@RequestPayload ApplyForCreditRequest request) {
        CreditApplicationResponseDto result = creditService.applyForCredit(CreditApplicationRequestDto.builder()
                .amount(request.getAmount())
                .identificationNumber(request.getIdentification())
                .months(request.getMonths())
                .build());

        ApplyForCreditResponse response = new ApplyForCreditResponse();

        response.setTotalAmount(result.getTotalAmount());
        response.setTotalInstallments(result.getTotalInstallments());
        response.getInstallments().addAll(
                result.getInstallments().stream()
                        .map(installmentDto -> modelMapper.map(installmentDto, CreditInstallment.class))
                        .toList()
        );
        response.setMonthlyInstallment(result.getMonthlyInstallment());
        response.setAnnualInterestRate(result.getAnnualInterestRate());

        return response;
    }

    //////////////////////////////////// PHONESHOP ////////////////////////////////////
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProductRequest(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        ProductDto product = productService.getProduct(UUID.fromString(request.getId()));
        response.setProduct(modelMapper.map(product, Product.class));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProductsRequest(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        List<Product> products = productService.getProducts()
                .stream()
                .map(productDto -> modelMapper.map(productDto, Product.class))
                .toList();
        response.getProducts().addAll(products);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createProductRequest")
    @ResponsePayload
    public CreateProductResponse createProductRequest(@RequestPayload CreateProductRequest request) {
        ProductDto productDto = modelMapper.map(request.getProduct(), ProductDto.class);
        byte[] imageFile = new byte[0];

        if (request.getProduct().getImageFile() != null) {
            Base64.Decoder decoder = Base64.getDecoder();
            imageFile = decoder.decode(request.getProduct().getImageFile());
            productDto.setImageFile(imageFile);
        }

        CreateProductResponse response = new CreateProductResponse();
        ProductDto saved = productService.createProduct(productDto);
        response.setProduct(modelMapper.map(saved, Product.class));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateProductRequest")
    @ResponsePayload
    public UpdateProductResponse updateProductRequest(@RequestPayload UpdateProductRequest request) throws Exception {
        ProductDto productDto = modelMapper.map(request.getProduct(), ProductDto.class);
        byte[] imageFile = new byte[0];

        if (request.getProduct().getImageFile() != null) {
            Base64.Decoder decoder = Base64.getDecoder();
            imageFile = decoder.decode(request.getProduct().getImageFile());
            productDto.setImageFile(imageFile);
        }

        productService.updateProduct(productDto);
        UpdateProductResponse response = new UpdateProductResponse();
        response.setProduct(modelMapper.map(request.getProduct(), Product.class));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProductRequest")
    @ResponsePayload
    public DeleteProductResponse deleteProductRequest(@RequestPayload DeleteProductRequest request) throws Exception {
        productService.deleteProduct(UUID.fromString(request.getId()));
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Producto eliminado");
        response.setStatus(ec.edu.monster.app.ws.ResponseStatus.SUCCESS);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "purchaseRequest")
    @ResponsePayload
    public PurchaseResponse purchaseRequest(@RequestPayload PurchaseRequest request) {
        PurchaseResponseDto result;

        try {
            result = purchaseService.purchase(PurchaseRequestDto.builder()
                    .method(PurchaseMethod.valueOf(request.getMethod().name()))
                    .items(request.getItems().stream()
                            .map(item -> modelMapper.map(item, PurchaseItemDto.class))
                            .toList())
                    .userId(UUID.fromString(request.getUserId()))
                    .months(request.getMonths())
                    .build());

            PurchaseResponse response = new PurchaseResponse();
            response.setMessage(result.getMessage());
            response.setStatus(ec.edu.monster.app.ws.ResponseStatus.valueOf(result.getStatus().name()));

            if (result.getCredit() != null) {
                AppliedCreditResponse credit = new AppliedCreditResponse();
                credit.setTotalAmount(result.getCredit().getTotalAmount());
                credit.setAnnualInterestRate(result.getCredit().getAnnualInterestRate());
                credit.setMonthlyInstallment(result.getCredit().getMonthlyInstallment());
                credit.setTotalInstallments(result.getCredit().getTotalInstallments());
                credit.getInstallments().addAll(
                        result.getCredit().getInstallments().stream()
                                .map(installmentDto -> modelMapper.map(installmentDto, CreditInstallment.class))
                                .toList()
                );
                response.setCredit(credit);
            }

            response.setInvoicePath(result.getInvoicePath());

            return response;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            result = PurchaseResponseDto.builder()
                    .message(e.getMessage())
                    .status(ec.edu.monster.banquito.dto.ResponseStatus.ERROR)
                    .build();
            PurchaseResponse response = new PurchaseResponse();
            response.setMessage(result.getMessage());
            response.setStatus(ec.edu.monster.app.ws.ResponseStatus.ERROR);

            return response;
        }
    }
}
