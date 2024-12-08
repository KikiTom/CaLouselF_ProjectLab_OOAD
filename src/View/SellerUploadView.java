package View;  

import Controller.SellerUploadController;  
import Service.ItemService;  
import javafx.application.Platform;  
import javafx.geometry.Insets;  
import javafx.geometry.Pos;  
import javafx.scene.Scene;  
import javafx.scene.control.*;  
import javafx.scene.layout.*;  
import javafx.stage.Stage;  

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
    private boolean isUploadButtonActionSetup = false;   

    // Input Components  
    private TextField itemNameField;  
    private ComboBox<String> categoryComboBox;  
    private ComboBox<String> sizeComboBox;  
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
        setupUploadButtonAction();  
    }  

    private void initializeComponents() {  
        sidebar = sidebarComponent.getSidebar(username, "Upload Item");  
        uploadButton = createUploadButton();  
    }  

    private void setupUploadButtonAction() {  
        if (isUploadButtonActionSetup) return;  
        
        if (uploadButton != null && uploadController != null) {  
            uploadButton.setOnAction(event -> {  
                Platform.runLater(() -> {  
                    uploadController.handleUploadButtonAction();  
                });  
            });  
            
            isUploadButtonActionSetup = true;  
        }  
    }  

    public Scene createSellerUploadScene() {  
        mainLayout = createMainLayout();  
        mainLayout.setStyle("-fx-background-color: #2C3E50;" + "-fx-background-radius: 15px;"  
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");  

        sellerUploadScene = new Scene(mainLayout, 1200, 700);  
        makeDraggable(mainLayout, primaryStage);  

        return sellerUploadScene;  
    }  

    private BorderPane createMainLayout() {  
        BorderPane mainLayout = new BorderPane();  
        mainLayout.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15px;"  
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");  

        mainLayout.setLeft(sidebar);  
        contentArea = createContentArea();  
        mainLayout.setCenter(contentArea);  

        return mainLayout;  
    }  

    private StackPane createContentArea() {  
        StackPane contentArea = new StackPane();  
        contentArea.setStyle("-fx-background-color: white;");  
        contentArea.setPadding(new Insets(20));  

        VBox uploadPanel = createUploadPanel();  
        contentArea.getChildren().add(uploadPanel);  
        StackPane.setAlignment(uploadPanel, Pos.CENTER);  

        return contentArea;  
    }  

    private VBox createUploadPanel() {  
        VBox uploadPanel = new VBox(30);  
        uploadPanel.setPadding(new Insets(40));  
        uploadPanel.setAlignment(Pos.TOP_CENTER);  
        uploadPanel.setStyle("-fx-background-color: linear-gradient(to bottom right, #f5f7fa, #e9ecef);"  
                + "-fx-background-radius: 20px;" + "-fx-border-radius: 20px;" + "-fx-border-color: #dfe6e9;"  
                + "-fx-border-width: 1px;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 0);");  

        VBox headerTextBox = new VBox(10);  
        headerTextBox.setAlignment(Pos.CENTER);  
        Label titleLabel = new Label("Upload New Item");  
        titleLabel.setStyle("-fx-font-size: 32px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #2C3E50;");  

        Label subtitleLabel = new Label("Provide the details of the item you wish to sell");  
        subtitleLabel.setStyle("-fx-font-size: 16px;" + "-fx-text-fill: #7F8C8D;");  

        headerTextBox.getChildren().addAll(titleLabel, subtitleLabel);  

        GridPane formGrid = createUploadForm();  
        formGrid.setAlignment(Pos.CENTER);  

        VBox actionBox = new VBox(20);  
        actionBox.setAlignment(Pos.CENTER);  

        errorLabel = new Label("");  
        errorLabel.setStyle("-fx-text-fill: #E74C3C; -fx-font-size: 14px;");  

        uploadButton = createUploadButton();  

        actionBox.getChildren().addAll(errorLabel, uploadButton);  

        uploadPanel.getChildren().addAll(headerTextBox, formGrid, actionBox);  

        return uploadPanel;  
    }  

    private GridPane createUploadForm() {  
        GridPane formGrid = new GridPane();  
        formGrid.setHgap(25);  
        formGrid.setVgap(20);  
        formGrid.setPadding(new Insets(20, 40, 20, 40));  
        formGrid.setStyle("-fx-background-color: transparent;");  

        String labelStyle = "-fx-font-size: 14px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #2C3E50;";  

        String inputStyle = "-fx-background-color: white;" + "-fx-background-radius: 10px;"  
                + "-fx-border-color: #BDC3C7;" + "-fx-border-width: 1px;" + "-fx-border-radius: 10px;"  
                + "-fx-padding: 12px;" + "-fx-font-size: 14px;";  

        Label nameLabel = new Label("Item Name");  
        nameLabel.setStyle(labelStyle);  
        itemNameField = new TextField();  
        itemNameField.setPromptText("e.g., Classic White Shirt");  
        itemNameField.setStyle(inputStyle);  
        itemNameField.setPrefWidth(300);  

        Label categoryLabel = new Label("Category");  
        categoryLabel.setStyle(labelStyle);  
        categoryComboBox = new ComboBox<>();  
        categoryComboBox.getItems().addAll("Atasan", "Bawahan", "Pakaian Formal", "Outerwear", "Pakaian Dalam",  
                "Pakaian Olahraga", "Aksesoris");  
        categoryComboBox.setPromptText("Select category");  
        categoryComboBox.setStyle(inputStyle);  
     

        Label sizeLabel = new Label("Size");  
        sizeLabel.setStyle(labelStyle);  
        sizeComboBox = new ComboBox<>();  
        sizeComboBox.setPromptText("Select Size");  
        sizeComboBox.setStyle(inputStyle);  
 
        categoryComboBox.setOnAction(e -> {  
            String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();  
            if (selectedCategory != null) {  
                sizeComboBox.getItems().clear();  
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
            }  
        });  

        Label priceLabel = new Label("Price (IDR)");  
        priceLabel.setStyle(labelStyle);  
        priceField = new TextField();  
        priceField.setPromptText("e.g., 150000");  
        priceField.setStyle(inputStyle);  
        priceField.setPrefWidth(300);  

        formGrid.add(nameLabel, 0, 0);  
        formGrid.add(itemNameField, 1, 0);  
        formGrid.add(categoryLabel, 0, 1);  
        formGrid.add(categoryComboBox, 1, 1);  
        formGrid.add(sizeLabel, 0, 2);  
        formGrid.add(sizeComboBox, 1, 2);  
        formGrid.add(priceLabel, 0, 3);  
        formGrid.add(priceField, 1, 3);  

        return formGrid;  
    }  

    private Button createUploadButton() {  
        Button uploadButton = new Button("Upload Item");  
        uploadButton.setStyle("-fx-background-color: #3498DB;" + "-fx-text-fill: white;"  
                + "-fx-background-radius: 30px;" + "-fx-font-size: 16px;" + "-fx-font-weight: bold;"  
                + "-fx-padding: 15px 60px;" + "-fx-cursor: hand;"  
                + "-fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.5), 10, 0, 0, 0);");  

        uploadButton.setOnMouseEntered(e -> {  
            uploadButton.setStyle("-fx-background-color: #2980B9;" + "-fx-text-fill: white;"  
                    + "-fx-background-radius: 30px;" + "-fx-font-size: 16px;" + "-fx-font-weight: bold;"  
                    + "-fx-padding: 15px 60px;" + "-fx-cursor: hand;"  
                    + "-fx-effect: dropshadow(three-pass-box, rgba(41,128,185,0.7), 15, 0, 0, 0);");  
        });  

        uploadButton.setOnMouseExited(e -> {  
            uploadButton.setStyle("-fx-background-color: #3498DB;" + "-fx-text-fill: white;"  
                    + "-fx-background-radius: 30px;" + "-fx-font-size: 16px;" + "-fx-font-weight: bold;"  
                    + "-fx-padding: 15px 60px;" + "-fx-cursor: hand;"  
                    + "-fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.5), 10, 0, 0, 0);");  
        });  

        return uploadButton;  
    }  

    public void resetForm() {  
        if (itemNameField != null)  
            itemNameField.clear();  
        if (categoryComboBox != null)  
            categoryComboBox.setValue(null);  
        if (sizeComboBox != null) {  
            sizeComboBox.setValue(null);  
            sizeComboBox.getItems().clear();  
        }  
        if (priceField != null)  
            priceField.clear();  
        if (errorLabel != null)  
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

    public Button getLogoutButton() {  
        return sidebarComponent.getLogoutButton();  
    }  

    public Button getUploadButton() {  
        if (uploadButton == null) {  
            uploadButton = createUploadButton();  
        }  
        return uploadButton;  
    }  

    public TextField getItemNameField() {  
        return itemNameField;  
    }  

    public ComboBox<String> getCategoryComboBox() {  
        return categoryComboBox;  
    }  

    public ComboBox<String> getSizeComboBox() {  
        return sizeComboBox;  
    }  

    public TextField getPriceField() {  
        return priceField;  
    }  

    public Label getErrorLabel() {  
        return errorLabel;  
    }  

    public SellerUploadController getUploadController() {  
        return uploadController;  
    }  
}