package ec.edu.monster.phoneshop;

import ec.edu.monster.phoneshop.dto.ApiCommunicationType;
import ec.edu.monster.phoneshop.dto.LoginRequestDto;
import ec.edu.monster.phoneshop.service.AuthService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static Stage stage;
    private final AuthService authService = new AuthService();

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.setTitle("PhoneShop");
        stage.requestFocus();
    }

    public static void setRoot(String fxml) {
        try {
            Parent root = loadFXML(fxml);
            scene.setRoot(root);
            stage.sizeToScene();
            scene.getWindow().centerOnScreen();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}