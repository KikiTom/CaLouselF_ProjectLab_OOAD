package Controller;  

import javafx.stage.Stage;
import Service.UserService;
import View.HomeView;  
import View.LoginView;
import View.popupView;  

public class HomeController { 
	private UserService userService;
    private HomeView homeView;  
    private Stage currentStage;  
    private String username;  

    public HomeController(UserService userService,Stage currentStage, HomeView homeView, String username) {  
        this.currentStage = currentStage;  
        this.username = username;
        this.userService = userService;
        this.homeView = homeView;  
        
        setupMenuButtons();  
        setupLogoutButton();  
    }  

    private void setupMenuButtons() {  
        homeView.getMenuButton1().setOnAction(e -> {  
            // Implementasi Menu 1  
            System.out.println("Menu 1 clicked");  
        });  

        homeView.getMenuButton2().setOnAction(e -> {  
            // Implementasi Menu 2  
            System.out.println("Menu 2 clicked");  
        });  

        homeView.getMenuButton3().setOnAction(e -> {  
            // Implementasi Menu 3  
            System.out.println("Menu 3 clicked");  
        });  
    }  

    private void setupLogoutButton() {  
        homeView.getLogoutButton().setOnAction(e -> {  
        	if(popupView.getInstance().showConfirmationPopup("Logout", "Are you sure want Logout?")) {
        		closeHomeScene();
        		showLoginScene();
        	}
        });  
    }
    
    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginStage.setScene(loginView.createLoginScene());
        loginStage.showAndWait();
    }

    public void showHomeScene(Stage Primarystage) {  
    	Primarystage.setScene(homeView.createHomeScene(Primarystage));  
    	Primarystage.showAndWait();  
    }  
    
    private void closeHomeScene() {
        if (currentStage != null) {  
            currentStage.close();  
        }
    }
}