package View;

import Model.Transaction;
import Controller.CustomerHistoryController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerHistoryView {
    private Stage primaryStage;
    private String username;
    private CustomerHistoryController controller;
    private TableView<Transaction> transactionTable;

    public CustomerHistoryView(Stage primaryStage, CustomerHistoryController controller, String username) {
    	this.username = username;
        this.primaryStage = primaryStage;
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #F0F4F8;");

        // Header
        HBox headerContainer = createHeaderSection();

        // Transaction Table
        transactionTable = createTransactionTable();
        VBox.setVgrow(transactionTable, Priority.ALWAYS);

        // Add to layout
        mainLayout.getChildren().addAll(headerContainer, transactionTable);

        // Scene
        Scene scene = new Scene(mainLayout, 1200, 700);

        // Remove default stylesheets to avoid conflicts
        scene.getStylesheets().clear();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Purchase History");
    }

    private HBox createHeaderSection() {
        HBox headerContainer = new HBox(10);
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        headerContainer.setPadding(new Insets(0, 0, 10, 0));

        Label historyTitle = new Label("Purchase History " + username);
        historyTitle.setStyle(
            "-fx-font-size: 22px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #2C3E50;"
        );

        Button backButton = new Button("Back");
        backButton.setStyle(
            "-fx-background-color: #3498DB;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 8 15 8 15;" +
            "-fx-background-radius: 5px;" +
            "-fx-cursor: hand;"
        );
        backButton.setOnAction(e -> {
            primaryStage.close();
            controller.showhomescene();
        });

        HBox leftHeader = new HBox(historyTitle);
        HBox.setHgrow(leftHeader, Priority.ALWAYS);

        headerContainer.getChildren().addAll(leftHeader, backButton);
        return headerContainer;
    }

    private TableView<Transaction> createTransactionTable() {
        TableView<Transaction> table = new TableView<>();
        table.setStyle(
            "-fx-background-color: white;" +
            "-fx-control-inner-background: white;" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 0;"
        );

        // Disable default scrollbar styling
        table.lookupAll(".scroll-bar").forEach(node -> node.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background-radius: 0;"
        ));

        // Excel-like style for borders
        table.setStyle("-fx-border-color: #BDC3C7;" + "-fx-border-width: 1;");

        // Columns with improved styling and sizing
        TableColumn<Transaction, Integer> transactionIdCol = new TableColumn<>("Transaction ID");
        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        transactionIdCol.setStyle("-fx-alignment: CENTER; -fx-border-color: #BDC3C7; -fx-border-width: 0 1px 0 0;");
        transactionIdCol.setPrefWidth(150);

        TableColumn<Transaction, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(cellData ->
            cellData.getValue().getItem() != null ?
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getItem().getName()) :
            new javafx.beans.property.SimpleStringProperty("")
        );
        itemNameCol.setStyle("-fx-alignment: CENTER; -fx-border-color: #BDC3C7; -fx-border-width: 0 1px 0 0;");
        itemNameCol.setPrefWidth(250);

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData ->
            cellData.getValue().getItem() != null ?
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getItem().getCategory()) :
            new javafx.beans.property.SimpleStringProperty("")
        );
        categoryCol.setStyle("-fx-border-color: #BDC3C7; -fx-border-width: 0 1px 0 0;");
        categoryCol.setPrefWidth(200);

        TableColumn<Transaction, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(cellData ->
            cellData.getValue().getItem() != null ?
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getItem().getSize()) :
            new javafx.beans.property.SimpleStringProperty("")
        );
        sizeCol.setStyle("-fx-alignment: CENTER; -fx-border-color: #BDC3C7; -fx-border-width: 0 1px 0 0;");
        sizeCol.setPrefWidth(100);

        TableColumn<Transaction, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData ->
            cellData.getValue().getItem() != null ?
            new javafx.beans.property.SimpleStringProperty(
                String.format("Rp %,d", cellData.getValue().getItem().getPrice())
            ) :
            new javafx.beans.property.SimpleStringProperty("")
        );
        priceCol.setStyle("-fx-alignment: CENTER; -fx-border-color: #BDC3C7; -fx-border-width: 0 1px 0 0;");
        priceCol.setPrefWidth(200);

        // Stretch columns to fill width of table
        transactionIdCol.setResizable(true);
        itemNameCol.setResizable(true);
        categoryCol.setResizable(true);
        sizeCol.setResizable(true);
        priceCol.setResizable(true);

        // Add columns to table
        table.getColumns().addAll(transactionIdCol, itemNameCol, categoryCol, sizeCol, priceCol);

        // Set items from controller
        table.setItems(controller.getCustomerTransactions());

        // Table placeholder
        table.setPlaceholder(new Label("No transactions found"));
        
     // Disable row selection
        table.setSelectionModel(null);  // Disable row selection

        // Stretch TableView to fill width of container
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    public void show() {
        primaryStage.show();
    }
}
