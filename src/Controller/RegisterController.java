package Controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    // Validation labels  
    private Label usernameValidationLabel;  
    private Label passwordValidationLabel;  
    private Label phoneValidationLabel;  
    private Label addressValidationLabel;  
    private Label passwordMatchLabel;

    public RegisterController(UserService userService, RegisterView registerView,Stage currentStage) {
        this.userService = userService;
        this.registerView = registerView;
        this.currentStage = currentStage;
        
        // Initialize validation labels  
        this.usernameValidationLabel = registerView.getusernameValidationLabel();  
        this.passwordValidationLabel = registerView.getPasswordValidationLabel();  
        this.phoneValidationLabel = registerView.getPhoneValidationLabel();  
        this.addressValidationLabel = registerView.getAddressValidationLabel();  
        this.passwordMatchLabel = registerView.getPasswordMatchLabel();  
        
        
        registerView.getRoleComboBox().setOnAction(e -> updateRegisterButtonState()); 
        setupRegisterAction();
        setupGetLoginHyperlink();
        addValidationListeners(); 
    }

    private void setupRegisterAction() {
        registerView.getRegisterButton().setOnAction(e -> handleRegister());
    }
    
    
    
    private void setupGetLoginHyperlink() {
    	registerView.getLoginLink().setOnAction(event -> {
    		closeRegisterScene();
    		showLoginScene();
    		System.out.println("Pindah ke login Scene...");
    	});
    }
    
    private void addValidationListeners() {  
        TextField usernameField = registerView.getUsernameField();  
        PasswordField passwordField = registerView.getPasswordField();  
        PasswordField confirmPasswordField = registerView.getConfirmPasswordField();  
        TextField phoneNumberField = registerView.getPhoneNumberField();  
        TextField addressField = registerView.getAddressField();  
        ComboBox<String> roleComboBox = registerView.getRoleComboBox();  

        // Listener untuk username  
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {  
            String validationMessage = validateUsername(newValue);  
            if (validationMessage != null) {  
                usernameValidationLabel.setText(validationMessage);  
            } else {  
                usernameValidationLabel.setText("");  
            }  
            updateRegisterButtonState();  
        });  

        // Listener untuk password  
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {  
            String validationMessage = validatePassword(newValue);  
            if (validationMessage != null) {  
                passwordValidationLabel.setText(validationMessage);  
            } else {  
                passwordValidationLabel.setText("");  
            }  
            validatePasswords();  
            updateRegisterButtonState();  
        });  

        // Listener untuk konfirmasi password  
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {  
            validatePasswords();  
            updateRegisterButtonState();  
        });  

        // Listener untuk nomor telepon  
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {  
            String validationMessage = validatePhoneNumber(newValue);  
            if (validationMessage != null) {  
                phoneValidationLabel.setText(validationMessage);  
            } else {  
                phoneValidationLabel.setText("");  
            }  
            updateRegisterButtonState();  
        });  

        // Listener untuk alamat  
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {  
            String validationMessage = validateAddress(newValue);  
            if (validationMessage != null) {  
                addressValidationLabel.setText(validationMessage);  
            } else {  
                addressValidationLabel.setText("");  
            }  
            updateRegisterButtonState();  
        });  

        // Listener untuk role  
        roleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {  
            updateRegisterButtonState();  
        });  
    }  

    private void validatePasswords() {  
        String password = registerView.getPasswordField().getText();  
        String confirmPassword = registerView.getConfirmPasswordField().getText();  
        
        if (!password.isEmpty() && !confirmPassword.isEmpty()) {  
            if (!password.equals(confirmPassword)) {  
            	passwordMatchLabel.setText("Passwords do not match");  
            	passwordMatchLabel.setStyle("-fx-text-fill: red;"); // Tetap mempertahankan style  
            } else {  
            	passwordMatchLabel.setText("Passwords match");  
            	passwordMatchLabel.setStyle("-fx-text-fill: green;"); // Tambahkan style hijau saat cocok  
            }  
        } else {  
            // Jika salah satu password kosong, tetap tampilkan label kosong  
        	passwordMatchLabel.setText("");  
        	passwordMatchLabel.setStyle(""); // Kembalikan style default  
        }  
    }  

    private void updateRegisterButtonState() {  
        // Validasi setiap field  
        boolean isUsernameValid = usernameValidationLabel.getText().isEmpty()   
                                   && !registerView.getUsernameField().getText().trim().isEmpty();  
        
        boolean isPasswordValid = passwordValidationLabel.getText().isEmpty()   
                                   && !registerView.getPasswordField().getText().trim().isEmpty();  
        
        boolean isPhoneValid = phoneValidationLabel.getText().isEmpty()   
                                && !registerView.getPhoneNumberField().getText().trim().isEmpty();  
        
        boolean isAddressValid = addressValidationLabel.getText().isEmpty()   
                                  && !registerView.getAddressField().getText().trim().isEmpty();  
        
        boolean arePasswordsMatching = !registerView.getPasswordField().getText().isEmpty()  
                                        && registerView.getPasswordField().getText().equals(  
                                            registerView.getConfirmPasswordField().getText()  
                                        );  
        
        boolean isRoleSelected = registerView.getRoleComboBox().getValue() != null;  

        // Aktifkan tombol register hanya jika semua kondisi terpenuhi  
        registerView.getRegisterButton().setDisable(!(  
            isUsernameValid &&  
            isPasswordValid &&  
            isPhoneValid &&  
            isAddressValid &&  
            arePasswordsMatching &&  
            isRoleSelected  
        ));  
    }
    
    
    
    // validate area
    private String validateUsername(String username) {  
        if (username == null || username.trim().isEmpty()) {  
            return "Username cannot be empty.";  
        } else if (username.length() < 3) {  
            return "Username must be at least 3 characters long.";  
        }else if (username.contains("Admin")) {
        	return "Jangan ya dek ya!";
        }
        return null;  
    }  

    private String validatePassword(String password) {  
        if (password == null || password.trim().isEmpty()) {  
            return "Password cannot be empty.";  
        } else if (password.length() < 8) {  
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

    private String validatePhoneNumber(String phoneNumber) {  
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {  
            return "Phone number cannot be empty.";  
        }  
        
        phoneNumber = phoneNumber.trim();  
        if (!phoneNumber.startsWith("+62")) {  
            return "Phone number must start with +62.";  
        }  
        
        if (phoneNumber.length() < 3) {  
            return "Invalid phone number format.";  
        }  
        
        String digitsOnly = phoneNumber.substring(3);  
        
        if (digitsOnly.length() != 9) {  
            return "Phone number must have 9 digits after +62.";  
        }  
        
        for (char c : digitsOnly.toCharArray()) {  
            if (!Character.isDigit(c)) {  
                return "Phone number must contain only digits after +62.";  
            }  
        }  
  
        return null;  
    }  

    private String validateAddress(String address) {  
        if (address == null || address.trim().isEmpty()) {  
            return "Address cannot be empty.";  
        } else if (address.length() < 5) {  
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
        	popup.showSuccessPopup("Success", "Register Success!");
        	closeRegisterScene();
        	showLoginScene();
        } else {
        	popup.showErrorPopup("Error", "Registration failed.");  
        }
    }
    
    

    public void showRegisterScene(Stage primaryStage) {
        primaryStage.setScene(registerView.createRegisterScene());
        primaryStage.showAndWait();
    }
    
    private void closeRegisterScene() {
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
}
