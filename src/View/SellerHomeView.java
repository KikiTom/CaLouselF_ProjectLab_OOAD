
package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SellerHomeView {
    // Modernized color palette
    private static final String PRIMARY_COLOR = "#4A90E2";      // Vibrant Blue
    private static final String SECONDARY_COLOR = "#34495E";    // Dark Blue-Gray
    private static final String ACCENT_COLOR = "#2ECC71";       // Fresh Green
    private static final String BACKGROUND_COLOR = "#F7F9FC";   // Light Blue-White
    private static final String TEXT_COLOR = "#2C3E50";         // Dark Charcoal

    private Label welcomeLabel;
    private Label nameLabel;
    private Label statsItemsLabel;
    private Label statsSalesLabel;
    private Label statsRevenueLabel;
    private Label addressLabel;
    private Label phoneLabel;

    private Image profileImage;
    private ImageView profileImageView;

    private Button uploadItemButton;
    private Button viewOfferedItemButton;
    private Button viewItemButton;
    private Button analyticsButton;
    private Button logoutButton;
    
    private VBox statsItemsBox;  
    private VBox statsSalesBox;  
    private VBox statsRevenueBox;

    public SellerHomeView(String name, String address, String phone) {  
        // Dummy data generation for seller statistics  
        String totalItems = generateDummyItemCount();  
        String totalSales = generateDummyTotalSales();  
        String totalRevenue = generateDummyRevenue();  

        // Welcome Label  
        welcomeLabel = new Label("Seller Dashboard");  
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));  
        welcomeLabel.setTextFill(Color.web(TEXT_COLOR));  

        // Profile Details Labels  
        nameLabel = new Label(name);  
        nameLabel.setFont(Font.font("Segoe UI", 18));  
        nameLabel.setTextFill(Color.web(SECONDARY_COLOR));  

        addressLabel = new Label(address);  
        addressLabel.setFont(Font.font("Segoe UI", 14));  
        addressLabel.setTextFill(Color.web(SECONDARY_COLOR));  

        phoneLabel = new Label(phone);  
        phoneLabel.setFont(Font.font("Segoe UI", 14));  
        phoneLabel.setTextFill(Color.web(SECONDARY_COLOR));  

        statsItemsBox = createStatLabel("Total Items", totalItems);  
        statsSalesBox = createStatLabel("Total Sales", totalSales);  
        statsRevenueBox = createStatLabel("Total Revenue", totalRevenue);
        
        // Profile Image  
        profileImage = new Image("file:resources/Seller_Image_Profile.png");   
        
        profileImageView = new ImageView();  
        profileImageView.setImage(profileImage);  
        profileImageView.setFitWidth(120);  
        profileImageView.setFitHeight(120);  
        profileImageView.setPreserveRatio(true);  
        
        // Create circular clip for profile image  
        Circle clip = new Circle(60, 60, 60);  
        profileImageView.setClip(clip);  

        // Main Menu Buttons  
        uploadItemButton = createModernButton("Upload Item", PRIMARY_COLOR);  
        viewOfferedItemButton = createModernButton("View Offered Items", SECONDARY_COLOR);  
        viewItemButton = createModernButton("View Items", ACCENT_COLOR);  
        analyticsButton = createModernButton("Sales Analytics", "#8E44AD");  
        
        // Logout Button  
        logoutButton = createLogoutButton();  
    }  
    
    private VBox createStatLabel(String title, String value) {  
        // Create main container  
        VBox statBox = new VBox(5);  
        statBox.setAlignment(Pos.CENTER);  
        statBox.setStyle(  
            "-fx-background-color: white;" +  
            "-fx-background-radius: 10;" +  
            "-fx-padding: 10px;" +  
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 1);"  
        );  

        // Title Label  
        Label titleLabel = new Label(title);  
        titleLabel.setFont(Font.font("Segoe UI", 12));  
        titleLabel.setTextFill(Color.web(SECONDARY_COLOR));  
        titleLabel.setStyle("-fx-opacity: 0.7;");  

        // Value Label  
        Label valueLabel = new Label(value);  
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));  
        valueLabel.setTextFill(Color.web(PRIMARY_COLOR));  

        // Add labels to container  
        statBox.getChildren().addAll(titleLabel, valueLabel);  

        return statBox;  
    }  

    // Helper methods to generate dummy data  
    private String generateDummyItemCount() {  
        // Simulate a reasonable range of items for a seller  
        return String.valueOf(new java.util.Random().nextInt(50) + 10);  
    }  

    private String generateDummyTotalSales() {  
        // Generate random sales count between 10 and 500  
        int sales = new java.util.Random().nextInt(491) + 10;  
        return String.valueOf(sales);  
    }  

    private String generateDummyRevenue() {  
        // Generate random revenue between \$500 and \$10,000  
        double revenue = 500 + (new java.util.Random().nextDouble() * 9500);  
        return String.format("$%.2f", revenue);  
    }  

    private Button createModernButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + backgroundColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 2);" +
            "-fx-padding: 12px 24px;" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-max-width: 250px;"
        );
        
        // Smoother hover and click effects
        button.setOnMouseEntered(e -> button.setStyle(
            button.getStyle() + 
            "-fx-background-color: derive(" + backgroundColor + ", -10%);"
        ));
        
        button.setOnMouseExited(e -> button.setStyle(
            button.getStyle().replaceAll("-fx-background-color: derive\\([^)]+\\);", 
            "-fx-background-color: " + backgroundColor + ";")
        ));
        
        return button;
    }

    private Button createLogoutButton() {
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
            "-fx-background-color: #E74C3C;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-size: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);"
        );
        return logoutBtn;
    }

    public Scene createSellerHomeScene(Stage primaryStage) {  
        VBox mainLayout = new VBox(20);  
        mainLayout.setAlignment(Pos.CENTER);  
        mainLayout.setPadding(new Insets(40));  
        mainLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");  

        // Profile Section with Statistics  
        VBox profileSection = new VBox(15);  
        profileSection.setAlignment(Pos.CENTER);  
        
        HBox statsBox = new HBox(30);  
        statsBox.setAlignment(Pos.CENTER);  
        statsBox.getChildren().addAll(  
            createStatLabel("Total Items", generateDummyItemCount()),  
            createStatLabel("Total Sales", generateDummyTotalSales()),  
            createStatLabel("Total Revenue", generateDummyRevenue())  
        );
        
        

        profileSection.getChildren().addAll(  
            profileImageView,  
            welcomeLabel,  
            nameLabel,  
            statsBox  
        ); 

        // Buttons Section
        VBox buttonSection = new VBox(15);
        buttonSection.setAlignment(Pos.CENTER);
        buttonSection.getChildren().addAll(
            uploadItemButton,
            viewOfferedItemButton,
            viewItemButton,
            analyticsButton
        );

        // Logout Section
        HBox logoutSection = new HBox(10);
        logoutSection.setAlignment(Pos.CENTER);
        logoutSection.getChildren().add(logoutButton);

        mainLayout.getChildren().addAll(
            profileSection,
            buttonSection,
            logoutSection
        );

        Scene scene = new Scene(mainLayout, 450, 700);
        primaryStage.setResizable(false);
        return scene;
    }

    // Getters for buttons
    public Button getLogoutButton() { return logoutButton; }
    
    public VBox getStatsItemsBox() {  
        return statsItemsBox;  
    }  

    public VBox getStatsSalesBox() {  
        return statsSalesBox;  
    }  

    public VBox getStatsRevenueBox() {  
        return statsRevenueBox;  
    }  
}

