package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CustomerComponentView {
    // Color Palette
    private static final String PRIMARY_COLOR = "#3498DB";     // Bright Blue  
    private static final String SECONDARY_COLOR = "#2C3E50";   // Dark Blue-Gray  
    private static final String ACCENT_COLOR = "#2ECC71";      // Bright Green  
    private static final String BACKGROUND_COLOR = "#ECF0F1";  // Light Gray-Blue  
    private static final String TEXT_COLOR = "#34495E";        // Dark Slate Blue  
    
    private HBox navbar;
    private Button[] buttons;
    private Runnable[] buttonActions;
    private Button logoutButton;

    public HBox getNavbar(String username) {
        // Main Navbar Container
        navbar = new HBox(20);  // Increased spacing
        navbar.setStyle(  
        	    "-fx-background-color: linear-gradient(to right, #2C3E50, #34495E);" + // Gradient background  
        	    "-fx-padding: 15px;" +  
        	    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);" +  // Subtle shadow  
        	    "-fx-background-radius: 10px;"  // Menambahkan radius melengkung  
        	);  
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPrefHeight(70);  // Slightly taller

        // Profile Section
        VBox profileSection = createProfileSection(username);

        // Buttons Section
        HBox buttonsSection = createButtonsSection();

        // Logout Button
        logoutButton = createLogoutButton();

        // Add all sections to navbar
        navbar.getChildren().addAll(profileSection, buttonsSection, logoutButton);
        HBox.setHgrow(buttonsSection, Priority.ALWAYS);

        return navbar;
    }

    private VBox createProfileSection(String username) {
        VBox profileSection = new VBox(8);  // Increased spacing
        profileSection.setAlignment(Pos.CENTER);

        // Profile Image with circular clip
        ImageView profileImage = new ImageView();
        profileImage.setFitWidth(50);  // Increased size
        profileImage.setFitHeight(50);
        profileImage.setPreserveRatio(true);
        
        // Create circular clip
        Circle clip = new Circle(25, 25, 25);
        profileImage.setClip(clip);
        
        try {
            // Use fixed path for profile image
            profileImage.setImage(new Image("file:resources/Customer_Image_Profile.png"));
        } catch (Exception e) {
            System.out.println("Could not load profile image: " + e.getMessage());
        }

        // Username Label with improved styling
        Label usernameLabel = new Label("Hi, " + username);
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        profileSection.getChildren().addAll(profileImage, usernameLabel);
        return profileSection;
    }
    
    private ImageView createButtonIcon(String iconPath) {  
        try {  
            Image icon = new Image(iconPath);  
            ImageView imageView = new ImageView(icon);  
            imageView.setFitWidth(22);  // Slightly larger  
            imageView.setFitHeight(22);  
            imageView.setPreserveRatio(true);  
            return imageView;  
        } catch (Exception e) {  
            System.out.println("Could not load icon: " + iconPath);  
            return null;  
        }  
    }  

    private HBox createButtonsSection() {  
        HBox buttonsSection = new HBox(15);  // Increased spacing
        buttonsSection.setAlignment(Pos.CENTER);  

        // Define button titles and icon paths  
        String[][] buttonData = {   
            {"Purchase History", "file:resources/transaction-history.png"},  
            {"Wishlist", "file:resources/wishlist.png"}  
        };  
        buttons = new Button[buttonData.length];  
        buttonActions = new Runnable[buttonData.length];  

        // Create buttons with hover effects and icons  
        for (int i = 0; i < buttonData.length; i++) {  
            Button btn = new Button(buttonData[i][0]);  
            
            // Add icon to button  
            ImageView icon = createButtonIcon(buttonData[i][1]);  
            if (icon != null) {  
                btn.setGraphic(icon);  
            }  

            // Default button style  
            btn.setStyle(  
                "-fx-background-color: transparent;" +  
                "-fx-text-fill: white;" +  
                "-fx-font-size: 14px;" +  
                "-fx-padding: 10 15 10 15;" +  
                "-fx-graphic-text-gap: 10;" + 
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-weight: bold;"
            );  

            // Hover effects  
            btn.setOnMouseEntered(e -> {  
                btn.setStyle(  
                    "-fx-background-color: rgba(255, 255, 255, 0.2);" + // Slight white overlay  
                    "-fx-text-fill: #3498DB;" + // Bright blue on hover  
                    "-fx-font-size: 14px;" +  
                    "-fx-padding: 10 15 10 15;" +  
                    "-fx-background-radius: 5px;" +  
                    "-fx-graphic-text-gap: 10;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-font-weight: bold;"
                );  
            });  

            btn.setOnMouseExited(e -> {  
                btn.setStyle(  
                    "-fx-background-color: transparent;" +  
                    "-fx-text-fill: white;" +  
                    "-fx-font-size: 14px;" +  
                    "-fx-padding: 10 15 10 15;" +  
                    "-fx-graphic-text-gap: 10;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-font-weight: bold;"
                );  
            });  

            buttons[i] = btn;  
            buttonsSection.getChildren().add(btn);  
        }  

        return buttonsSection;  
    }  

    private Button createLogoutButton() {  
        Button logoutBtn = new Button("Logout");  
        
        // Add logout icon  
        ImageView logoutIcon = createButtonIcon("file:resources/logout.png");  
        if (logoutIcon != null) {  
            logoutBtn.setGraphic(logoutIcon);  
        }  

        logoutBtn.setStyle(  
            "-fx-background-color: #E74C3C;" + // Red color  
            "-fx-text-fill: white;" +  
            "-fx-font-size: 12px;" +  
            "-fx-padding: 10 15 10 15;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-graphic-text-gap: 10;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-weight: bold;"
        );  

        // Hover effects for logout button  
        logoutBtn.setOnMouseEntered(e -> {  
            logoutBtn.setStyle(  
                "-fx-background-color: #C0392B;" + // Darker red on hover  
                "-fx-text-fill: white;" +  
                "-fx-font-size: 12px;" +  
                "-fx-padding: 10 15 10 15;" +  
                "-fx-background-radius: 5px;" +  
                "-fx-graphic-text-gap: 10;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-weight: bold;"
            );  
        });  

        logoutBtn.setOnMouseExited(e -> {  
            logoutBtn.setStyle(  
                "-fx-background-color: #E74C3C;" +  
                "-fx-text-fill: white;" +  
                "-fx-font-size: 12px;" +  
                "-fx-padding: 10 15 10 15;" +  
                "-fx-background-radius: 5px;" +  
                "-fx-graphic-text-gap: 10;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-weight: bold;"
            );  
        });  

        return logoutBtn;  
    }  

    // Method to set button actions
    public void setButtonAction(int buttonIndex, Runnable action) {
        if (buttonIndex >= 0 && buttonIndex < buttonActions.length) {
            buttons[buttonIndex].setOnAction(e -> action.run());
        }
    }

    // Getter for logout button
    public Button getLogoutButton() {
        return logoutButton;
    }
}