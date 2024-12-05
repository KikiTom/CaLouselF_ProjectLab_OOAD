package Main;

import javafx.application.Application;
import javafx.stage.Stage;
import Controller.LoginController;
import Service.UserService;
import Service.UserServiceImpl;
import TestView.testing;
import Repository.Database;
import Repository.UserRepository;
import Repository.UserRepositoryimpl;
import View.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize repository, service, and views
        Database database = Database.getInstance();
        UserRepository userRepository = new UserRepositoryimpl(database);
        UserService userService = new UserServiceImpl(userRepository);

        LoginView loginView = new LoginView();

        // Initialize controllers
        LoginController loginController = new LoginController(userService, loginView, primaryStage);

        // Show the Login scene initially
//       loginController.showLoginScene(primaryStage);
        testing testing = new testing(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
