package Controller;  

import Service.UserService;  
import View.SellerHomeView;  
import View.LoginView;  
import View.popupView;  
import javafx.stage.Stage;  
import javafx.scene.control.Button;  

public class SellerHomeController {  
    private UserService userService;  
    private SellerHomeView sellerHomeView;  
    private Stage currentStage;  
    private String username;
    private int useriD;

    public SellerHomeController(UserService userService, Stage currentStage, String username) {  
        this.currentStage = currentStage;  
        this.username = username;  
        this.userService = userService;
        this.useriD = userService.getUserID(username);
        this.sellerHomeView = new SellerHomeView(currentStage, username);
        
        // Setup komponen view  
        setupComponents();  
    }  

    private void setupComponents() {  
        // Setup logout  
        setupLogoutButton();  
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

    // Getter untuk view jika diperlukan  
    public SellerHomeView getSellerHomeView() {  
        return sellerHomeView;  
    }  
}