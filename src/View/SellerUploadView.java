package View;

import Controller.SellerUploadController;
import Model.Item;
import Service.ItemService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SellerUploadView {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private Scene sellerUploadScene;
    private BorderPane mainLayout;
    private SellerComponentView sidebarComponent;
    private VBox sidebar;
    private StackPane contentArea;
    private int userId;
    private SellerUploadController uploadController;
    private ItemService itemService;

    // Input Components
    private TextField itemNameField;
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> sizecomboBox;
    private TextField priceField;
    private Button uploadButton;
    private Label errorLabel;
    private String username;

    public SellerUploadView(Stage primaryStage, SellerComponentView sidebarComponent, int userId, 
                             SellerUploadController uploadController, ItemService itemService, String username) {
        this.primaryStage = primaryStage;
        this.sidebarComponent = sidebarComponent;
        this.userId = userId;
        this.uploadController = uploadController;
        this.itemService = itemService;
        this.username = username;
        
        initializeComponents();
        createSellerUploadScene();
    }

    private void initializeComponents() {
        // Use the passed sidebarComponent to maintain consistency
        sidebar = sidebarComponent.getSidebar(username, "Upload Item");
    }

    public Scene createSellerUploadScene() {
        mainLayout = createMainLayout();
        mainLayout.setStyle("-fx-background-color: #2C3E50;" + 
                             "-fx-background-radius: 15px;" + 
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        
        sellerUploadScene = new Scene(mainLayout, 1200, 700);
        makeDraggable(mainLayout, primaryStage);

        return sellerUploadScene;
    }

    private BorderPane createMainLayout() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: white;" + 
                             "-fx-background-radius: 15px;" + 
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Sidebar
        mainLayout.setLeft(sidebar);

        // Content Area
        contentArea = createContentArea();
        mainLayout.setCenter(contentArea);

        return mainLayout;
    }

    private StackPane createContentArea() {
        StackPane contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: white;");

        // Upload Panel
        VBox uploadPanel = createUploadPanel();
        contentArea.getChildren().add(uploadPanel);

        return contentArea;
    }

    private VBox createUploadPanel() {  
        VBox uploadPanel = new VBox(20);  
        uploadPanel.setPadding(new Insets(40));  
        uploadPanel.setStyle(  
            "-fx-background-color: linear-gradient(to right, #f5f7fa, #e9ecef);" +  
            "-fx-background-radius: 15px;"  
        );  

        // Header Section  
        HBox headerBox = new HBox();  
        headerBox.setAlignment(Pos.CENTER_LEFT);  
        
        VBox headerTextBox = new VBox(5);  
        Label titleLabel = new Label("Upload New Item");  
        titleLabel.setStyle(  
            "-fx-font-size: 28px;" +   
            "-fx-font-weight: bold;" +   
            "-fx-text-fill: #2C3E50;"  
        );  
        
        Label subtitleLabel = new Label("Fill in the details of the item you want to sell");  
        subtitleLabel.setStyle(  
            "-fx-font-size: 14px;" +   
            "-fx-text-fill: #7F8C8D;"  
        );  
        
        headerTextBox.getChildren().addAll(titleLabel, subtitleLabel);  
        headerBox.getChildren().add(headerTextBox);  

        // Form Grid  
        GridPane formGrid = createUploadForm();  
        
        // Error and Upload Button Section  
        VBox actionBox = new VBox(15);  
        actionBox.setAlignment(Pos.CENTER);  
        
        errorLabel = new Label();  
        errorLabel.setStyle("-fx-text-fill: #E74C3C; -fx-font-size: 14px;");  
        
        uploadButton = createUploadButton();  
        
        actionBox.getChildren().addAll(errorLabel, uploadButton);  

        // Combine all sections  
        uploadPanel.getChildren().addAll(headerBox, formGrid, actionBox);  
        VBox.setVgrow(formGrid, Priority.ALWAYS);  

        return uploadPanel;  
    } 

    private GridPane createUploadForm() {  
        GridPane formGrid = new GridPane();  
        formGrid.setHgap(20);  
        formGrid.setVgap(20);  
        formGrid.setPadding(new Insets(30, 0, 30, 0));  
        formGrid.setStyle("-fx-background-color: transparent;");  

        // Custom styles  
        String labelStyle =   
            "-fx-font-size: 14px;" +   
            "-fx-font-weight: bold;" +   
            "-fx-text-fill: #2C3E50;";  
        
        String inputStyle =   
            "-fx-background-color: white;" +  
            "-fx-background-radius: 8px;" +  
            "-fx-border-color: #BDC3C7;" +  
            "-fx-border-width: 1px;" +  
            "-fx-border-radius: 8px;" +  
            "-fx-padding: 10px;" +  
            "-fx-font-size: 14px;";  

        // Item Name  
        Label nameLabel = new Label("Item Name");  
        nameLabel.setStyle(labelStyle);  
        itemNameField = new TextField();  
        itemNameField.setPromptText("Enter item name");  
        itemNameField.setStyle(inputStyle);  
        
        // Category  
        Label categoryLabel = new Label("Category");  
        categoryLabel.setStyle(labelStyle);  
        categoryComboBox = new ComboBox<>();  
        categoryComboBox.getItems().addAll(  
            "Atasan", "Bawahan", "Pakaian Formal",   
            "Outerwear", "Pakaian Dalam", "Pakaian Olahraga", "Aksesoris"  
        );  
        categoryComboBox.setPromptText("Select category");  
        categoryComboBox.setStyle(inputStyle);  

        // Size  
        Label sizeLabel = new Label("Size");  
        sizeLabel.setStyle(labelStyle);  
        sizecomboBox = new ComboBox<>();  
        sizecomboBox.setPromptText("Select Size");  
        sizecomboBox.setStyle(inputStyle);  
        categoryComboBox.setOnAction(e -> {  
            String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();  
            if (selectedCategory != null) {  
                sizecomboBox.getItems().clear();  
                switch (selectedCategory) {  
                    case "Atasan":  
                    case "Bawahan":  
                    case "Pakaian Olahraga":  
                        sizecomboBox.getItems().addAll("S", "M", "L", "XL");  
                        break;  
                    case "Pakaian Formal":  
                        sizecomboBox.getItems().addAll("S", "M", "L");  
                        break;  
                    case "Outerwear":  
                        sizecomboBox.getItems().addAll("M", "L", "XL");  
                        break;  
                    case "Pakaian Dalam":  
                        sizecomboBox.getItems().addAll("S", "M", "L", "XL", "XXL");  
                        break;  
                    case "Aksesoris":  
                        sizecomboBox.getItems().addAll("One Size");  
                        break;  
                    default:  
                        sizecomboBox.getItems().clear();  
                }  
            }  
        });
        
        // Price  
        Label priceLabel = new Label("Price");  
        priceLabel.setStyle(labelStyle);  
        priceField = new TextField();  
        priceField.setPromptText("Enter price");  
        priceField.setStyle(inputStyle);  

        // Add to grid  
        formGrid.add(nameLabel, 0, 0);  
        formGrid.add(itemNameField, 1, 0);  
        formGrid.add(categoryLabel, 0, 1);  
        formGrid.add(categoryComboBox, 1, 1);  
        formGrid.add(sizeLabel, 0, 2);  
        formGrid.add(sizecomboBox, 1, 2);  
        formGrid.add(priceLabel, 0, 3);  
        formGrid.add(priceField, 1, 3);   
        

        return formGrid;  
    }

    
    private Button createUploadButton() {  
        Button uploadButton = new Button("Upload Item");  
        uploadButton.setStyle(  
            "-fx-background-color: #3498DB;" +   
            "-fx-text-fill: white;" +   
            "-fx-background-radius: 25px;" +   
            "-fx-font-size: 16px;" +  
            "-fx-font-weight: bold;" +  
            "-fx-padding: 12px 40px;" +  
            "-fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.5), 10, 0, 0, 0);"  
        );  

        uploadButton.setOnMouseEntered(e -> {  
            uploadButton.setStyle(  
                "-fx-background-color: #2980B9;" +   
                "-fx-text-fill: white;" +   
                "-fx-background-radius: 25px;" +   
                "-fx-font-size: 16px;" +  
                "-fx-font-weight: bold;" +  
                "-fx-padding: 12px 40px;" +  
                "-fx-effect: dropshadow(three-pass-box, rgba(41,128,185,0.7), 15, 0, 0, 0);"  
            );  
        });  

        uploadButton.setOnMouseExited(e -> {  
            uploadButton.setStyle(  
                "-fx-background-color: #3498DB;" +   
                "-fx-text-fill: white;" +   
                "-fx-background-radius: 25px;" +   
                "-fx-font-size: 16px;" +  
                "-fx-font-weight: bold;" +  
                "-fx-padding: 12px 40px;" +  
                "-fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.5), 10, 0, 0, 0);"  
            );  
        });  

        uploadButton.setOnAction(e -> validateAndUploadItem());  

        return uploadButton;  
    }

    private void validateAndUploadItem() {
        // Reset error label
        errorLabel.setText("");
        
        // Prepare data for upload
        Item uploadData = new Item(
            itemNameField.getText().trim(),   // name
            false,      // isAccepted
            "Waiting Approval",  // status
            categoryComboBox.getValue(),   // category
            sizecomboBox.getValue(),   // size
            parsePrice(),      // price
            userId      // userId
        );
        
        // Call controller to validate and upload
        String errorMessage = uploadController.validateAndUploadItem(uploadData);
        
        if (errorMessage == null) {
            // Show success dialog
        	popupView.getInstance().showSuccessPopup("Success", "Your item has been uploaded and is waiting for admin approval.");
            // Reset form
            resetForm();
        } else {
            // Display error message
            errorLabel.setText(errorMessage);
        }
    }

    private int parsePrice() {
        try {
            return Integer.parseInt(priceField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void resetForm() {
        itemNameField.clear();
        categoryComboBox.setValue(null);
        sizecomboBox.setValue(null);;
        priceField.clear();
        errorLabel.setText("");
    }

    private void makeDraggable(BorderPane root, Stage stage) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    // Getter methods
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getSellerUploadScene() {
        return sellerUploadScene;
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }

    public StackPane getContentArea() {
        return contentArea;
    }
    
    public SellerComponentView getSidebarComponent() {
        return sidebarComponent;
    }
    
    /**
     * Provides access to the logout button for action assignment.
     *
     * @return the logout Button
     */
    public Button getLogoutButton() {
        return sidebarComponent.getLogoutButton();
    }
}
