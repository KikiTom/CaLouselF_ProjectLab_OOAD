package View;

import Controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class RegisterView {
	private Label shopNameLabel, passwordMatchLabel, welcomeLabel;
	private TextField usernameField, phoneNumberField, addressField;
	private PasswordField passwordField, confirmPasswordField;
	private ComboBox<String> roleComboBox;
	private Button registerButton;
	private Hyperlink loginLink;

	private Label usernameValidationLabel;
	private Label passwordValidationLabel;
	private Label phoneValidationLabel;
	private Label addressValidationLabel;

	public RegisterView() {
		initializeComponents();
	}

	private void initializeComponents() {
		// Shop Name Label
		shopNameLabel = new Label("CaLouseIF");
		shopNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		shopNameLabel.setTextFill(Color.DARKBLUE);
		shopNameLabel.setStyle("-fx-alignment: center;");

		// Welcome Label
		welcomeLabel = new Label("Create Your Account");
		welcomeLabel.setFont(Font.font("Arial", 16));
		welcomeLabel.setTextFill(Color.GRAY);
		welcomeLabel.setStyle("-fx-alignment: center;");

		// Input Fields with Responsive Styling
		usernameField = createResponsiveTextField("Choose a username");
		passwordField = createResponsivePasswordField("Create a strong password");
		confirmPasswordField = createResponsivePasswordField("Confirm your password");
		phoneNumberField = createResponsiveTextField("Enter your phone number");
		addressField = createResponsiveTextField("Enter your address");

		// Validation Labels
		passwordMatchLabel = createValidationLabel();
		usernameValidationLabel = createValidationLabel();
		passwordValidationLabel = createValidationLabel();
		phoneValidationLabel = createValidationLabel();
		addressValidationLabel = createValidationLabel();

		// Role ComboBox
		roleComboBox = new ComboBox<>();
		roleComboBox.setPromptText("Select your role");
		roleComboBox.getItems().addAll("Customer", "Seller");
		styleComboBox(roleComboBox);

		// Register Button
		registerButton = createResponsiveButton("REGISTER");
		registerButton.setDisable(true);

		// Login Link
		loginLink = new Hyperlink("Already have an account? Login here");
		loginLink.setTextFill(Color.BLUE);
		loginLink.setStyle("-fx-alignment: center;");
	}

	private TextField createResponsiveTextField(String prompt) {
		TextField textField = new TextField();
		textField.setPromptText(prompt);
		textField.setStyle(
				"-fx-background-radius: 5;" + "-fx-padding: 10px;" + "-fx-max-width: 300px;" + "-fx-min-width: 200px;");
		return textField;
	}

	private PasswordField createResponsivePasswordField(String prompt) {
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText(prompt);
		passwordField.setStyle(
				"-fx-background-radius: 5;" + "-fx-padding: 10px;" + "-fx-max-width: 300px;" + "-fx-min-width: 200px;");
		return passwordField;
	}

	private void styleComboBox(ComboBox<String> comboBox) {
		comboBox.setStyle(
				"-fx-background-radius: 5;" + "-fx-padding: 10px;" + "-fx-max-width: 300px;" + "-fx-min-width: 200px;");
		comboBox.setMaxWidth(Double.MAX_VALUE);
	}

	private Button createResponsiveButton(String text) {
		Button button = new Button(text);
		button.setStyle("-fx-background-color: #2ecc71;" + "-fx-text-fill: white;" + "-fx-background-radius: 5;"
				+ "-fx-padding: 10px 20px;" + "-fx-font-weight: bold;" + "-fx-max-width: 300px;"
				+ "-fx-min-width: 200px;");

		// Hover effects
		button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #27ae60;" + "-fx-text-fill: white;"
				+ "-fx-background-radius: 5;" + "-fx-padding: 10px 20px;" + "-fx-font-weight: bold;"
				+ "-fx-max-width: 300px;" + "-fx-min-width: 200px;"));

		button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2ecc71;" + "-fx-text-fill: white;"
				+ "-fx-background-radius: 5;" + "-fx-padding: 10px 20px;" + "-fx-font-weight: bold;"
				+ "-fx-max-width: 300px;" + "-fx-min-width: 200px;"));

		return button;
	}

	private Label createValidationLabel() {
		Label label = new Label();
		label.setStyle("-fx-text-fill: red;" + "-fx-font-size: 10px;" + "-fx-alignment: center-left;");
		return label;
	}

	public Scene createRegisterScene(Stage primaryStage) {
		// Main Layout with Responsive Design
		VBox mainLayout = new VBox(20);
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.setPadding(new Insets(20));
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
		registerForm.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10;" + "-fx-padding: 30px;");
		registerForm.setEffect(dropShadow);
		registerForm.setMaxWidth(400); // Limit max width
		VBox.setVgrow(registerForm, Priority.ALWAYS);

		// Add components to register form
		registerForm.getChildren().addAll(shopNameLabel, welcomeLabel,
				createLabeledField("Username", usernameField, usernameValidationLabel),
				createLabeledField("Password", passwordField, passwordValidationLabel),
				createLabeledField("Confirm Password", confirmPasswordField, passwordMatchLabel),
				createLabeledField("Phone Number", phoneNumberField, phoneValidationLabel),
				createLabeledField("Address", addressField, addressValidationLabel),
				createLabeledField("Role", roleComboBox, null), registerButton, loginLink);

		// Add register form to main layout
		mainLayout.getChildren().add(registerForm);

		// Create scene with responsive sizing
		Scene scene = new Scene(mainLayout);
		scene.widthProperty().addListener((obs, oldVal, newVal) -> {
			registerForm.setMaxWidth(Math.min(newVal.doubleValue() * 0.8, 400));
		});

		return scene;
	}

// Helper method to create labeled fields with validation  
	private VBox createLabeledField(String labelText, Control control, Label validationLabel) {
		VBox fieldBox = new VBox(5);
		fieldBox.setAlignment(Pos.CENTER_LEFT);

		Label label = new Label(labelText);
		label.setStyle("-fx-font-weight: bold;");

		fieldBox.getChildren().add(label);
		fieldBox.getChildren().add(control);

		if (validationLabel != null) {
			fieldBox.getChildren().add(validationLabel);
		}

		return fieldBox;
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

	public Label getusernameValidationLabel() {
		return usernameValidationLabel;
	}

	public Label getPasswordValidationLabel() {
		return passwordValidationLabel;
	}

	public Label getPhoneValidationLabel() {
		return phoneValidationLabel;
	}

	public Label getAddressValidationLabel() {
		return addressValidationLabel;
	}

	public Label getPasswordMatchLabel() {
		return passwordMatchLabel;
	}

}
