package View;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterView {
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField phoneNumberField;
    private TextField addressField;
    private TextField roleField;
    private Button registerButton;

    public RegisterView() {
        usernameField = new TextField();
        passwordField = new PasswordField();
        phoneNumberField = new TextField();
        addressField = new TextField();
        roleField = new TextField();
        registerButton = new Button("Register");
    }

    public Scene createRegisterScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.add(usernameField, 0, 0);
        grid.add(passwordField, 0, 1);
        grid.add(phoneNumberField, 0, 2);
        grid.add(addressField, 0, 3);
        grid.add(roleField, 0, 4);
        grid.add(registerButton, 0, 5);

        Scene scene = new Scene(grid, 300, 250);
        return scene;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public TextField getRoleField() {
        return roleField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
