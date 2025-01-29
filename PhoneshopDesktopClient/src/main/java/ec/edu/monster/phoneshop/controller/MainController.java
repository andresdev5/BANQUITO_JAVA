package ec.edu.monster.phoneshop.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import ec.edu.monster.phoneshop.App;
import ec.edu.monster.phoneshop.common.ApplicationContext;
import ec.edu.monster.phoneshop.dto.*;
import ec.edu.monster.phoneshop.enums.SnackBarMessageType;
import ec.edu.monster.phoneshop.service.AuthService;
import ec.edu.monster.phoneshop.service.MainService;
import ec.edu.monster.phoneshop.service.RouterService;
import ec.edu.monster.phoneshop.service.ui.ShoppingCartPopupService;
import ec.edu.monster.phoneshop.service.ui.SnackBarService;
import ec.edu.monster.phoneshop.service.ui.UserMenuService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import okhttp3.Route;

/**
 *
 * @author Andres
 */
public class MainController implements Initializable {
    private final Logger logger = Logger.getLogger(MainController.class.getName());
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();
    private final AuthService authService = new AuthService();

    @FXML
    Label usernameLabel;

    @FXML
    Label shoppingCartCountLabel;

    @FXML
    Button logoutButton;

    @FXML
    VBox snackBarWrapper;

    @FXML
    TextFlow snackBarContainer;

    @FXML
    StackPane viewContainer;

    @FXML
    ImageView shoppingCartIconImage;

    @FXML
    Label logoLabel;

    public MainController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene scene = App.getScene();

        scene.getWindow().setWidth(1200);
        scene.getWindow().setHeight(650);

        SnackBarService.getInstance().setUIComponents(snackBarWrapper, snackBarContainer);
        ShoppingCartPopupService.getInstance().setUIComponent(shoppingCartCountLabel);
        UserMenuService.getInstance().setUIComponent(usernameLabel);
        UserMenuService.getInstance().setUser(applicationContext.getAuthenticatedUser());

        RouterService.getInstance().setViewContainer(viewContainer);
        RouterService.getInstance().setScene(scene);

        logoutButton.setOnAction(event -> {
            authService.logout();
            Platform.runLater(() -> App.setRoot("login"));
        });

        shoppingCartIconImage.setOnMouseClicked(event -> {
            RouterService.getInstance().navigate("checkout");
        });

        logoLabel.setOnMouseClicked(event -> {
            RouterService.getInstance().navigate("products");
        });

        RouterService.getInstance().navigate("products");
    }
}
