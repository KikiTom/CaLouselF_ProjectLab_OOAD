package Service;  

import Model.Item;  
import Repository.ItemRepository;  
import View.SellerHomeView;  
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;  
import javafx.scene.control.ScrollPane;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;  
import java.util.List;
import java.util.stream.Collectors;  

public class ItemService {  
    private ItemRepository itemRepository;  
    private SellerHomeView sellerHomeView;
    private UserService userService;

    public ItemService(ItemRepository itemRepository, UserService userService, SellerHomeView sellerHomeView) {  
        this.itemRepository = itemRepository;  
        this.sellerHomeView = sellerHomeView;
        this.userService = userService;
    }  

    // Metode untuk mengisi ScrollPane dengan item-item  
    public void populateItemRows(String username) {  
        try {  
            // Dapatkan user ID berdasarkan username  
            int userId = userService.getUserID(username);  

            // Dapatkan daftar item dari repository berdasarkan user ID  
            List<Item> items = itemRepository.getAll().stream()  
                .filter(item -> item.getUserId() == userId)  
                .collect(Collectors.toList());  

            // Gunakan Platform.runLater untuk operasi UI  
            Platform.runLater(() -> {  
                // Dapatkan ScrollPane secara langsung dari contentArea  
                ScrollPane scrollPane = findScrollPane(sellerHomeView.getContentArea());  

                if (scrollPane != null) {  
                    // Dapatkan VBox container untuk item  
                    VBox itemsContainer = (VBox) scrollPane.getContent();  
                    
                    // Bersihkan item yang ada  
                    itemsContainer.getChildren().clear();  

                    // Tambahkan item baru  
                    for (Item item : items) {  
                        HBox itemRow = createItemRowFromItem(item);  
                        itemsContainer.getChildren().add(itemRow);  
                    }  

                    // Update statistik footer  
//                    updateFooterStatistics(items);  
                } else {  
                    System.err.println("ScrollPane tidak ditemukan");  
                }  
            });  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.err.println("Error dalam populateItemRows: " + e.getMessage());  
        }  
    }
    
    private ScrollPane findScrollPane(StackPane contentArea) {  
        if (contentArea == null) {  
            System.err.println("ContentArea adalah null");  
            return null;  
        }  

        // Cari di semua children dari contentArea  
        for (Node node : contentArea.getChildren()) {  
            if (node instanceof VBox) {  
                // Cari ScrollPane di dalam VBox  
                for (Node child : ((VBox) node).getChildren()) {  
                    if (child instanceof ScrollPane) {  
                        return (ScrollPane) child;  
                    }  
                }  
            }  
        }  

        System.err.println("ScrollPane tidak ditemukan di ContentArea");  
        return null;  
    } 
    
//    private void updateFooterStatistics(List<Item> items) {  
//        // Hitung total item  
//        int totalItems = items.size();  
//
//        // Hitung item yang terjual  
//        int soldItems = (int) items.stream()  
//            .filter(item -> item.getSoldQuantity() > 0)  
//            .count();  
//
//        // Hitung total transaksi  
//        double totalTransaction = items.stream()  
//            .mapToDouble(item -> item.getPrice() * item.getSoldQuantity())  
//            .sum();  
//
//        // Update label-label di footer  
//        // Anda perlu menambahkan metode di SellerHomeView untuk mengupdate footer  
//        // Misalnya:  
//        // sellerHomeView.updateFooterStatistics(totalItems, soldItems, totalTransaction);  
//    }

    // Metode untuk membuat item row dari objek Item  
    private HBox createItemRowFromItem(Item item) {  
        // Gunakan metode getcreateItemRow() dari SellerHomeView  
        HBox itemRow = sellerHomeView.getcreateItemRow();  

        // Update label-label di row dengan data item  
        sellerHomeView.updateItemRowLabels(  
            itemRow,   
            item.getCategory(),   
            item.getName(),   
            item.getSize(),   
            String.valueOf(item.getPrice()), // Ubah menjadi String.valueOf()  
            5,   
            item.getStatus()  
        );  

        return itemRow;  
    }     

    // Metode untuk mengupdate label-label di item row  
    private void updateItemRowLabels(HBox itemRow, Item item) {  
        // Dapatkan label-label dari row  
        // Sesuaikan dengan urutan label di createItemRow()  
        // Label editLabel = (Label) itemRow.getChildren().get(0);  
        // Label spacer = (Label) itemRow.getChildren().get(1);  
        Label categoryLabel = (Label) itemRow.getChildren().get(2);  
        Label nameLabel = (Label) itemRow.getChildren().get(3);  
        Label sizeLabel = (Label) itemRow.getChildren().get(4);  
        
        // Untuk price box  
        VBox priceBox = (VBox) itemRow.getChildren().get(5);  
        Label priceLabel = (Label) priceBox.getChildren().get(0);  
        Label soldLabel = (Label) priceBox.getChildren().get(1);  
        
        Label statusLabel = (Label) itemRow.getChildren().get(6);  

        // Update label-label  
        categoryLabel.setText(item.getCategory());  
        nameLabel.setText(item.getName());  
        sizeLabel.setText(item.getSize());  
        priceLabel.setText("$" + item.getPrice());  
        
        // Untuk sold label, Anda mungkin perlu menambahkan logika untuk mendapatkan jumlah penjualan  
        // Sementara ini, gunakan contoh statis  
        soldLabel.setText("0 sold");  
        
        statusLabel.setText(item.getStatus());  
    }  

//     Metode tambahan untuk operasi item  
//    public boolean addItem(Item item) {  
//        boolean result = itemRepository.create(item);  
//        if (result) {  
//            // Refresh tampilan item  
//        	populateItemRows(username);  
//        }  
//        return result;  
//    }  
//
//    public boolean updateItem(int id, Item item) {  
//        boolean result = itemRepository.update(id, item);  
//        if (result) {  
//            // Refresh tampilan item  
//        	populateItemRows(username);  
//        }  
//        return result;  
//    }  
//
//    public boolean deleteItem(int id) {  
//        boolean result = itemRepository.delete(id);  
//        if (result) {  
//            // Refresh tampilan item  
//        	populateItemRows(username);  
//        }  
//        return result;  
//    }  
}