package ec.edu.monster.phoneshop.controller.view;

import ec.edu.monster.phoneshop.App;
import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.controller.control.CheckoutItemController;
import ec.edu.monster.phoneshop.controller.control.ProductListItemController;
import ec.edu.monster.phoneshop.dto.*;
import ec.edu.monster.phoneshop.enums.CheckoutItemEventType;
import ec.edu.monster.phoneshop.enums.SnackBarMessageType;
import ec.edu.monster.phoneshop.service.*;
import ec.edu.monster.phoneshop.service.ui.MessageBoxService;
import ec.edu.monster.phoneshop.service.ui.SnackBarService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckoutViewController extends BaseView {
    private final Logger logger = Logger.getLogger(CheckoutViewController.class.getName());
    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();
    private final PurchaseService purchaseService = new PurchaseService();
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final MainService mainService = new MainService();

    @FXML
    VBox cartItemsContainerVBox;

    @FXML
    Label subtotalLabel;

    @FXML
    Label taxAmountLabel;

    @FXML
    Label totalLabel;

    @FXML
    Button checkoutButton;

    @FXML
    ComboBox<PurchaseMethod> paymentMethodComboBox = new ComboBox<>();

    @FXML
    Spinner<Integer> totalMonthsSpinner = new Spinner<>();

    @FXML
    TextField checkCreditValueInput;

    @FXML
    Button checkCreditButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkoutButton.setOnAction(event -> {
            processPayment();
        });

        paymentMethodComboBox.getItems().addAll(PurchaseMethod.values());
        totalMonthsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        totalMonthsSpinner.getValueFactory().setValue(0);

        paymentMethodComboBox.valueProperty().addListener(new ChangeListener<PurchaseMethod>() {
            @Override
            public void changed(ObservableValue<? extends PurchaseMethod> observableValue, PurchaseMethod oldValue, PurchaseMethod newValue) {
                totalMonthsSpinner.setDisable(newValue == PurchaseMethod.CASH);
            }
        });

        paymentMethodComboBox.selectionModelProperty().get().selectFirst();

        checkCreditButton.setOnAction(event -> {
            checkCredit();
        });

        loadShoppingCartItems();
        calculateTotals();
    }

    private void loadShoppingCartItems() {
        List<ShoppingCartItem> items = shoppingCartService.getItems();
        cartItemsContainerVBox.getChildren().clear();

        for (ShoppingCartItem item : items) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("checkout-item.fxml"));
                VBox root = fxmlLoader.load();
                CheckoutItemController controller = fxmlLoader.getController();

                controller.onCheckoutItemRemoved(this::onCheckoutItemRemoved);
                controller.onCheckoutItemQuantityUpdated(this::onCheckoutItemQuantityUpdated);

                controller.setData(item);
                cartItemsContainerVBox.getChildren().add(root);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void onCheckoutItemRemoved(CheckoutItemController.CheckoutItemEventParam event) {
        shoppingCartService.remove(event.getItem().getProduct().getId());
        loadShoppingCartItems();
        calculateTotals();
    }

    private void onCheckoutItemQuantityUpdated(CheckoutItemController.CheckoutItemEventParam event) {
        if (event.getType() == CheckoutItemEventType.INCREASED) {
            shoppingCartService.add(event.getItem().getProduct());
        } else {
            shoppingCartService.remove(event.getItem().getProduct().getId(), 1);
        }

        loadShoppingCartItems();
        calculateTotals();
    }

    private void calculateTotals() {
        BigDecimal subtotal = BigDecimal.valueOf(shoppingCartService.getTotalPrice());
        BigDecimal taxAmount = subtotal.multiply(BigDecimal.valueOf(0.15));
        BigDecimal total = subtotal.add(taxAmount);

        subtotalLabel.setText(String.format("$%s", subtotal.setScale(2, RoundingMode.HALF_UP)));
        taxAmountLabel.setText(String.format("$%s", taxAmount.setScale(2, RoundingMode.HALF_UP)));
        totalLabel.setText(String.format("$%s", total.setScale(2, RoundingMode.HALF_UP)));
    }

    private void processPayment() {
        try {
            PurchaseMethod purchaseMethod = paymentMethodComboBox.getValue();
            Integer months = totalMonthsSpinner.getValue();

            if (purchaseMethod == null) {
                MessageBoxService.getInstance().error("Debe seleccionar el método de pago");
                return;
            }

            PurchaseRequestDto request = PurchaseRequestDto.builder()
                    .method(purchaseMethod)
                    .months(months)
                    .build();

            PurchaseResponseDto response = purchaseService.purchase(request);

            if (response.getStatus() == ResponseStatus.ERROR) {
                MessageBoxService.getInstance().error(response.getMessage());
            } else {
                String baseUrl = applicationContext.getServerApiUrl();
                String invoiceUrl = baseUrl + response.getInvoicePath().replace(".pdf", ".html");
                shoppingCartService.clear();
                RouterService.getInstance().navigate("payment-complete", Map.of(
                        "invoiceUrl", invoiceUrl,
                        "purchaseResponse", response
                ));
            }
        } catch (Exception ex) {
            MessageBoxService.getInstance().error(ex.getMessage());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void checkCredit() {
        Double value = null;

        try {
            if (checkCreditValueInput.getText().trim().isEmpty()) {
                throw new Exception("Invalid value");
            }

            value = Double.parseDouble(checkCreditValueInput.getText());
        } catch (Exception ex) {
            MessageBoxService.getInstance().error("Ingresa un valor correcto para verificar el crédito");
            return;
        }

        try {
            CheckCreditResultDto result = mainService.checkCredit(CheckCreditRequestDto.builder()
                    .amount(BigDecimal.valueOf(value))
                    .identificationNumber(applicationContext.getAuthenticatedUser().getIdentificationNumber())
                    .build());

            SnackBarService.getInstance().showMessage(result.isEligible() ? SnackBarMessageType.SUCCESS : SnackBarMessageType.ERROR, result.isEligible()
                ? "Dispones del crédito solicitado"
                : result.getMessage());
        } catch (Exception ex) {
            MessageBoxService.getInstance().error(ex.getMessage());
        }
    }
}
