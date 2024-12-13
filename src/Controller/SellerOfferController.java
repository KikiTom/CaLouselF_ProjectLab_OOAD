package Controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
     * Populate item rows with offered items.  
     */  
	private void populateItemRows() {  
	    Platform.runLater(() -> {  
	        // Fetch and process offered items  
	        List<Offer> offeredItems = offerService.getOfferedItemsForSeller(username);  
	        
	        // Jika offeredItems null, langsung return tanpa try-catch  
	        if (offeredItems == null) {  
	            return;  
	        }  
	        
	        // Jika tidak ada penawaran  
	        if (offeredItems.isEmpty()) {  
	            VBox itemsContainer = (VBox) sellerOfferView.getItemsScrollPane().getContent();  
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
	        
	        List<Item> allItems = itemService.getallitem();  
	        List<Item> filteredItems = filterItemsForOffers(offeredItems, allItems);  

	        // Clear and populate items container  
	        VBox itemsContainer = (VBox) sellerOfferView.getItemsScrollPane().getContent();  
	        itemsContainer.getChildren().clear();  

	        populateItemContainer(itemsContainer, filteredItems, offeredItems);  
	        updateFooterStatistics(offeredItems);  
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
        for (int i = 0; i < filteredItems.size(); i++) {  
            Item item = filteredItems.get(i);  
            Offer offer = offeredItems.get(i);  

            HBox itemRow = createItemRow(item, offer);  
            itemsContainer.getChildren().add(itemRow);  
        }  
    }  

    /**  
     * Create an item row with event handlers.  
     */  
    private HBox createItemRow(Item item, Offer offer) {  
        HBox itemRow = sellerOfferView.getcreateItemRow();  
        
        Button acceptBtn = sellerOfferView.getAcceptButton(itemRow);  
        Button declineBtn = sellerOfferView.getDeclineButton(itemRow);  

        acceptBtn.setOnAction(event -> handleAcceptOffer(item, offer));  
        declineBtn.setOnAction(event -> handleDeclineOffer(item, offer));  

        sellerOfferView.updateItemRowLabels(  
            itemRow,  
            item.getName(),  
            item.getCategory(),  
            String.format("Rp %.2f", item.getPrice()),  
            String.format("Rp %.2f", offer.getAmount()),  
            userService.getUserNamebyid(offer.getUserId())  
        );  

        return itemRow;  
    }  

    /**  
     * Update footer statistics.  
     */  
    private void updateFooterStatistics(List<Offer> offeredItems) {  
        int totalOffers = offeredItems.size();  
        double highestOffer = offeredItems.stream()  
            .mapToDouble(Offer::getAmount)  
            .max()  
            .orElse(0);  
        double averageOffer = offeredItems.stream()  
            .mapToDouble(Offer::getAmount)  
            .average()  
            .orElse(0);  

        sellerOfferView.updateFooterStatistics(  
            String.valueOf(totalOffers),  
            String.format("Rp %.2f", highestOffer),  
            String.format("Rp %.2f", averageOffer)  
        );  
    }  

    /**  
     * Handle population errors.  
     */  
    private void handlePopulationError(Exception e) {  
        e.printStackTrace();  
        popupView.getInstance().showErrorPopup(  
            "Error",   
            "Failed to load offered items."  
        );  
    }  

	// Metode baru untuk handle accept offer  
	private void handleAcceptOffer(Item item, Offer offer) {  
	    try {  
	        // Tampilkan konfirmasi popup  
	        boolean confirmed = popupView.getInstance().showConfirmationPopup(  
	            "Accept Offer",   
	            "Are you sure you want to accept this offer for " + item.getName() + "?"  
	        );  

	        if (confirmed) {  
	            // Proses penerimaan penawaran  
	            boolean success = offerService.acceptOffer(offer.getId());  
	            
	            if (success) {  
	                popupView.getInstance().showSuccessPopup(  
	                    "Offer Accepted",   
	                    "The offer for " + item.getName() + " has been accepted."  
	                );  
	                
	                // Refresh daftar penawaran  
	                refreshOfferedItems();  
	            } else {  
	                popupView.getInstance().showErrorPopup(  
	                    "Error",   
	                    "Failed to accept the offer. Please try again."  
	                );  
	            }  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        popupView.getInstance().showErrorPopup(  
	            "Error",   
	            "An error occurred while processing the offer."  
	        );  
	    }  
	}  

	// Metode baru untuk handle decline offer  
	private void handleDeclineOffer(Item item, Offer offer) {  
	    try {  
	        // Tampilkan popup alasan penolakan  
	        String declineReason = popupView.getInstance().showInputPopup(  
	            "Decline Offer",   
	            "Reason for declining the offer for " + item.getName() + ":"  
	        );  

	        if (declineReason != null && !declineReason.trim().isEmpty()) {  
	            // Proses penolakan penawaran  
	            boolean success = offerService.declineOffer(offer.getId(), declineReason);  
	            
	            if (success) {  
	                popupView.getInstance().showSuccessPopup(  
	                    "Offer Declined",   
	                    "The offer for " + item.getName() + " has been declined."  
	                );  
	                
	                // Refresh daftar penawaran  
	                refreshOfferedItems();  
	            } else {  
	                popupView.getInstance().showErrorPopup(  
	                    "Error",   
	                    "Failed to decline the offer. Please try again."  
	                );  
	            }  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        popupView.getInstance().showErrorPopup(  
	            "Error",   
	            "An error occurred while processing the offer."  
	        );  
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

}
