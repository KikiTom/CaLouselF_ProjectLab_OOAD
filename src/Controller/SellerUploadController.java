package Controller;

import Repository.Database;
import Repository.ItemRepository;
import Service.ItemService;
import Service.UserService;
import View.LoginView;
import View.SellerComponentView;
import View.SellerHomeView;
import View.SellerUploadView;
import View.popupView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import Model.Item;

public class SellerUploadController {  
    private UserService userService;  
    private SellerUploadView sellerUploadView;  
    private ItemService itemService;  
    private ItemRepository itemRepository;  
    private Stage currentStage;  
    private String username;  
    private int userId;  
    private SellerComponentView sidebarComponent;  

    public SellerUploadController(UserService userService, Stage currentStage, String username, 
                                  SellerComponentView sidebarComponent, ItemService itemService) {  
        this.currentStage = currentStage;  
        this.username = username;  
        this.userService = userService;  
        this.userId = userService.getUserID(username);  
        this.sidebarComponent = sidebarComponent;  
        this.itemService = itemService;  
        
        // Initialize database and repository  
        Database database = Database.getInstance();  
        itemRepository = new ItemRepository(database);  
        // itemService is already passed
        
        // Create view with existing sidebarComponent
        this.sellerUploadView = new SellerUploadView(  
            currentStage,   
            this.sidebarComponent,   
            userId,   
            this,   
            itemService,
            username
        );  

        // Setup components  
        setupComponents();
        initializeSidebarButtonActions();
    }  

    private void setupComponents() {  
        // Setup logout or other required components  
        setupLogoutButton();  
    }  

    private void setupLogoutButton() {  
        try {  
            Button logoutButton = sellerUploadView.getLogoutButton();  
            if (logoutButton == null) {  
                System.err.println("Logout button is null!");  
                return;  
            }  

            logoutButton.setOnAction(e -> {  
                if(popupView.getInstance().showConfirmationPopup("Logout", "Are you sure you want to logout?")) {  
                    closeUploadScene();  
                    showLoginScene();  
                }  
            });  
        } catch (Exception ex) {  
            System.err.println("Error setting up logout button: " + ex.getMessage());  
            ex.printStackTrace();  
        }  
    }  

    /**
     * Validates the item data and attempts to upload it.
     *
     * @param item the item to be uploaded
     * @return null if successful, or an error message string
     */
    public String validateAndUploadItem(Item item) {  
        // Validate input  
        if (item.getName() == null || item.getName().trim().isEmpty()) {  
            return "Item name cannot be empty!";  
        }else if(item.getName().length() < 3) {
        	return "Item name Must at least be 3 character long!";
        }

        if (item.getCategory() == null || item.getCategory().trim().isEmpty()) {  
            return "Category must be selected!";  
        }
        
        if (item.getSize() == null || item.getSize().trim().isEmpty()) {  
            return "Size must be selected!";  
        }

        if (item.getPrice() <= 0) {  
            return "Price must be greater than 0 or Must be Number!";  
        }  

        try {  
            // Save item using itemService  
            boolean success = itemService.addItem(item);  
            
            if (success) {
                return null; // Indicates success  
            } else {  
                return "Failed to upload item. Please try again.";  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "An error occurred while uploading the item: " + e.getMessage();  
        }  
    }  

    private void showLoginScene() {  
        LoginView loginView = new LoginView();  
        Stage loginStage = new Stage();  
        LoginController loginController = new LoginController(userService, loginView, loginStage);  
        loginController.showLoginScene(loginStage);  
    }  

    public void showSellerUploadScene(Stage primaryStage) {  
        primaryStage.setScene(sellerUploadView.createSellerUploadScene()); 
        primaryStage.show();  
    }  

    private void closeUploadScene() {  
        if (currentStage != null) {  
            currentStage.close();  
        }  
    }
    
    private void initializeSidebarButtonActions() {
        // Assuming button indices:
        // 0 - View Items
        // 1 - Upload Item
        // 2 - View Offered Items

        // View Items Button Action
    	sellerUploadView.getSidebarComponent().setButtonAction(0, () -> {
    		navigateToHomeView();
    	});

        // View Offered Items Button Action
        sellerUploadView.getSidebarComponent().setButtonAction(2, () -> {
            navigateToOfferedItemsView();
        });
    }
    
    private void navigateToHomeView() {
        try {
            // Initialize SellerUploadController with existing sidebar
            SellerHomeController sellerHomeController = new SellerHomeController(
                userService,
                currentStage,
                username
            );
            sellerHomeController.showSellerHomeScene(currentStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error navigating to Upload Item view: " + e.getMessage());
        }
    }
    
    private void navigateToOfferedItemsView() {
    	
    }

    // Getter for the view if needed  
    public SellerUploadView getSellerUploadView() {  
        return sellerUploadView;  
    }  
}
