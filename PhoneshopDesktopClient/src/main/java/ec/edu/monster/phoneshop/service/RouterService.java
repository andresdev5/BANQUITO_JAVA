package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.App;
import ec.edu.monster.phoneshop.controller.MainController;
import ec.edu.monster.phoneshop.controller.view.BaseView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouterService {
    private static RouterService instance;
    private final Logger logger = Logger.getLogger(RouterService.class.getName());

    @Getter
    @Setter
    private StackPane viewContainer;

    @Setter
    private Scene scene;

    private HashMap<String, HashMap<String, Object>> parameters = new HashMap<>();

    private RouterService() {}

    public static RouterService getInstance() {
        if (instance == null) {
            instance = new RouterService();
        }

        return instance;
    }

    public void navigate(String view, Map<String, Object> parameters) {
        setView(view, parameters);

        new Thread(() -> {
            try {
                Thread.sleep(200);
                scene.getWindow().centerOnScreen();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }).start();
    }

    public void navigate(String view) {
        navigate(view, new HashMap<>());
    }

    private void setView(String view, Map<String, Object> parameters) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(view + ".fxml"));
            Parent root = fxmlLoader.load();
            BaseView controller = fxmlLoader.getController();
            controller.preNavigate(parameters);
            viewContainer.getChildren().clear();
            viewContainer.getChildren().add(root);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
