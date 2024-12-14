package View;


import javafx.event.ActionEvent;
import javafx.geometry.Insets;  
import javafx.geometry.Pos;  
import javafx.scene.Scene;  
import javafx.scene.control.*;  
import javafx.scene.layout.GridPane;  
import javafx.scene.layout.HBox;  
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;  
import javafx.stage.Stage;  
import Model.Item;  

public class SellerEditItemView {  
    private Item itemToEdit;  
    private TextField nameField;  
    private TextField priceField;  
    private ComboBox<String> categoryComboBox;  
    private TextField sizeField;  
    private TextArea descriptionArea;  
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;  

    public SellerEditItemView(Item itemToEdit) {  
        this.itemToEdit = itemToEdit;  
    }
    
 // Interface untuk callback delete  
    public interface DeleteItemHandler {  
        void onDeleteItem(Item item);  
    }  
    
    private DeleteItemHandler deleteItemHandler; 
    
    // Setter untuk delete handler  
    public void setDeleteItemHandler(DeleteItemHandler handler) {  
        this.deleteItemHandler = handler;  
    } 

    public Scene createEditItemScene() {  
        // Main container with modern, clean layout  
        VBox mainLayout = new VBox(15); // Kurangi spasi antar komponen  
        mainLayout.setPadding(new Insets(20)); // Kurangi padding  
        mainLayout.setStyle(  
            "-fx-background-color: linear-gradient(to bottom, #f0f4f8, #e6eaf0);" +  
            "-fx-background-radius: 10px;"  
        );  

        // Title with modern styling  
        Label titleLabel = new Label("Edit Item Details");  
        titleLabel.setFont(Font.font("Segoe UI", 20)); // Sedikit kurangi ukuran font  
        titleLabel.setTextFill(Color.web("#2c3e50"));  
        titleLabel.setStyle("-fx-font-weight: bold;");  

        // Form layout with improved design  
        GridPane formContainer = createFormLayout();  

        // Button box with modern styling  
        HBox buttonBox = createStyledButtonBox();  

        // Add components to main layout  
        mainLayout.getChildren().addAll(titleLabel, formContainer, buttonBox);  
        mainLayout.setAlignment(Pos.CENTER);  

        // Pastikan form dapat di-scroll jika konten terlalu panjang  
        ScrollPane scrollPane = new ScrollPane(mainLayout);  
        scrollPane.setFitToWidth(true);  
        scrollPane.setStyle("-fx-background-color: transparent;");  

        // Create scene dengan ukuran yang lebih kompak  
        Scene scene = new Scene(scrollPane, 500, 450);  
        scene.setFill(Color.web("#f0f4f8"));  

        return scene;  
    }  
    
    private GridPane createFormLayout() {  
        GridPane grid = new GridPane();  
        grid.setHgap(25);  
        grid.setVgap(20);  
        grid.setPadding(new Insets(20, 40, 20, 40));  
        grid.setStyle("-fx-background-color: transparent;");  

        // Style untuk label  
        String labelStyle = "-fx-font-size: 14px;" +   
                            "-fx-font-weight: bold;" +   
                            "-fx-text-fill: #2C3E50;";  

        // Style untuk input  
        String inputStyle = "-fx-background-color: white;" +   
                            "-fx-background-radius: 10px;" +  
                            "-fx-border-color: #BDC3C7;" +   
                            "-fx-border-width: 1px;" +   
                            "-fx-border-radius: 10px;" +  
                            "-fx-padding: 12px;" +   
                            "-fx-font-size: 14px;";  

        // Name  
        Label nameLabel = new Label("Item Name");  
        nameLabel.setStyle(labelStyle);  
        nameField = new TextField(itemToEdit.getName());  
        nameField.setStyle(inputStyle);  
        nameField.setPrefWidth(300);  
        nameField.setPrefHeight(40); 

        // Category  
        Label categoryLabel = new Label("Category");  
        categoryLabel.setStyle(labelStyle);  
        categoryComboBox = new ComboBox<>();  
        categoryComboBox.getItems().addAll(  
            "Atasan",   
            "Bawahan",   
            "Pakaian Formal",   
            "Outerwear",   
            "Pakaian Dalam",  
            "Pakaian Olahraga",   
            "Aksesoris"  
        );  
        categoryComboBox.setValue(itemToEdit.getCategory());  
        categoryComboBox.setStyle(inputStyle);  
        categoryComboBox.setPrefWidth(300);  

     // Size  
        Label sizeLabel = new Label("Size");  
        sizeLabel.setStyle(labelStyle);  
        sizeField = new TextField(itemToEdit.getSize());  
        
        // Tambahkan ComboBox untuk size dengan logika dinamis  
        ComboBox<String> sizeComboBox = new ComboBox<>();  
        sizeComboBox.setStyle(inputStyle);  
        sizeComboBox.setPrefWidth(300);  

        categoryComboBox.setOnAction(e -> {  
            String selectedCategory = categoryComboBox.getValue();  
            sizeComboBox.getItems().clear();  
            
            if (selectedCategory != null) {  
                switch (selectedCategory) {  
                    case "Atasan":  
                    case "Bawahan":  
                    case "Pakaian Olahraga":  
                        sizeComboBox.getItems().addAll("S", "M", "L", "XL");  
                        break;  
                    case "Pakaian Formal":  
                        sizeComboBox.getItems().addAll("S", "M", "L");  
                        break;  
                    case "Outerwear":  
                        sizeComboBox.getItems().addAll("M", "L", "XL");  
                        break;  
                    case "Pakaian Dalam":  
                        sizeComboBox.getItems().addAll("S", "M", "L", "XL", "XXL");  
                        break;  
                    case "Aksesoris":  
                        sizeComboBox.getItems().addAll("One Size");  
                        break;  
                    default:  
                        sizeComboBox.getItems().clear();  
                }  
                
             // Set default size jika item yang diedit sudah memiliki size  
                if (itemToEdit.getSize() != null && !itemToEdit.getSize().isEmpty()) {  
                    // Cek apakah size ada di list  
                    if (sizeComboBox.getItems().contains(itemToEdit.getSize())) {  
                        sizeComboBox.setValue(itemToEdit.getSize());  
                    } else {  
                        // Jika tidak ada, set ke item pertama atau kosongkan  
                        if (!sizeComboBox.getItems().isEmpty()) {  
                            sizeComboBox.setValue(sizeComboBox.getItems().get(0));  
                        } else {  
                            sizeComboBox.setValue(null);  
                        }  
                    }  
                }  
            }  
        });  

        // Tambahkan listener untuk sizeComboBox  
        sizeComboBox.setOnAction(e -> {  
            // Update sizeField dengan value dari sizeComboBox  
            sizeField.setText(sizeComboBox.getValue());  
        });  

        // Trigger initial size population based on current category  
        if (itemToEdit.getCategory() != null) {  
            categoryComboBox.fireEvent(new ActionEvent());  
        }  


        // Price  
        Label priceLabel = new Label("Price (IDR)");  
        priceLabel.setStyle(labelStyle);  
        priceField = new TextField(String.valueOf(itemToEdit.getPrice()));  
        priceField.setStyle(inputStyle);  
        priceField.setPrefWidth(300);  

        // Tambahkan komponen ke grid  
        grid.add(nameLabel, 0, 0);  
        grid.add(nameField, 1, 0);  
        grid.add(categoryLabel, 0, 1);  
        grid.add(categoryComboBox, 1, 1);  
        grid.add(sizeLabel, 0, 2);  
        grid.add(sizeComboBox, 1, 2);  
        grid.add(priceLabel, 0, 3);  
        grid.add(priceField, 1, 3);   

        return grid;  
    }
    
    private Label createFormLabel(String text) {  
        Label label = new Label(text);  
        label.setFont(Font.font("Segoe UI", 14));  
        label.setTextFill(Color.web("#34495e"));  
        label.setStyle("-fx-font-weight: bold;");  // Ganti dari semi-bold ke bold  
        return label;  
    }  
    
    private TextField createStyledTextField(String defaultText) {  
        TextField textField = new TextField(defaultText);  
        textField.setStyle(  
            "-fx-background-color: #f1f3f4;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-border-radius: 5px;" +  
            "-fx-border-color: #d1d8e0;" +  
            "-fx-border-width: 1px;" +  
            "-fx-padding: 8px;"  
        );  
        textField.setFont(Font.font("Segoe UI", 14));  
        return textField;  
    }
    
    private ComboBox<String> createStyledComboBox() {  
        ComboBox<String> comboBox = new ComboBox<>();  
        comboBox.getItems().addAll(  
            "Electronics", "Clothing", "Books",   
            "Home & Garden", "Sports", "Other"  
        );  
        comboBox.setValue(itemToEdit.getCategory());  
        comboBox.setStyle(  
            "-fx-background-color: #f1f3f4;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-border-radius: 5px;" +  
            "-fx-border-color: #d1d8e0;" +  
            "-fx-border-width: 1px;" +  
            "-fx-padding: 8px;" +  
            "-fx-font-family: 'Segoe UI';" +  // Tambahkan ini untuk font  
            "-fx-font-size: 14px;"            // Tambahkan ini untuk ukuran font  
        );  
        return comboBox;  
    }  
    
    private HBox createStyledButtonBox() {  
        HBox buttonBox = new HBox(15);  
        buttonBox.setAlignment(Pos.CENTER);  

        // Save button with gradient and hover effect  
        saveButton = createStyledButton("Save Changes", "#2ecc71", "#27ae60");  
        cancelButton = createStyledButton("Cancel", "#e74c3c", "#c0392b");  
        deleteButton = createStyledButton("Delete Item", "#95a5a6", "#7f8c8d");  

        // Delete button action  
        deleteButton.setOnAction(e -> {  
            if (deleteItemHandler != null) {  
                deleteItemHandler.onDeleteItem(itemToEdit);  
            }  
        });  

        buttonBox.getChildren().addAll(saveButton, cancelButton, deleteButton);  
        return buttonBox;  
    }
    
    private Button createStyledButton(String text, String primaryColor, String secondaryColor) {  
        Button button = new Button(text);  
        button.setStyle(  
            "-fx-background-color: linear-gradient(to bottom, " + primaryColor + ", " + secondaryColor + ");" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-padding: 10px 20px;" +  
            "-fx-font-size: 14px;" +  
            "-fx-font-weight: bold;" +  
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"  
        );  

        // Hover and press effects  
        button.setOnMouseEntered(e -> button.setStyle(  
            button.getStyle() +   
            "-fx-background-color: linear-gradient(to bottom, " + secondaryColor + ", " + primaryColor + ");"  
        ));  

        button.setOnMouseExited(e -> button.setStyle(  
            "-fx-background-color: linear-gradient(to bottom, " + primaryColor + ", " + secondaryColor + ");" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-padding: 10px 20px;" +  
            "-fx-font-size: 14px;" +  
            "-fx-font-weight: bold;" +  
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"  
        ));  

        return button;  
    } 

    

    

    // Getters for accessing form components  
    public TextField getNameField() {  
        return nameField;  
    }  

    public TextField getPriceField() {  
        return priceField;  
    }  

    public ComboBox<String> getCategoryComboBox() {  
        return categoryComboBox;  
    }  

    public TextField getSizeField() {  
        return sizeField;  
    }  

    public TextArea getDescriptionArea() {  
        return descriptionArea;  
    }  

    public Button getSaveButton() {  
        return saveButton;  
    }  

    public Button getCancelButton() {  
        return cancelButton;  
    }  

    // Method to show the edit dialog  
    public void showEditDialog(Stage parentStage) {  
        Stage dialogStage = new Stage();  
        dialogStage.initModality(Modality.WINDOW_MODAL);  
        dialogStage.initOwner(parentStage);  
        dialogStage.setTitle("Edit Item");  

        Scene scene = createEditItemScene();  
        dialogStage.setScene(scene);  
        dialogStage.show();  
    }  

 // Validation method (keep existing validation logic)  
    public boolean validateInput() {  
    	// Name validation  
    	if (nameField.getText().trim().isEmpty()) {  
    	    showValidationError("Item name cannot be empty");  
    	    return false;  
    	}  

    	if (nameField.getText().trim().length() < 3) {  
    	    showValidationError("Item name must be at least 3 characters long");  
    	    return false;  
    	}  

        // Category validation  
        if (categoryComboBox.getValue() == null) {  
            showValidationError("Please select a category");  
            return false;  
        }  

        // Size validation (untuk ComboBox)  
        ComboBox<String> sizeComboBox = (ComboBox<String>) ((GridPane)nameField.getParent()).getChildren().get(5);  
        if (sizeComboBox.getValue() == null) {  
            showValidationError("Please select a size");  
            return false;  
        }  

        // Price validation  
        try {  
            double price = Double.parseDouble(priceField.getText().trim());  
            if (price <= 0) {  
                showValidationError("Price must be a positive number");  
                return false;  
            }  
        } catch (NumberFormatException e) {  
            showValidationError("Invalid price format");  
            return false;  
        }  

        return true;  
    }  

    private void showValidationError(String message) {  
        Alert alert = new Alert(Alert.AlertType.ERROR);  
        alert.setTitle("Validation Error");  
        alert.setHeaderText(null);  
        alert.setContentText(message);  
        alert.showAndWait();  
    }  
}