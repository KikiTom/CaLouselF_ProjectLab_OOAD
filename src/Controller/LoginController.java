package Controller;

import javafx.stage.Stage;
import Service.UserService;
import View.LoginView;

public class LoginController {
    private UserService userService;
    private LoginView loginView;

    public LoginController(UserService userService, LoginView loginView) {
        this.userService = userService;
        this.loginView = loginView;
        setupLoginAction();
    }

    private void setupLoginAction() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        if (userService.loginUser(username, password)) {
            loginView.showAlert("Success", "Login successful!");
        } else {
            loginView.showAlert("Error", "Invalid username or password.");
        }
    }

    public void showLoginScene(Stage primaryStage) {
        primaryStage.setScene(loginView.createLoginScene(primaryStage));
        primaryStage.show();
    }
}
