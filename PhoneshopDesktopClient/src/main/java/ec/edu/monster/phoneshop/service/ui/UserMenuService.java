package ec.edu.monster.phoneshop.service.ui;

import ec.edu.monster.phoneshop.dto.UserDto;
import javafx.scene.control.Label;

import java.util.logging.Logger;

public class UserMenuService {
    private final Logger logger = Logger.getLogger(UserMenuService.class.getName());
    private static UserMenuService instance;
    private Label usernameLabel;

    private UserMenuService() {}

    public void setUIComponent(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
        this.usernameLabel.setText("");
    }

    public void setUser(UserDto user) {
        usernameLabel.setText(user.getUsername());
    }

    public static UserMenuService getInstance() {
        if (instance == null) {
            instance = new UserMenuService();
        }
        return instance;
    }
}
