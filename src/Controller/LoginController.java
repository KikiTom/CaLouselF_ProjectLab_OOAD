package Controller;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import Service.UserService;
import View.HomeView;
import View.LoginView;
import View.RegisterView;
import View.SellerHomeView;
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
        // Get user role from UserService  
        String userRole = userService.getUserRole(username);  
        
        // Navigate based on user role  
        if ("Seller".equalsIgnoreCase(userRole)) {  
            // Load profile image directly   
            
            SellerHomeView sellerHomeView = new SellerHomeView(  
                userService.getUserName(username),  
                userService.getUserAddress(username),  
                userService.getUserPhone(username)   
            );  
            
            Stage sellerHomeStage = new Stage();  
            SellerHomeController sellerHomeController = new SellerHomeController(userService, sellerHomeStage, username);  
            sellerHomeStage.setScene(sellerHomeView.createSellerHomeScene(sellerHomeStage));  
            sellerHomeStage.show();  
        } else {  
            // Default Home Page (for other roles or customers)  
            HomeView homeView = new HomeView(username);  
            Stage homeStage = new Stage();  
            HomeController homeController = new HomeController(userService, homeStage, username);  
            homeStage.setScene(homeView.createHomeScene(homeStage));  
            homeStage.show();  
        }  
    } 
}
