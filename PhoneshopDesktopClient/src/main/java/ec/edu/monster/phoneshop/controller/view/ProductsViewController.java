package ec.edu.monster.phoneshop.controller.view;

import ec.edu.monster.phoneshop.App;
import ec.edu.monster.phoneshop.controller.control.ProductListItemController;
import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.enums.SnackBarMessageType;
import ec.edu.monster.phoneshop.service.ProductService;
import ec.edu.monster.phoneshop.service.RouterService;
import ec.edu.monster.phoneshop.service.ShoppingCartService;
import ec.edu.monster.phoneshop.service.ui.MessageBoxService;
import ec.edu.monster.phoneshop.service.ui.SnackBarService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class ProductsViewController extends BaseView {
    private final Logger logger = Logger.getLogger(ProductsViewController.class.getName());
    private final ProductService productService = new ProductService();
    private final SnackBarService snackBarService = SnackBarService.getInstance();
    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();

    @FXML
    VBox productsListVBox;

    @FXML
    Button addProductButton;

    private List<ProductDto> products = new ArrayList<>();
    private final HashMap<UUID, GridPane> productListItems = new HashMap<>();

    @Override
    public void preNavigate(Map<String, Object> parameters) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductButton.setOnAction(event -> {
            goToAddProduct();
        });

        loadProducts(productService.getProducts());
    }

    public void loadProducts(List<ProductDto> products) {
        int rowNumber = 1;

        productsListVBox.getChildren().clear();
        productListItems.clear();

        for (ProductDto product : products) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("product-list-item.fxml"));
                GridPane root = fxmlLoader.load();
                ProductListItemController controller = fxmlLoader.getController();

                controller.setData(rowNumber, product);
                controller.onProductListItemDeleted(this::handleProductListItemDeleted);
                controller.onProductListItemUpdated(this::handleProductListItemUpdate);
                controller.onProductListItemAddedToCart(this::handleProductListItemAddedToCart);
                productsListVBox.getChildren().add(root);
                productListItems.put(product.getId(), root);
                rowNumber++;
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void goToAddProduct() {
        RouterService.getInstance().navigate("product-form");
    }

    private void handleProductListItemDeleted(ProductListItemController.ProductListEventParam event) {
        boolean confirmed = MessageBoxService.getInstance().confirm("Confirmación", "Esta seguro que desea eliminar el producto?");

        if (!confirmed) {
            return;
        }

        try {
            productService.deleteProduct(event.getProduct().getId());
            ShoppingCartService.getInstance().remove(event.getProduct().getId());
            SnackBarService.getInstance().showMessage(SnackBarMessageType.SUCCESS, "Producto eliminado correctamente");
            loadProducts(productService.getProducts());
        } catch (Exception ex) {
            MessageBoxService.getInstance().error(ex.getMessage());
        }
    }

    private void handleProductListItemUpdate(ProductListItemController.ProductListEventParam event) {
        RouterService.getInstance().navigate("product-form", Map.of(
            "product", event.getProduct()
        ));
    }

    private void handleProductListItemAddedToCart(ProductListItemController.ProductListEventParam event) {
        shoppingCartService.add(event.getProduct());
    }
}
