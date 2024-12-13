package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import Controller.LoginController;
import Model.Offer;
import Service.ItemService;
import Service.OfferService;
import Service.TransactionService;
import Service.UserService;
import Service.UserServiceImpl;
import TestView.Testing;
import Repository.Database;
import Repository.ItemRepository;
import Repository.OfferRepository;
import Repository.TransactionRepository;
import Repository.UserRepository;
import Repository.UserRepositoryimpl;
import View.LoginView;
import View.SellerHomeView;

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
        loginController.showLoginScene(primaryStage);
        
//          Testing testing = new Testing(primaryStage);
//          Scene scene = new Scene(testing);
//          primaryStage.setScene(scene);
//          primaryStage.setTitle("Employee Management System");
//          primaryStage.show();
//        SellerHomeView sellerHomeView = new SellerHomeView(primaryStage);  
//        primaryStage.setScene(sellerHomeView.createSellerHomeScene());  
//        
//        primaryStage.show(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
