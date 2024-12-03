package Controller;  

import Service.UserService;  
import View.SellerHomeView;  
import View.LoginView;  
import View.popupView;  
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;  

public class SellerHomeController {  
    private UserService userService;  
    private SellerHomeView sellerHomeView;  
    private Stage currentStage;  
    private String username;  

    public SellerHomeController(UserService userService, Stage currentStage, String username) {  
        this.currentStage = currentStage;  
        this.username = username;  
        this.userService = userService;  

        // Assuming you have a method to get user details  
        String name = userService.getUserName(username);  
        String address = userService.getUserAddress(username);  
        String phone = userService.getUserPhone(username);
        setupLogoutButton();  
    }  

    private void setupLogoutButton() {  
        try {  
            Button logoutButton = sellerHomeView.getLogoutButton();  
            if (logoutButton == null) {  
                System.err.println("Logout button is null!");  
                return;  
            }  

            popupView popupView = View.popupView.getInstance();  
            if (popupView == null) {  
                System.err.println("PopupView is null!");  
                return;  
            }  

            logoutButton.setOnAction(e -> {  
                if(popupView.showConfirmationPopup("Logout", "Are you sure you want to logout?")) {  
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
        loginStage.setScene(loginView.createLoginScene(loginStage));  
        loginStage.show();  
    }  

    public void showSellerHomeScene() {  
        currentStage.setScene(sellerHomeView.createSellerHomeScene(currentStage));  
        currentStage.show();  
    }  

    private void closeHomeScene() {  
        if (currentStage != null) {  
            currentStage.close();  
        }  
    }  
}