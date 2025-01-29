package ec.edu.monster.phoneshop.enums;

import javafx.scene.control.Alert;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageBoxType {
    ERROR(Alert.AlertType.ERROR),
    WARNING(Alert.AlertType.WARNING),
    INFORMATION(Alert.AlertType.INFORMATION);

    private final Alert.AlertType alertType;
}
