package Controller;

import javafx.stage.Stage;
import Service.UserService;
import View.HomeView;
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
        	closeloginScene();
        	showRegisterScene();
            System.out.println("Pindah ke Register Scene...");
        });
    }

    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        if (userService.loginUser(username, password)) {
        	popupView.getInstance().showSuccessPopup("Login Success","Welcome," + username);
        	System.out.println("Pindah ke Home Scene...");
        	closeloginScene();
        	showHomepageScene(username);
        } else {
        	popupView.getInstance().showErrorPopup("Login Failed", "Invalid username or password.");
        }
    }

    public void showLoginScene(Stage primaryStage) {
        primaryStage.setScene(loginView.createLoginScene(primaryStage));
        primaryStage.show();
    }
    
    private void closeloginScene() {
    	if (currentStage != null) {
    		currentStage.close();
    	}
    }
    
    private void showRegisterScene() {
        // Membuat dan menampilkan RegisterView
    	RegisterView registerView = new RegisterView();  
    	Stage registerStage = new Stage();  
    	RegisterController registerController = new RegisterController(userService, registerView, registerStage);  
    	registerStage.setScene(registerView.createRegisterScene(registerStage));  
    	registerStage.show(); 
    }
    
    private void showHomepageScene(String username) {
    	HomeView homeView = new HomeView(username);
    	Stage HomeStage = new Stage();
    	HomeController homeControlller = new HomeController(userService, HomeStage, username);
    	HomeStage.setScene(homeView.createHomeScene(HomeStage));
    	HomeStage.show();
    }
}
