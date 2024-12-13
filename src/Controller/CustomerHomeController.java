package Controller;  

import View.CustomerHomeView;  
import Model.Item;  
import Service.ItemService;  
import javafx.scene.Scene;  
import javafx.scene.control.ScrollPane;  
import javafx.stage.Stage;  

import java.util.List;  

public class CustomerHomeController {  
    private CustomerHomeView view;  
    private ItemService itemService;  
    private String username;  

    public CustomerHomeController(String username, ItemService itemService) {  
        this.username = username;  
        this.itemService = itemService;  
        
        // Buat view dengan username dan item service  
        this.view = new CustomerHomeView(username, itemService);  
        
        // Set controller ke view  
        this.view.setController(this);  
    }  

    public void show(Stage primaryStage) {  
        // Buat scene dengan view  
        Scene scene = new Scene(view);  
        
        // Atur stage  
        primaryStage.setScene(scene);  
        primaryStage.setTitle("CaLouselF - Customer Home");  
        primaryStage.setWidth(1200);  
        primaryStage.setHeight(700);  
        
        // Tampilkan stage  
        primaryStage.show();  
        
        // Muat dan tampilkan item  
        loadAndDisplayItems();  
    }  

    private void loadAndDisplayItems() {  
        // Dapatkan item yang sudah diterima  
        List<Item> availableItems = itemService.Getaccepteditem();  
        
        // Buat ScrollPane dengan item  
        ScrollPane itemScrollPane = view.createItemScrollPane(availableItems);  
        
        // Perbarui tampilan dengan ScrollPane  
        view.updateItemScrollPane(itemScrollPane);  
    }  

    // Metode untuk mencari item  
    public void searchItems(String keyword) {  
        // Implementasi pencarian item  
        List<Item> searchResults = itemService.searchItems(keyword);  
        
        // Buat ScrollPane dengan hasil pencarian  
        ScrollPane searchResultScrollPane = view.createItemScrollPane(searchResults);  
        
        // Perbarui tampilan dengan hasil pencarian  
        view.updateItemScrollPane(searchResultScrollPane);  
    }  

    // Metode untuk menangani klik item  
    public void handleItemClick(Item item) {  
        // Buka detail item atau lakukan aksi lain  
        System.out.println("Item diklik: " + item.getName());  
        // Misalnya, buka dialog detail item  
        // openItemDetailDialog(item);  
    }  

    // Metode untuk logout  
    public void logout(Stage stage) {  
        // Tutup stage saat ini  
        stage.close();  
        
        // Kembali ke layar login atau lakukan aksi logout lainnya  
        // Misalnya:  
        // LoginView loginView = new LoginView();  
        // loginView.show();  
    }  
}