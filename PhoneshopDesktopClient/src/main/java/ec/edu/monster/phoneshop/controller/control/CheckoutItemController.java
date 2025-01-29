package ec.edu.monster.phoneshop.controller.control;

import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.enums.CheckoutItemEventType;
import ec.edu.monster.phoneshop.service.ShoppingCartItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Builder;
import lombok.Data;

import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CheckoutItemController implements Initializable {
    @Data
    @Builder
    public static class CheckoutItemEventParam {
        private CheckoutItemEventType type;
        private ShoppingCartItem item;
    }

    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private Consumer<CheckoutItemController.CheckoutItemEventParam> onItemRemoved;
    private Consumer<CheckoutItemController.CheckoutItemEventParam> onQuantityUpdated;
    private ShoppingCartItem data;

    @FXML
    ImageView productImageView;

    @FXML
    Label productNameLabel;

    @FXML
    Label unitPriceLabel;

    @FXML
    Button quantityMinusButton;

    @FXML
    TextField quantityInput;

    @FXML
    Button quantityPlusButton;

    @FXML
    Button removeButton;

    @FXML
    Label totalPriceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeButton.setOnAction(event -> {
            if (onItemRemoved != null) {
                onItemRemoved.accept(CheckoutItemEventParam.builder()
                        .type(CheckoutItemEventType.REMOVED)
                        .item(data)
                        .build());
            }
        });

        quantityMinusButton.setOnAction(event -> {
            if (onQuantityUpdated != null) {
                onQuantityUpdated.accept(CheckoutItemEventParam.builder()
                        .type(CheckoutItemEventType.DECREASED)
                        .item(data)
                        .build());
            }
        });

        quantityPlusButton.setOnAction(event -> {
            if (onQuantityUpdated != null) {
                onQuantityUpdated.accept(CheckoutItemEventParam.builder()
                        .type(CheckoutItemEventType.INCREASED)
                        .item(data)
                        .build());
            }
        });
    }

    public void setData(ShoppingCartItem item) {
        data = item;

        unitPriceLabel.setText(String.format("$%s", item.getProduct().getPrice().setScale(2, RoundingMode.HALF_UP)));
        totalPriceLabel.setText(String.format("$%s", item.getTotal().setScale(2, RoundingMode.HALF_UP)));
        productNameLabel.setText(item.getProduct().getName());
        quantityInput.setText(String.valueOf(item.getQuantity()));

        if (item.getProduct().getImagePath() != null) {
            String baseUrl = applicationContext.getServerApiUrl();
            String imagePath = item.getProduct().getImagePath();
            productImageView.setImage(new javafx.scene.image.Image(baseUrl + imagePath));
        }
    }

    public void onCheckoutItemRemoved(Consumer<CheckoutItemEventParam> callback) {
        this.onItemRemoved = callback;
    }

    public void onCheckoutItemQuantityUpdated(Consumer<CheckoutItemEventParam> callback) {
        this.onQuantityUpdated = callback;
    }
}
