package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.client.PhoneShopServerClient;
import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.*;
import ec.edu.monster.phoneshop.ws.AppServicePort;
import ec.edu.monster.phoneshop.ws.Product;
import ec.edu.monster.phoneshop.ws.PurchaseRequest;
import ec.edu.monster.phoneshop.ws.PurchaseResponse;
import org.modelmapper.ModelMapper;

import java.util.logging.Logger;

public class PurchaseService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();
    private final ModelMapper modelMapper;

    public PurchaseService() {
        modelMapper = applicationContext.getModelMapper();
    }

    public PurchaseResponseDto purchase(PurchaseRequestDto request) {
        try {
            request.setUserId(applicationContext.getAuthenticatedUser().getId());
            request.setItems(shoppingCartService.getItems().stream().map(item -> PurchaseItemDto.builder()
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .build()).toList());
            return applicationContext.getApiCommunicationType() == ApiCommunicationType.REST
                    ? purchaseThroughRest(request)
                    : purchaseThroughSoap(request);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return PurchaseResponseDto.builder()
                    .message(e.getMessage())
                    .status(ResponseStatus.ERROR)
                    .build();
        }
    }

    private PurchaseResponseDto purchaseThroughRest(PurchaseRequestDto request) {
        logger.info("Purchasing through REST");
        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);
        return phoneshopBankServerClient.purchase(request);
    }

    private PurchaseResponseDto purchaseThroughSoap(PurchaseRequestDto request) throws Exception {
        logger.info("Purchasing through SOAP");
        AppServicePort port = applicationContext.buildSoapClient();
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setMethod(request.getMethod() == PurchaseMethod.CREDIT
                ? ec.edu.monster.phoneshop.ws.PurchaseMethod.CREDIT
                : ec.edu.monster.phoneshop.ws.PurchaseMethod.CASH);
        purchaseRequest.setUserId(request.getUserId().toString());
        purchaseRequest.setMonths(request.getMonths());
        purchaseRequest.getItems().clear();

        for (PurchaseItemDto item : request.getItems()) {
            ec.edu.monster.phoneshop.ws.PurchaseItem purchaseItem = new ec.edu.monster.phoneshop.ws.PurchaseItem();
            Product product = new Product();
            product.setId(item.getProduct().getId().toString());

            purchaseItem.setProduct(product);
            purchaseItem.setQuantity(item.getQuantity());
            purchaseRequest.getItems().add(purchaseItem);
        }

        PurchaseResponse response = port.purchase(purchaseRequest);
        PurchaseResponseDto result = PurchaseResponseDto.builder()
                .message(response.getMessage())
                .status(ResponseStatus.valueOf(response.getStatus().name()))
                .invoicePath(response.getInvoicePath())
                .build();

        if (response.getCredit() != null) {
            result.setCredit(modelMapper.map(response.getCredit(), CreditApplicationResponseDto.class));
        }

        return result;
    }
}