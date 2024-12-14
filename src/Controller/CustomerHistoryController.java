package Controller;  

import Model.Transaction;
import Repository.Database;
import Repository.TransactionRepository;
import Service.ItemService;
import Service.TransactionService;
import Service.UserService;
import View.CustomerHistoryView;  
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;  
import javafx.stage.Stage;  

import java.util.List;  

public class CustomerHistoryController {  
    private TransactionService transactionService;  
    private TransactionRepository transactionRepository = new TransactionRepository(Database.getInstance());
    private UserService userService;
    private ItemService itemService;
    private int userId;  
    private CustomerHistoryView view;  

    public CustomerHistoryController(int userId, UserService userService, ItemService itemService) {  
        this.userId = userId;
        this.itemService = itemService;
        this.userService = userService;
        this.transactionService = new TransactionService(transactionRepository);  
        initializeView();  
    }  

    private void initializeView() {  
        Stage stage = new Stage();  
        view = new CustomerHistoryView(stage, this, userService.getUserNamebyid(this.userId));  
    }  

    public ObservableList<Transaction> getCustomerTransactions() {  
        try {  
            // Fetch transactions for the specific user  
            List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);  
            return FXCollections.observableArrayList(transactions);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return FXCollections.emptyObservableList();  
        }  
    }  

    public void showView() {  
        view.show();  
    } 
    
    public void showhomescene() {
   	 Stage customerHomeStage = new Stage();
   	 CustomerHomeController homeController =   
                new CustomerHomeController(userService.getUserNamebyid(userId), itemService, userService);  
        homeController.show(customerHomeStage);
   }
}  