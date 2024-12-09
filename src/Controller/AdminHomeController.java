package Controller;

import Model.Item;
import Repository.Database;
import Repository.ItemRepository;
import Service.ItemService;
import Service.UserService;
import View.AdminHomeView;
import View.LoginView;
import View.popupView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class AdminHomeController {
    private AdminHomeView adminHomeView;
    private ItemService itemService;
    private ItemRepository itemRepository;
    private UserService userService;
    private Stage primaryStage;

    public AdminHomeController(Stage primaryStage, AdminHomeView adminHomeView, UserService userservice) {
        this.primaryStage = primaryStage;
        this.adminHomeView = adminHomeView;
        this.userService = userservice;

        Database database = Database.getInstance();

        // Initialize Repository
        itemRepository = new ItemRepository(database);
        itemService = new ItemService(itemRepository, userService);
        // Do not call setupComponents here
    }

    private void setupComponents() {
        // Setup logout button
        setupLogoutButton();

        // Populate pending items
        populateItemRows();
    }

    private void setupLogoutButton() {
        try {
            Button logoutButton = adminHomeView.getLogoutButton();

            // Set action for logout
            logoutButton.setOnAction(e -> {
                // Show logout confirmation
                if (popupView.getInstance().showConfirmationPopup("Logout", "Are you sure you want to logout?")) {
                    closeHomeScene();
                    showLoginScene();
                }
            });
        } catch (Exception ex) {
            System.err.println("Unexpected error setting up logout button: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void setupItemActionButtons() {
        // Access Approve and Decline buttons via getters
        Button approveButton = adminHomeView.getApproveButton();
        Button declineButton = adminHomeView.getDeclineButton();

        // Null checks to prevent NullPointerException
        if (approveButton == null || declineButton == null) {
            System.err.println("Approve or Decline buttons are not initialized!");
            return;
        }

        // Approve button action
        approveButton.setOnAction(e -> {
            List<Item> selectedItems = adminHomeView.getTableView()
                .getItems()
                .stream()
                .filter(Item::isSelected)
                .collect(Collectors.toList());

            if (!selectedItems.isEmpty()) {
                boolean allApproved = true;
                for (Item item : selectedItems) {
                    boolean approved = itemService.approveItem(item.getId());
                    if (!approved) {
                        allApproved = false;
                        popupView.getInstance().showErrorPopup("Error", "Failed to approve item ID: " + item.getId());
                    }
                }

                if (allApproved) {
                    popupView.getInstance().showSuccessPopup("Success", "All selected items have been approved!");
                    populateItemRows();
                } else {
                    popupView.getInstance().showErrorPopup("Partial Success", "Some items could not be approved.");
                }
            } else {
                popupView.getInstance().showErrorPopup("Error", "No items selected to approve!");
            }
        });

        // Decline button action
        declineButton.setOnAction(e -> {
            List<Item> selectedItems = adminHomeView.getTableView()
                .getItems()
                .stream()
                .filter(Item::isSelected)
                .collect(Collectors.toList());

            if (selectedItems.isEmpty()) {
                popupView.getInstance().showErrorPopup("Error", "No items selected to decline!");
                return;
            }

            String reason = popupView.getInstance().showInputPopup("Reason", "Write the reason to decline the selected items:");
            if (reason != null && !reason.trim().isEmpty()) {
                boolean allDeclined = true;
                for (Item item : selectedItems) {
                    boolean declined = itemService.declineItem(item.getId(),reason);
                    if (!declined) {
                        allDeclined = false;
                        popupView.getInstance().showErrorPopup("Error", "Failed to decline item ID: " + item.getId());
                    }
                }

                if (allDeclined) {
                    popupView.getInstance().showSuccessPopup("Success", "All selected items have been declined!");
                    populateItemRows();
                } else {
                    popupView.getInstance().showErrorPopup("Partial Success", "Some items could not be declined.");
                }
            } 
        });
    }

    private void populateItemRows() {  
        // Fetch list of pending items  
        List<Item> pendingItems = itemService.getPendingItems();  

        // Debug: print IDs  
        for (Item item : pendingItems) {  
            System.out.println("Item ID: " + item.getId());  
        }  

        // Update table  
        Platform.runLater(() -> {  
            adminHomeView.populateItemRows(pendingItems);  
        });  
    }

    public void showAdminHomeScene() {
        primaryStage.setScene(adminHomeView.createAdminHomeScene(primaryStage));
        primaryStage.setTitle("Admin Home");
        primaryStage.centerOnScreen();
        primaryStage.show();

        // Initialize components after scene is created
        setupComponents();
        setupItemActionButtons(); // Now buttons are initialized
    }

    private void closeHomeScene() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }

    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginController.showLoginScene(loginStage);
    }
}
