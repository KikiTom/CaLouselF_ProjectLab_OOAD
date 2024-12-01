package Controller;

import javafx.stage.Stage;
import Service.UserService;
import View.LoginView;
import View.RegisterView;
import View.popupView;

public class LoginController {
    private UserService userService;
    private LoginView loginView;
    private Stage currentStage;

    public LoginController(UserService userService, LoginView loginView, Stage currentStage) {
        this.userService = userService;
        this.loginView = loginView;
        this.currentStage = currentStage;
        setupLoginAction();
        setupregisterHyperlink();
    }

    private void setupLoginAction() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
    }
    
    private void setupregisterHyperlink() {
        loginView.getRegisterLink().setOnAction(event -> {
        	stageClose();
        	showRegisterScene();
            System.out.println("Pindah ke Register Scene...");
        });
    }
    
    private void stageClose() {
    	if (currentStage != null) {
    		currentStage.close();
    	}
    }

    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        if (userService.loginUser(username, password)) {
        	popupView.getInstance().showSuccessPopup("Login Success","Welcome," + username);
            HomeController homeController = new HomeController(currentStage, username);
            stageClose();
            homeController.showHomeScene(); 
        } else {
        	popupView.getInstance().showErrorPopup("Login Failed", "Invalid username or password.");
        }
    }

    public void showLoginScene(Stage primaryStage) {
        primaryStage.setScene(loginView.createLoginScene(primaryStage));
        primaryStage.show();
    }
    
    private void showRegisterScene() {
        // Membuat dan menampilkan RegisterView
    	RegisterView registerView = new RegisterView();  
    	Stage registerStage = new Stage();  
    	RegisterController registerController = new RegisterController(userService, registerView, registerStage);  
    	registerStage.setScene(registerView.createRegisterScene(registerStage));  
    	registerStage.show(); 
    }
}
