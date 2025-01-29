package ec.edu.monster.phoneshop.service.ui;

import javafx.scene.control.Label;

import java.util.logging.Logger;

public class ShoppingCartPopupService {
    private static ShoppingCartPopupService instance;
    private final Logger logger = Logger.getLogger(ShoppingCartPopupService.class.getName());
    private Label counterLabel;
    private int counter = 0;

    public static ShoppingCartPopupService getInstance() {
        if (instance == null) {
            instance = new ShoppingCartPopupService();
        }
        return instance;
    }

    public void setUIComponent(Label counterLabel) {
        this.counterLabel = counterLabel;
        this.counterLabel.setVisible(false);
    }

    public void setValue(int value) {
        counter = Math.max(value, 0);
        updateUI();
    }

    public void increment() {
        setValue(counter + 1);
    }

    public void decrement() {
        setValue(counter - 1);
    }

    private void updateUI() {
        if (counter <= 0) {
            counterLabel.setVisible(false);
            return;
        }

        String text = String.valueOf(counter);
        counterLabel.setText(text);
        counterLabel.setVisible(true);
    }

    private ShoppingCartPopupService() {}
}
