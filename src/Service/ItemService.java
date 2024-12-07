package Service;

import Model.Item;
import Model.Transaction;
import Repository.ItemRepository;
import Repository.TransactionRepository;
import Service.UserService;
import java.util.List;
import java.util.stream.Collectors;

public class ItemService {  
    private ItemRepository itemRepository;  
    private UserService userService;
    private TransactionRepository transactionRepository;

    public ItemService(ItemRepository itemRepository, UserService userService) {  
        this.itemRepository = itemRepository;  
        this.userService = userService;
    }
    
    public ItemService(ItemRepository itemRepository, UserService userService, TransactionRepository transactionRepository) {  
        this.itemRepository = itemRepository;  
        this.userService = userService;  
        this.transactionRepository = transactionRepository;  
    }

    /**
     * Fetches all items for a given username.
     *
     * @param username the username of the seller
     * @return list of items belonging to the user
     */
    public List<Item> getItemsByUsername(String username) {  
        try {  
            // Get user ID based on username  
            int userId = userService.getUserID(username);  

            // Retrieve items from repository based on user ID  
            return itemRepository.getAll().stream()  
                .filter(item -> item.getUserId() == userId)  
                .collect(Collectors.toList());  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.err.println("Error in getItemsByUsername: " + e.getMessage());  
            return List.of(); // Return empty list on error
        }  
    }
    
    /**
     * Adds a new item to the repository.
     *
     * @param item the item to add
     * @return true if successful, false otherwise
     */
    public boolean uploadItem(int userId, String itemName, String category, String size, double price) {  
        try {  
            // Buat objek Item dengan status default  
            Item newItem = new Item(  
                itemName,       // name  
                false,          // isAccepted (default false)  
                "Waiting Approval", // status  
                category,       // category  
                size,           // size  
                (int) price,    // price (convert double to int)  
                userId          // userId  
            );  

            // Gunakan repository untuk menyimpan item  
            return itemRepository.create(newItem);  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.err.println("Error uploading item: " + e.getMessage());  
            return false;  
        }  
    }
    
    /**  
     * Menghitung total item yang dimiliki oleh pengguna  
     *  
     * @param username nama pengguna  
     * @return jumlah total item  
     */  
    public int getTotalItemsByUsername(String username) {  
        try {  
            int userId = userService.getUserID(username);  
            return (int) itemRepository.getAll().stream()  
                .filter(item -> item.getUserId() == userId)  
                .count();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

      
    
    // Additional business logic methods can be added here
}
