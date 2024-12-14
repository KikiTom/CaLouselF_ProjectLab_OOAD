package Controller;  

import View.CustomerHomeView;
import View.LoginView;
import View.popupView;
import Model.Item;
import Model.Transaction;
import Model.User;
import Repository.Database;
import Repository.OfferRepository;
import Repository.TransactionRepository;
import Repository.WishListRepository;
import Service.ItemService;
import Service.OfferService;
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
	private OfferRepository offerRepository;
	private Stage currentstage;
	private ItemService itemService;
	private UserService userService;
	private TransactionService transactionService;
	private WishlistService wishlistService;
	private OfferService offerService;
	private String username;

    public CustomerHomeController(String username, ItemService itemService, UserService userService) {  
    	this.username = username;
		this.itemService = itemService;
		
		
		// Inisialisasi Repository  
		this.wishlistrepository = new WishListRepository(Database.getInstance());  
        this.transactionRepository = new TransactionRepository(Database.getInstance());  
        this.offerRepository = new OfferRepository(Database.getInstance());
        
        // Inisialisasi Service  
        this.wishlistService = new WishlistService(this.wishlistrepository);  
        this.transactionService = new TransactionService(this.transactionRepository);
        this.offerService = new OfferService(this.offerRepository);
		
		
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
        	    "Are you sure you want to purchase %s for Rp %s?",   
        	    item.getName(),   
        	    view.formatRupiah(item.getPrice())  
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


	// Metode untuk submit offer
    public void HandleOffer(Item item) {  
        try {  
            // Cek amount offer yang sudah ada  
            int currentOfferAmount = offerService.getAmountOffer(item.getId());  
            
            // Siapkan pesan untuk popup input  
            String message;  
            if (currentOfferAmount > 0) {  
                // Jika sudah ada offer sebelumnya  
                message = "Item ini sudah memiliki penawaran sebesar Rp " +   
                          view.formatRupiah(currentOfferAmount) +   
                          "\nMasukkan penawaran baru (harus lebih dari " +   
                          view.formatRupiah(currentOfferAmount) + "):";  
            } else {  
                // Jika belum ada offer  
                message = "Masukkan penawaran untuk item " + item.getName() +   
                          " (harus lebih dari 0):";  
            }  
            
            // Tampilkan popup input  
            String offerInput = popupView.getInstance().showInputPopup(  
            	    "Buat Penawaran",   
            	    message  
            	);  

            	// Cek apakah input null (cancel ditekan)  
            	if (offerInput == null) {  
            	    // Jika cancel ditekan, keluar dari method tanpa error  
            	    return;  
            	}  

            	// Validasi input kosong setelah trim  
            	if (offerInput.trim().isEmpty()) {  
            	    popupView.getInstance().showErrorPopup(  
            	        "Gagal",   
            	        "Penawaran tidak boleh kosong"  
            	    );  
            	    return;  
            	}  
            
            // Konversi input ke integer  
            int offerPrice;  
            try {  
                // Hapus karakter non-digit (misal titik atau koma)  
                offerInput = offerInput.replaceAll("[^\\d]", "");  
                offerPrice = Integer.parseInt(offerInput);  
            } catch (NumberFormatException e) {  
                popupView.getInstance().showErrorPopup(  
                    "Kesalahan",   
                    "Masukkan harga yang valid"  
                );  
                return;  
            }  
            
            // Validasi harga penawaran  
            if (offerPrice <= 0) {  
                popupView.getInstance().showErrorPopup(  
                    "Kesalahan",   
                    "Penawaran harus lebih dari 0"  
                );  
                return;  
            }  
            
            // Jika sudah ada penawaran sebelumnya, cek apakah penawaran baru lebih tinggi  
            if (currentOfferAmount > 0 && offerPrice <= currentOfferAmount) {  
                popupView.getInstance().showErrorPopup(  
                    "Kesalahan",   
                    "Penawaran harus lebih tinggi dari penawaran sebelumnya (Rp " +   
                    view.formatRupiah(currentOfferAmount) + ")"  
                );  
                return;  
            }  
            
            // Dapatkan user ID  
            int userId = userService.getUserID(username);  
            
            // Validasi user ID  
            if (userId <= 0) {  
                popupView.getInstance().showErrorPopup(  
                    "Kesalahan",   
                    "Silakan login terlebih dahulu"  
                );  
                return;  
            }  
            
            // Submit offer  
            boolean offerSubmitted = offerService.makeNewOffer(  
                item.getId(),   
                userId,   
                offerPrice  
            );  
            
            // Cek hasil submit  
            if (offerSubmitted) {  
                // Tampilkan popup sukses  
                popupView.getInstance().showSuccessPopup(  
                    "Berhasil",   
                    "Penawaran sebesar Rp " + view.formatRupiah(offerPrice) +   
                    " untuk " + item.getName() + " berhasil diajukan"  
                );  
            } else {  
                // Tampilkan popup error  
                popupView.getInstance().showErrorPopup(  
                    "Gagal",   
                    "Gagal mengajukan penawaran untuk " + item.getName()  
                );  
            }  
        } catch (Exception e) {  
            // Handle error yang tidak terduga  
            System.err.println("Gagal submit offer: " + e.getMessage());  
            e.printStackTrace();  
            
            popupView.getInstance().showErrorPopup(  
                "Kesalahan Sistem",   
                "Terjadi kesalahan: " + e.getMessage()  
            );  
        }  
    }  


	// Navigasi ke Purchase History
	public void navigateToPurchaseHistory() {
		int userId = userService.getUserID(username);
		System.out.println(userId);
		CustomerHistoryController historyController = new CustomerHistoryController(
				userId,
				userService,
				itemService);
		closeHomeScene();
		historyController.showView();
	}

	// Navigasi ke Wishlist
	public void navigateToWishlist() {
		int userId = userService.getUserID(username);
		CustomerWishlistController wishlistController = new CustomerWishlistController(  
			    wishlistService,   
			    itemService,         
			    userService,        
			    userId,   
			    username  
			);   

		// Show the wishlist
		closeHomeScene();
		wishlistController.show(); 
	}

	// Tambahkan item ke wishlist
    public void addToWishlist(Item item) {  
        try {  
            // Dapatkan user ID dari username yang sedang login  
            int userId = userService.getUserID(username);  
            // Panggil service untuk menambahkan ke wishlist  
            boolean addedToWishlist = wishlistService.Addtowishlist(userId, item);  

            if (addedToWishlist) {  
                // Tampilkan popup sukses  
                popupView.getInstance().showSuccessPopup(  
                    "Wishlist Berhasil",   
                    "Item " + item.getName() + " berhasil ditambahkan ke wishlist"  
                );  
            } else {  
                // Tampilkan popup error jika item sudah ada di wishlist  
                popupView.getInstance().showErrorPopup(  
                    "Gagal Menambahkan",   
                    "Item " + item.getName() + " sudah ada di wishlist Anda"  
                );  
            }  
        } catch (Exception e) {  
            // Tangani error yang tidak terduga  
            popupView.getInstance().showErrorPopup(  
                "Kesalahan Sistem",   
                "Gagal menambahkan item ke wishlist: " + e.getMessage()  
            );  
        }  
    }  
    
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