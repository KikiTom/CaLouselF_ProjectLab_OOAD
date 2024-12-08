package Controller;  

import Service.ItemService;  
import Service.UserService;
import View.LoginView;
import View.SellerComponentView;  
import View.SellerUploadView;
import View.popupView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;  

public class SellerUploadController {  
    private UserService userService;  
    private Stage currentStage;  
    private String username;  
    private SellerComponentView sidebarComponent;  
    private ItemService itemService;  
    private int userId;  
    private SellerUploadView sellerUploadView;  

    public SellerUploadController(UserService userService, Stage currentStage, String username,  
                                   SellerComponentView sidebarComponent, ItemService itemService) {  
        this.currentStage = currentStage;  
        this.username = username;  
        this.userService = userService;  
        this.userId = userService.getUserID(username);  
        this.sidebarComponent = sidebarComponent;  
        this.itemService = itemService;  

        // Create view  
        this.sellerUploadView = new SellerUploadView(  
            currentStage,   
            sidebarComponent,   
            userId,   
            this,   
            itemService,   
            username  
        ); 
        
        initializeSidebarButtonActions();
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
                    closeScene();
                    showLoginScene();
                }
            });
        } catch (Exception ex) {
            System.err.println("Error setting up logout button: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void closeScene() {
        if (currentStage != null) {
            currentStage.close();
        }
    }
    
    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginController.showLoginScene(loginStage);
    }
    
    
    private void initializeSidebarButtonActions() {  
        // View Items Button Action (Index 0)  
        sidebarComponent.setButtonAction(0, () -> {  
        	navigateToHomeView(); 
              
        });   

        // View Offered Items Button Action (Index 2)  
        sidebarComponent.setButtonAction(2, () -> {  
//            navigateToOfferedItemsView();  
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
    
    public void showView() {  
        // Set scene ke current stage  
        currentStage.setScene(sellerUploadView.getSellerUploadScene());  
        
        // Pastikan stage terlihat  
        currentStage.show();  
        
        // Opsional: Fokuskan ke stage  
        currentStage.requestFocus();  
    } 

    public void handleUploadButtonAction() {  
        try {  
            // Reset error label  
            sellerUploadView.getErrorLabel().setText("");  

            // Retrieve input values  
            String itemName = sellerUploadView.getItemNameField().getText().trim();  
            String category = sellerUploadView.getCategoryComboBox().getValue();  
            String size = sellerUploadView.getSizeComboBox().getValue();  
            String priceText = sellerUploadView.getPriceField().getText().trim();  

            // Validate input  
            if (!validateInput(itemName, category, size, priceText)) {  
                return;  
            }  

            // Process item upload  
            processItemUpload(itemName, category, size, priceText);  

        } catch (Exception e) {  
            showErrorMessage("An unexpected error occurred: " + e.getMessage());  
        }  
    }  

    private boolean validateInput(String itemName, String category, String size, String priceText) {  
        // Input validation logic  
        if (itemName.isEmpty()) {  
            showErrorMessage("Item name cannot be empty!");  
            return false;  
        }
        
        if (itemName.length() < 3){
        	showErrorMessage("Item name Must At least 3 character long!");  
            return false; 
        }

        if (category == null) {  
            showErrorMessage("Please select a category!");  
            return false;  
        }  

        if (size == null) {  
            showErrorMessage("Please select a size!");  
            return false;  
        }  

        try {  
            double price = Double.parseDouble(priceText);  
            if (price <= 0) {  
                showErrorMessage("Price must be a positive number!");  
                return false;  
            }else if (price > 1000000000) {
            	showErrorMessage("I'm not letting that happen!");
            	return false;
            }
        } catch (NumberFormatException e) {  
            showErrorMessage("Invalid price format!");  
            return false;  
        }  

        return true;  
    }  

    private void processItemUpload(String itemName, String category, String size, String priceText) {  
        try {  
            double price = Double.parseDouble(priceText);  
            
            // Call item service to upload item  
            boolean uploadSuccess = itemService.uploadItem(  
                userId,   
                itemName,   
                category,   
                size,   
                price  
            );  

            if (uploadSuccess) {  
                Platform.runLater(() -> {  
                	popupView.getInstance().showSuccessPopup("Success", "Your item has been uploaded and is waiting for admin approval.");  
                    sellerUploadView.resetForm();  
                });  
            } else {  
                showErrorMessage("Failed to upload item. Please try again.");  
            }  

        } catch (Exception e) {  
            showErrorMessage("Upload failed: " + e.getMessage());  
        }  
    }  

    private void showErrorMessage(String message) {  
        Platform.runLater(() -> {  
            sellerUploadView.getErrorLabel().setText(message);  
            sellerUploadView.getErrorLabel().setStyle("-fx-text-fill: #E74C3C;");  
        });  
    }  
  

    // Getter for the view if needed  
    public SellerUploadView getSellerUploadView() {  
        return sellerUploadView;  
    }  
}