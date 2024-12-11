package Controller;

import Repository.Database;
import Repository.ItemRepository;
import Repository.TransactionRepository;
import Service.ItemService;
import Service.TransactionService;
import Service.UserService;
import View.SellerHomeView;
import View.LoginView;
import View.SellerUploadView;
import View.popupView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Model.Item;

public class SellerHomeController {
	private UserService userService;  
    private SellerHomeView sellerHomeView;  
    private ItemService itemService;  
    private ItemRepository itemRepository;  
    private TransactionService transactionService;  
    private TransactionRepository transactionRepository;  
    private Stage currentStage;  
    private String username;  
    private int useriD;

    public SellerHomeController(UserService userService, Stage currentStage, String username) {
        this.currentStage = currentStage;
        this.username = username;
        this.userService = userService;
        this.useriD = userService.getUserID(username);
        this.sellerHomeView = new SellerHomeView(currentStage, username);
        
        // Inisialisasi Database  
        Database database = Database.getInstance();  
        
        // Inisialisasi Repository  
        itemRepository = new ItemRepository(database);  
        transactionRepository = new TransactionRepository(database);  
        
        // Inisialisasi Service  
        itemService = new ItemService(itemRepository, userService, transactionRepository);  
        transactionService = new TransactionService(transactionRepository, itemRepository, itemService);
        
        // Setup components and populate items  
        setupComponents();  
        populateItemRows();  
        
        // Update footer statistics  
        updateFooterStatistics();  
        
        // Initialize button actions  
        initializeSidebarButtonActions(); 
    }

    private void setupComponents() {
        // Setup logout
        setupLogoutButton();
        
        // Additional component setups can be added here
    }
    
    public void refreshParentView() {  
        Platform.runLater(() -> {  
            try {  
                // Refresh daftar item  
                populateItemRows();  
                
                // Update statistik footer  
                updateFooterStatistics();  
                
                // Optional: Tambahkan log atau notifikasi  
                System.out.println("Parent view refreshed successfully");  
            } catch (Exception e) {  
                e.printStackTrace();  
                popupView.getInstance().showErrorPopup(  
                    "Refresh Error",   
                    "Unable to refresh view"  
                );  
            }  
        });  
    } 

    private void setupLogoutButton() {
        try {
            Button logoutButton = sellerHomeView.getLogoutButton();
            if (logoutButton == null) {
                System.err.println("Logout button is null!");
                return;
            }

            logoutButton.setOnAction(e -> {
                if(popupView.getInstance().showConfirmationPopup("Logout", "Are you sure you want to logout?")) {
                    closeHomeScene();
                    showLoginScene();
                }
            });
        } catch (Exception ex) {
            System.err.println("Error setting up logout button: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Initializes actions for sidebar buttons.
     */
    private void initializeSidebarButtonActions() {
        // Assuming button indices:
        // 0 - View Items
        // 1 - Upload Item
        // 2 - View Offered Items

        // View Items Button Action
        sellerHomeView.getSidebarComponent().setButtonAction(0, () -> {
            // Already on View Items, possibly refresh
            populateItemRows();
        });

        // Upload Item Button Action
        sellerHomeView.getSidebarComponent().setButtonAction(1, () -> {
            navigateToUploadItemView();
        });

        // View Offered Items Button Action
        sellerHomeView.getSidebarComponent().setButtonAction(2, () -> {
            navigateToOfferedItemsView();
        });
    }

    /**
     * Navigates to the Upload Item view.
     */
    private void navigateToUploadItemView() {
        try {
            // Initialize SellerUploadController with existing sidebar
            SellerUploadController uploadController = new SellerUploadController(
                userService,
                currentStage,
                username,
                sellerHomeView.getSidebarComponent(),
                itemService
            );
            uploadController.showView();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error navigating to Upload Item view: " + e.getMessage());
        }
    }

    /**
     * Navigates to the Offered Items view.
     * Implement similarly to navigateToUploadItemView().
     */
    private void navigateToOfferedItemsView() {
        SellerOfferController OfferController = new SellerOfferController(
        		userService,
                currentStage,
                username,
                sellerHomeView.getSidebarComponent(),
                itemService
        		);
        OfferController.showOfferScene(currentStage);
    }

    /**
     * Populates the item rows in the UI based on the current user's items.
     */
    private void populateItemRows() {
        try {
            // Fetch items using the service
            List<Item> items = itemService.getItemsByUsername(username);
            
            List<Item> sortedItems = items.stream()  
                    .sorted((item1, item2) -> {  
                        // Custom sorting logic  
                        List<String> priorityOrder = Arrays.asList(  
                            "Decline",   
                            "Waiting Approval",   
                            "Available"  
                        );  

                        int index1 = findStatusPriority(priorityOrder, item1.getStatus());  
                        int index2 = findStatusPriority(priorityOrder, item2.getStatus());  

                        // Jika prioritas sama, urutkan berdasarkan nama item  
                        if (index1 == index2) {  
                            return item1.getName().compareTo(item2.getName());  
                        }  

                        return Integer.compare(index1, index2);  
                    })  
                    .collect(Collectors.toList());
            
            
            // Update UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                // Option 1: Using the getter method
                ScrollPane scrollPane = sellerHomeView.getItemsScrollPane();

                // Option 2: Using lookup with ID (ensure the ID matches)
                // ScrollPane scrollPane = (ScrollPane) sellerHomeView.getContentArea().lookup("#items-scroll-pane");

                if (scrollPane != null) {
                    // Get VBox container for items
                    VBox itemsContainer = (VBox) scrollPane.getContent();

                    // Clear existing items
                    itemsContainer.getChildren().clear();

                    // Add new items
                    for (Item item : sortedItems) {
                        HBox itemRow = sellerHomeView.getcreateItemRow();
                        sellerHomeView.updateItemRowLabels(itemRow,
                                item.getCategory(),
                                item.getName(),
                                item.getSize(),
                                formatCurrency(item.getPrice()),
                                transactionService.getTransactionCountByItemId(item.getId()),
                                item.getStatus());
                        
                        setupItemRowEditActions(itemRow, item); 
                        itemsContainer.getChildren().add(itemRow);
                    }

                    // Optionally, update footer statistics
                    updateFooterStatistics(); 
                } else {
                    System.err.println("ScrollPane not found in ContentArea");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in populateItemRows: " + e.getMessage());
        }
    }
    
    private void setupItemRowEditActions(HBox itemRow, Item item) {  
        Label editLabel = sellerHomeView.getEditLabel(itemRow);  
        
        editLabel.setOnMouseClicked(event -> {  
            // Determine action based on status  
            switch (item.getStatus().toLowerCase()) {  
                case "waiting approval":  
                    handleDeleteItem(item);  
                    break;  
                case "available":  
                    handleEditItem(item);  
                    break;  
                default:  
                    if (item.getStatus().toLowerCase().contains("decline")) {
                    	String cleanStatus = item.getStatus().replaceFirst(".*Decline, ", "");
                    	popupView.getInstance().showErrorPopup("Reason Decline", cleanStatus);
                    	try {
                    		handledeleteitemwithreason(item);
                    	}catch (Exception e) {  
                            e.printStackTrace();  
                            popupView.getInstance().showErrorPopup(  
                                "Delete Error",   
                                "An error occurred while deleting the item"  
                            );  
                        }  
                    }else {
                    	handleDeleteItem(item);
                    }
            }
        });  
    }
    
    private void handledeleteitemwithreason(Item item) {
    	boolean deleteSuccess = itemService.deleteitembyid(item.getId());     
        if (deleteSuccess) {  
            // Refresh items after successful deletion  
            Platform.runLater(() -> {  
                populateItemRows();
            });  
        }
    }

    private void handleEditItem(Item item) {  
        try {  
            Platform.runLater(() -> {  
                // Create an edit dialog or navigate to edit view  
                SellerEditItemController editController = new SellerEditItemController(  
                    userService,   
                    currentStage,   
                    username,   
                    item,  
                    itemService  
                );  
                
                // Optional: Set parent controller for direct refresh  
                editController.setParentController(this);  
                
                editController.showEditItemView();
            });
        } catch (Exception e) {  
            e.printStackTrace();  
            popupView.getInstance().showErrorPopup("Edit Error", "Unable to edit item");  
        }  
    }  

    public void handleDeleteItem(Item item) {  
        try {  
            // Show confirmation popup  
            boolean confirmDelete = popupView.getInstance().showConfirmationPopup(  
                "Delete Item",   
                "Are you sure you want to delete this item?"  
            );  
            
            if (confirmDelete) {  
                // Perform delete operation  
                boolean deleteSuccess = itemService.deleteitembyid(item.getId());  
                
                if (deleteSuccess) {  
                    // Refresh items after successful deletion  
                    Platform.runLater(() -> {  
                        populateItemRows();  
                        popupView.getInstance().showSuccessPopup(  
                            "Delete Successful",   
                            "Item has been deleted successfully"  
                        );
                        populateItemRows();
                    });  
                } else {  
                    popupView.getInstance().showErrorPopup(  
                        "Delete Failed",   
                        "Unable to delete the item"  
                    );  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            popupView.getInstance().showErrorPopup(  
                "Delete Error",   
                "An error occurred while deleting the item"  
            );  
        }  
    }  
    
    private int findStatusPriority(List<String> priorityOrder, String status) {  
        // Prioritas utama: Cek status Decline  
        if (isDeclineStatus(status)) {  
            return priorityOrder.indexOf("Decline");  
        }  
        
        // Untuk status lainnya, lakukan pencocokan  
        for (int i = 0; i < priorityOrder.size(); i++) {  
            if (status != null && status.contains(priorityOrder.get(i))) {  
                return i;  
            }  
        }  
        
        // Jika status tidak cocok, tempatkan di akhir  
        return priorityOrder.size();  
    }  

    // Metode tambahan untuk memeriksa status Decline  
    private boolean isDeclineStatus(String status) {  
        return status != null && status.startsWith("Decline,");  
    }
    
    private void updateFooterStatistics() {  
        try {  
            // Dapatkan statistik dari ItemService dan TransactionService  
            int totalItems = itemService.getTotalItemsByUsername(username);  
            int totalTransactionValue = transactionService.getTotalTransactionValueByUsername(username);  
            
            // Hitung jumlah item terjual  
            List<Item> items = itemService.getItemsByUsername(username);  
            long soldItems = items.stream()  
                .mapToLong(item -> transactionService.getTransactionCountByItemId(item.getId()))  
                .sum();  

            // Update footer di view  
            sellerHomeView.updateFooterStatistics(  
                String.valueOf(totalItems),  
                String.valueOf(soldItems),  
                formatCurrency(totalTransactionValue)  
            );  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.err.println("Error updating footer statistics: " + e.getMessage());  
        }  
    }

    // Method utilitas untuk format mata uang  
    private String formatCurrency(int amount) {  
        // Gunakan NumberFormat untuk memformat angka  
    	return "Rp. " + String.format("%,d", amount).replace(',', '.');  
    }  
    
    
    
    /**
     * Finds the ScrollPane within the content area.
     *
     * @return the ScrollPane if found, otherwise null
     */
    private ScrollPane findScrollPane() {
        // Assign an ID to the ScrollPane in SellerHomeView for easier lookup
        // Modify SellerHomeView.createItemsScrollPane() to set ID
        ScrollPane scrollPane = (ScrollPane) sellerHomeView.getContentArea().lookup("#items-scroll-pane");
        return scrollPane;
    }

    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginController.showLoginScene(loginStage);
    }

    public void showSellerHomeScene(Stage PrimaryStage) {
        PrimaryStage.setScene(sellerHomeView.createSellerHomeScene());
        PrimaryStage.show();
    }

    private void closeHomeScene() {
        if (currentStage != null) {
            currentStage.close();
        }
    }

    // Getter for the view if needed
    public SellerHomeView getSellerHomeView() {
        return sellerHomeView;
    }
}
