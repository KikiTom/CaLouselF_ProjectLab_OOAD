
package View;

import Controller.RegisterController;
import Service.UserService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;

public class RegisterView {
    private Label shopNameLabel,passwordMatchLabel,welcomeLabel,validationlabel;
    private TextField usernameField,phoneNumberField, addressField;
    private PasswordField passwordField,confirmPasswordField;
    private ComboBox<String> roleComboBox;
    private Button registerButton;
    private Hyperlink loginLink;
    
    private Label usernameValidationLabel;  
    private Label passwordValidationLabel;  
    private Label phoneValidationLabel;  
    private Label addressValidationLabel;

    public RegisterView() {
        shopNameLabel = new Label("CaLouseIF");
        shopNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        shopNameLabel.setTextFill(Color.DARKBLUE);

        welcomeLabel = new Label("Create Your Account");
        welcomeLabel.setFont(Font.font("Arial", 16));
        welcomeLabel.setTextFill(Color.GRAY);

        usernameField = new TextField();
        usernameField.setPromptText("Choose a username");
        usernameField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Create a strong password");
        passwordField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        passwordMatchLabel = new Label();
        passwordMatchLabel.setStyle("-fx-text-fill: red;");

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePasswords();
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePasswords();
        });

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter your phone number");
        phoneNumberField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        addressField = new TextField();
        addressField.setPromptText("Enter your address");
        addressField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        roleComboBox = new ComboBox<>();
        roleComboBox.setPromptText("Select your role");
        roleComboBox.getItems().addAll("Customer", "Seller");
        roleComboBox.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");
        roleComboBox.setMaxWidth(Double.MAX_VALUE);

        registerButton = new Button("REGISTER");
        registerButton.setStyle(
            "-fx-background-color: #2ecc71;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-weight: bold;"
        );
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-weight: bold;"
        ));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(
            "-fx-background-color: #2ecc71;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-weight: bold;"
        ));

        registerButton.setDisable(true);

        loginLink = new Hyperlink("Already have an account? Login here");
        loginLink.setTextFill(Color.BLUE);
        
        usernameValidationLabel = createValidationLabel();  
        passwordValidationLabel = createValidationLabel();  
        phoneValidationLabel = createValidationLabel();  
        addressValidationLabel = createValidationLabel(); 
        
        addValidationListeners();
    }
    
    private Label createValidationLabel() {  
        Label label = new Label();  
        label.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");  
        return label;  
    }
    
    private void addValidationListeners() {  
        
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {  
            if (RegisterController.validateUsername(newValue) != null) {
            	String label = RegisterController.validateUsername(newValue);
                usernameValidationLabel.setText(label);  
                updateRegisterButtonState();  
                return;  
            }  
            
            // Trim to remove leading/trailing whitespaces  
            newValue = newValue.trim();  
            
            // If all checks pass  
            usernameValidationLabel.setText("");  
            updateRegisterButtonState();  
        });  

        // Password Validation  
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {  
            // Null or empty check  
            if (RegisterController.validatePassword(newValue) != null) {
            	String label = RegisterController.validatePassword(newValue);
                passwordValidationLabel.setText(label);  
                updateRegisterButtonState();  
                return;  
            }  
            
            // Trim to remove leading/trailing whitespaces  
            newValue = newValue.trim();  
              
            // If all checks pass  
            passwordValidationLabel.setText("");  
            updateRegisterButtonState();  
        });  

        // Phone Number Validation  
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {  
            // Null or empty check  
            if (RegisterController.validatePhoneNumber(newValue) != null) {
            	String label = RegisterController.validatePhoneNumber(newValue);
                phoneValidationLabel.setText(label);  
                updateRegisterButtonState();  
                return;  
            }  
            
            // Trim to remove leading/trailing whitespaces  
            newValue = newValue.trim();    
            
            // If all checks pass  
            phoneValidationLabel.setText("");  
            updateRegisterButtonState();  
        });  

        // Address Validation  
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {  
            // Null or empty check  
            if (RegisterController.validateAddress(newValue) != null) {
            	String label = RegisterController.validateAddress(newValue);
                addressValidationLabel.setText(label);  
                updateRegisterButtonState();  
                return;  
            }  
            
            // Trim to remove leading/trailing whitespaces  
            newValue = newValue.trim();  
         
            // If all checks pass  
            addressValidationLabel.setText("");  
            updateRegisterButtonState();  
        });  

         
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {  
            validatePasswords();  
        });  

        
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {  
            validatePasswords();  
        });  
    } 
    
    private void updateRegisterButtonState() {  
        boolean isUsernameValid = usernameValidationLabel.getText().isEmpty();  
        boolean isPasswordValid = passwordValidationLabel.getText().isEmpty();  
        boolean isPhoneValid = phoneValidationLabel.getText().isEmpty();  
        boolean isAddressValid = addressValidationLabel.getText().isEmpty();  
        
        
        boolean arePasswordsMatching = !passwordField.getText().isEmpty()   
                                        && passwordField.getText().equals(confirmPasswordField.getText());  
        
        boolean isRoleSelected = roleComboBox.getValue() != null;  

        registerButton.setDisable(!(  
            isUsernameValid &&   
            isPasswordValid &&   
            isPhoneValid &&   
            isAddressValid &&   
            arePasswordsMatching &&   
            isRoleSelected  
        ));  
    } 

    private void validatePasswords() {  
        String password = passwordField.getText();  
        String confirmPassword = confirmPasswordField.getText();  

        if (password.isEmpty() && confirmPassword.isEmpty()) {  
            passwordMatchLabel.setText("");  
            passwordMatchLabel.setStyle(""); 
        } else if (password.equals(confirmPassword)) {  
            passwordMatchLabel.setText("Passwords match ✓");  
            passwordMatchLabel.setStyle("-fx-text-fill: green;");  
        } else {  
            passwordMatchLabel.setText("Passwords do not match ✗");  
            passwordMatchLabel.setStyle("-fx-text-fill: red;");  
        }  
        
         
        updateRegisterButtonState();  
    }  

    public Scene createRegisterScene(Stage primaryStage) {
        // Main Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");

        // Drop Shadow Effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        // Register Form Container
        VBox registerForm = new VBox(10);
        registerForm.setAlignment(Pos.CENTER);
        registerForm.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 30px;"
        );
        registerForm.setEffect(dropShadow);

        // Add components to register form
        registerForm.getChildren().addAll(  
        		shopNameLabel,  
                welcomeLabel,  
                new Label("Username"),  
                usernameField,  
                usernameValidationLabel,  
                new Label("Password"),  
                passwordField,  
                passwordValidationLabel,  
                new Label("Confirm Password"),  
                confirmPasswordField,  
                passwordMatchLabel,  
                new Label("Phone Number"),  
                phoneNumberField,  
                phoneValidationLabel,  
                new Label("Address"),  
                addressField,  
                addressValidationLabel,  
                new Label("Role"),  
                roleComboBox,  
                registerButton,  
                loginLink  
            ); 

        roleComboBox.setOnAction(e -> updateRegisterButtonState());
        
        // Add register form to main layout
        mainLayout.getChildren().add(registerForm);

        // Create scene
        Scene scene = new Scene(mainLayout, 400, 750);
        primaryStage.setResizable(false);
        return scene;
    }

    // Getters
    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
    
    public Hyperlink getLoginLink() {
    	return loginLink;
    }
    
    public void setUsernameValidationLabel(String string) {  
        this.usernameValidationLabel.setText(string);   
    }

}

