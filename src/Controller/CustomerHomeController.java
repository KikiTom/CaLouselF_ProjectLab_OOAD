package Controller;  

import View.CustomerHomeView;
import View.LoginView;
import View.popupView;
import Model.Item;
import Model.Transaction;
import Model.User;
import Repository.Database;
import Repository.TransactionRepository;
import Repository.WishListRepository;
import Service.ItemService;
import Service.TransactionService;
import Service.UserService;
import Service.WishlistService;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;  
import javafx.stage.Stage;  

import java.util.List;  

public class CustomerHomeController {  
	private CustomerHomeView view;
	private WishListRepository wishlistrepository;
	private TransactionRepository transactionRepository;
	private Stage currentstage;
	private ItemService itemService;
	private UserService userService;
	private TransactionService transactionService;
	private WishlistService wishlistService;
	private String username;

    public CustomerHomeController(String username, ItemService itemService, UserService userService) {  
    	this.username = username;
		this.itemService = itemService;
		
		
		// Inisialisasi Repository  
		this.wishlistrepository = new WishListRepository(Database.getInstance());  
        this.transactionRepository = new TransactionRepository(Database.getInstance());  
        
        // Inisialisasi Service  
//        wishlistService = new ItemService(itemRepository, userService, transactionRepository);  
        this.transactionService = new TransactionService(transactionRepository);
		
		
		this.userService = userService;

		// Buat view dengan username dan service
		this.view = new CustomerHomeView(username, itemService);

		// Set controller ke view
		this.view.setController(this);
    }  

    public void show(Stage primaryStage) {  
        // Buat scene dengan view
    	this.currentstage = primaryStage;
        Scene scene = new Scene(view);  
        
        // Atur stage  
        primaryStage.setScene(scene);  
        primaryStage.setTitle("CaLouselF - Customer Home");  
        primaryStage.setWidth(1200);  
        primaryStage.setHeight(700);  
        
        // Tampilkan stage  
        primaryStage.show();  
        
        // Muat dan tampilkan item  
        loadAndDisplayItems();  
    }  

    private void loadAndDisplayItems() {  
        // Dapatkan item yang sudah diterima  
        List<Item> availableItems = itemService.Getaccepteditem();  
        
        // Buat ScrollPane dengan item  
        ScrollPane itemScrollPane = view.createItemScrollPane(availableItems);  
        
        // Perbarui tampilan dengan ScrollPane  
        view.updateItemScrollPane(itemScrollPane);  
    }  

    // Metode untuk mencari item  
    public void searchItems(String keyword) {  
        // Implementasi pencarian item  
        List<Item> searchResults = itemService.searchItems(keyword);  
        
        // Buat ScrollPane dengan hasil pencarian  
        ScrollPane searchResultScrollPane = view.createItemScrollPane(searchResults);  
        
        // Perbarui tampilan dengan hasil pencarian  
        view.updateItemScrollPane(searchResultScrollPane);  
    }  

    // Metode untuk menangani klik item  
    public void handleItemClick(Item item) {  
        // Buka detail item atau lakukan aksi lain  
        System.out.println("Item diklik: " + item.getName());  
        // Misalnya, buka dialog detail item  
        // openItemDetailDialog(item);  
    }
    
    /**  
     * Handles the purchase process for a given item  
     *   
     * @param item The item to be purchased  
     * @return boolean indicating whether the purchase was successful  
     */  
    public boolean handlePurchase(Item item) {  
        // Validate input  
        if (item == null) {  
            popupView.getInstance().showErrorPopup("Purchase Error", "Invalid item selected");  
            return false;  
        }  

        // Confirm purchase with user  
        String confirmationMessage = String.format(  
            "Are you sure you want to purchase %s for %s?",   
            item.getName(),   
            item.getPrice()  
        );  
        
        if (!popupView.getInstance().showConfirmationPopup("Confirm Purchase", confirmationMessage)) {  
            return false; // User cancelled  
        }  

        // Get current user  
        User currentUser = userService.getUserByUsername(username); 
        if (currentUser == null) {  
            popupView.getInstance().showErrorPopup("Purchase Error", "Please log in to make a purchase");  
            return false;  
        }  

        // Create transaction  
        Transaction transaction = new Transaction(currentUser.getId(), item, "Purchased");  

        // Attempt purchase  
        try {  
            boolean purchaseSuccess = transactionService.createTransaction(transaction);  

            if (purchaseSuccess) {  
                popupView.getInstance().showSuccessPopup(  
                    "Purchase Successful",   
                    String.format("%s has been purchased successfully!", item.getName())  
                );  
                return true;  
            } else {  
                popupView.getInstance().showErrorPopup(  
                    "Purchase Failed",   
                    "Unable to complete the transaction. Please try again."  
                );  
                return false;  
            }  
        } catch (Exception e) {                   
            // Show user-friendly error message  
            popupView.getInstance().showErrorPopup(  
                "Purchase Error",   
                "An unexpected error occurred. Please contact support."  
            );  
            return false;  
        }  
    }  

	// Tambahkan metode untuk handle make offer
//	public void handleMakeOffer(Item item) {
//		// Buka view make offer
//		Stage makeOfferStage = new Stage();
//		makeOfferStage.initModality(Modality.APPLICATION_MODAL);
//
//		MakeOfferView makeOfferView = new MakeOfferView(item, username);
//		makeOfferView.setController(this);
//
//		Scene makeOfferScene = new Scene(makeOfferView);
//		makeOfferStage.setScene(makeOfferScene);
//		makeOfferStage.setTitle("Make Offer: " + item.getName());
//		makeOfferStage.showAndWait();
//	}

	// Metode untuk submit offer
//	public boolean submitOffer(Item item, double offerPrice) {
//		try {
//			// Lakukan proses submit offer melalui item service atau offer service
//			boolean offerSubmitted = itemService.submitOffer(username, item, offerPrice);
//
//			if (offerSubmitted) {
//				// Tampilkan pesan sukses
//				return true;
//			} else {
//				// Tampilkan pesan gagal
//				return false;
//			}
//		} catch (Exception e) {
//			// Handle error
//			System.err.println("Gagal submit offer: " + e.getMessage());
//			return false;
//		}
//	}

	// Navigasi ke Purchase History
//	public void navigateToPurchaseHistory() {
//		Stage purchaseHistoryStage = new Stage();
//		purchaseHistoryStage.initModality(Modality.APPLICATION_MODAL);
//
//		// Dapatkan daftar purchase history dari service
//		List<Item> purchaseHistory = purchaseService.getPurchaseHistory(username);
//
//		PurchaseHistoryView purchaseHistoryView = new PurchaseHistoryView(purchaseHistory);
//		purchaseHistoryView.setController(this);
//
//		Scene purchaseHistoryScene = new Scene(purchaseHistoryView);
//		purchaseHistoryStage.setScene(purchaseHistoryScene);
//		purchaseHistoryStage.setTitle("Purchase History");
//		purchaseHistoryStage.showAndWait();
//	}

	// Navigasi ke Wishlist
//	public void navigateToWishlist() {
//		Stage wishlistStage = new Stage();
//		wishlistStage.initModality(Modality.APPLICATION_MODAL);
//
//		// Dapatkan daftar wishlist dari service
//		List<Item> wishlistItems = wishlistService.getWishlistItems(username);
//
//		WishlistView wishlistView = new WishlistView(wishlistItems);
//		wishlistView.setController(this);
//
//		Scene wishlistScene = new Scene(wishlistView);
//		wishlistStage.setScene(wishlistScene);
//		wishlistStage.setTitle("Wishlist");
//		wishlistStage.showAndWait();
//	}

	// Tambahkan item ke wishlist
//	public void addToWishlist(Item item) {
//		try {
//			boolean addedToWishlist = wishlistService.addToWishlist(username, item);
//
//			if (addedToWishlist) {
//				// Tampilkan pesan sukses
//				System.out.println("Item ditambahkan ke wishlist: " + item.getName());
//			} else {
//				// Tampilkan pesan gagal
//				System.out.println("Gagal menambahkan item ke wishlist");
//			}
//		} catch (Exception e) {
//			// Handle error
//			System.err.println("Gagal menambahkan ke wishlist: " + e.getMessage());
//		}
//	}
    
    private void closeHomeScene() {
		Platform.runLater(() -> {
			if (currentstage != null) {
				currentstage.close();
			}
		});
	}
    
    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginController.showLoginScene(loginStage);
    }
    
    // Metode untuk logout  
    public void logout() {  
    	try {
    			if(popupView.getInstance().showConfirmationPopup("Logout", "Are you sure you want to logout?")) {
                    closeHomeScene();
                    showLoginScene();
                }
           
        } catch (Exception ex) {
            System.err.println("Error setting up logout button: " + ex.getMessage());
            ex.printStackTrace();
        }
    }  
}