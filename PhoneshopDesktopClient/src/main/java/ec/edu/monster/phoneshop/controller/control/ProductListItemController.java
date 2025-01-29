package ec.edu.monster.phoneshop.controller.control;

import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.ProductDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ProductListItemController  implements Initializable {
    private final Logger logger = Logger.getLogger(ProductListItemController.class.getName());
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();

    @Data
    @Builder
    public static class ProductListEventParam {
        private ProductListItemController controller;
        private ProductDto product;
    }

    @FXML
    Label productIdLabel;

    @FXML
    Label productNameLabel;

    @FXML
    Label productPriceLabel;

    @FXML
    ImageView productImageView;

    @FXML
    Button updateButton;

    @FXML
    Button deleteButton;

    @FXML
    Button addToCartButton;

    private Consumer<ProductListEventParam> onProductListItemDeleted;
    private Consumer<ProductListEventParam> onProductListItemUpdated;
    private Consumer<ProductListEventParam> onProductListItemAddedToCart;
    private ProductDto product;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.setOnAction(event -> {
            if (onProductListItemUpdated != null) {
                onProductListItemUpdated.accept(ProductListEventParam.builder()
                        .controller(this)
                        .product(product)
                        .build());
            }
        });

        deleteButton.setOnAction(event -> {
            if (onProductListItemDeleted != null) {
                onProductListItemDeleted.accept(ProductListEventParam.builder()
                        .controller(this)
                        .product(product)
                        .build());
            }
        });

        addToCartButton.setOnAction(event -> {
            if (onProductListItemAddedToCart != null) {
                onProductListItemAddedToCart.accept(ProductListEventParam.builder()
                        .controller(this)
                        .product(product)
                        .build());
            }
        });
    }

    public void setData(int rowNumber, ProductDto product) {
        this.product = product;
        
        productIdLabel.setText(String.valueOf(rowNumber));
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(String.valueOf(product.getPrice()));

        String baseUrl = applicationContext.getServerApiUrl();
        String imagePath = product.getImagePath();
        String imageUrl = baseUrl + imagePath;

        logger.info("setting product image with url: " + imageUrl);

        productImageView.setImage(new javafx.scene.image.Image(imageUrl));
    }

    public void onProductListItemDeleted(Consumer<ProductListEventParam> onProductListItemDeleted) {
        this.onProductListItemDeleted = onProductListItemDeleted;
    }

    public void onProductListItemUpdated(Consumer<ProductListEventParam> onProductListItemUpdated) {
        this.onProductListItemUpdated = onProductListItemUpdated;
    }

    public void onProductListItemAddedToCart(Consumer<ProductListEventParam> onProductListItemAddedToCart) {
        this.onProductListItemAddedToCart = onProductListItemAddedToCart;
    }
}
