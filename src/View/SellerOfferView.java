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

public class SellerOfferView {
	private static final double[] COLUMN_WIDTHS = { 100, 100, 150, 150, 150, 150, 150 };
	private double xOffset = 0;
	private double yOffset = 0;
	private Stage primaryStage;
	private Scene offeredItemsScene;
	private BorderPane mainLayout;
	private SellerComponentView sidebarComponent;
	private VBox sidebar;
	private HBox footer;
	private StackPane contentArea;
	private ScrollPane itemsScrollPane;
	private String username;
	private Button buttonlogout;

	public SellerOfferView(Stage primaryStage, String username, SellerComponentView sidebarComponent) {
		this.primaryStage = primaryStage;
		this.username = username;
		this.sidebarComponent = sidebarComponent;
		initializeComponents();
		
		// Debug print  
	    System.out.println("ItemsScrollPane after init: " + itemsScrollPane);  
	}

	public Scene createOfferedItemsScene() {
		mainLayout = createMainLayout();
		mainLayout.setStyle("-fx-background-color: #2C3E50;" + "-fx-background-radius: 15px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
		offeredItemsScene = new Scene(mainLayout, 1200, 700);
		offeredItemsScene.setFill(Color.TRANSPARENT);

		makeDraggable(mainLayout, primaryStage);

		return offeredItemsScene;
	}

	private void initializeComponents() {
		sidebar = sidebarComponent.getSidebar(username, "View Offered Items");
		itemsScrollPane = createItemsScrollPane();  
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
	    searchField.setPromptText("Search offered items...");  
	    searchField.setStyle("-fx-background-color: #F4F6F7;" + "-fx-border-color: #BDC3C7;"  
	            + "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;" + "-fx-font-size: 14px;");  
	    searchField.setPrefSize(300, 40);  

	    Label titleLabel = new Label("Offered Items");  
	    titleLabel.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");  

	    // Table Header  
	    HBox tableHeader = createTableHeader();  

	    // Gunakan itemsScrollPane yang sudah ada, bukan membuat baru  
	    // this.itemsScrollPane = createItemsScrollPane();  // Hapus baris ini  

	    // Pastikan content dari ScrollPane adalah VBox yang bisa diupdate  
	    VBox itemsContainer = (VBox) itemsScrollPane.getContent();  
	    if (itemsContainer == null) {  
	        itemsContainer = new VBox(10);  
	        itemsContainer.setStyle("-fx-background-color: white;");  
	        itemsScrollPane.setContent(itemsContainer);  
	    }  

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

	    // Sesuaikan dengan urutan kolom baru  
	    String[] headers = {   
	        "Accept",   
	        "Decline",   
	        "Name Item",   
	        "Category",   
	        "Initial Price",   
	        "Highest Offer"  
	    };  

	    for (int i = 0; i < headers.length; i++) {  
	        Label label = new Label(headers[i]);  
	        label.setStyle("-fx-font-weight: bold;" +   
	                       "-fx-text-fill: #2C3E50;" +   
	                       "-fx-font-size: 14px;");  

	        // Gunakan lebar kolom yang sama dengan row  
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
	    scrollPane.setId("offered-items-scroll-pane");  

	    VBox itemsContainer = new VBox(10);  
	    itemsContainer.setStyle("-fx-background-color: white;");  

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

	/**
	 * Creates a styled item row HBox for offered items.
	 *
	 * @return a styled HBox representing an offered item row
	 */
	public HBox createItemRow() {  
	    HBox itemRow = new HBox(10);  
	    itemRow.setAlignment(Pos.CENTER_LEFT);  
	    itemRow.setPadding(new Insets(10));  
	    itemRow.setStyle("-fx-background-color: #F4F6F7;" +   
	                     "-fx-background-radius: 10px;" +   
	                     "-fx-border-color: #E0E0E0;" +   
	                     "-fx-border-width: 1px;");  

	    // Tombol Accept  
	    Button acceptBtn = new Button("Accept");  
	    acceptBtn.setStyle("-fx-background-color: #2ECC71;" +   
	                       "-fx-text-fill: white;" +   
	                       "-fx-background-radius: 20px;" +   
	                       "-fx-padding: 5px 10px;");  
	    acceptBtn.setPrefWidth(COLUMN_WIDTHS[0]);  

	    // Tombol Decline  
	    Button declineBtn = new Button("Decline");  
	    declineBtn.setStyle("-fx-background-color: #E74C3C;" +   
	                        "-fx-text-fill: white;" +   
	                        "-fx-background-radius: 20px;" +   
	                        "-fx-padding: 5px 10px;");  
	    declineBtn.setPrefWidth(COLUMN_WIDTHS[1]);  

	    // Label untuk setiap kolom  
	    Label nameItemLabel = createStandardLabel("", COLUMN_WIDTHS[2], "#2C3E50");  
	    Label categoryLabel = createStandardLabel("", COLUMN_WIDTHS[3], "#2C3E50");  
	    Label initialPriceLabel = createStandardLabel("", COLUMN_WIDTHS[4], "#2C3E50");  
	    
	    // VBox untuk Offered Price dan Customer  
	    VBox offeredPriceCustomerBox = new VBox(2);  
	    offeredPriceCustomerBox.setAlignment(Pos.CENTER);  
	    
	    Label offeredPriceLabel = createStandardLabel("", COLUMN_WIDTHS[5], "#E67E22");  
	    Label customerLabel = createStandardLabel("", COLUMN_WIDTHS[5], "#2C3E50");  
	    customerLabel.setStyle(customerLabel.getStyle() +   
	                           "-fx-font-size: 10px;" +   
	                           "-fx-text-fill: #7F8C8D;");  
	    
	    offeredPriceCustomerBox.getChildren().addAll(offeredPriceLabel, customerLabel);  

	    // Tambahkan komponen ke row  
	    itemRow.getChildren().addAll(  
	        acceptBtn,   
	        declineBtn,   
	        nameItemLabel,   
	        categoryLabel,   
	        initialPriceLabel,   
	        offeredPriceCustomerBox  
	    );  

	    return itemRow;  
	}  

	/**
	 * Updates the labels within an offered item row HBox.
	 *
	 * @param itemRow      the HBox representing the item row
	 * @param name         the name of the item
	 * @param category     the category of the item
	 * @param size         the size of the item
	 * @param initialPrice the initial price of the item
	 * @param offeredPrice the offered price by the buyer
	 */
	public void updateItemRowLabels(  
		    HBox itemRow,   
		    String itemName,   
		    String category,   
		    String initialPrice,   
		    String offeredPrice,   
		    String customerName  
		) {  
		    try {  
		        // Debug print  
		        System.out.println("Updating Item Row Labels:");  
		        System.out.println("Item Name: " + itemName);  
		        System.out.println("Category: " + category);  
		        System.out.println("Initial Price: " + initialPrice);  
		        System.out.println("Offered Price: " + offeredPrice);  
		        System.out.println("Customer Name: " + customerName);  

		        // Gunakan getter baru untuk mengakses label  
		        Label nameItemLabel = getNameItemLabel(itemRow);  
		        Label categoryLabel = getCategoryLabel(itemRow);  
		        Label initialPriceLabel = getInitialPriceLabel(itemRow);  
		        Label offeredPriceLabel = getOfferedPriceLabel(itemRow);  
		        Label customerLabel = getCustomerLabel(itemRow);  

		        // Pastikan label tidak null sebelum di-set  
		        if (nameItemLabel != null) {  
		            nameItemLabel.setText(itemName);  
		        } else {  
		            System.err.println("Name Item Label is NULL");  
		        }  

		        if (categoryLabel != null) {  
		            categoryLabel.setText(category);  
		        } else {  
		            System.err.println("Category Label is NULL");  
		        }  

		        if (initialPriceLabel != null) {  
		            initialPriceLabel.setText(initialPrice);  
		        } else {  
		            System.err.println("Initial Price Label is NULL");  
		        }  

		        if (offeredPriceLabel != null) {  
		            offeredPriceLabel.setText(offeredPrice);  
		        } else {  
		            System.err.println("Offered Price Label is NULL");  
		        }  

		        if (customerLabel != null) {  
		            customerLabel.setText(customerName);  
		        } else {  
		            System.err.println("Customer Label is NULL");  
		        }  

		    } catch (Exception e) {  
		        System.err.println("Error updating item row labels: " + e.getMessage());  
		        e.printStackTrace();  
		    }  
		}   

	private HBox createTableFooter() {
		footer = new HBox(20);
		footer.setAlignment(Pos.CENTER);
		footer.setPadding(new Insets(15, 10, 15, 10));
		footer.setStyle("-fx-background-color: #F4F6F7;" + "-fx-background-radius: 0 0 10px 10px;"
				+ "-fx-border-color: #E0E0E0;" + "-fx-border-width: 1px;");

		// Total Offers
		VBox totalOffersBox = createStatBox("Total Offers", "0", "#3498DB");
		Label totalOffersLabel = (Label) totalOffersBox.getChildren().get(0); // First child (title label)
		totalOffersLabel.setStyle("-fx-font-weight: bold;"); // Make title bold
		Label totalOffersValueLabel = (Label) totalOffersBox.getChildren().get(1);
		totalOffersValueLabel.setId("total-offers-label");

		// Highest Offer
		VBox highestOfferBox = createStatBox("Highest Offer", "Rp 0", "#E67E22");
		Label highestOfferTitleLabel = (Label) highestOfferBox.getChildren().get(0);
		highestOfferTitleLabel.setStyle("-fx-font-weight: bold;"); // Make title bold
		Label highestOfferValueLabel = (Label) highestOfferBox.getChildren().get(1);
		highestOfferValueLabel.setId("highest-offer-label");

		// Average Offer
		VBox averageOfferBox = createStatBox("Average Offer", "Rp 0", "#1ABC9C");
		Label averageOfferTitleLabel = (Label) averageOfferBox.getChildren().get(0);
		averageOfferTitleLabel.setStyle("-fx-font-weight: bold;"); // Make title bold
		Label averageOfferValueLabel = (Label) averageOfferBox.getChildren().get(1);
		averageOfferValueLabel.setId("average-offer-label");

		// Wrap each stat box with a border
		HBox[] statBoxes = { wrapStatBoxWithBorder(totalOffersBox), wrapStatBoxWithBorder(highestOfferBox),
				wrapStatBoxWithBorder(averageOfferBox) };

		footer.getChildren().addAll(statBoxes);
		footer.setSpacing(20);
		HBox.setHgrow(footer, Priority.ALWAYS);

		return footer;
	}

	// Method to update footer statistics
	public void updateFooterStatistics(String totalOffers, String highestOffer, String averageOffer) {
		Platform.runLater(() -> {
			// Find labels in the footer
			Label totalOffersLabel = (Label) footer.lookup("#total-offers-label");
			Label highestOfferLabel = (Label) footer.lookup("#highest-offer-label");
			Label averageOfferLabel = (Label) footer.lookup("#average-offer-label");

			// Update labels if found
			if (totalOffersLabel != null) {
				totalOffersLabel.setText(totalOffers);
			}
			if (highestOfferLabel != null) {
				highestOfferLabel.setText(highestOffer);
			}
			if (averageOfferLabel != null) {
				averageOfferLabel.setText(averageOffer);
			}
		});
	}

	// Helper method to create a statistic box
	private VBox createStatBox(String title, String value, String color) {
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);

		// Title label
		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

		// Value label
		Label valueLabel = new Label(value);
		valueLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

		box.getChildren().addAll(titleLabel, valueLabel);
		return box;
	}

	private HBox wrapStatBoxWithBorder(VBox statBox) {
		HBox borderBox = new HBox(statBox);
		borderBox.setAlignment(Pos.CENTER);
		borderBox.setPadding(new Insets(15));
		borderBox.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10px;"
				+ "-fx-border-color: #E0E0E0;" + "-fx-border-width: 1px;" + "-fx-border-radius: 10px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

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

	public Scene getOfferedItemsScene() {
		return offeredItemsScene;
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

	public ScrollPane getItemsScrollPane() {
		return this.itemsScrollPane;
	}

	/**
	 * Provides a method to create a new styled offered item row.
	 *
	 * @return a new HBox representing a styled offered item row
	 */
	public HBox getcreateItemRow() {
		return createItemRow();
	}

	// Label retrieval methods to encapsulate the UI structure

	public Button getLogoutButton() {
		return sidebarComponent.getLogoutButton();
	}

	// Tambahkan getter untuk label baru  
	public Label getNameItemLabel(HBox itemRow) {  
	    return (Label) itemRow.getChildren().get(2);  
	}  

	public Label getCategoryLabel(HBox itemRow) {  
	    return (Label) itemRow.getChildren().get(3);  
	}  

	public Label getInitialPriceLabel(HBox itemRow) {  
	    return (Label) itemRow.getChildren().get(4);  
	}  

	// Update getter methods  
	public Label getOfferedPriceLabel(HBox itemRow) {  
	    VBox offeredPriceCustomerBox = (VBox) itemRow.getChildren().get(5);  
	    return (Label) offeredPriceCustomerBox.getChildren().get(0);  
	}  

	public Label getCustomerLabel(HBox itemRow) {  
	    VBox offeredPriceCustomerBox = (VBox) itemRow.getChildren().get(5);  
	    return (Label) offeredPriceCustomerBox.getChildren().get(1);  
	}    

	

	// Tambahkan getter untuk tombol  
	public Button getAcceptButton(HBox itemRow) {  
	    return (Button) itemRow.getChildren().get(0);  
	}  

	public Button getDeclineButton(HBox itemRow) {  
	    return (Button) itemRow.getChildren().get(1);  
	} 
	
	public VBox getItemsContainer() {  
	    if (itemsScrollPane == null) {  
	        return null;  
	    }  
	    
	    // Pastikan content adalah VBox  
	    if (!(itemsScrollPane.getContent() instanceof VBox)) {  
	        VBox itemsContainer = new VBox(10);  
	        itemsContainer.setStyle("-fx-background-color: white;");  
	        itemsScrollPane.setContent(itemsContainer);  
	    }  
	    
	    return (VBox) itemsScrollPane.getContent();  
	}
}
