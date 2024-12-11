package View;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SellerHomeView {
    private static final double[] COLUMN_WIDTHS = { 80, 160, 200, 120, 90, 150 };
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private Scene sellerHomeScene;
    private BorderPane mainLayout;
    private SellerComponentView sidebarComponent;
    private VBox sidebar;
    private HBox footer;
    private StackPane contentArea;
    private ScrollPane itemsScrollPane;
    private String username;

    public SellerHomeView(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;
        initializeComponents();
    }

    public Scene createSellerHomeScene() {
        mainLayout = createMainLayout();
        mainLayout.setStyle("-fx-background-color: #2C3E50;" +
                            "-fx-background-radius: 15px;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        sellerHomeScene = new Scene(mainLayout, 1200, 700);
        sellerHomeScene.setFill(Color.TRANSPARENT);

        makeDraggable(mainLayout, primaryStage);

        return sellerHomeScene;
    }

    private void initializeComponents() {
        sidebarComponent = new SellerComponentView();
        sidebar = sidebarComponent.getSidebar(username, "View Items");
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

        // Overview Panel
        VBox overviewPanel = createOverviewPanel();
        contentArea.getChildren().add(overviewPanel);

        return contentArea;
    }

    private VBox createOverviewPanel() {
        VBox overviewPanel = new VBox(15);
        overviewPanel.setPadding(new Insets(20));
        overviewPanel.setStyle("-fx-background-color: white;");

        // Search TextField
        TextField searchField = new TextField();
        searchField.setPromptText("Search items...");
        searchField.setStyle("-fx-background-color: #F4F6F7;" +
                            "-fx-border-color: #BDC3C7;" +
                            "-fx-border-radius: 30px;" +
                            "-fx-background-radius: 30px;" +
                            "-fx-font-size: 14px;");
        searchField.setPrefSize(300, 40);

        Label titleLabel = new Label("My Items CalouseIF");
        titleLabel.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");

        // Table Header
        HBox tableHeader = createTableHeader();

        // Scrollable Items
        ScrollPane itemsScrollPane = createItemsScrollPane();

        // Table Footer with Statistics
        HBox tableFooter = createTableFooter();

        overviewPanel.getChildren().addAll(searchField, titleLabel, tableHeader, itemsScrollPane, tableFooter);

        return overviewPanel;
    }

    private HBox createTableHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #F4F6F7;" +
                       "-fx-padding: 10px;" +
                       "-fx-background-radius: 10px;");

        String[] headers = { "", "Category", "Name", "Size", "Price & Sold", "Status" };

        // Add spacer to match the row's layout
        Region editSpacer = new Region();
        editSpacer.setPrefWidth(10);
        header.getChildren().add(editSpacer);

        for (int i = 0; i < headers.length; i++) {
            Label label = new Label(headers[i]);
            label.setStyle("-fx-font-weight: bold;" +
                           "-fx-text-fill: #2C3E50;" +
                           "-fx-font-size: 14px;");

            // Use the same column widths as in the row
            label.setPrefWidth(COLUMN_WIDTHS[i]);
            label.setMaxWidth(COLUMN_WIDTHS[i]);
            label.setMinWidth(COLUMN_WIDTHS[i]);

            // Center align text for all columns
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);

            header.getChildren().add(label);
        }

        return header;
    }

    private ScrollPane createItemsScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);
        
        // Assign a unique ID for easier lookup
        scrollPane.setId("items-scroll-pane");
        
        VBox itemsContainer = new VBox(10);
        itemsContainer.setStyle("-fx-background-color: white;");
        
        // Optionally, add a sample item row
        // HBox item = createItemRow();
        // itemsContainer.getChildren().add(item);
        
        scrollPane.setContent(itemsContainer);
        scrollPane.setPrefHeight(400);
        
        this.itemsScrollPane = scrollPane; // Assign to class field
        
        return scrollPane;
    }

    private Label createStandardLabel(String text, double width, String color) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setPrefWidth(width);
        label.setStyle("-fx-text-fill: " + color + ";" +
                      "-fx-font-size: 12px;" +
                      "-fx-alignment: center;");
        return label;
    }

    private Label createEditButton() {  
        Label editLabel = new Label("Edit");  
        editLabel.setAlignment(Pos.CENTER);  
        editLabel.setTextAlignment(TextAlignment.CENTER);  
        editLabel.setPrefWidth(COLUMN_WIDTHS[0]);  
        
        // Default style for Edit  
        editLabel.setStyle("-fx-background-color: #3498DB;" +  
                           "-fx-text-fill: white;" +  
                           "-fx-background-radius: 20px;" +  
                           "-fx-padding: 5px 10px;" +  
                           "-fx-alignment: center;");  

        // Hover effects with dynamic styling based on current text  
        editLabel.setOnMouseEntered(e -> {  
            String currentText = editLabel.getText();  
            String hoverColor = currentText.equals("Delete") ? "#C0392B" : "#2980B9";  
            
            editLabel.setStyle("-fx-background-color: " + hoverColor + ";" +  
                               "-fx-text-fill: white;" +  
                               "-fx-background-radius: 20px;" +  
                               "-fx-padding: 5px 10px;" +  
                               "-fx-alignment: center;" +  
                               "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 0);");  
        });  

        editLabel.setOnMouseExited(e -> {  
            String currentText = editLabel.getText();  
            String backgroundColor = currentText.equals("Delete") ? "#E74C3C" : "#3498DB";  
            
            editLabel.setStyle("-fx-background-color: " + backgroundColor + ";" +  
                               "-fx-text-fill: white;" +  
                               "-fx-background-radius: 20px;" +  
                               "-fx-padding: 5px 10px;" +  
                               "-fx-alignment: center;");  
        });  

        return editLabel;  
    } 

    private Label createCategoryLabel(String category) {
        return createStandardLabel(category, COLUMN_WIDTHS[1], "#2C3E50");
    }

    private Label createNameLabel(String name) {
        return createStandardLabel(name, COLUMN_WIDTHS[2], "#2C3E50");
    }

    private Label createSizeLabel(String size) {
        return createStandardLabel(size, COLUMN_WIDTHS[3], "#2C3E50");
    }

    private Label createPriceLabel(String price) {
        return createStandardLabel(price, COLUMN_WIDTHS[4], "#2C3E50");
    }

    private Label createStatusLabel(String status) {
        Label statusLabel = createStandardLabel(status, COLUMN_WIDTHS[5], "#2C3E50");
        return statusLabel;
    }
    
    

    private Label createSoldQuantityLabel(int soldQuantity) {
        Label soldLabel = new Label(soldQuantity > 0 ? soldQuantity + " sold" : "Not Sold");
        soldLabel.setAlignment(Pos.CENTER);
        soldLabel.setTextAlignment(TextAlignment.CENTER);
        soldLabel.setPrefWidth(COLUMN_WIDTHS[4]);
        soldLabel.setStyle("-fx-font-weight: bold;" +
                           "-fx-background-radius: 15px;" +
                           "-fx-padding: 3px 8px;" +
                           (soldQuantity > 0 ?
                            "-fx-background-color: rgba(46, 204, 113, 0.1);" :
                            "-fx-background-color: rgba(231, 76, 60, 0.1);" +
                            "-fx-text-fill: " + (soldQuantity > 0 ? "#2ECC71" : "#E74C3C") + ";"));

        // Tooltip for additional information
        Tooltip tooltip = new Tooltip(
            soldQuantity > 0 ?
            "Item has been sold " + soldQuantity + " times" :
            "Item has not been sold yet");
        Tooltip.install(soldLabel, tooltip);

        return soldLabel;
    }

    /**
     * Creates a styled item row HBox.
     * This method is responsible for all styling of the item row.
     *
     * @return a styled HBox representing an item row
     */
    public HBox createItemRow() {
        HBox itemRow = new HBox(10);
        itemRow.setAlignment(Pos.CENTER_LEFT);
        itemRow.setPadding(new Insets(10));
        itemRow.setStyle("-fx-background-color: #F4F6F7;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #E0E0E0;" +
                        "-fx-border-width: 1px;");

        // Create labels using view's styling methods
        Label editLabel = createEditButton();

        // Add some spacing between Edit and Category
        Region spacer = new Region();
        spacer.setPrefWidth(10);

        // Create other labels with empty initial text
        Label categoryLabel = createCategoryLabel("");
        Label nameLabel = createNameLabel("");
        Label sizeLabel = createSizeLabel("");

        // Price and Sold Quantity within one VBox
        VBox priceBox = new VBox(5);
        priceBox.setAlignment(Pos.CENTER);

        Label priceLabel = createPriceLabel("");
        Label soldQuantityLabel = createSoldQuantityLabel(0);

        priceBox.getChildren().addAll(priceLabel, soldQuantityLabel);

        Label statusLabel = createStatusLabel("");

        // Add components to row
        itemRow.getChildren().addAll(editLabel, spacer, categoryLabel, nameLabel, sizeLabel, priceBox, statusLabel);

        return itemRow;
    }

    /**
     * Updates the labels within an item row HBox.
     * This method ensures that all styling is maintained by using the view's methods.
     *
     * @param itemRow      the HBox representing the item row
     * @param category     the category of the item
     * @param name         the name of the item
     * @param size         the size of the item
     * @param price        the price of the item
     * @param soldQuantity the quantity sold
     * @param status       the status of the item
     */
    public void updateItemRowLabels(HBox itemRow, String category, String name, String size, String price,
                                    int soldQuantity, String status) {
        try {
        	
        	Label editLabel = getEditLabel(itemRow);  
            switch (status.toLowerCase()) {  
                case "waiting approval":  
                    editLabel.setText("Delete");  
                    editLabel.setStyle("-fx-background-color: #E74C3C;" +  
                                       "-fx-text-fill: white;" +  
                                       "-fx-background-radius: 20px;" +  
                                       "-fx-padding: 5px 10px;" +  
                                       "-fx-alignment: center;");  
                    break;  
                case "available":  
                    editLabel.setText("Edit");  
                    editLabel.setStyle("-fx-background-color: #3498DB;" +  
                                       "-fx-text-fill: white;" +  
                                       "-fx-background-radius: 20px;" +  
                                       "-fx-padding: 5px 10px;" +  
                                       "-fx-alignment: center;");  
                    break;  
                default:  
                    if (status.toLowerCase().contains("decline")) {  
                        editLabel.setText("Delete");  
                        editLabel.setStyle("-fx-background-color: #E74C3C;" +  
                                           "-fx-text-fill: white;" +  
                                           "-fx-background-radius: 20px;" +  
                                           "-fx-padding: 5px 10px;" +  
                                           "-fx-alignment: center;");  
                    }  
            }  
        	
        	
            // Update category label
            Label categoryLabel = getCategoryLabel(itemRow);
            categoryLabel.setText(category);

            // Update name label
            Label nameLabel = getNameLabel(itemRow);
            nameLabel.setText(name);

            // Update size label
            Label sizeLabel = getSizeLabel(itemRow);
            sizeLabel.setText(size);

            // Update price label
            Label priceLabel = getPriceLabel(itemRow);
            priceLabel.setText(price);

            // Update sold quantity label
            Label soldQuantityLabel = getSoldQuantityLabel(itemRow);
            if (status.toLowerCase().contains("decline")) {  
                soldQuantityLabel.setText("Rejected");  
                soldQuantityLabel.setStyle("-fx-font-weight: bold;" +  
                                           "-fx-background-radius: 15px;" +  
                                           "-fx-padding: 3px 8px;" +  
                                           "-fx-background-color: rgba(192, 57, 43, 0.1);" +  // Deep red  
                                           "-fx-text-fill: #C0392B;");  // Dark red  
            } else if (status.equals("Waiting Approval")) {  
                soldQuantityLabel.setText("Pending");  
                soldQuantityLabel.setStyle("-fx-font-weight: bold;" +  
                                           "-fx-background-radius: 15px;" +  
                                           "-fx-padding: 3px 8px;" +  
                                           "-fx-background-color: rgba(255, 165, 0, 0.1);" +  // Warna oranye untuk pending approval  
                                           "-fx-text-fill: #FFA500;");  // Warna teks oranye  
            } else {  
                // Jika tidak dalam status "Waiting Approval" atau "Decline", cek jumlah sold  
                soldQuantityLabel.setText(soldQuantity > 0 ? soldQuantity + " sold" : "Not Sold");  
                soldQuantityLabel.setStyle("-fx-font-weight: bold;" +  
                                           "-fx-background-radius: 15px;" +  
                                           "-fx-padding: 3px 8px;" +  
                                           (soldQuantity > 0 ?   
                                            "-fx-background-color: rgba(46, 204, 113, 0.1);" :  // Soft green for sold  
                                            "-fx-background-color: rgba(149, 165, 166, 0.1);") +  // Soft gray for not sold  
                                           "-fx-text-fill: " + (soldQuantity > 0 ? "#2ECC71" : "#95A5A6") + ";");  // Green for sold, gray for not sold  
            }

            // Update status label
            Label statusLabel = getStatusLabel(itemRow);
            statusLabel.setText(status);
            switch (status.toLowerCase()) {  
            case "available":  
                statusLabel.setStyle("-fx-text-fill: #2ECC71;" +  
                                    "-fx-font-size: 12px;" +  
                                    "-fx-font-weight: bold;");  
                break;  
            case "waiting approval":  
                statusLabel.setStyle("-fx-text-fill: #95A5A6;" +  
                                    "-fx-font-size: 12px;" +  
                                    "-fx-font-weight: bold;");  
                break;  
            default:  
                if (status.toLowerCase().contains("decline")) {
                	statusLabel.setText("Decline");
                    statusLabel.setStyle("-fx-text-fill: #E74C3C;" +  
                                        "-fx-font-size: 12px;" +  
                                        "-fx-font-weight: bold;");  
                } else {  
                    statusLabel.setStyle("-fx-text-fill: #95A5A6;" +  
                                        "-fx-font-size: 12px;" +  
                                        "-fx-font-weight: bold;" +  
                                        "-fx-alignment: center;");  
                }  
        }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating item row labels: " + e.getMessage());
        }
    }

    private HBox createTableFooter() {  
        footer = new HBox(20);  
        footer.setAlignment(Pos.CENTER);  
        footer.setPadding(new Insets(15, 10, 15, 10));  
        footer.setStyle("-fx-background-color: #F4F6F7;" +  
                        "-fx-background-radius: 0 0 10px 10px;" +  
                        "-fx-border-color: #E0E0E0;" +  
                        "-fx-border-width: 1px;");  

        // Total Items  
        VBox totalItemsBox = createStatBox("Total Items", "0", "#3498DB");  
        Label totalItemsLabel = (Label) totalItemsBox.getChildren().get(0); // First child (title label)  
        totalItemsLabel.setStyle("-fx-font-weight: bold;"); // Make title bold  
        Label totalValueLabel = (Label) totalItemsBox.getChildren().get(1);   
        totalValueLabel.setId("total-items-label");  

        // Sold Items  
        VBox soldItemsBox = createStatBox("Sold Items", "0", "#2ECC71");  
        Label soldTitleLabel = (Label) soldItemsBox.getChildren().get(0);  
        soldTitleLabel.setStyle("-fx-font-weight: bold;"); // Make title bold  
        Label soldValueLabel = (Label) soldItemsBox.getChildren().get(1);  
        soldValueLabel.setId("sold-items-label");  

        // Total Transaction  
        VBox transactionBox = createStatBox("Total Transaction", "Rp 0", "#F39C12");  
        Label transactionTitleLabel = (Label) transactionBox.getChildren().get(0);  
        transactionTitleLabel.setStyle("-fx-font-weight: bold;"); // Make title bold  
        Label transactionValueLabel = (Label) transactionBox.getChildren().get(1);  
        transactionValueLabel.setId("total-transaction-label");  

        // Wrap each stat box with a border  
        HBox[] statBoxes = {  
            wrapStatBoxWithBorder(totalItemsBox),  
            wrapStatBoxWithBorder(soldItemsBox),  
            wrapStatBoxWithBorder(transactionBox)  
        };  

        footer.getChildren().addAll(statBoxes);  
        footer.setSpacing(20);  
        HBox.setHgrow(footer, Priority.ALWAYS);  

        return footer;  
    }  

    // Method untuk mengupdate footer statistics  
    public void updateFooterStatistics(String totalItems, String soldItems, String totalTransaction) {  
        Platform.runLater(() -> {  
            // Cari label-label di dalam footer  
            Label totalItemsLabel = (Label) footer.lookup("#total-items-label");  
            Label soldItemsLabel = (Label) footer.lookup("#sold-items-label");  
            Label totalTransactionLabel = (Label) footer.lookup("#total-transaction-label");  

            // Update label jika ditemukan  
            if (totalItemsLabel != null) {  
                totalItemsLabel.setText(totalItems);  
            }  
            if (soldItemsLabel != null) {  
                soldItemsLabel.setText(soldItems);  
            }  
            if (totalTransactionLabel != null) {  
                totalTransactionLabel.setText(totalTransaction);  
            }  
        });  
    }  

    // Method createStatBox yang mungkin sudah ada  
    private VBox createStatBox(String title, String value, String color) {  
        VBox box = new VBox(5);  
        box.setAlignment(Pos.CENTER);  

        // Label judul  
        Label titleLabel = new Label(title);  
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");  

        // Label nilai  
        Label valueLabel = new Label(value);  
        valueLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");  

        box.getChildren().addAll(titleLabel, valueLabel);  
        return box;  
    }  

    private HBox wrapStatBoxWithBorder(VBox statBox) {
        HBox borderBox = new HBox(statBox);
        borderBox.setAlignment(Pos.CENTER);
        borderBox.setPadding(new Insets(15));
        borderBox.setStyle("-fx-background-color: white;" +
                           "-fx-background-radius: 10px;" +
                           "-fx-border-color: #E0E0E0;" +
                           "-fx-border-width: 1px;" +
                           "-fx-border-radius: 10px;" +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

        // Ensure statBox fills the available space
        HBox.setHgrow(borderBox, Priority.ALWAYS);

        return borderBox;
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

    // Getter methods for accessing UI components

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getSellerHomeScene() {
        return sellerHomeScene;
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }

    public SellerComponentView getSidebarComponent() {
        return sidebarComponent;
    }

    public VBox getSidebar() {
        return sidebar;
    }

    public StackPane getContentArea() {
        return contentArea;
    }

    /**
     * Retrieves the logout button from the sidebar component.
     *
     * @return the logout Button, or null if not found
     */
    public Button getLogoutButton() {
        if (sidebarComponent == null) {
            System.err.println("Sidebar component is null!");
            return null;
        }

        // Retrieve logout button from sidebar component
        Button logoutButton = sidebarComponent.getLogoutButton();

        if (logoutButton == null) {
            System.err.println("Logout button from sidebar component is null!");
        }

        return logoutButton;
    }

    /**
     * Provides a method to create a new styled item row.
     *
     * @return a new HBox representing a styled item row
     */
    public HBox getcreateItemRow() {
        return createItemRow();
    }
    
    // Label retrieval methods to encapsulate the UI structure
    
    public Label getEditLabel(HBox itemRow) {  
        return (Label) itemRow.getChildren().get(0);   
    }

    public Label getCategoryLabel(HBox itemRow) {
        return (Label) itemRow.getChildren().get(2);
    }

    public Label getNameLabel(HBox itemRow) {
        return (Label) itemRow.getChildren().get(3);
    }

    public Label getSizeLabel(HBox itemRow) {
        return (Label) itemRow.getChildren().get(4);
    }

    public Label getPriceLabel(HBox itemRow) {
        VBox priceBox = (VBox) itemRow.getChildren().get(5);
        return (Label) priceBox.getChildren().get(0);
    }

    public Label getSoldQuantityLabel(HBox itemRow) {
        VBox priceBox = (VBox) itemRow.getChildren().get(5);
        return (Label) priceBox.getChildren().get(1);
    }

    public Label getStatusLabel(HBox itemRow) {
        return (Label) itemRow.getChildren().get(6);
    }
    
    public ScrollPane getItemsScrollPane() {
        return this.itemsScrollPane;
    }
}
