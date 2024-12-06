package View;

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
	private StackPane contentArea;
	private String username;

	public SellerHomeView(Stage primaryStage, String username) {
		this.primaryStage = primaryStage;
		this.username = username;
		initializeComponents();
	}

	public Scene createSellerHomeScene() {
		mainLayout = createMainLayout();
		mainLayout.setStyle("-fx-background-color: #2C3E50;" + "-fx-background-radius: 15px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
		sellerHomeScene = new Scene(mainLayout, 1200, 700);
		sellerHomeScene.setFill(Color.TRANSPARENT);

		primaryStage.initStyle(StageStyle.DECORATED);

		makeDraggable(mainLayout, primaryStage);

		return sellerHomeScene;
	}

	private void initializeComponents() {
		sidebarComponent = new SellerComponentView();
		sidebar = sidebarComponent.getSidebar(username);
	}

	private BorderPane createMainLayout() {
		BorderPane mainLayout = new BorderPane();
		mainLayout.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

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
		searchField.setStyle("-fx-background-color: #F4F6F7;" + "-fx-border-color: #BDC3C7;"
				+ "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;" + "-fx-font-size: 14px;");
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
		header.setStyle("-fx-background-color: #F4F6F7;" + "-fx-padding: 10px;" + "-fx-background-radius: 10px;");

		String[] headers = { "", "Category", "Name", "Size", "Price & Sold", "Status" };

		// Add spacer to match the row's layout
		Region editSpacer = new Region();
		editSpacer.setPrefWidth(10);
		header.getChildren().add(editSpacer);

		for (int i = 0; i < headers.length; i++) {
			Label label = new Label(headers[i]);
			label.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #2C3E50;" + "-fx-font-size: 14px;");

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

		VBox itemsContainer = new VBox(10);
		itemsContainer.setStyle("-fx-background-color: white;");

		
		
		HBox item = createItemRow();
		itemsContainer.getChildren().add(item);
		

		scrollPane.setContent(itemsContainer);
		scrollPane.setPrefHeight(400);

		return scrollPane;
	}

	private Label createStandardLabel(String text, double width, String color) {
		Label label = new Label(text);
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setPrefWidth(width);
		label.setStyle("-fx-text-fill: " + color + ";" + "-fx-font-size: 12px;" + "-fx-alignment: center;");
		return label;
	}

	private Label createEditButton() {
		Label editLabel = new Label("Edit");
		editLabel.setAlignment(Pos.CENTER);
		editLabel.setTextAlignment(TextAlignment.CENTER);
		editLabel.setPrefWidth(COLUMN_WIDTHS[0]);
		editLabel.setStyle("-fx-background-color: #3498DB;" + "-fx-text-fill: white;" + "-fx-background-radius: 20px;"
				+ "-fx-padding: 5px 10px;" + "-fx-alignment: center;");

		// Hover effects
		editLabel.setOnMouseEntered(e -> {
			editLabel.setStyle("-fx-background-color: #2980B9;" + "-fx-text-fill: white;"
					+ "-fx-background-radius: 20px;" + "-fx-padding: 5px 10px;" + "-fx-alignment: center;"
					+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 0);");
		});

		editLabel.setOnMouseExited(e -> {
			editLabel.setStyle("-fx-background-color: #3498DB;" + "-fx-text-fill: white;"
					+ "-fx-background-radius: 20px;" + "-fx-padding: 5px 10px;" + "-fx-alignment: center;");
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
		Label soldLabel = createStandardLabel(soldQuantity > 0 ? String.valueOf(soldQuantity) + " sold" : "Not Sold",
				COLUMN_WIDTHS[4], soldQuantity > 0 ? "#2ECC71" : "#E74C3C");

		soldLabel.setStyle("-fx-font-weight: bold;" + "-fx-background-radius: 15px;" + "-fx-padding: 3px 8px;"
				+ (soldQuantity > 0 ? "-fx-background-color: rgba(46, 204, 113, 0.1);"
						: "-fx-background-color: rgba(231, 76, 60, 0.1);"));

		// Tooltip untuk informasi tambahan
		Tooltip tooltip = new Tooltip(
				soldQuantity > 0 ? "Item has been sold " + soldQuantity + " times" : "Item has not been sold yet");
		Tooltip.install(soldLabel, tooltip);

		return soldLabel;
	}

	private HBox createItemRow() {
		HBox itemRow = new HBox(10);
		itemRow.setAlignment(Pos.CENTER_LEFT);
		itemRow.setPadding(new Insets(10));
		itemRow.setStyle("-fx-background-color: #F4F6F7;" + "-fx-background-radius: 10px;"
				+ "-fx-border-color: #E0E0E0;" + "-fx-border-width: 1px;");

		// Create labels using new methods
		Label editLabel = createEditButton();

		// Add some spacing between Edit and Category
		Region spacer = new Region();
		spacer.setPrefWidth(10);

		// Create other labels
		Label categoryLabel = createCategoryLabel("");
		Label nameLabel = createNameLabel("");
		Label sizeLabel = createSizeLabel("");

		// Price dan Sold Quantity dalam satu VBox
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

	public void updateItemRowLabels(HBox itemRow, String category, String name, String size, String price,
			int soldQuantity, String status) {
		try {
			// Update category label  
			Label categoryLabel = (Label) itemRow.getChildren().get(2);
			categoryLabel.setText(category);

			// Update name label  
			Label nameLabel = (Label) itemRow.getChildren().get(3);
			nameLabel.setText(name);

			// Update size label  
			Label sizeLabel = (Label) itemRow.getChildren().get(4);
			sizeLabel.setText(size);

			// Update price dan sold quantity  
			VBox priceBox = (VBox) itemRow.getChildren().get(5);
			Label priceLabel = (Label) priceBox.getChildren().get(0);

			// Tambahkan $ di depan harga jika belum ada  
			priceLabel.setText(price.startsWith("Rp.") ? price : "Rp. " + price + ",00");

			Label soldQuantityLabel = (Label) priceBox.getChildren().get(1);
			soldQuantityLabel = createSoldQuantityLabel(soldQuantity);
			priceBox.getChildren().set(1, soldQuantityLabel);

			// Update status label  
	        Label statusLabel = (Label) itemRow.getChildren().get(6);  
	        statusLabel.setText(status);
	        switch (status.toLowerCase()) {  
            case "available":  
                statusLabel.setStyle("-fx-text-fill: #2ECC71;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;");  
                break;  
            case "waiting approval":  
                statusLabel.setStyle("-fx-text-fill: #95A5A6;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;");  
                break;  
            case "denied":  
                statusLabel.setStyle("-fx-text-fill: #E74C3C;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;");  
                break;  
            default:  
                statusLabel.setStyle("-fx-text-fill: #95A5A6;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;"  
                        + "-fx-alignment: center;");  
	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error updating item row labels: " + e.getMessage());
		}
	}

	private HBox createTableFooter() {
		HBox footer = new HBox(20);
		footer.setAlignment(Pos.CENTER);
		footer.setPadding(new Insets(15, 10, 15, 10));
		footer.setStyle("-fx-background-color: #F4F6F7;" + "-fx-background-radius: 0 0 10px 10px;"
				+ "-fx-border-color: #E0E0E0;" + "-fx-border-width: 1px;");

		// Total Items
		VBox totalItemsBox = createStatBox("Total Items", "50", "#3498DB");

		// Sold Items
		VBox soldItemsBox = createStatBox("Sold Items", "25", "#2ECC71");

		// Total Transaction
		VBox transactionBox = createStatBox("Total Transaction", "$5,250.00", "#F39C12");

		// Tambahkan border dan padding ke setiap VBox
		HBox[] statBoxes = { wrapStatBoxWithBorder(totalItemsBox), wrapStatBoxWithBorder(soldItemsBox),
				wrapStatBoxWithBorder(transactionBox) };

		footer.getChildren().addAll(statBoxes);
		footer.setSpacing(20);
		HBox.setHgrow(footer, Priority.ALWAYS);

		return footer;
	}

	private HBox wrapStatBoxWithBorder(VBox statBox) {
		HBox borderBox = new HBox(statBox);
		borderBox.setAlignment(Pos.CENTER);
		borderBox.setPadding(new Insets(15));
		borderBox.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10px;"
				+ "-fx-border-color: #E0E0E0;" + "-fx-border-width: 1px;" + "-fx-border-radius: 10px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

		// Pastikan statBox memenuhi ruang
		HBox.setHgrow(borderBox, Priority.ALWAYS);

		return borderBox;
	}

	private VBox createStatBox(String title, String value, String color) {
		VBox statBox = new VBox(10);
		statBox.setAlignment(Pos.CENTER);
		statBox.setPrefWidth(200);
		statBox.setPrefHeight(120);

		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: #7F8C8D;" + "-fx-font-weight: normal;");

		Label valueLabel = new Label(value);
		valueLabel.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;" + "-fx-text-fill: " + color + ";");

		statBox.getChildren().addAll(titleLabel, valueLabel);

		return statBox;
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

	public Button getLogoutButton() {
		if (sidebarComponent == null) {
			System.err.println("Sidebar component is null!");
			return null;
		}

		// Dapatkan logout button dari sidebar component
		Button logoutButton = sidebarComponent.getLogoutButton();

		if (logoutButton == null) {
			System.err.println("Logout button from sidebar component is null!");
		}

		return logoutButton;
	}

	public HBox getcreateItemRow() {
		return createItemRow();
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

}