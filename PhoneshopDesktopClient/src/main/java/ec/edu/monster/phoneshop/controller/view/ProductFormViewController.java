package ec.edu.monster.phoneshop.controller.view;

import ec.edu.monster.phoneshop.App;
import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.enums.SnackBarMessageType;
import ec.edu.monster.phoneshop.service.ProductService;
import ec.edu.monster.phoneshop.service.RouterService;
import ec.edu.monster.phoneshop.service.ui.MessageBoxService;
import ec.edu.monster.phoneshop.service.ui.SnackBarService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ProductFormViewController extends BaseView {
    private final Logger logger = Logger.getLogger(ProductFormViewController.class.getName());
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final ProductService productService = new ProductService();
    private ProductDto currentProduct;
    private byte[] imageFileBytes;

    @FXML
    TextField nameInput;

    @FXML
    TextField priceInput;

    @FXML
    Button chooseFileButton;

    @FXML
    TextField filePathInput;

    @FXML
    ImageView imageView;

    @FXML
    Button saveButton;

    @FXML
    Label viewTitleLabel;

    @Override
    public void preNavigate(Map<String, Object> parameters) {
        for (String key : parameters.keySet()) {
            logger.info(String.format("<%s> = <%s>", key, parameters.get(key).toString().replace("\n", "")));
        }

        if (parameters.containsKey("product")) {
            ProductDto product = (ProductDto) parameters.get("product");
            currentProduct = product;
            fillForm(product);
            viewTitleLabel.setText("Editar producto");
        } else {
            viewTitleLabel.setText("Agregar producto");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnAction(event -> {
            saveProduct();
        });

        chooseFileButton.setOnAction(event -> {
            openImageChooser();
        });
    }

    private void fillForm(ProductDto product) {
        nameInput.setText(product.getName());
        priceInput.setText(String.valueOf(product.getPrice()));
        filePathInput.setText("");

        if (product.getImagePath() != null) {
            String baseUrl = applicationContext.getServerApiUrl();
            String imagePath = product.getImagePath();
            String imageUrl = baseUrl + imagePath;

            imageView.setImage(new javafx.scene.image.Image(imageUrl));
        }
    }

    private void saveProduct() {
        String name = nameInput.getText().trim();
        BigDecimal price = BigDecimal.ZERO;

        try {
            price = new BigDecimal(priceInput.getText().trim());
        } catch (Exception e) {
            MessageBoxService.getInstance().error("El precio ingresado es incorrecto");
            return;
        }

        ProductDto product = ProductDto.builder()
                .id(currentProduct != null ? currentProduct.getId() : null)
                .name(name)
                .price(price)
                .imageFile(imageFileBytes)
                .build();

        try {
            if (currentProduct != null) {
                productService.updateProduct(product);
            } else {
                productService.createProduct(product);
            }

            RouterService.getInstance().navigate("products");
            String action = currentProduct != null ? "actualizado" : "creado";
            SnackBarService.getInstance().showMessage(SnackBarMessageType.SUCCESS, "Producto " + action + " exitosamente");
        } catch (Exception e) {
            MessageBoxService.getInstance().error(e.getMessage());
            logger.warning(e.getMessage());
        }
    }

    private void openImageChooser() {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(List.of(
                new FileChooser.ExtensionFilter("Imagenes", List.of(
                    "*.jpg", "*.png", "*.jpeg", "*.gif"
                ))
        ));

        File imageFile = fileChooser.showOpenDialog(App.getScene().getWindow());

        try {
            if (imageFile != null) {
                imageFileBytes = Files.readAllBytes(imageFile.toPath());
                filePathInput.setText(imageFile.getAbsolutePath());
                imageView.setImage(new javafx.scene.image.Image(imageFile.toURI().toString()));
            } else {
                throw new Exception("Error al leer la imagen");
            }
        } catch (Exception e) {
            MessageBoxService.getInstance().error(e.getMessage());
            logger.warning(e.getMessage());
        }
    }
}
