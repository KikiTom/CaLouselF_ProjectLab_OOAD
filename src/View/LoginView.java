
package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Priority;

public class LoginView {
	private TextField usernameField;
	private PasswordField passwordField;
	private Label welcomeLabel, shopNameLabel;
	private Button loginButton;
	private Hyperlink registerLink;
	private Label usernameValidationLabel;
	private Label passwordValidationLabel;

	public LoginView() {
		initializeComponents();
	}

	private void initializeComponents() {
		// Shop Name Label
		shopNameLabel = new Label("CaLouseIF");
		shopNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		shopNameLabel.setTextFill(Color.DARKBLUE);
		shopNameLabel.setStyle("-fx-alignment: center;");

		// Welcome Label
		welcomeLabel = new Label("Welcome, Please Login!");
		welcomeLabel.setFont(Font.font("Arial", 16));
		welcomeLabel.setTextFill(Color.GRAY);
		welcomeLabel.setStyle("-fx-alignment: center;");

		// Username Field
		usernameField = createResponsiveTextField("Enter your username");

		// Password Field
		passwordField = createResponsivePasswordField("Enter your password");

		// Validation Labels
		usernameValidationLabel = createValidationLabel();
		passwordValidationLabel = createValidationLabel();

		// Login Button
		loginButton = createResponsiveButton("LOGIN");

		// Register Link
		registerLink = new Hyperlink("Don't have an account? Register here");
		registerLink.setTextFill(Color.BLUE);
		registerLink.setStyle("-fx-alignment: center;");
	}

	private TextField createResponsiveTextField(String prompt) {
		TextField textField = new TextField();
		textField.setPromptText(prompt);
		textField.setStyle(
				"-fx-background-radius: 5;" + "-fx-padding: 10px;" + "-fx-max-width: 300px;" + "-fx-min-width: 250px;");
		return textField;
	}

	private PasswordField createResponsivePasswordField(String prompt) {
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText(prompt);
		passwordField.setStyle(
				"-fx-background-radius: 5;" + "-fx-padding: 10px;" + "-fx-max-width: 300px;" + "-fx-min-width: 250px;");
		return passwordField;
	}

	private Button createResponsiveButton(String text) {
		Button button = new Button(text);
		button.setStyle("-fx-background-color: #3498db;" + "-fx-text-fill: white;" + "-fx-background-radius: 5;"
				+ "-fx-padding: 10px 20px;" + "-fx-font-weight: bold;" + "-fx-max-width: 300px;"
				+ "-fx-min-width: 250px;");

		// Hover effects
		button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980b9;" + "-fx-text-fill: white;"
				+ "-fx-background-radius: 5;" + "-fx-padding: 10px 20px;" + "-fx-font-weight: bold;"
				+ "-fx-max-width: 300px;" + "-fx-min-width: 250px;"));

		button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498db;" + "-fx-text-fill: white;"
				+ "-fx-background-radius: 5;" + "-fx-padding: 10px 20px;" + "-fx-font-weight: bold;"
				+ "-fx-max-width: 300px;" + "-fx-min-width: 250px;"));

		return button;
	}

	private Label createValidationLabel() {
		Label label = new Label();
		label.setStyle("-fx-text-fill: red;" + "-fx-font-size: 10px;" + "-fx-alignment: center-left;");
		return label;
	}

	public Scene createLoginScene(Stage primaryStage) {
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

		// Login Form Container
		VBox loginForm = new VBox(10);
		loginForm.setAlignment(Pos.CENTER);
		loginForm.setStyle("-fx-background-color: white;" + "-fx-background-radius: 10;" + "-fx-padding: 30px;");
		loginForm.setEffect(dropShadow);
		loginForm.setMaxWidth(400);
		VBox.setVgrow(loginForm, Priority.ALWAYS);

		// Add components to login form
		loginForm.getChildren().addAll(shopNameLabel, welcomeLabel,
				createLabeledField("Username", usernameField, usernameValidationLabel),
				createLabeledField("Password", passwordField, passwordValidationLabel), loginButton, registerLink);

		// Add login form to main layout
		mainLayout.getChildren().add(loginForm);

		// Create scene with responsive sizing
		Scene scene = new Scene(mainLayout);
		scene.widthProperty().addListener((obs, oldVal, newVal) -> {
			loginForm.setMaxWidth(Math.min(newVal.doubleValue() * 0.8, 400));
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

	// Existing getters
	public TextField getUsernameField() {
		return usernameField;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public Hyperlink getRegisterLink() {
		return registerLink;
	}

	// New getters for validation labels
	public Label getUsernameValidationLabel() {
		return usernameValidationLabel;
	}

	public Label getPasswordValidationLabel() {
		return passwordValidationLabel;
	}
}
