package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.client.PhoneShopServerClient;
import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.ApiCommunicationType;
import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.ws.*;
import org.modelmapper.ModelMapper;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ProductService {
    private final Logger logger = Logger.getLogger(ProductService.class.getName());
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final ModelMapper modelMapper;

    public ProductService() {
        modelMapper = applicationContext.getModelMapper();
    }

    public List<ProductDto> getProducts() {
        try {
            return applicationContext.getApiCommunicationType() == ApiCommunicationType.REST
                    ? getProductsThroughRest()
                    : getProductsThroughSoap();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return List.of();
        }
    }

    public ProductDto getProduct(UUID id) throws Exception {
        return applicationContext.getApiCommunicationType() == ApiCommunicationType.REST
                ? getProductThroughRest(id)
                : getProductThroughSoap(id);
    }

    public ProductDto createProduct(ProductDto product) throws Exception {
        return applicationContext.getApiCommunicationType() == ApiCommunicationType.REST
                ? createProductThroughRest(product)
                : createProductThroughSoap(product);
    }

    public void deleteProduct(UUID id) throws Exception {
        if (applicationContext.getApiCommunicationType() == ApiCommunicationType.REST) {
            deleteProductThroughRest(id);
        } else {
            deleteProductThroughSoap(id);
        }
    }

    public void updateProduct(ProductDto product) throws Exception {
        if (applicationContext.getApiCommunicationType() == ApiCommunicationType.REST) {
            updateProductThroughRest(product);
        } else {
            updateProductThroughSoap(product);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private List<ProductDto> getProductsThroughRest() {
        logger.info("Getting products through REST");
        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);
        return phoneshopBankServerClient.getProducts();
    }

    private List<ProductDto> getProductsThroughSoap() {
        logger.info("Getting products through SOAP");
        AppServicePort port = applicationContext.buildSoapClient();

        return port.getProducts(new GetProductsRequest()).getProducts()
                .stream().map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }

    private ProductDto getProductThroughRest(UUID id) throws Exception {
        logger.info("Getting product through REST");
        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);
        return phoneshopBankServerClient.getProduct(id);
    }

    private ProductDto getProductThroughSoap(UUID id) throws Exception {
        logger.info("Getting product through SOAP");
        AppServicePort port = applicationContext.buildSoapClient();
        GetProductRequest request = new GetProductRequest();
        request.setId(id.toString());
        return modelMapper.map(port.getProduct(request).getProduct(), ProductDto.class);
    }

    private ProductDto createProductThroughRest(ProductDto product) throws Exception {
        logger.info("Creating product through REST");
        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);
        return phoneshopBankServerClient.createProduct(product);
    }

    private ProductDto createProductThroughSoap(ProductDto product) throws Exception {
        logger.info("Creating product through SOAP");
        AppServicePort port = applicationContext.buildSoapClient();
        CreateProductRequest request = new CreateProductRequest();
        Product productParam = modelMapper.map(product, Product.class);

        if (product.getImageFile() != null && product.getImageFile().length > 0) {
            String encoded = Base64.getEncoder().encodeToString(product.getImageFile());
            productParam.setImageFile(encoded);
        }

        request.setProduct(productParam);
        return modelMapper.map(port.createProduct(request).getProduct(), ProductDto.class);
    }

    private ProductDto updateProductThroughRest(ProductDto product) throws Exception {
        logger.info("Updating product through REST");
        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);
        return phoneshopBankServerClient.updateProduct(product);
    }

    private ProductDto updateProductThroughSoap(ProductDto product) throws Exception {
        logger.info("Updating product through SOAP");
        AppServicePort port = applicationContext.buildSoapClient();
        UpdateProductRequest request = new UpdateProductRequest();
        Product productParam = modelMapper.map(product, Product.class);

        if (product.getImageFile() != null && product.getImageFile().length > 0) {
            String encoded = Base64.getEncoder().encodeToString(product.getImageFile());
            productParam.setImageFile(encoded);
        } else {
            productParam.setImageFile(null);
        }

        request.setProduct(productParam);
        return modelMapper.map(port.updateProduct(request).getProduct(), ProductDto.class);
    }

    private void deleteProductThroughRest(UUID id) throws Exception {
        logger.info("Deleting product through REST");
        PhoneShopServerClient phoneshopBankServerClient = applicationContext.buildFeignClient(PhoneShopServerClient.class);
        phoneshopBankServerClient.deleteProduct(id);
    }

    private void deleteProductThroughSoap(UUID id) throws Exception {
        logger.info("Deleting product through SOAP");
        AppServicePort port = applicationContext.buildSoapClient();
        DeleteProductRequest request = new DeleteProductRequest();
        request.setId(id.toString());
        port.deleteProduct(request);
    }
}
