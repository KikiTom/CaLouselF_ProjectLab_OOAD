package View;  

import javafx.geometry.Insets;  
import javafx.geometry.Pos;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.scene.control.Label;  
import javafx.scene.layout.HBox;  
import javafx.scene.layout.VBox;  
import javafx.scene.paint.Color;  
import javafx.scene.shape.Circle;  
import javafx.scene.text.Font;  
import javafx.scene.text.FontWeight;  
import javafx.stage.Stage;  

public class HomeView {  
    private Label welcomeLabel;  
    private Label usernameLabel;  
    private Button menuButton1;  
    private Button menuButton2;  
    private Button menuButton3;  
    private Button logoutButton;  

    public HomeView(String username) {  
        // Welcome Label  
        welcomeLabel = new Label("Welcome Back");  
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));  
        welcomeLabel.setTextFill(Color.DARKBLUE);  

        // Username Label  
        usernameLabel = new Label(username);  
        usernameLabel.setFont(Font.font("Arial", 16));  
        usernameLabel.setTextFill(Color.GRAY);  

        // Profile Image (Placeholder)  
        Circle profileCircle = new Circle(50);  
        profileCircle.setFill(Color.LIGHTGRAY);  

        // Menu Buttons  
        menuButton1 = createStyledButton("Menu 1");  
        menuButton2 = createStyledButton("Menu 2");  
        menuButton3 = createStyledButton("Menu 3");  

        // Logout Button  
        logoutButton = new Button("Logout");  
        logoutButton.setStyle(  
            "-fx-background-color: #e74c3c;" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5;" +  
            "-fx-padding: 10px 20px;"  
        );  
    }  

    private Button createStyledButton(String text) {  
        Button button = new Button(text);  
        button.setStyle(  
            "-fx-background-color: #3498db;" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5;" +  
            "-fx-padding: 10px 20px;" +  
            "-fx-font-size: 14px;"  
        );  
        button.setMaxWidth(Double.MAX_VALUE);  
        
        // Hover effects  
        button.setOnMouseEntered(e -> button.setStyle(  
            "-fx-background-color: #2980b9;" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5;" +  
            "-fx-padding: 10px 20px;" +  
            "-fx-font-size: 14px;"  
        ));  
        
        button.setOnMouseExited(e -> button.setStyle(  
            "-fx-background-color: #3498db;" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5;" +  
            "-fx-padding: 10px 20px;" +  
            "-fx-font-size: 14px;"  
        ));  
        
        return button;  
    }  

    public Scene createHomeScene(Stage primaryStage) {  
        // Main Layout  
        VBox mainLayout = new VBox(20);  
        mainLayout.setAlignment(Pos.CENTER);  
        mainLayout.setPadding(new Insets(40));  
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");  

        // Profile Section  
        VBox profileSection = new VBox(10);  
        profileSection.setAlignment(Pos.CENTER);  
        
        // Profile Circle  
        Circle profileCircle = new Circle(50);  
        profileCircle.setFill(Color.LIGHTGRAY);  

        profileSection.getChildren().addAll(  
            profileCircle,  
            welcomeLabel,  
            usernameLabel  
        );  

        // Menu Buttons Section  
        VBox menuSection = new VBox(15);  
        menuSection.setAlignment(Pos.CENTER);  
        menuSection.getChildren().addAll(  
            menuButton1,  
            menuButton2,  
            menuButton3  
        );  

        // Logout Section  
        HBox logoutSection = new HBox(10);  
        logoutSection.setAlignment(Pos.CENTER);  
        logoutSection.getChildren().add(logoutButton);  

        // Add all sections to main layout  
        mainLayout.getChildren().addAll(  
            profileSection,  
            menuSection,  
            logoutSection  
        );  

        // Create scene  
        Scene scene = new Scene(mainLayout, 400, 600);  
        primaryStage.setResizable(false);  
        return scene;  
    }  

    // Getters for buttons  
    public Button getMenuButton1() {  
        return menuButton1;  
    }  

    public Button getMenuButton2() {  
        return menuButton2;  
    }  

    public Button getMenuButton3() {  
        return menuButton3;  
    }  

    public Button getLogoutButton() {  
        return logoutButton;  
    }  
    
}