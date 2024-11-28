package Controller;

import javafx.stage.Stage;
import Model.User;
import Service.UserService;
import View.RegisterView;

public class RegisterController {
    private UserService userService;
    private RegisterView registerView;

    public RegisterController(UserService userService, RegisterView registerView) {
        this.userService = userService;
        this.registerView = registerView;
        setupRegisterAction();
    }

    private void setupRegisterAction() {
        registerView.getRegisterButton().setOnAction(e -> handleRegister());
    }

    private void handleRegister() {
        String username = registerView.getUsernameField().getText();
        String password = registerView.getPasswordField().getText();
        String phoneNumber = registerView.getPhoneNumberField().getText();
        String address = registerView.getAddressField().getText();
        String role = registerView.getRoleField().getText();

        User newUser = new User(username, password, phoneNumber, address, role);

        if (userService.registerUser(newUser)) {
            registerView.showAlert("Success", "Registration successful!");
        } else {
            registerView.showAlert("Error", "Registration failed.");
        }
    }

    public void showRegisterScene(Stage primaryStage) {
        primaryStage.setScene(registerView.createRegisterScene(primaryStage));
        primaryStage.show();
    }
}
