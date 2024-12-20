package Controller;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import Repository.Database;
import Repository.ItemRepository;
import Service.ItemService;
import Service.UserService;
import View.AdminHomeView;
import View.CustomerHomeView;
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
        	System.out.println("Pindah ke Register Scene...");
        	showRegisterScene();
        });
    }

    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();
        
        if (username.contains("admin")) {
        	if(password.contains("admin")) {
        		popupView.getInstance().showSuccessPopup("Login Success","Welcome, ADMIN");
            	closeloginScene();
            	showAdminpageScene();
        	}else {
        		popupView.getInstance().showErrorPopup("Login Failed", "Invalid username or password.");
        	}   	
        }else if (userService.loginUser(username, password)) {
        	popupView.getInstance().showSuccessPopup("Login Success","Welcome, " + userService.getUserName(username));
        	System.out.println("Pindah ke Home Scene...");
        	closeloginScene();
        	showHomepageScene(userService.getUserName(username));
        } else {
        	popupView.getInstance().showErrorPopup("Login Failed", "Invalid username or password.");
        }
    }

    public void showLoginScene(Stage primaryStage) {
        primaryStage.setScene(loginView.createLoginScene());
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
    	registerController.showRegisterScene(registerStage);
    }
    
    private void showAdminpageScene() {
    	AdminHomeView adminHomeView = new AdminHomeView("Admin");
    	Stage adminStage = new Stage();
    	AdminHomeController adminhomecontroller = new AdminHomeController(adminStage, adminHomeView, userService);
    	adminhomecontroller.showAdminHomeScene();
    }
    
    private void showHomepageScene(String username) {  
        // Get user role from UserService  
        String userRole = userService.getUserRole(username);  
        
        
        // Navigate based on user role  
        if ("Seller".equalsIgnoreCase(userRole)) {  
        	Stage sellerHomeStage = new Stage();
        	SellerHomeView sellerHomeView = new SellerHomeView(sellerHomeStage, username); 
            SellerHomeController sellerHomeController = new SellerHomeController(userService, sellerHomeStage, username);  
            sellerHomeController.showSellerHomeScene(sellerHomeStage);
        } else if ("Customer".equalsIgnoreCase(userRole)) {  
            // Default Home Page (for other roles or customers) 
        	ItemRepository itemRepository = new ItemRepository(Database.getInstance());
        	ItemService itemService = new ItemService(itemRepository, userService);  
            CustomerHomeView homeView = new CustomerHomeView(username, itemService);  
            Stage customerHomeStage = new Stage();  
            
            // Buat controller dan tampilkan home  
            CustomerHomeController homeController =   
                new CustomerHomeController(username, itemService, userService);  
            homeController.show(customerHomeStage);
        }  
    } 
}
