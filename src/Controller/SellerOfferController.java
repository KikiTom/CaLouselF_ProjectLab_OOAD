package Controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Service.ItemService;
import Service.OfferService;
import Service.TransactionService;
import Service.UserService;
import View.LoginView;
import View.SellerComponentView;
import View.SellerOfferView;
import View.SellerUploadView;
import View.popupView;

import java.util.ArrayList;
import java.util.List;

import Model.Item;
import Model.Offer;
import Repository.Database;
import Repository.OfferRepository;
import Repository.TransactionRepository;

/**
 * Controller class for managing the Seller Offer view.
 */
public class SellerOfferController {
	private UserService userService;
	private OfferRepository offerRepository;
	private TransactionRepository transactionRepository;
	private OfferService offerService;
	private TransactionService transactionService;
    private Stage currentStage;  
    private String username;  
    private SellerComponentView sidebarComponent;  
    private ItemService itemService;  
    private int userId;  
    private SellerOfferView sellerOfferView;  

	/**
	 * Constructor to initialize the SellerOfferController.
	 *
	 * @param userService  The service handling user-related operations.
	 * @param currentStage The current JavaFX stage.
	 * @param offerView    The view representing the seller offer UI.
	 * @param username     The username of the current seller.
	 */
	public SellerOfferController(UserService userService, Stage currentStage, String username,
			SellerComponentView sidebarComponent, ItemService itemService) {
		this.currentStage = currentStage;
		this.username = username;
		this.userService = userService;
		this.userId = userService.getUserID(username);
		this.sidebarComponent = sidebarComponent;
		this.itemService = itemService;
		
		this.transactionRepository = new TransactionRepository(Database.getInstance());
		this.transactionService = new TransactionService(transactionRepository);
		this.offerRepository = new OfferRepository(Database.getInstance());
		this.offerService = new OfferService(offerRepository, userService, itemService, transactionService);
		this.sellerOfferView = new SellerOfferView(currentStage, username, sidebarComponent);
		initializeSidebarButtonActions();
		setupLogoutButton();
		populateItemRows();
	}

	/**
	 * Initializes sidebar button actions based on their indices.
	 */
	private void initializeSidebarButtonActions() {
		// Assuming button indices:
		// 0 - View Items
		// 1 - Upload Item
		// 2 - View Offered Items

		// View Items Button Action
		sellerOfferView.getSidebarComponent().setButtonAction(0, () -> {
			// Already on View Items, possibly refresh
			System.out.println("View Items button clicked - Refreshing items");
			navigateToHomeView();
		});

		// Upload Item Button Action
		sellerOfferView.getSidebarComponent().setButtonAction(1, () -> {
			System.out.println("Upload Item button clicked - Navigating to Upload Item View");
			navigateToUploadItemView();
		});
	}
	
	private void navigateToHomeView() {
        try {
            // Initialize SellerUploadController with existing sidebar
            SellerHomeController homeController = new SellerHomeController(
                userService,
                currentStage,
                username
            );
            homeController.showSellerHomeScene(currentStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error navigating to Upload Item view: " + e.getMessage());
        }
    }
	
	/**
	 * Navigates to the Upload Item view. Initializes and displays the
	 * UploadItemView and its controller.
	 */
	private void navigateToUploadItemView() {
		try {
			// Initialize SellerUploadController with existing sidebar
			SellerUploadController uploadController = new SellerUploadController(userService, currentStage, username,
					sellerOfferView.getSidebarComponent(), itemService);
			uploadController.showView();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error navigating to Upload Item view: " + e.getMessage());
		}
	}

	/**
	 * Sets up the event handler for the logout button.
	 */
	private void setupLogoutButton() {
		Button logoutButton = sellerOfferView.getLogoutButton();
		if (logoutButton != null) {
			logoutButton.setOnAction(e -> {
				boolean confirm = popupView.getInstance().showConfirmationPopup("Logout",
						"Are you sure you want to logout?");
				if (confirm) {
					closeOfferScene();
					showLoginScene();
				}
			});
		}
	}

	/**
	 * Displays the login scene.
	 */
	private void showLoginScene() {
		Platform.runLater(() -> {
			LoginView loginView = new LoginView();
			Stage loginStage = new Stage();
			LoginController loginController = new LoginController(userService, loginView, loginStage);
			loginStage.setScene(loginView.createLoginScene());
			loginStage.setTitle("Login");
			loginStage.show();
		});
	}

	/**
	 * Closes the current offer scene.
	 */
	private void closeOfferScene() {
		Platform.runLater(() -> {
			if (currentStage != null) {
				currentStage.close();
			}
		});
	}

	
	public void showOfferScene(Stage primaryStage) {
		Platform.runLater(() -> {
			primaryStage.setScene(sellerOfferView.createOfferedItemsScene());
			primaryStage.setTitle("Seller - Offered Items");
			primaryStage.show();
		});
	}

	/**
	 * Populates the offered items into the view. Fetches data from the UserService
	 * and updates the UI.
	 */
	private void populateItemRows() {
		Platform.runLater(() -> {
			try {
				// Fetch offered items for the seller from the service
				List<Offer> offeredItems = offerService.getOfferedItemsForSeller(username);
		        
		        // Mendapatkan semua item
		        List<Item> allItems = itemService.getallitem();

		        // Menyaring item berdasarkan itemId yang cocok dengan offer.getItemId()
		        List<Item> filteredItems = new ArrayList<>();
		        
		        for (Offer offer : offeredItems) {
		            for (Item item : allItems) {
		                if (item.getId() == offer.getItemId()) {
		                    filteredItems.add(item);
		                    break;  // Karena item.id hanya satu, tidak perlu lanjutkan pencarian
		                }
		            }
		        }
				

				// Get the container where items will be displayed
				VBox itemsContainer = (VBox) sellerOfferView.getItemsScrollPane().getContent();
				itemsContainer.getChildren().clear(); // Clear existing items

				// Iterate over each offered item and create a row in the view
				for (Item item : filteredItems) {
					HBox itemRow = sellerOfferView.createItemRow();
					sellerOfferView.updateItemRowLabels(itemRow, item.getName(), item.getCategory(), item.getSize(),
							String.format("Rp %.2f", item.getPrice()),
							String.format("Rp %.2f", item.getPrice()));

					// Optionally, add event handlers to the item row or specific buttons within it
					// For example, accepting or declining an offer
					// Example:
					// itemRow.setOnMouseClicked(e -> handleOfferClick(item));

					itemsContainer.getChildren().add(itemRow);
				}

				// Update footer statistics
//				int totalOffers = offeredItems.size();
//				double highestOffer = offeredItems.stream().mapToDouble(OfferedItem::getOfferedPrice).max().orElse(0);
//				double averageOffer = offeredItems.stream().mapToDouble(OfferedItem::getOfferedPrice).average()
//						.orElse(0);
				
				int totalOffers = 12;
				double highestOffer = 12;
				double averageOffer = 12;

				sellerOfferView.updateFooterStatistics(String.valueOf(totalOffers), String.format("Rp %.2f", highestOffer),
						String.format("Rp %.2f", averageOffer));

			} catch (Exception e) {
				e.printStackTrace();
				popupView.getInstance().showErrorPopup("Error", "Failed to load offered items.");
			}
		});
	}

	

	/**
	 * Navigates to the Offered Items view. Since this controller manages the
	 * Offered Items view, this might refresh the view.
	 */
	private void navigateToOfferedItemsView() {
		Platform.runLater(() -> {
			try {
				// If already on Offered Items view, refresh the items
				populateItemRows();
			} catch (Exception e) {
				e.printStackTrace();
				popupView.getInstance().showErrorPopup("Error", "Failed to navigate to Offered Items view.");
			}
		});
	}

	/**
	 * Example method to refresh the offered items view. Can be called after
	 * accepting or declining an offer to refresh the data.
	 */
	public void refreshOfferedItems() {
		populateItemRows();
	}

	/**
	 * Inner class representing an offered item. This should ideally be a separate
	 * class in your model package.
	 */
	public static class OfferedItem {
		private String name;
		private String category;
		private String size;
		private double initialPrice;
		private double offeredPrice;

		// Constructor
		public OfferedItem(String name, String category, String size, double initialPrice, double offeredPrice) {
			this.name = name;
			this.category = category;
			this.size = size;
			this.initialPrice = initialPrice;
			this.offeredPrice = offeredPrice;
		}

		// Getters
		public String getName() {
			return name;
		}

		public String getCategory() {
			return category;
		}

		public String getSize() {
			return size;
		}

		public double getInitialPrice() {
			return initialPrice;
		}

		public double getOfferedPrice() {
			return offeredPrice;
		}
	}
}
