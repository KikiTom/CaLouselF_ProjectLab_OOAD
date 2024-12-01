package Controller;  

import javafx.stage.Stage;  
import View.HomeView;  
import View.LoginView;  

public class HomeController {  
    private HomeView homeView;  
    private Stage primaryStage;  
    private String username;  

    public HomeController(Stage primaryStage, String username) {  
        this.primaryStage = primaryStage;  
        this.username = username;  
        this.homeView = new HomeView(username);  
        
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
            // Kembali ke halaman login  
            LoginView loginView = new LoginView();  
            primaryStage.setScene(loginView.createLoginScene(primaryStage));  
        });  
    }  

    public void showHomeScene() {  
        primaryStage.setScene(homeView.createHomeScene(primaryStage));  
        primaryStage.show();  
    }  
}