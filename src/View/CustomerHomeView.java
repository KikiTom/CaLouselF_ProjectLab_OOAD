package View;

import java.util.List;

import Controller.CustomerHomeController;
import Model.Item;
import Service.ItemService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class CustomerHomeView extends BorderPane {
	private CustomerHomeController controller;
	private CustomerComponentView navbarView;
	private StackPane chosenItemCard;
	private Item currentChosenItem = null;
	private String username;
	private ItemService itemService;

	// Konstanta warna
	private static final String PRIMARY_COLOR = "#3498DB";
	private static final String SECONDARY_COLOR = "#2C3E50";
	private static final String ACCENT_COLOR = "#2ECC71";
	private static final String BACKGROUND_COLOR = "#ECF0F1";
	private static final String TEXT_COLOR = "#34495E";

	public CustomerHomeView(String username, ItemService itemService) {
		this.username = username;
		this.itemService = itemService;

		// Set overall style for the entire view
		setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";" + "-fx-font-family: 'Segoe UI';");

		initializeView();
	}

	private void initializeView() {
		// Set overall layout to 1200x700
		setPrefSize(1200, 700);
		setMaxSize(1200, 700);

		// Left Side
		VBox leftSide = createLeftSide();
		setLeft(leftSide);

		// Center Section
		VBox centerSection = createCenterSection();
		setCenter(centerSection);
	}

	private VBox createLeftSide() {
		VBox leftSide = new VBox(15);
		leftSide.setPrefWidth(350);
		leftSide.setMaxWidth(350);
		leftSide.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0.2, 0, 5);");
		leftSide.setPadding(new Insets(25));

		// Combined Title and Search Box
		VBox titleAndSearchBox = createTitleAndSearchBox();

		// Chosen Item Row
		VBox chosenItemRow = createChosenItemRow();

		// Add components
		leftSide.getChildren().addAll(titleAndSearchBox, chosenItemRow);

		// Ensure proper growth
		VBox.setVgrow(titleAndSearchBox, Priority.NEVER);
		VBox.setVgrow(chosenItemRow, Priority.ALWAYS);

		return leftSide;
	}

	private VBox createTitleAndSearchBox() {
		VBox container = new VBox(15);
		container.setAlignment(Pos.CENTER);

		// Title
		Label titleLabel = new Label("CaLouselF");
		titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
		titleLabel.setTextFill(Color.web(PRIMARY_COLOR));

		// Subtitle
		Label subtitleLabel = new Label("Marketplace");
		subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 16));
		subtitleLabel.setTextFill(Color.web(SECONDARY_COLOR));

		// Search Box
		HBox searchBox = createModernSearchBox();

		// Add to container
		container.getChildren().addAll(titleLabel, subtitleLabel, searchBox);

		return container;
	}

	private VBox createChosenItemRow() {
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(20));

		// Inisialisasi chosenItemCard dengan status awal kosong
		chosenItemCard = createEmptyChosenItemCard();

		container.getChildren().add(chosenItemCard);
		return container;
	}

	// Metode untuk membuat kartu item kosong
	private StackPane createEmptyChosenItemCard() {
		StackPane cardContainer = new StackPane();

		VBox card = new VBox(15);
		card.setAlignment(Pos.CENTER);
		card.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.2, 0, 3);");
		card.setPadding(new Insets(20));
		card.setPrefHeight(450);

		// Pesan placeholder
		Label placeholderLabel = new Label("No Item Selected");
		placeholderLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		placeholderLabel.setTextFill(Color.web("#BDC3C7"));

		// Ikon placeholder
		ImageView placeholderIcon = new ImageView(new Image("file:resources/image.png"));
		placeholderIcon.setFitWidth(150);
		placeholderIcon.setFitHeight(150);
		placeholderIcon.setOpacity(0.5);

		card.getChildren().addAll(placeholderIcon, placeholderLabel);

		// Tambahkan card ke container
		cardContainer.getChildren().add(card);

		return cardContainer;
	}

	private HBox createModernSearchBox() {
		HBox searchBox = new HBox(10);
		searchBox.setAlignment(Pos.CENTER);

		TextField searchField = new TextField();
		searchField.setPromptText("Search items...");
		searchField.setPrefWidth(250);
		searchField.setStyle("-fx-background-radius: 25;" + "-fx-background-color: white;" + "-fx-border-color: "
				+ PRIMARY_COLOR + ";" + "-fx-border-radius: 25;" + "-fx-border-width: 2;" + "-fx-padding: 10px;"
				+ "-fx-font-size: 14px;");

		Button searchButton = new Button("Search");
		searchButton.setPrefWidth(100);
		searchButton.setStyle(
				"-fx-background-color: " + PRIMARY_COLOR + ";" + "-fx-text-fill: white;" + "-fx-background-radius: 25;"
						+ "-fx-font-size: 14px;" + "-fx-font-weight: bold;" + "-fx-padding: 10px;");

		searchBox.getChildren().addAll(searchField, searchButton);
		return searchBox;
	}

	// Metode untuk membuat kartu item yang dipilih
	private StackPane createSelectedItemCard(Item item) {  
	    StackPane cardContainer = new StackPane();  

	    VBox card = new VBox(15);  
	    card.setAlignment(Pos.CENTER);  
	    card.setStyle(  
	        "-fx-background-color: white;" +   
	        "-fx-background-radius: 15;" +  
	        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.2, 0, 3);"  
	    );  
	    card.setPadding(new Insets(20));  
	    card.setPrefHeight(450);  

	    // Item Image  
	    ImageView itemImage = new ImageView();  
	    try {  
	        Image image = new Image(item.getDefaultImagePathByCategory());  
	        itemImage.setImage(image);  
	        itemImage.setFitWidth(250);  
	        itemImage.setFitHeight(250);  
	        itemImage.setPreserveRatio(true);  
	    } catch (Exception e) {  
	        System.err.println("Gagal memuat gambar untuk item: " + item.getName());  
	    }  

	    // Tambahkan tombol wishlist di sudut kanan atas  
	    ImageView wishlistIcon = new ImageView(new Image("file:resources/add-to-wishlist.png"));  
	    wishlistIcon.setFitWidth(30);  
	    wishlistIcon.setFitHeight(30);  
	    wishlistIcon.setPreserveRatio(true);  
	    wishlistIcon.setStyle("-fx-cursor: hand;");  

	    wishlistIcon.setOnMouseEntered(e -> wishlistIcon.setOpacity(0.7));  
	    wishlistIcon.setOnMouseExited(e -> wishlistIcon.setOpacity(1.0));  

	    wishlistIcon.setOnMouseClicked(e -> {  
	        System.out.println("Menambahkan item ke wishlist: " + item.getName());
	        if (controller != null) {  
	            // Panggil method addToWishlist dari controller  
	            controller.addToWishlist(item);  
	        } else {  
	            // Optional: Tampilkan pesan error jika controller belum di-set  
	            System.err.println("Controller belum diinisialisasi");  
	        }  
	    });  

	    // Gunakan AnchorPane untuk positioning absolut  
	    AnchorPane cardWithWishlist = new AnchorPane(card);  
	    
	    // Posisikan wishlist icon di kanan atas  
	    AnchorPane.setTopAnchor(wishlistIcon, 10.0);  
	    AnchorPane.setRightAnchor(wishlistIcon, 10.0);  
	    
	    // Tambahkan wishlist icon ke AnchorPane  
	    cardWithWishlist.getChildren().add(wishlistIcon);  

	    // Tambahkan card dengan wishlist icon ke container  
	    cardContainer.getChildren().add(cardWithWishlist);  

	    // Item Details  
	    Label itemNameLabel = new Label(item.getName());  
	    itemNameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));  
	    itemNameLabel.setTextFill(Color.web(TEXT_COLOR));  

	    Label categoryLabel = new Label(item.getCategory());  
	    categoryLabel.setFont(Font.font("Segoe UI", 14));  
	    categoryLabel.setTextFill(Color.web("#7F8C8D"));  

	    // Description (Size)  
	    Label descriptionLabel = new Label(item.getSize());  
	    descriptionLabel.setFont(Font.font("Segoe UI", 14));  
	    descriptionLabel.setTextFill(Color.web(TEXT_COLOR));  
	    descriptionLabel.setWrapText(true);  
	    descriptionLabel.setMaxWidth(300);  
	    descriptionLabel.setAlignment(Pos.CENTER);  
	    descriptionLabel.setTextAlignment(TextAlignment.CENTER);  

	    // Price Label  
	    Label priceLabel = new Label("Rp " + formatRupiah(item.getPrice()));  
	    priceLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));  
	    priceLabel.setTextFill(Color.web(ACCENT_COLOR));  

	    // Container untuk tombol  
	    HBox buttonContainer = new HBox(15);  
	    buttonContainer.setAlignment(Pos.CENTER);  

	    // Tombol Purchase  
	    Button purchaseButton = new Button("Purchase");  
	    purchaseButton.setStyle(  
	        "-fx-background-color: #2ECC71;" +   
	        "-fx-text-fill: white;" +   
	        "-fx-background-radius: 25;" +  
	        "-fx-font-size: 14px;" +   
	        "-fx-font-weight: bold;" +   
	        "-fx-padding: 10px 20px;"  
	    );  
	    purchaseButton.setOnAction(e -> {  
	        System.out.println("Membeli item: " + item.getName()); 
	        controller.handlePurchase(currentChosenItem);
	    });  

	    // Tombol Make Offer  
	    Button makeOfferButton = new Button("Make Offer");  
	    makeOfferButton.setStyle(  
	        "-fx-background-color: #F39C12;" +   
	        "-fx-text-fill: white;" +   
	        "-fx-background-radius: 25;" +  
	        "-fx-font-size: 14px;" +   
	        "-fx-font-weight: bold;" +   
	        "-fx-padding: 10px 20px;"  
	    );  
	    makeOfferButton.setOnAction(e -> {  
	        System.out.println("Membuat penawaran untuk item: " + item.getName());  
	    });  

	    // Tambahkan tombol ke container  
	    buttonContainer.getChildren().addAll(purchaseButton, makeOfferButton);  

	    // Tambahkan semua elemen ke card  
	    card.getChildren().addAll(  
	        itemImage,   
	        itemNameLabel,   
	        categoryLabel,   
	        descriptionLabel,   
	        priceLabel,   
	        buttonContainer  
	    );  

	    return cardContainer;  
	}  

	private VBox createCenterSection() {
		VBox centerSection = new VBox(20);
		centerSection.setPadding(new Insets(20));
		centerSection.setPrefWidth(830);
		centerSection.setMaxWidth(830);
		centerSection.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.2, 0, 3);");

		// Navbar with buttons using CustomerNavbarView
		navbarView = new CustomerComponentView();
		HBox navbar = navbarView.getNavbar(username);

		// Setup navbar button actions
		setupNavbarButtonActions();

		// Tambahkan navbar ke centerSection
		centerSection.getChildren().add(navbar);

		return centerSection;
	}

	private void setupNavbarButtonActions() {
		// Action for View Purchase List button (index 0)
		navbarView.setButtonAction(0, () -> {
			System.out.println("View Purchase List clicked");
		});

		// Action for Wishlist button (index 1)
		navbarView.setButtonAction(1, () -> {
			System.out.println("Wishlist clicked");
		});

		// Get logout button and set its action
		Button logoutButton = navbarView.getLogoutButton();
		logoutButton.setOnAction(e -> {
			System.out.println("Logout clicked");
			controller.logout();
		});
	}

	// Metode untuk memperbarui ScrollPane dengan item baru
	public void updateItemScrollPane(ScrollPane itemScrollPane) {
		// Dapatkan VBox center yang sudah ada
		VBox centerSection = (VBox) getCenter();

		// Hapus ScrollPane lama jika ada
		centerSection.getChildren().removeIf(node -> node instanceof ScrollPane);

		// Tambahkan ScrollPane baru di akhir
		centerSection.getChildren().add(itemScrollPane);

		// Atur pertumbuhan vertikal
		VBox.setVgrow(itemScrollPane, Priority.ALWAYS);
	}

	public ScrollPane createItemScrollPane(List<Item> items) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		// Custom scrollbar style
		scrollPane.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 10;");

		// Use GridPane to ensure exactly 3 columns
		GridPane itemGrid = new GridPane();
		itemGrid.setHgap(15);
		itemGrid.setVgap(15);
		itemGrid.setPadding(new Insets(10));
		itemGrid.setAlignment(Pos.CENTER);

		// Tambahkan item dari service ke grid
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			VBox itemBox = createItemBox(item);

			// Calculate row and column
			int row = i / 3;
			int col = i % 3;

			// Add to grid
			itemGrid.add(itemBox, col, row);
		}

		// Configure column constraints to make columns equal width
		for (int i = 0; i < 3; i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setPercentWidth(33.33);
			itemGrid.getColumnConstraints().add(colConstraints);
		}

		scrollPane.setContent(itemGrid);
		return scrollPane;
	}

	private VBox createItemBox(Item item) {
		VBox itemBox = new VBox(10);
		itemBox.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.2, 0, 3);" + "-fx-cursor: hand;");
		itemBox.setPadding(new Insets(10));
		itemBox.setAlignment(Pos.CENTER);

		// Flexible sizing
		itemBox.setPrefWidth(230);
		itemBox.setMaxWidth(230);
		itemBox.setMaxHeight(350);

		// Gambar Item
		ImageView itemImage = new ImageView();
		try {
			Image image = new Image(item.getDefaultImagePathByCategory());
			itemImage.setImage(image);
			itemImage.setFitWidth(180);
			itemImage.setFitHeight(180);
			itemImage.setPreserveRatio(true);
		} catch (Exception e) {
			System.err.println("Gagal memuat gambar untuk item: " + item.getName());
		}

		// Placeholder background for image
		itemImage.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";" + "-fx-background-radius: 10;");

		// Labels
		Label nameLabel = new Label(item.getName());
		nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
		nameLabel.setTextFill(Color.web(TEXT_COLOR));

		Label categoryLabel = new Label(item.getCategory());
		categoryLabel.setFont(Font.font("Segoe UI", 14));
		categoryLabel.setTextFill(Color.web("#7F8C8D"));

		Label priceLabel = new Label("Rp " + formatRupiah(item.getPrice()));
		priceLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
		priceLabel.setTextFill(Color.web(ACCENT_COLOR));

		// Hover effects
		itemBox.setOnMouseEntered(e -> itemBox.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0.2, 0, 5);" + "-fx-cursor: hand;"));
		itemBox.setOnMouseExited(e -> itemBox.setStyle("-fx-background-color: white;" + "-fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.2, 0, 3);" + "-fx-cursor: hand;"));

		// Spacing between elements
		VBox.setMargin(nameLabel, new Insets(10, 0, 0, 0));
		VBox.setMargin(categoryLabel, new Insets(0, 0, 0, 0));
		VBox.setMargin(priceLabel, new Insets(0, 0, 10, 0));

		itemBox.getChildren().addAll(itemImage, nameLabel, categoryLabel, priceLabel);

		// Optional: Add click event handler
		itemBox.setOnMouseClicked(event -> {
			// Handle item click - e.g., open detail view
			updateChosenItemCard(item);
			System.out.println("Item clicked: " + item.getName());
		});

		return itemBox;
	}

	// Metode untuk memperbarui chosen item card
	private void updateChosenItemCard(Item item) {
		// Dapatkan VBox container dari left side
		VBox leftSide = (VBox) getLeft();

		// Hapus semua children dari left side
		leftSide.getChildren().clear();

		// Buat kartu item baru yang dipilih
		chosenItemCard = createSelectedItemCard(item);

		// Simpan item yang dipilih
		currentChosenItem = item;

		// Tambahkan kartu baru ke container
		leftSide.getChildren().addAll(createTitleAndSearchBox(), // Pastikan mengembalikan elemen yang diperlukan
				chosenItemCard);
	}

	public String formatRupiah(int price) {
		StringBuilder formatted = new StringBuilder(String.valueOf(price));
		for (int i = formatted.length() - 3; i > 0; i -= 3) {
			formatted.insert(i, ".");
		}
		return formatted.toString();
	}

	// Setter untuk controller
	public void setController(CustomerHomeController controller) {
		this.controller = controller;
	}
}