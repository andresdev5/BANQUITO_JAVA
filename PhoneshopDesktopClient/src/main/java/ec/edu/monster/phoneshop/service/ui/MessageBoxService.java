package ec.edu.monster.phoneshop.service.ui;

import ec.edu.monster.phoneshop.enums.MessageBoxType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.Builder;
import lombok.Data;

public class MessageBoxService {
    private static MessageBoxService instance;

    private MessageBoxService() {}

    public static MessageBoxService getInstance() {
        if (instance == null) {
            instance = new MessageBoxService();
        }

        return instance;
    }

    public void show(MessageBoxType type, String title, String message) {
        Alert alert = new Alert(type.getAlertType());
        ButtonType acceptButton = new ButtonType("Aceptar");

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(acceptButton);
        alert.showAndWait();
    }

    public void error(String message) {
        show(MessageBoxType.ERROR, "Error", message);
    }

    public void info(String message) {
        show(MessageBoxType.INFORMATION, "Información", message);
    }

    public boolean confirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType acceptButton = new ButtonType("Aceptar");
        ButtonType cancelButton = new ButtonType("Cancelar");

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(acceptButton, cancelButton);

        return alert.showAndWait().map(response -> response == acceptButton).orElse(false);
    }
}
