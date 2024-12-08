package View;  

import javafx.geometry.Insets;  
import javafx.geometry.Pos;  
import javafx.scene.control.Button;  
import javafx.scene.control.Label;  
import javafx.scene.image.Image;  
import javafx.scene.image.ImageView;  
import javafx.scene.layout.Priority;  
import javafx.scene.layout.Region;  
import javafx.scene.layout.VBox;  
import javafx.scene.paint.Color;  
import javafx.scene.shape.Circle;  

public class AdminComponentView {  
    private Button logoutButton;  

    public Button getLogoutButton() {  
        if (logoutButton == null) {  
            System.err.println("Logout button is not initialized!");  
        }
        System.err.println(logoutButton); 
        return logoutButton;  
    }
    

    
    public VBox getSidebar(String username) {  
        VBox sidebar = createSidebar(username);  

        // Ensure logoutButton is initialized  
        logoutButton = createLogoutButton();  

        // Add logoutButton to sidebar  
        sidebar.getChildren().add(logoutButton);  
        VBox.setMargin(logoutButton, new Insets(0, 0, 20, 0));  
        return sidebar;  
    }  

    private VBox createSidebar(String username) {  
        VBox sidebar = new VBox(20);  
//        sidebar.setStyle("-fx-background-color: #2C3E50;");
         
     // Pilihan 1: Hijau Profesional Gelap  
        //sidebar.setStyle("-fx-background-color: #0A5132;");  
        
        // Pilihan 2: Hijau Emerald Kelembutan  
         //sidebar.setStyle("-fx-background-color: #0B6623;");  
        
        // Pilihan 3: Hijau Pinus Elegan  
         //sidebar.setStyle("-fx-background-color: #01442D;");  
        
        // Pilihan 4: Hijau Hutan Profesional  
        // sidebar.setStyle("-fx-background-color: #0E3B2D;");  
        
        // Pilihan 5: Hijau Gelap Sophisticated  
         sidebar.setStyle("-fx-background-color: #1B4332;");
        
        sidebar.setAlignment(Pos.CENTER);  // Ubah ke CENTER  
        sidebar.setPrefWidth(250);  
        sidebar.setPrefHeight(700);  // Tambahkan tinggi tetap  
        
        // Spacer di atas  
        Region topSpacer = new Region();  
        VBox.setVgrow(topSpacer, Priority.ALWAYS);  

        // Profile Image  
        ImageView profileImage = createProfileImage();  

        // Profile Name  
        Label profileName = createProfileNameLabel(username);  

        // Spacer di bawah  
        Region bottomSpacer = new Region();  
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);  

        // Tambahkan spacer di atas dan bawah untuk menempatkan konten di tengah  
        sidebar.getChildren().addAll(  
            topSpacer,  
            profileImage,   
            profileName,   
            bottomSpacer 
        );  

        return sidebar;  
    }   

    private ImageView createProfileImage() {  
        Image profileimg = new Image("file:resources/Admin_Image_Profile.png");  
        ImageView profileImage = new ImageView(profileimg);  
        profileImage.setFitHeight(150);  // Sedikit lebih besar  
        profileImage.setFitWidth(150);  
        profileImage.setPreserveRatio(true);  

        // Add circular clip to profile image  
        Circle clip = new Circle(75, 75, 75);  
        profileImage.setClip(clip);  

        return profileImage;  
    }  

    private Label createProfileNameLabel(String username) {  
        Label profileName = new Label(username);  
        profileName.setTextFill(Color.WHITE);  
        profileName.setStyle(  
            "-fx-font-size: 20px;" +   
            "-fx-font-weight: bold;" +   
            "-fx-text-alignment: center;"   
        );  
        profileName.setAlignment(Pos.CENTER);   
        profileName.setPrefWidth(250);  // Sesuaikan dengan lebar sidebar  
        return profileName;  
    }  

    private Button createLogoutButton() {  
        Button logoutBtn = new Button("Logout");  
        logoutBtn.setPrefWidth(220);  
        logoutBtn.setPrefHeight(50);  
        logoutBtn.setAlignment(Pos.CENTER);  
        
        Image logoutIcon = new Image("file:resources/logout.png");  
        ImageView icon = new ImageView(logoutIcon);  
        icon.setFitHeight(25);  
        icon.setFitWidth(25);  

        logoutBtn.setGraphic(icon);  
        logoutBtn.setGraphicTextGap(15);  

        // Sesuaikan warna dengan sidebar hijau tua  
        logoutBtn.setStyle(getButtonStyle("#0E2D1F", false));  

        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle(getButtonStyle("#0A5132", true)));  
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle(getButtonStyle("#0E2D1F", false)));  

        return logoutBtn;  
    }  

    private String getButtonStyle(String backgroundColor, boolean isActive) {  
        return String.format(  
                "-fx-background-color: %s;" +   
                "-fx-text-fill: white;" +   
                "-fx-font-size: 14px;" +  
                "-fx-background-radius: 10px;" +  
                "-fx-border-color: rgba(255,255,255,0.2);" + // Tambahkan border tipis  
                "-fx-border-radius: 10px;" +   
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,%s), %s, 0, 0, 0);",  
                backgroundColor,   
                isActive ? "0.3" : "0.1",   
                isActive ? "5" : "3");  
    }    
}