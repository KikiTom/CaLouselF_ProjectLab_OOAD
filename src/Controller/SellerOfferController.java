package Controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
			SellerHomeController homeController = new SellerHomeController(userService, currentStage, username);
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
	 * Populate item rows with offered items.
	 */
	private void populateItemRows() {  
	    Platform.runLater(() -> {  
	        try {  
	            // Pastikan sellerOfferView tidak null  
	            if (sellerOfferView == null) {  
	                System.err.println("SellerOfferView is null");  
	                return;  
	            }  

	            // Ambil ScrollPane dari view  
	            ScrollPane itemsScrollPane = sellerOfferView.getItemsScrollPane();  
	            if (itemsScrollPane == null) {  
	                System.err.println("ItemsScrollPane is null");  
	                return;  
	            }  

	            // Ambil VBox container dari ScrollPane  
	            VBox itemsContainer = sellerOfferView.getItemsContainer();  
	            if (itemsContainer == null) {  
	                System.err.println("ItemsContainer is null");  
	                return;  
	            }  

	            // Fetch and process offered items  
	            List<Offer> offeredItems = offerService.getOfferedItemsForSeller(username);  
	            
	            // Jika offeredItems null, tampilkan pesan tidak ada penawaran  
	            if (offeredItems == null || offeredItems.isEmpty()) {  
	                // Bersihkan container  
	                itemsContainer.getChildren().clear();  
	                
	                // Tambahkan label untuk menunjukkan tidak ada penawaran  
	                Label noOffersLabel = new Label("No offers available");  
	                noOffersLabel.setStyle(  
	                    "-fx-font-size: 16px;" +   
	                    "-fx-text-fill: #7F8C8D;" +   
	                    "-fx-alignment: center;"  
	                );  
	                itemsContainer.getChildren().add(noOffersLabel);  
	                
	                // Update footer statistics menjadi 0  
	                sellerOfferView.updateFooterStatistics(  
	                    "0",   
	                    "Rp 0.00",  
	                    "Rp 0.00"  
	                );  
	                
	                return;  
	            }  
	            
	            // Null check untuk itemService  
	            List<Item> allItems = itemService.getallitem();  
	            if (allItems == null) {  
	                System.err.println("No items found");  
	                return;  
	            }  
	            
	            List<Item> filteredItems = filterItemsForOffers(offeredItems, allItems);  

	            // Null check untuk filteredItems  
	            if (filteredItems == null || filteredItems.isEmpty()) {  
	                System.err.println("No filtered items found");  
	                return;  
	            }  

	            // Populate container  
	            populateItemContainer(itemsContainer, filteredItems, offeredItems);  
	            updateFooterStatistics(offeredItems);  

	        } catch (Exception e) {  
	            // Log the full exception for debugging  
	            System.err.println("Error in populateItemRows: " + e.getMessage());  
	            e.printStackTrace();  
	        }  
	    });  
	}  

	/**
	 * Filter items based on offered items.
	 */
	private List<Item> filterItemsForOffers(List<Offer> offeredItems, List<Item> allItems) {
		// Pastikan input tidak null
		if (offeredItems == null || allItems == null) {
			return new ArrayList<>();
		}

		List<Item> filteredItems = new ArrayList<>();
		for (Offer offer : offeredItems) {
			for (Item item : allItems) {
				if (item.getId() == offer.getItemId()) {
					filteredItems.add(item);
					break;
				}
			}
		}
		return filteredItems;
	}

	/**
	 * Populate item container with rows.
	 */
	private void populateItemContainer(  
		    VBox itemsContainer,   
		    List<Item> filteredItems,   
		    List<Offer> offeredItems  
		) {  
		    // Pastikan container tidak null  
		    if (itemsContainer == null) {  
		        // Coba ambil dari view  
		        itemsContainer = sellerOfferView.getItemsContainer();  
		        
		        if (itemsContainer == null) {  
		            System.err.println("Items container is null, cannot populate");  
		            return;  
		        }  
		    }  

		    // Bersihkan container sebelum menambahkan item baru  
		    itemsContainer.getChildren().clear();  

		    for (int i = 0; i < filteredItems.size(); i++) {  
		        Item item = filteredItems.get(i);  
		        Offer offer = offeredItems.get(i);  

		        HBox itemRow = createItemRow(item, offer);  
		        
		        // Pastikan itemRow tidak null sebelum menambahkan  
		        if (itemRow != null) {  
		            itemsContainer.getChildren().add(itemRow);  
		        }  
		    }  

		    // Debug: Cetak jumlah item yang ditambahkan  
		    System.out.println("Total items added to container: " + itemsContainer.getChildren().size());  
		}  

	/**
	 * Create an item row with event handlers.
	 */
	private HBox createItemRow(Item item, Offer offer) {  
	    try {  
	        HBox itemRow = sellerOfferView.getcreateItemRow();  
	        
	        // Debug: Pastikan itemRow tidak null  
	        if (itemRow == null) {  
	            System.err.println("getcreateItemRow returned NULL");  
	            return null;  
	        }  
	        
	        Button acceptBtn = sellerOfferView.getAcceptButton(itemRow);  
	        Button declineBtn = sellerOfferView.getDeclineButton(itemRow);  

	        // Debug: Pastikan tombol tidak null  
	        if (acceptBtn == null || declineBtn == null) {  
	            System.err.println("Button is NULL - Accept: " + acceptBtn + ", Decline: " + declineBtn);  
	        }  

	        acceptBtn.setOnAction(event -> handleAcceptOffer(item, offer));  
	        declineBtn.setOnAction(event -> handleDeclineOffer(item, offer));  

	        // Konversi eksplisit ke double untuk memastikan format yang benar  
	        double itemPrice = convertToDouble(item.getPrice());  
	        double offerAmount = convertToDouble(offer.getAmount());  

	        // Dapatkan username dengan pengecekan null  
	        String username = "";  
	        try {  
	            username = userService.getUserNamebyid(offer.getUserId());  
	        } catch (Exception e) {  
	            System.err.println("Error getting username: " + e.getMessage());  
	        }  

	        // Debug: Cetak kategori item  
	        String itemCategory = item.getCategory();  
	        System.out.println("Item Category: " + itemCategory);  

	        sellerOfferView.updateItemRowLabels(  
	            itemRow,  
	            item.getName(),  
	            itemCategory != null ? itemCategory : "Uncategorized",  // Tambahkan null check  
	            formatCurrency(itemPrice),  
	            formatCurrency(offerAmount),  
	            username  
	        );  

	        return itemRow;  
	    } catch (Exception e) {  
	        System.err.println("Comprehensive error in createItemRow: " + e.getMessage());  
	        e.printStackTrace();  
	        return null;  
	    }  
	}  

	// Tambahkan method untuk mencetak detail seluruh proses  
	private void debugOfferAndItemDetails(List<Offer> offeredItems, List<Item> allItems) {  
	    System.out.println("===== DEBUG: Offer and Item Details =====");  
	    System.out.println("Total Offered Items: " + offeredItems.size());  
	    System.out.println("Total All Items: " + allItems.size());  

	    for (int i = 0; i < offeredItems.size(); i++) {  
	        Offer offer = offeredItems.get(i);  
	        System.out.println("\nOffer " + (i+1) + ":");  
	        System.out.println("Offer ID: " + offer.getId());  
	        System.out.println("Item ID: " + offer.getItemId());  
	        System.out.println("Offer Amount: " + offer.getAmount());  
	        System.out.println("User ID: " + offer.getUserId());  
	    }  

	    for (int i = 0; i < allItems.size(); i++) {  
	        Item item = allItems.get(i);  
	        System.out.println("\nItem " + (i+1) + ":");  
	        System.out.println("Item ID: " + item.getId());  
	        System.out.println("Item Name: " + item.getName());  
	        System.out.println("Item Price: " + item.getPrice());  
	    }  
	}  
	
	// Metode utility untuk konversi ke double
	private double convertToDouble(Object value) {
		if (value == null) {
			return 0.0;
		}

		if (value instanceof Double) {
			return (Double) value;
		}

		if (value instanceof Float) {
			return ((Float) value).doubleValue();
		}

		if (value instanceof Integer) {
			return ((Integer) value).doubleValue();
		}

		if (value instanceof String) {
			try {
				return Double.parseDouble((String) value);
			} catch (NumberFormatException e) {
				System.err.println("Could not convert to double: " + value);
				return 0.0;
			}
		}

		System.err.println("Unsupported type for conversion: " + value.getClass());
		return 0.0;
	}

	/**
	 * Update footer statistics.
	 */
	private void updateFooterStatistics(List<Offer> offeredItems) {
		int totalOffers = offeredItems.size();
		double highestOffer = offeredItems.stream().mapToDouble(offer -> convertToDouble(offer.getAmount())).max()
				.orElse(0);
		double averageOffer = offeredItems.stream().mapToDouble(offer -> convertToDouble(offer.getAmount())).average()
				.orElse(0);

		sellerOfferView.updateFooterStatistics(String.valueOf(totalOffers), formatCurrency(highestOffer),
				formatCurrency(averageOffer));
	}

	/**
	 * Handle population errors.
	 */
	private void handlePopulationError(Exception e) {
		e.printStackTrace();
		popupView.getInstance().showErrorPopup("Error", "Failed to load offered items.");
	}

	// Metode baru untuk handle accept offer
	private void handleAcceptOffer(Item item, Offer offer) {
		try {
			// Tampilkan konfirmasi popup
			boolean confirmed = popupView.getInstance().showConfirmationPopup("Accept Offer",
					"Are you sure you want to accept this offer for " + item.getName() + "?");

			if (confirmed) {
				// Proses penerimaan penawaran
				boolean success = offerService.acceptOffer(offer.getId());

				if (success) {
					popupView.getInstance().showSuccessPopup("Offer Accepted",
							"The offer for " + item.getName() + " has been accepted.");

					// Refresh daftar penawaran
					refreshOfferedItems();
				} else {
					popupView.getInstance().showErrorPopup("Error", "Failed to accept the offer. Please try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			popupView.getInstance().showErrorPopup("Error", "An error occurred while processing the offer.");
		}
	}

	// Metode baru untuk handle decline offer
	private void handleDeclineOffer(Item item, Offer offer) {
		try {
			// Tampilkan popup alasan penolakan
			String declineReason = popupView.getInstance().showInputPopup("Decline Offer",
					"Reason for declining the offer for " + item.getName() + ":");

			if (declineReason != null && !declineReason.trim().isEmpty()) {
				// Proses penolakan penawaran
				boolean success = offerService.declineOffer(offer.getId(), declineReason);

				if (success) {
					popupView.getInstance().showSuccessPopup("Offer Declined",
							"The offer for " + item.getName() + " has been declined.");

					// Refresh daftar penawaran
					refreshOfferedItems();
				} else {
					popupView.getInstance().showErrorPopup("Error", "Failed to decline the offer. Please try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			popupView.getInstance().showErrorPopup("Error", "An error occurred while processing the offer.");
		}
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

	// Metode utility untuk format mata uang
	private String formatCurrency(double amount) {
		return String.format("Rp %.2f", amount);
	}

}
