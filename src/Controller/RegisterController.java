package Controller;

import javafx.stage.Stage;
import Model.User;
import Service.UserService;
import View.LoginView;
import View.RegisterView;

public class RegisterController {
    private UserService userService;
    private RegisterView registerView;
    private Stage currentStage; 

    public RegisterController(UserService userService, RegisterView registerView,Stage currentStage) {
        this.userService = userService;
        this.registerView = registerView;
        this.currentStage = currentStage;
        setupRegisterAction();
        setupGetLoginHyperlink();
    }

    private void setupRegisterAction() {
        registerView.getRegisterButton().setOnAction(e -> handleRegister());
    }
    
    private void setupGetLoginHyperlink() {
    	registerView.getLoginLink().setOnAction(event -> {
            if (currentStage != null) {  
                currentStage.close();  
            }
    		showLoginScene();
    		System.out.println("Pindah ke login Scene...");
    	});
    }

    private void handleRegister() {
        String username = registerView.getUsernameField().getText();
        String password = registerView.getPasswordField().getText();
        String phoneNumber = registerView.getPhoneNumberField().getText();
        String address = registerView.getAddressField().getText();
        String role = registerView.getRoleComboBox().getValue();  

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
    
    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginStage.setScene(loginView.createLoginScene(loginStage));
        loginStage.show();
    }
}
