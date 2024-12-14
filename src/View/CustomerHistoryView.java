package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerHistoryView {

    private Stage primaryStage;
    private BorderPane mainLayout;

    public CustomerHistoryView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initialize();
    }

    private void initialize() {
        // Main layout
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #F5F5F5;"); // Light background color

        // Header Section
        HBox headerContainer = new HBox();
        headerContainer.setPadding(new Insets(0, 0, 20, 0));
        headerContainer.setAlignment(Pos.CENTER_LEFT);

        // History title
        Label historyTitle = new Label("Purchase History");
        historyTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;"); // Light blue color

        // Back Button (aligned to the right)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: #333333; -fx-border-radius: 5px;");
        backButton.setOnAction(e -> primaryStage.close()); // Example: Close the window on click

        HBox leftHeader = new HBox(10, historyTitle);
        HBox.setHgrow(leftHeader, Priority.ALWAYS);

        headerContainer.getChildren().addAll(leftHeader, backButton);

        // Purchase History Table Container
        VBox historyContainer = new VBox(15);
        historyContainer.setPadding(new Insets(10));
        historyContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 5px; -fx-border-color: #CCCCCC; -fx-border-width: 1px;");

        // Add Table Header
        historyContainer.getChildren().add(createTableHeader());

        // Add 20 dummy transaction rows
        for (int i = 1; i <= 20; i++) {
            String transactionId = "TID" + i;
            String itemName = "Item " + i;
            String category = "Category " + i;
            String size = "Size " + i;
            String price = "$" + (i * 10);
            historyContainer.getChildren().add(createTransactionRow(transactionId, itemName, category, size, price));
        }

        // ScrollPane for purchase history
        ScrollPane scrollPane = new ScrollPane(historyContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Add components to main layout
        mainLayout.setTop(headerContainer);
        mainLayout.setCenter(scrollPane);

        // Set scene with size 1200x700
        Scene scene = new Scene(mainLayout, 1200, 700);
        scene.setFill(javafx.scene.paint.Color.web("#FFFFFF"));  // Light background color
        primaryStage.setScene(scene);
    }

    private HBox createTableHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #4A90E2; -fx-border-radius: 5px;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label transactionIdLabel = new Label("Transaction ID");
        transactionIdLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-pref-width: 150px;");

        Label itemNameLabel = new Label("Item Name");
        itemNameLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-pref-width: 200px;");

        Label categoryLabel = new Label("Category");
        categoryLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-pref-width: 150px;");

        Label sizeLabel = new Label("Size");
        sizeLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-pref-width: 100px;");

        Label priceLabel = new Label("Price");
        priceLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-pref-width: 100px;");

        header.getChildren().addAll(transactionIdLabel, itemNameLabel, categoryLabel, sizeLabel, priceLabel);
        return header;
    }

    private HBox createTransactionRow(String transactionId, String itemName, String category, String size, String price) {
        HBox row = new HBox();
        row.setPadding(new Insets(10));
        row.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 0 0 1px 0;");
        row.setAlignment(Pos.CENTER_LEFT);

        Label transactionIdLabel = new Label(transactionId);
        transactionIdLabel.setStyle("-fx-text-fill: #333333; -fx-pref-width: 150px;");

        Label itemNameLabel = new Label(itemName);
        itemNameLabel.setStyle("-fx-text-fill: #333333; -fx-pref-width: 200px;");

        Label categoryLabel = new Label(category);
        categoryLabel.setStyle("-fx-text-fill: #666666; -fx-pref-width: 150px;");

        Label sizeLabel = new Label(size);
        sizeLabel.setStyle("-fx-text-fill: #666666; -fx-pref-width: 100px;");

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-text-fill: #333333; -fx-pref-width: 100px;");

        row.getChildren().addAll(transactionIdLabel, itemNameLabel, categoryLabel, sizeLabel, priceLabel);
        return row;
    }

    public void show() {
        primaryStage.show();
    }
}
