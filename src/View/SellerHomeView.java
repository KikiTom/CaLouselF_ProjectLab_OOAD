
package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SellerHomeView {
    private Label welcomeLabel;
    private Label nameLabel;
    private Label addressLabel;
    private Label phoneLabel;
    
    private Image profileImage;
    private ImageView profileImageView;
    
    private Button uploadItemButton;
    private Button viewOfferedItemButton;
    private Button viewItemButton;
    private Button logoutButton;

    private VBox menuOptionsBox;

    public SellerHomeView(String name, String address, String phone) {
        // Welcome Label
        welcomeLabel = new Label("Seller Dashboard");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeLabel.setTextFill(Color.DARKBLUE);

        // Profile Details Labels
        nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", 16));
        nameLabel.setTextFill(Color.GRAY);

        addressLabel = new Label(address);
        addressLabel.setFont(Font.font("Arial", 14));
        addressLabel.setTextFill(Color.DARKGRAY);

        phoneLabel = new Label(phone);
        phoneLabel.setFont(Font.font("Arial", 14));
        phoneLabel.setTextFill(Color.DARKGRAY); 
        
        // Profile Image
        profileImage = new Image("file:resources/Seller_Image_Profile.png"); 
        
        profileImageView = new ImageView();
        profileImageView.setImage(profileImage);
        profileImageView.setFitWidth(100);  
        profileImageView.setFitHeight(100);  
        profileImageView.setPreserveRatio(true);
        
        // Create circular clip for profile image
        Circle clip = new Circle(50, 50, 50);
        profileImageView.setClip(clip);

        // Main Menu Buttons
        uploadItemButton = createStyledButton("Upload Item");
        viewOfferedItemButton = createStyledButton("View Offered Items");
        viewItemButton = createStyledButton("View Items");

        // Logout Button
        logoutButton = new Button("Logout");
        logoutButton.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;"
        );

        // Menu Options Box (initially hidden)
        menuOptionsBox = new VBox(10);
        menuOptionsBox.setAlignment(Pos.CENTER);
        menuOptionsBox.setVisible(false);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-size: 14px;" +
            "-fx-max-width: 250px;"
        );
        
        // Hover effects
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #2980b9;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-size: 14px;" +
            "-fx-max-width: 250px;"
        ));
        
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-size: 14px;" +
            "-fx-max-width: 250px;"
        ));
        
        return button;
    }
         

    public Scene createSellerHomeScene(Stage primaryStage) {
        // Main Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");

        // Profile Section
        VBox profileSection = new VBox(10);
        profileSection.setAlignment(Pos.CENTER);
        profileSection.getChildren().addAll(
            profileImageView,
            welcomeLabel,
            nameLabel,
            addressLabel,
            phoneLabel
        );

        // Main Menu Buttons Section
        VBox mainMenuSection = new VBox(15);
        mainMenuSection.setAlignment(Pos.CENTER);

        mainMenuSection.getChildren().addAll(
        	uploadItemButton,
        	viewOfferedItemButton,
        	viewItemButton
        );

        // Logout Section
        HBox logoutSection = new HBox(10);
        logoutSection.setAlignment(Pos.CENTER);
        logoutSection.getChildren().add(logoutButton);

        // Add Menu Options Box
        mainMenuSection.getChildren().add(menuOptionsBox);

        // Add all sections to main layout
        mainLayout.getChildren().addAll(
            profileSection,
            mainMenuSection,
            logoutSection
        );

        // Create scene
        Scene scene = new Scene(mainLayout, 400, 700);
        primaryStage.setResizable(false);
        return scene;
    }

    // Getters for buttons
    public Button getLogoutButton() {
        return logoutButton;
    }
}

