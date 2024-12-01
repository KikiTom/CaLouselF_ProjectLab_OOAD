package Controller;

import javafx.stage.Stage;
import Model.User;
import Service.UserService;
import View.LoginView;
import View.RegisterView;
import View.popupView;

public class RegisterController {
    private UserService userService;
    private RegisterView registerView;
    private Stage currentStage; 

    public RegisterController(UserService userService, RegisterView registerView,Stage currentStage) {
        this.userService = userService;
        this.registerView = registerView;
        this.currentStage = currentStage;
        setupRegisterAction();
        setupGetLoginHyperlink();
    }

    private void setupRegisterAction() {
        registerView.getRegisterButton().setOnAction(e -> handleRegister());
    }
    
    private void setupGetLoginHyperlink() {
    	registerView.getLoginLink().setOnAction(event -> {
            if (currentStage != null) {  
                currentStage.close();  
            }
    		showLoginScene();
    		System.out.println("Pindah ke login Scene...");
    	});
    }
    
    // validate area
    public static String validateUsername(String username) {
    	// Null or empty check
    	if (username == null || username.trim().isEmpty()) {
    		return "Username cannot be empty.";
    	}else if (username.length() < 3) {
    		return "Username must be at least 3 characters long.";
    	}
    	return null;
    }
    
    public static String validatePassword(String password) {
    	// Null or empty check
    	if (password == null || password.trim().isEmpty()) {
    		return "Password cannot be empty.";
    	}else if (password.length() < 8) {
    		return "Password must be at least 8 characters long.";
    	}
    	
        boolean hasSpecialChar = false;  
        String specialChars = "!@#$%^&*";
        for (char c : password.toCharArray()) {  
            if (specialChars.indexOf(c) != -1) {  
                hasSpecialChar = true;  
                break;  
            }  
        }
        if (!hasSpecialChar) {
        	return "Password must include at least one special character (!,@,#,$,%,^,&,*).";
        }
    	return null;
    }
    
    public static String validatePhoneNumber(String phoneNumber) {   
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {  
            return "Phone number cannot be empty.";  
        }  
        
        // Trim whitespace  
        phoneNumber = phoneNumber.trim();  
        // Cek awalan +62  
        if (!phoneNumber.startsWith("+62")) {  
            return "Phone number must start with +62.";  
        }  
        
        // Pastikan panjang cukup untuk substring  
        if (phoneNumber.length() < 3) {  
            return "Invalid phone number format.";  
        }  
        
        // Ambil digit setelah +62  
        String digitsOnly = phoneNumber.substring(3);  
        
        // Cek panjang digit  
        if (digitsOnly.length() != 10) {  
            return "Phone number must have 10 digits after +62.";  
        }  
        
        // Cek apakah semua karakter adalah digit  
        for (char c : digitsOnly.toCharArray()) {  
            if (!Character.isDigit(c)) {  
                return "Phone number must contain only digits after +62.";  
            }  
        }  
  
        // Validasi lolos  
        return null;  
    }
    
    public static String validateAddress(String Address) {
    	if (Address == null || Address.trim().isEmpty()) {
    		return "Address cannot be empty.";
    	}else if(Address.length() < 5) {
    		return "Address must be at least 5 characters long.";
    	}
    		
    	return null;
    }
    
    private boolean isUsernametaken(String username) {
    	if (userService.getUserByUsername(username) != null) {
    		return false;
    	}
    	return true;
    }
    
    
    // end validate area
    
    private void handleRegister() {
        String username = registerView.getUsernameField().getText();
        String password = registerView.getPasswordField().getText();
        String phoneNumber = registerView.getPhoneNumberField().getText();
        String address = registerView.getAddressField().getText();
        String role = registerView.getRoleComboBox().getValue();
        
   
        
        if (!isUsernametaken(username)) {  
        	registerView.setUsernameValidationLabel("Username already Exist!");  
            return;  
        } 
        
        User newUser = new User(username, password, phoneNumber, address, role);
        popupView popup = popupView.getInstance();
        if (userService.registerUser(newUser)) {
        	popup.showSuccessPopup("Success", "Welcome to our Platform!");
        } else {
        	popup.showErrorPopup("Error", "Registration failed.");  
        }
    }
    
    

    public void showRegisterScene(Stage primaryStage) {
        primaryStage.setScene(registerView.createRegisterScene(primaryStage));
        primaryStage.show();
    }
    
    private void showLoginScene() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        LoginController loginController = new LoginController(userService, loginView, loginStage);
        loginStage.setScene(loginView.createLoginScene(loginStage));
        loginStage.show();
    }
}
