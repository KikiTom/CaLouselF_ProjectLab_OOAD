package View;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class LoginView {
    private TextField usernameField;
    private PasswordField passwordField;
    private Label welcomeLabel, shopNameLabel;
    private Button loginButton;
    private Hyperlink registerLink;

    public LoginView() {
        // Initialize components
        shopNameLabel = new Label("CaLouseIF");
        shopNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        shopNameLabel.setTextFill(Color.DARKBLUE);

        welcomeLabel = new Label("Welcome, Please Login!");
        welcomeLabel.setFont(Font.font("Arial", 16));
        welcomeLabel.setTextFill(Color.GRAY);

        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-background-radius: 5; -fx-padding: 10px;");

        loginButton = new Button("LOGIN");
        loginButton.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-weight: bold;"
        );
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
            "-fx-background-color: #2980b9;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-weight: bold;"
        ));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10px 20px;" +
            "-fx-font-weight: bold;"
        ));

        registerLink = new Hyperlink("Don't have an account? Register here");
        registerLink.setTextFill(Color.BLUE);
    }

    public Scene createLoginScene(Stage primaryStage) {
        // Create layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");

        // Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        // Login form container
        VBox loginForm = new VBox(10);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 30px;"
        );
        loginForm.setEffect(dropShadow);

        // Add components to login form
        loginForm.getChildren().addAll(
            shopNameLabel,
            welcomeLabel,
            new Label("Username"),
            usernameField,
            new Label("Password"),
            passwordField,
            loginButton,
            registerLink
        );

        // Add login form to main layout
        mainLayout.getChildren().add(loginForm);

        // Create scene
        Scene scene = new Scene(mainLayout, 400, 600);
        primaryStage.setResizable(false);
        return scene;
}




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


}
