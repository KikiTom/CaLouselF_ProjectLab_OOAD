package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.LoginController;
import Controller.RegisterController;
import Service.UserService;
import Service.UserServiceImpl;
import Repository.Database;
import Repository.UserRepository;
import Repository.UserRepositoryimpl;
import View.LoginView;
import View.RegisterView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize repository, service, and views
        Database database = Database.getInstance();
        UserRepository userRepository = new UserRepositoryimpl(database);
        UserService userService = new UserServiceImpl(userRepository);

        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();

        // Initialize controllers
        LoginController loginController = new LoginController(userService, loginView);
        RegisterController registerController = new RegisterController(userService, registerView);

        // Show the Login scene initially
//        loginController.showLoginScene(primaryStage);
//        registerController.showRegisterScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
