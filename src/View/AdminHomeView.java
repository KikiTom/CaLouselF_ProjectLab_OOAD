package View;

import java.util.stream.Stream;

import Controller.AdminHomeController;
import Model.Item;
import Model.User;
import Repository.Database;
import Repository.UserRepository;
import Repository.UserRepositoryimpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;

public class AdminHomeView {
    private TableView<Item> tableView;
    private AdminComponentView adminComponentView;
    private String username;
    private Button logoutButton;

    // Approve and Decline buttons
    private Button approveButton;
    private Button declineButton;

    public AdminHomeView(String username) {
        this.username = username;
    }

    public Scene createAdminHomeScene(Stage stage) {  
        // Create BorderPane as the root layout  
        BorderPane root = new BorderPane();  

        // Initialize AdminComponentView  
        adminComponentView = new AdminComponentView();  
        VBox sidebar = adminComponentView.getSidebar(username);  

        logoutButton = adminComponentView.getLogoutButton();  

        // Create TableView  
        tableView = new TableView<>();  
        tableView.setEditable(true);   
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Gunakan CONSTRAINED untuk mengisi penuh  
        tableView.setPrefWidth(1000);  
        tableView.setPrefHeight(600);  
        tableView.setStyle(  
            "-fx-background-color: white;" +  
            "-fx-control-inner-background: #F4F4F4;" + // Warna background alternatif  
            "-fx-background-insets: 0;" +  
            "-fx-background-radius: 5;" +  
            "-fx-font-size: 13px;"  
        );  
        
        // Define columns dengan lebar proporsional  
        TableColumn<Item, Boolean> selectCol = new TableColumn<>("Select");  
        selectCol.setMaxWidth(50);  
        selectCol.setMinWidth(50);  
        selectCol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());  
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(param ->   
            tableView.getItems().get(param).selectedProperty()  
        ));   
        selectCol.setEditable(true);   
        selectCol.setSortable(false);  

        TableColumn<Item, Integer> itemIdCol = new TableColumn<>("Item ID");  
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));  
        itemIdCol.setMaxWidth(60);  
        itemIdCol.setMinWidth(60);  
        itemIdCol.setPrefWidth(60);  
        itemIdCol.setStyle("-fx-alignment: CENTER;");  

        // Tambahkan custom cell factory untuk format tampilan  
        itemIdCol.setCellFactory(column -> new TableCell<Item, Integer>() {  
            @Override  
            protected void updateItem(Integer item, boolean empty) {  
                super.updateItem(item, empty);  
                
                if (item == null || empty) {  
                    setText(null);  
                    setGraphic(null);  
                } else {  
                    setText(String.valueOf(item)); // Pastikan ditampilkan sebagai string  
                    setStyle("-fx-alignment: CENTER;");  
                }  
            }  
        }); 

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");  
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));  

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");  
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");  
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        sizeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Item, Integer> priceCol = new TableColumn<>("Price");  
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));  

        TableColumn<Item, String> statusCol = new TableColumn<>("Status");  
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));  
        
        TableColumn<Item, String> userCol = new TableColumn<>("Seller");  
        userCol.setCellValueFactory(cellData ->   
            new SimpleStringProperty(cellData.getValue().getUser().getUsername())  
        );  

        // Styling kolom  
        Stream.of(itemIdCol, nameCol, categoryCol, sizeCol, priceCol, statusCol, userCol)  
            .forEach(col -> {  
                col.setStyle("-fx-alignment: CENTER;");  
                col.setSortable(true);  
            });  

        // Add columns to TableView  
        tableView.getColumns().addAll(  
            selectCol, itemIdCol, nameCol, categoryCol,  
            sizeCol, priceCol, statusCol, userCol  
        );   

        // Add action buttons  
        HBox actionButtons = createActionButtons();  

        // Layout for table and buttons  
        VBox tableBox = new VBox(10);  
        tableBox.setAlignment(Pos.CENTER);  
        tableBox.setPadding(new Insets(10));  
        tableBox.getChildren().addAll(  
            new Label("Pending Items") {{  
                setStyle(  
                    "-fx-font-size: 16px;" +  
                    "-fx-font-weight: bold;" +  
                    "-fx-padding: 5 0 10 0;"  
                );  
            }},  
            tableView,  
            actionButtons  
        );  
        VBox.setVgrow(tableView, Priority.ALWAYS);  

        // Add sidebar and table to BorderPane  
        root.setLeft(sidebar);  
        root.setCenter(tableBox);  

        // Create scene with size 1200x700  
        Scene scene = new Scene(root, 1200, 700);  

        // Set minimum and fixed size  
        stage.setMinWidth(1200);  
        stage.setMinHeight(700);  
        stage.setMaxWidth(1200);  
        stage.setMaxHeight(700);  

        return scene;  
    }  

    private HBox createActionButtons() {  
        HBox buttonBox = new HBox(20);  
        buttonBox.setAlignment(Pos.CENTER);  
        buttonBox.setPadding(new Insets(10, 0, 0, 0));  

        // Initialize approveButton and declineButton  
        approveButton = new Button("Approve Selected");  
        approveButton.setStyle(  
            "-fx-background-color: #2ECC71;" +  
            "-fx-text-fill: white;" +  
            "-fx-font-size: 13px;" +  
            "-fx-padding: 8 15 8 15;" +  
            "-fx-background-radius: 5;" +  
            "-fx-cursor: hand;"  
        );  

        declineButton = new Button("Decline Selected");  
        declineButton.setStyle(  
            "-fx-background-color: #E74C3C;" +  
            "-fx-text-fill: white;" +  
            "-fx-font-size: 13px;" +  
            "-fx-padding: 8 15 8 15;" +  
            "-fx-background-radius: 5;" +  
            "-fx-cursor: hand;"  
        );  

        buttonBox.getChildren().addAll(approveButton, declineButton);  

        return buttonBox;  
    }

    public TableView<Item> getTableView() {
        return tableView;
    }

    public Button getLogoutButton() {
        // Ensure logoutButton is initialized
        if (logoutButton == null) {
            // Attempt to get from adminComponentView
            if (adminComponentView != null) {
                logoutButton = adminComponentView.getLogoutButton();
            }

            // If still null, create a new one as a fallback
            if (logoutButton == null) {
                logoutButton = new Button("Logout");
                System.err.println("Created a new logout button as a fallback");
            }
        }
        return logoutButton;
    }

    // Getters for Approve and Decline buttons
    public Button getApproveButton() {
        return approveButton;
    }

    public Button getDeclineButton() {
        return declineButton;
    }

    public void populateItemRows(java.util.List<Item> items) {  
        // Bersihkan item yang ada
    	Database database = Database.getInstance();
    	UserRepository userRepository = new UserRepositoryimpl(database);
        tableView.getItems().clear();  

        // Tambahkan item baru  
        for (Item item : items) {  
            // Pastikan setiap item memiliki properti selected yang baru
        	item.setUserRepository(userRepository);
            item.setSelected(false);  
        }  

        // Tambahkan semua item ke tabel  
        tableView.getItems().addAll(items);  
    }
}
