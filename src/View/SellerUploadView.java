package View;  

import Controller.SellerUploadController;  
import javafx.geometry.Insets;  
import javafx.geometry.Pos;  
import javafx.scene.Scene;  
import javafx.scene.control.*;  
import javafx.scene.layout.GridPane;  
import javafx.scene.layout.HBox;  
import javafx.scene.layout.VBox;  
import javafx.stage.Stage;  

public class SellerUploadView {  
    private Stage primaryStage;  
    private SellerHomeView sidebarView;  
    private SellerUploadController controller;  
    private String username;  

    // Komponen input  
    private TextField itemNameField;  
    private ComboBox<String> categoryComboBox;  
    private TextField itemSizeField;  
    private TextField priceField;  
    private TextArea descriptionArea;  
    private Button uploadButton;  
    private Label errorLabel;  

    public SellerUploadView(Stage primaryStage, SellerHomeView sidebarView, String username, SellerUploadController controller) {  
        this.primaryStage = primaryStage;  
        this.sidebarView = sidebarView;  
        this.username = username;  
        this.controller = controller;  
        
        // Buat scene  
        Scene scene = createUploadScene();  
        
        // Set scene ke primary stage  
        primaryStage.setScene(scene);  
        primaryStage.setTitle("Upload New Item");  
    }  

    private Scene createUploadScene() {  
        // Layout utama dengan sidebar dan konten  
        HBox mainLayout = new HBox();  
        
        // Tambahkan sidebar dari SellerHomeView  
        VBox sidebar = sidebarView.createSidebar(username);  
        
        // Konten upload  
        VBox uploadContent = createUploadContent();  
        
        // Tambahkan sidebar dan konten ke main layout  
        mainLayout.getChildren().addAll(sidebar, uploadContent);  
        
        // Buat scene  
        Scene scene = new Scene(mainLayout, 1200, 700);  
        return scene;  
    }  

    private VBox createUploadContent() {  
        VBox content = new VBox(20);  
        content.setPadding(new Insets(20));  
        content.setStyle("-fx-background-color: #f4f4f4;");  
        
        // Judul  
        Label titleLabel = new Label("Upload New Item");  
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");  
        
        // Grid untuk input  
        GridPane inputGrid = new GridPane();  
        inputGrid.setHgap(10);  
        inputGrid.setVgap(15);  
        inputGrid.setPadding(new Insets(20));  
        
        // Item Name  
        Label nameLabel = new Label("Item Name:");  
        itemNameField = new TextField();  
        itemNameField.setPromptText("Enter item name");  
        inputGrid.add(nameLabel, 0, 0);  
        inputGrid.add(itemNameField, 1, 0);  
        
        // Category  
        Label categoryLabel = new Label("Category:");  
        categoryComboBox = new ComboBox<>();  
        categoryComboBox.getItems().addAll(  
            "Electronics", "Clothing", "Books",   
            "Home & Kitchen", "Sports", "Toys", "Other"  
        );  
        categoryComboBox.setPromptText("Select category");  
        inputGrid.add(categoryLabel, 0, 1);  
        inputGrid.add(categoryComboBox, 1, 1);  
        
        // Item Size  
        Label sizeLabel = new Label("Item Size:");  
        itemSizeField = new TextField();  
        itemSizeField.setPromptText("Enter item size");  
        inputGrid.add(sizeLabel, 0, 2);  
        inputGrid.add(itemSizeField, 1, 2);  
        
        // Price  
        Label priceLabel = new Label("Price:");  
        priceField = new TextField();  
        priceField.setPromptText("Enter price");  
        inputGrid.add(priceLabel, 0, 3);  
        inputGrid.add(priceField, 1, 3);  
        
        // Description  
        Label descriptionLabel = new Label("Description:");  
        descriptionArea = new TextArea();  
        descriptionArea.setPromptText("Enter item description (optional)");  
        descriptionArea.setPrefRowCount(4);  
        inputGrid.add(descriptionLabel, 0, 4);  
        inputGrid.add(descriptionArea, 1, 4);  
        
        // Error Label  
        errorLabel = new Label();  
        errorLabel.setStyle("-fx-text-fill: red;");  
        
        // Upload Button  
        uploadButton = new Button("Upload Item");  
        uploadButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");  
        uploadButton.setOnAction(e -> validateAndUploadItem());  
        
        // Layout akhir  
        content.getChildren().addAll(  
            titleLabel,   
            inputGrid,   
            errorLabel,   
            createButtonLayout()  
        );  
        
        return content;  
    }  

    private HBox createButtonLayout() {  
        HBox buttonLayout = new HBox(10);  
        buttonLayout.setAlignment(Pos.CENTER);  
        buttonLayout.getChildren().addAll(uploadButton);  
        return buttonLayout;  
    }  

    private void validateAndUploadItem() {  
        // Reset error label  
        errorLabel.setText("");  
        
        // Validasi Item Name  
        String itemName = itemNameField.getText().trim();  
        if (itemName.isEmpty()) {  
            errorLabel.setText("Item Name cannot be empty.");  
            return;  
        }  
        if (itemName.length() < 3) {  
            errorLabel.setText("Item Name must be at least 3 characters long.");  
            return;  
        }  
        
        // Validasi Category  
        String category = categoryComboBox.getValue();  
        if (category == null || category.isEmpty()) {  
            errorLabel.setText("Category cannot be empty.");  
            return;  
        }  
        if (category.length() < 3) {  
            errorLabel.setText("Category must be at least 3 characters long.");  
            return;  
        }  
        
        // Validasi Item Size  
        String itemSize = itemSizeField.getText().trim();  
        if (itemSize.isEmpty()) {  
            errorLabel.setText("Item Size cannot be empty.");  
            return;  
        }  
        
        // Validasi Price  
        String priceText = priceField.getText().trim();  
        double price;  
        try {  
            price = Double.parseDouble(priceText);  
            if (price <= 0) {  
                errorLabel.setText("Price must be greater than 0.");  
                return;  
            }  
        } catch (NumberFormatException e) {  
            errorLabel.setText("Price must be a valid number.");  
            return;  
        }  
        
        // Siapkan data untuk upload  
        UploadItemDTO uploadData = new UploadItemDTO(  
            itemName,   
            category,   
            itemSize,   
            price,   
            descriptionArea.getText().trim()  
        );  
        
        // Panggil controller untuk upload  
        UploadResult result = controller.uploadItem(username, uploadData);  
        
        if (result.isSuccess()) {  
            // Tampilkan pesan sukses  
            showSuccessDialog();  
            
            // Reset form  
            resetForm();  
        } else {  
            errorLabel.setText(result.getErrorMessage());  
        }  
    }  

    private void showSuccessDialog() {  
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  
        alert.setTitle("Upload Successful");  
        alert.setHeaderText(null);  
        alert.setContentText("Your item has been uploaded and is waiting for admin approval.");  
        alert.showAndWait();  
    }  

    private void resetForm() {  
        itemNameField.clear();  
        categoryComboBox.setValue(null);  
        itemSizeField.clear();  
        priceField.clear();  
        descriptionArea.clear();  
        errorLabel.setText("");  
    }  
}