package ec.edu.monster.phoneshop.service.ui;

import ec.edu.monster.phoneshop.enums.SnackBarMessageType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class SnackBarService {
    private static SnackBarService instance;
    private VBox snackBarWrapper;
    private TextFlow snackBarContainer;

    private SnackBarService() {}

    public static SnackBarService getInstance() {
        if (instance == null) {
            instance = new SnackBarService();
        }
        return instance;
    }

    public void showMessage(SnackBarMessageType type, String message, int delay, int duration) {
        snackBarContainer.getStyleClass().clear();
        snackBarContainer.getStyleClass().add("snackbar");
        snackBarContainer.getStyleClass().add(type.getCssClass());

        Text text = (Text) snackBarContainer.lookup("#snackBarMessageText");
        text.setText(message);

        if (delay > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(delay);
                    Platform.runLater(this::show);
                } catch (Exception ignored) {
                }
            }).start();
        } else {
            show();
        }

        new Thread(() -> {
            try {
                Thread.sleep(duration);
                Platform.runLater(this::hide);
            } catch (Exception ignored) {}
        }).start();
    }

    public void showMessage(SnackBarMessageType type, String message, int delay) {
        showMessage(type, message, delay, 5000);
    }

    public void showMessage(SnackBarMessageType type, String message) {
        showMessage(type, message, 0, 5000);
    }

    private void show() {
        snackBarWrapper.setVisible(true);
        snackBarContainer.setVisible(true);

        Timeline showAnimation = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(snackBarWrapper.maxHeightProperty(), 35),
                        new KeyValue(snackBarWrapper.minHeightProperty(), 35),
                        new KeyValue(snackBarContainer.maxHeightProperty(), 35),
                        new KeyValue(snackBarContainer.minHeightProperty(), 35)
                )
        );
        showAnimation.play();
    }

    private void hide() {
        Timeline hideAnimation = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(snackBarWrapper.maxHeightProperty(), 0),
                        new KeyValue(snackBarWrapper.minHeightProperty(), 0),
                        new KeyValue(snackBarContainer.maxHeightProperty(), 0),
                        new KeyValue(snackBarContainer.minHeightProperty(), 0)
                )
        );

        hideAnimation.setOnFinished(event -> {
            snackBarWrapper.setVisible(false);
            snackBarContainer.setVisible(false);
        });

        hideAnimation.play();
    }

    public void setUIComponents(VBox snackBarWrapper, TextFlow snackBarContainer) {
        this.snackBarWrapper = snackBarWrapper;
        this.snackBarContainer = snackBarContainer;
        hide();
    }
}
