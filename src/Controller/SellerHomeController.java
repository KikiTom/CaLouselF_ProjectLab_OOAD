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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

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
        // Implement Offered Items navigation here
    }

    /**
     * Populates the item rows in the UI based on the current user's items.
     */
    private void populateItemRows() {
        try {
            // Fetch items using the service
            List<Item> items = itemService.getItemsByUsername(username);

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
                    for (Item item : items) {
                        HBox itemRow = sellerHomeView.getcreateItemRow();
                        sellerHomeView.updateItemRowLabels(itemRow,
                                item.getCategory(),
                                item.getName(),
                                item.getSize(),
                                formatCurrency(item.getPrice()),
                                transactionService.getTransactionCountByItemId(item.getId()),
                                item.getStatus());
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
    
    private void updateFooterStatistics() {  
        try {  
            // Dapatkan statistik dari ItemService dan TransactionService  
            int totalItems = itemService.getTotalItemsByUsername(username);  
            int totalTransactionValue = transactionService.getTotalTransactionValueByUsername(username);  
            
            // Hitung jumlah item terjual (bisa disesuaikan dengan logika bisnis Anda)  
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
