package TestView;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class testing {
	private double xOffset = 0;
	private double yOffset = 0;
	private Button[] sidebarButtons;
	private Button activeButton;
	private static final double[] COLUMN_WIDTHS = { 80, 160, 200, 120, 90, 150 };

	public testing(Stage primaryStage) {
		BorderPane root = createMainLayout(primaryStage);

		Scene scene = new Scene(root, 1200, 700);
		scene.setFill(Color.TRANSPARENT);

		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);

		makeDraggable(root, primaryStage);

		primaryStage.show();
	}

	// Utility method for button styling
	private String getButtonStyle(String backgroundColor, boolean isActive) {
		return String.format(
				"-fx-background-color: %s;" + "-fx-text-fill: white;" + "-fx-font-size: 14px;"
						+ "-fx-background-radius: 10px;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,%s), %s, 0, 0, 0);",
				backgroundColor, isActive ? "0.3" : "0.1", isActive ? "5" : "3");
	}

	// Utility method for creating standard labels
	private Label createStandardLabel(String text, double width, String color) {
		Label label = new Label(text);
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setPrefWidth(width);
		label.setStyle("-fx-text-fill: " + color + ";" + "-fx-font-size: 12px;" + "-fx-alignment: center;");
		return label;
	}

	private BorderPane createMainLayout(Stage primaryStage) {
		BorderPane mainLayout = new BorderPane();
		mainLayout.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

		// Left Sidebar
		VBox sidebar = createSidebar(primaryStage);
		mainLayout.setLeft(sidebar);

		// Right Content Area
		StackPane contentArea = createContentArea();
		mainLayout.setCenter(contentArea);

		return mainLayout;
	}

	private VBox createSidebar(Stage primaryStage) {
		VBox sidebar = new VBox(10);
		sidebar.setStyle("-fx-background-color: #2C3E50;");
		sidebar.setAlignment(Pos.TOP_CENTER);
		sidebar.setPrefWidth(250);

		// Profile Image
		Image profileimg = new Image("file:resources/Seller_Image_Profile.png");
		ImageView profileImage = new ImageView(profileimg);
		profileImage.setFitHeight(100);
		profileImage.setFitWidth(100);
		profileImage.setPreserveRatio(true);

		// Add circular clip to profile image
		Circle clip = new Circle(50, 50, 50);
		profileImage.setClip(clip);

		Label profileName = new Label("Jimmy Fallon");
		profileName.setTextFill(Color.WHITE);
		profileName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

		// Sidebar Buttons
		String[][] buttonData = { { "View Items", "file:resources/grid.png" },
				{ "Upload Item", "file:resources/upload.png" }, { "View Offered Items", "file:resources/email.png" } };

		VBox buttonContainer = new VBox(10);
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setPadding(new Insets(20, 0, 0, 0));

		// Initialize buttons array
		sidebarButtons = new Button[buttonData.length];

		for (int i = 0; i < buttonData.length; i++) {
			Button btn = createSidebarButton(buttonData[i][0], buttonData[i][1]);

			// Store reference to buttons
			sidebarButtons[i] = btn;

			// Add click handler to set active button
			if (buttonData[i][0].contains("View Items"))
				btn.setOnAction(e -> setActiveButton(btn));

			buttonContainer.getChildren().add(btn);
		}

		// Set first button as active by default
		if (sidebarButtons.length > 0) {
			setActiveButton(sidebarButtons[0]);
		}

		// Logout Button
		Button logoutButton = createLogoutButton();

		// Spacer to push logout button to bottom
		Region spacer = new Region();
		VBox.setVgrow(spacer, Priority.ALWAYS);

		sidebar.getChildren().addAll(profileImage, profileName, buttonContainer, spacer, logoutButton);

		VBox.setMargin(profileImage, new Insets(30, 0, 10, 0));
		VBox.setMargin(logoutButton, new Insets(0, 0, 20, 0));

		return sidebar;
	}

	private Button createSidebarButton(String text, String iconPath) {
		Button btn = new Button(text);
		btn.setPrefWidth(220);
		btn.setPrefHeight(50);

		Image iconimg = new Image(iconPath);
		ImageView icon = new ImageView(iconimg);
		icon.setFitHeight(25);
		icon.setFitWidth(25);

		btn.setGraphic(icon);
		btn.setGraphicTextGap(15);

		// Default styling
		btn.setStyle(getButtonStyle("#34495E", false));

		// Hover effects
		btn.setOnMouseEntered(e -> {
			if (btn != activeButton) {
				btn.setStyle(getButtonStyle("#2C3E50", false));
			}
		});

		btn.setOnMouseExited(e -> {
			if (btn != activeButton) {
				btn.setStyle(getButtonStyle("#34495E", false));
			}
		});

		return btn;
	}

	private Button createLogoutButton() {
		Button logoutBtn = new Button("Logout");
		logoutBtn.setPrefWidth(220);
		logoutBtn.setPrefHeight(50);

		Image logoutIcon = new Image("file:resources/logout.png");
		ImageView icon = new ImageView(logoutIcon);
		icon.setFitHeight(25);
		icon.setFitWidth(25);

		logoutBtn.setGraphic(icon);
		logoutBtn.setGraphicTextGap(15);

		logoutBtn.setStyle(getButtonStyle("#34495E", false));

		logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle(getButtonStyle("#34495E", true)));

		logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle(getButtonStyle("#34495E", false)));

		logoutBtn.setOnAction(e -> System.out.println("Logout clicked"));

		return logoutBtn;
	}

	private void setActiveButton(Button activeBtn) {
		for (Button btn : sidebarButtons) {
			btn.setStyle(getButtonStyle("#34495E", false));
		}

		activeBtn.setStyle(getButtonStyle("#2980B9", true));
		this.activeButton = activeBtn;
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
	    searchField.setStyle(  
	        "-fx-background-color: #F4F6F7;" +   
	        "-fx-border-color: #BDC3C7;" +  
	        "-fx-border-radius: 30px;" +   
	        "-fx-background-radius: 30px;" +   
	        "-fx-font-size: 14px;"  
	    );  
	    searchField.setPrefSize(300, 40);  

	    Label titleLabel = new Label("My Items CalouseIF");  
	    titleLabel.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");  

	    // Table Header  
	    HBox tableHeader = createTableHeader();  

	    // Scrollable Items  
	    ScrollPane itemsScrollPane = createItemsScrollPane();  

	    // Table Footer with Statistics  
	    HBox tableFooter = createTableFooter();  

	    overviewPanel.getChildren().addAll(  
	        searchField,   
	        titleLabel,   
	        tableHeader,   
	        itemsScrollPane,   
	        tableFooter  
	    );  

	    return overviewPanel;  
	}

	private HBox createTableHeader() {  
	    HBox header = new HBox(10);  
	    header.setAlignment(Pos.CENTER_LEFT);  
	    header.setPadding(new Insets(10));  
	    header.setStyle(  
	        "-fx-background-color: #F4F6F7;" +  
	        "-fx-padding: 10px;" +  
	        "-fx-background-radius: 10px;"  
	    );  
	    
	    String[] headers = {"", "Category", "Name", "Size", "Price & Sold", "Status"};  
	    
	    // Add spacer to match the row's layout  
	    Region editSpacer = new Region();  
	    editSpacer.setPrefWidth(10);  
	    header.getChildren().add(editSpacer);  
	    
	    for (int i = 0; i < headers.length; i++) {  
	        Label label = new Label(headers[i]);  
	        label.setStyle(  
	            "-fx-font-weight: bold;" +  
	            "-fx-text-fill: #2C3E50;" +  
	            "-fx-font-size: 14px;"  
	        );  
	        
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

		// Create 10 sample items
		for (int i = 0; i < 10; i++) {
			HBox item = createItemRow();
			itemsContainer.getChildren().add(item);
		}

		scrollPane.setContent(itemsContainer);
		scrollPane.setPrefHeight(400);

		return scrollPane;
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
        
        switch (status.toLowerCase()) {  
            case "Available":  
            	statusLabel.setStyle(  
                        "-fx-text-fill: #2ECC71;" +  
                        "-fx-font-size: 12px;" +  
                        "-fx-font-weight: bold;" 
                    );  
                break;  
            case "Waiting Approval":  
            	statusLabel.setStyle(  
                        "-fx-text-fill: #95A5A6;" +   
                        "-fx-font-size: 12px;" +  
                        "-fx-font-weight: bold;" 
                    );  
                break;  
            case "denied":  
            	statusLabel.setStyle(  
                        "-fx-text-fill: #E74C3C;" +    
                        "-fx-font-size: 12px;" +  
                        "-fx-font-weight: bold;" 
                    );  
                    break;  
                default:  
                    statusLabel.setStyle(  
                        "-fx-text-fill: #95A5A6;" +  
                        "-fx-font-size: 12px;" +  
                        "-fx-font-weight: bold;" +  
                        "-fx-alignment: center;"  
                    );  
            }  
            
            return statusLabel;  
        }
	
	private Label createSoldQuantityLabel(int soldQuantity) {  
	    Label soldLabel = createStandardLabel(  
	        soldQuantity > 0 ?   
	            String.valueOf(soldQuantity) + " sold" :   
	            "Not Sold",   
	        COLUMN_WIDTHS[4],   
	        soldQuantity > 0 ? "#2ECC71" : "#E74C3C"  
	    );  
	    
	    soldLabel.setStyle(  
	        "-fx-font-weight: bold;" +  
	        "-fx-background-radius: 15px;" +  
	        "-fx-padding: 3px 8px;" +  
	        (soldQuantity > 0 ?   
	            "-fx-background-color: rgba(46, 204, 113, 0.1);" :  
	            "-fx-background-color: rgba(231, 76, 60, 0.1);"  
	        )  
	    );  
	    
	    // Tooltip untuk informasi tambahan  
	    Tooltip tooltip = new Tooltip(  
	        soldQuantity > 0 ?   
	            "Item has been sold " + soldQuantity + " times" :   
	            "Item has not been sold yet"  
	    );  
	    Tooltip.install(soldLabel, tooltip);  
	    
	    return soldLabel;  
	}

	private HBox createItemRow() {  
	    HBox itemRow = new HBox(10);  
	    itemRow.setAlignment(Pos.CENTER_LEFT);  
	    itemRow.setPadding(new Insets(10));  
	    itemRow.setStyle(  
	        "-fx-background-color: #F4F6F7;" +  
	        "-fx-background-radius: 10px;" +  
	        "-fx-border-color: #E0E0E0;" +  
	        "-fx-border-width: 1px;"  
	    );  
	    
	    // Create labels using new methods  
	    Label editLabel = createEditButton();  
	    
	    // Add some spacing between Edit and Category  
	    Region spacer = new Region();  
	    spacer.setPrefWidth(10);  
	    
	    // Create other labels  
	    Label categoryLabel = createCategoryLabel("Clothes");  
	    Label nameLabel = createNameLabel("BAJU SUNIB ALA ALAAAAAAAAAAA");  
	    Label sizeLabel = createSizeLabel("XXL");  
	    
	    // Price dan Sold Quantity dalam satu VBox  
	    VBox priceBox = new VBox(5);  
	    priceBox.setAlignment(Pos.CENTER);  
	    
	    Label priceLabel = createPriceLabel("$10.99");  
	    
	    // Contoh dengan berbagai kondisi penjualan  
	    Label soldQuantityLabel1 = createSoldQuantityLabel(25);  // Sudah terjual  
	    Label soldQuantityLabel2 = createSoldQuantityLabel(0);   // Belum terjual  
	    
	    priceBox.getChildren().addAll(priceLabel, soldQuantityLabel2);  
	    
	    Label statusLabel = createStatusLabel("Waiting Approval");  
	    
	    // Add components to row  
	    itemRow.getChildren().addAll(  
	        editLabel,   
	        spacer,   
	        categoryLabel,   
	        nameLabel,   
	        sizeLabel,   
	        priceBox,   
	        statusLabel  
	    );  
	    
	    return itemRow;  
	}  
	
	private HBox createTableFooter() {  
	    HBox footer = new HBox(20);  
	    footer.setAlignment(Pos.CENTER);  
	    footer.setPadding(new Insets(15, 10, 15, 10));  
	    footer.setStyle(  
	        "-fx-background-color: #F4F6F7;" +  
	        "-fx-background-radius: 0 0 10px 10px;" +  
	        "-fx-border-color: #E0E0E0;" +  
	        "-fx-border-width: 1px;"  
	    );  

	    // Total Items  
	    VBox totalItemsBox = createStatBox(  
	        "Total Items",   
	        "50",   
	        "#3498DB"  
	    );  

	    // Sold Items  
	    VBox soldItemsBox = createStatBox(  
	        "Sold Items",   
	        "25",   
	        "#2ECC71"  
	    );  

	    // Total Transaction  
	    VBox transactionBox = createStatBox(  
	        "Total Transaction",   
	        "$5,250.00",   
	        "#F39C12"  
	    );  

	    // Tambahkan border dan padding ke setiap VBox  
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
	
	private HBox wrapStatBoxWithBorder(VBox statBox) {  
	    HBox borderBox = new HBox(statBox);  
	    borderBox.setAlignment(Pos.CENTER);  
	    borderBox.setPadding(new Insets(15));  
	    borderBox.setStyle(  
	        "-fx-background-color: white;" +  
	        "-fx-background-radius: 10px;" +  
	        "-fx-border-color: #E0E0E0;" +  
	        "-fx-border-width: 1px;" +  
	        "-fx-border-radius: 10px;" +  
	        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);"  
	    );  

	    // Pastikan statBox memenuhi ruang  
	    HBox.setHgrow(borderBox, Priority.ALWAYS);  
	    
	    return borderBox;  
	}

	private VBox createStatBox(String title, String value, String color) {  
	    VBox statBox = new VBox(10);  // Tambah spasi antara title dan value  
	    statBox.setAlignment(Pos.CENTER);  
	    statBox.setPrefWidth(200);  // Tambah lebar  
	    statBox.setPrefHeight(120);  // Tambah tinggi  

	    Label titleLabel = new Label(title);  
	    titleLabel.setStyle(  
	        "-fx-font-size: 14px;" +  // Perbesar ukuran font  
	        "-fx-text-fill: #7F8C8D;" +  
	        "-fx-font-weight: normal;"  
	    );  

	    Label valueLabel = new Label(value);  
	    valueLabel.setStyle(  
	        "-fx-font-size: 24px;" +  // Perbesar ukuran font value  
	        "-fx-font-weight: bold;" +  
	        "-fx-text-fill: " + color + ";"  
	    );  

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
}