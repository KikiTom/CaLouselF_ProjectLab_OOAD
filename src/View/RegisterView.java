
package View;

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
    private Label shopNameLabel;
    private Label welcomeLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField phoneNumberField;
    private TextField addressField;
    private ComboBox<String> roleComboBox;
    private Button registerButton;
    private Hyperlink loginLink;
    private Label passwordMatchLabel;

    public RegisterView() {
        // Shop Name Label
        shopNameLabel = new Label("CaLouseIF");
        shopNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        shopNameLabel.setTextFill(Color.DARKBLUE);

        // Welcome Label
        welcomeLabel = new Label("Create Your Account");
        welcomeLabel.setFont(Font.font("Arial", 16));
        welcomeLabel.setTextFill(Color.GRAY);

        // Username Field
        usernameField = new TextField();
        usernameField.setPromptText("Choose a username");
        usernameField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        // Password Field
        passwordField = new PasswordField();
        passwordField.setPromptText("Create a strong password");
        passwordField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        // Confirm Password Field
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        // Password Match Label
        passwordMatchLabel = new Label();
        passwordMatchLabel.setStyle("-fx-text-fill: red;");

        // Add password matching validation
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePasswords();
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePasswords();
        });

        // Phone Number Field
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter your phone number");
        phoneNumberField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        // Address Field
        addressField = new TextField();
        addressField.setPromptText("Enter your address");
        addressField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        // Role ComboBox
        roleComboBox = new ComboBox<>();
        roleComboBox.setPromptText("Select your role");
        roleComboBox.getItems().addAll("Customer", "Seller", "Admin");
        roleComboBox.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");
        roleComboBox.setMaxWidth(Double.MAX_VALUE);

        // Register Button
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

        // Disable register button initially
        registerButton.setDisable(true);

        // Login Link
        loginLink = new Hyperlink("Already have an account? Login here");
        loginLink.setTextFill(Color.BLUE);
    }

    // Password validation method
    private void validatePasswords() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            passwordMatchLabel.setText("");
            registerButton.setDisable(true);
        } else if (password.equals(confirmPassword)) {
            passwordMatchLabel.setText("Passwords match ✓");
            passwordMatchLabel.setStyle("-fx-text-fill: green;");
            registerButton.setDisable(false);
        } else {
            passwordMatchLabel.setText("Passwords do not match ✗");
            passwordMatchLabel.setStyle("-fx-text-fill: red;");
            registerButton.setDisable(true);
        }
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
            new Label("Password"),
            passwordField,
            new Label("Confirm Password"),
            confirmPasswordField,
            passwordMatchLabel,
            new Label("Phone Number"),
            phoneNumberField,
            new Label("Address"),
            addressField,
            new Label("Role"),
            roleComboBox,
            registerButton,
            loginLink
        );

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

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

