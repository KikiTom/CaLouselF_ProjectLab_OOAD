
package View;

import java.io.File;
import java.util.List;

import Controller.CustomerWishlistController;
import Model.WishList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class CustomerWishlistView {

	private Stage primaryStage;  
	private BorderPane mainLayout;  
	private VBox wishlistContainer;  
	private Label usernameLabel;  
	private String username;
	private CustomerWishlistController controller;

	public CustomerWishlistView(Stage primaryStage, String username, CustomerWishlistController controller) {  
        this.primaryStage = primaryStage;  
        this.username = username;  
        this.controller = controller; 
        initialize();  
    }  

	private void initialize() {  
	    // Main layout with improved background  
	    mainLayout = new BorderPane();  
	    mainLayout.setPadding(new Insets(20));  
	    mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0F4F8, #E6EAF0);"); // Soft gradient background  

	    // Header Section with enhanced styling  
	    HBox headerContainer = new HBox();  
	    headerContainer.setPadding(new Insets(0, 0, 20, 0));  
	    headerContainer.setAlignment(Pos.CENTER_LEFT);  
	    headerContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 10px;");  

	    // Wishlist title with modern typography  
	    usernameLabel = new Label(username.toUpperCase() + "'S WISHLIST");  
	    usernameLabel.setStyle(  
	        "-fx-font-size: 26px;" +  
	        "-fx-font-weight: bold;" +  
	        "-fx-text-fill: #2C3E50;" + // Deep blue-gray color  
	        "-fx-font-family: 'Segoe UI';"  
	    );  

	    // Back Button with hover effect  
	    Button backButton = new Button("Back");  
	    backButton.setStyle(  
	        "-fx-background-color: #3498DB;" + // Vibrant blue  
	        "-fx-text-fill: white;" +  
	        "-fx-background-radius: 20px;" +  
	        "-fx-padding: 8 15;"  
	    );  
	    backButton.setOnAction(e -> {
	    	primaryStage.close();
	    	controller.showhomescene();
	    });  
	    
	    // Hover and pressed effects  
	    backButton.setOnMouseEntered(e -> backButton.setStyle(  
	        "-fx-background-color: #2980B9;" + // Slightly darker blue on hover  
	        "-fx-text-fill: white;" +  
	        "-fx-background-radius: 20px;" +  
	        "-fx-padding: 8 15;"  
	    ));  
	    backButton.setOnMouseExited(e -> backButton.setStyle(  
	        "-fx-background-color: #3498DB;" +  
	        "-fx-text-fill: white;" +  
	        "-fx-background-radius: 20px;" +  
	        "-fx-padding: 8 15;"  
	    ));  

	    HBox leftHeader = new HBox(10, usernameLabel);  
	    HBox.setHgrow(leftHeader, Priority.ALWAYS);  

	    headerContainer.getChildren().addAll(leftHeader, backButton);  

	    // Wishlist Items Container with refined styling  
	    wishlistContainer = new VBox(15);  
	    wishlistContainer.setPadding(new Insets(10));  
	    wishlistContainer.setStyle(  
	        "-fx-background-color: white;" +  
	        "-fx-background-radius: 10px;" +  
	        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"  
	    );  

	    // ScrollPane with transparent scrollbar  
	    ScrollPane scrollPane = new ScrollPane(wishlistContainer);  
	    scrollPane.setFitToWidth(true);  
	    scrollPane.setStyle(  
	        "-fx-background-color: transparent;" +  
	        "-fx-background-radius: 10px;"  
	    );  
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  

	    // Add components to main layout  
	    mainLayout.setTop(headerContainer);  
	    mainLayout.setCenter(scrollPane);  

	    // Scene with soft shadow  
	    Scene scene = new Scene(mainLayout, 1200, 700);  
	    scene.setFill(Color.TRANSPARENT);  
	    primaryStage.setScene(scene);  
	}
	
	private String formatRupiah(double price) {  
	    return String.format("Rp %,.0f", price);  
	}  

	private HBox createWishlistItem(WishList wishlistItem) {  
	    HBox itemBox = new HBox(15);  
	    itemBox.setPadding(new Insets(15));  
	    itemBox.setStyle(  
	        "-fx-background-color: white;" +  
	        "-fx-background-radius: 10px;" +  
	        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);" +  
	        "-fx-border-color: #ECF0F1;" +  
	        "-fx-border-radius: 10px;"  
	    );  

	    // Item image with improved styling and loading  
	    ImageView itemImage = new ImageView(new Image(wishlistItem.getItem().getDefaultImagePathByCategory()));  
	    itemImage.setFitWidth(300);  
	    itemImage.setFitHeight(120);  
	    itemImage.setPreserveRatio(true);  
	    itemImage.setSmooth(true);  
	    itemImage.setCache(true);  

	    // Style for image container  
	    itemImage.setStyle(  
	        "-fx-background-color: #F1F3F4;" +  
	        "-fx-background-radius: 10px;"  
	    );  

	    // Item Details with refined typography  
	    VBox itemDetails = new VBox(5);  

	    // Item Name  
	    Label nameLabel = new Label(wishlistItem.getItem().getName());  
	    nameLabel.setStyle(  
	        "-fx-text-fill: #2C3E50;" +  
	        "-fx-font-size: 16px;" +  
	        "-fx-font-weight: bold;" +  
	        "-fx-font-family: 'Segoe UI Semibold';"  
	    );  

	    // Category Label  
	    Label metadataLabel = new Label("Category: " + wishlistItem.getItem().getCategory());  
	    metadataLabel.setStyle(  
	        "-fx-text-fill: #7F8C8D;" +  
	        "-fx-font-size: 12px;"  
	    );  

	    // Size Label  
	    Label releaseDateLabel = new Label("Size: " + wishlistItem.getItem().getSize());  
	    releaseDateLabel.setStyle(  
	        "-fx-text-fill: #95A5A6;" +  
	        "-fx-font-size: 12px;"  
	    );  

	    // Price Box with Rupiah formatting  
	    HBox priceBox = new HBox();  
	    priceBox.setPadding(new Insets(5, 0, 0, 0));  
	    Label priceLabel = new Label(formatRupiah(wishlistItem.getItem().getPrice()));  
	    priceLabel.setStyle(  
	        "-fx-background-color: #E8F4F8;" +  
	        "-fx-text-fill: #2980B9;" +  
	        "-fx-padding: 3 6;" +  
	        "-fx-background-radius: 15px;" +  
	        "-fx-font-weight: bold;"  
	    );  
	    priceBox.getChildren().add(priceLabel);  

	    // Remove Button with interactive styling  
	    VBox controls = new VBox();  
	    controls.setAlignment(Pos.BOTTOM_RIGHT);  
	    controls.setSpacing(10);  

	    Label removeLink = new Label("remove");  
	    removeLink.setStyle(  
	        "-fx-text-fill: #E74C3C;" +  
	        "-fx-cursor: hand;" +  
	        "-fx-font-size: 12px;"  
	    );  
	    
	    // Hover effect for remove link  
	    removeLink.setOnMouseEntered(e -> removeLink.setStyle(  
	        "-fx-text-fill: #C0392B;" +  
	        "-fx-cursor: hand;" +  
	        "-fx-font-size: 12px;" +  
	        "-fx-underline: true;"  
	    ));  
	    removeLink.setOnMouseExited(e -> removeLink.setStyle(  
	        "-fx-text-fill: #E74C3C;" +  
	        "-fx-cursor: hand;" +  
	        "-fx-font-size: 12px;"  
	    ));  

	    // Add remove functionality  
	    removeLink.setOnMouseClicked(e -> {  
	        System.out.println("remove clicked on" + wishlistItem.getItem().getName());  
	        controller.removeFromWishlist(wishlistItem);  
	    });  

	    controls.getChildren().add(removeLink);  

	    itemDetails.getChildren().addAll(nameLabel, metadataLabel, releaseDateLabel, priceBox);  

	    itemBox.getChildren().addAll(itemImage, itemDetails, controls);  
	    HBox.setHgrow(itemDetails, Priority.ALWAYS);  
	    
	    return itemBox;  
	}  
    
 // Method to update wishlist items  
    public void updateWishlistItems(List<WishList> wishlistItems) {  
        // Clear previous items  
        wishlistContainer.getChildren().clear();  
        
        // Add wishlist items  
        for (WishList wishlistItem : wishlistItems) {  
            wishlistContainer.getChildren().add(createWishlistItem(wishlistItem));  
        }  

        // Optional: Add a message if no items in wishlist  
        if (wishlistItems.isEmpty()) {  
            Label emptyWishlistLabel = new Label("Your wishlist is empty");  
            emptyWishlistLabel.setStyle(  
                "-fx-font-size: 16px;" +  
                "-fx-text-fill: #7F8C8D;" +  
                "-fx-padding: 20px;"  
            );  
            wishlistContainer.getChildren().add(emptyWishlistLabel);  
        }  
    }  

    public void show() {
        primaryStage.show();
    }
    
    private Image getDefaultPlaceholderImage() {  
        return new Image(getClass().getResourceAsStream("/images/default-placeholder.png"), 300, 120, true, true);  
    }  
}

