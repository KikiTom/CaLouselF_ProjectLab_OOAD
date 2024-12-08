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

    /**  
    * Fetches all pending (not yet approved) items.  
    *  
    * @return list of pending items  
    */  
   public List<Item> getPendingItems() {  
       try {  
           // Ambil semua item, filter yang belum di-approve  
           return itemRepository.getAll().stream()  
               .filter(item -> item.getIsAccepted() == false && item.getStatus().contains("Waiting Approval"))  
               .collect(Collectors.toList());  
       } catch (Exception e) {  
           e.printStackTrace();  
           System.err.println("Error in getPendingItems: " + e.getMessage());  
           return List.of(); // Return empty list on error  
       }  
   }  

   /**  
    * Approve an item by its ID.  
    *  
    * @param itemId the ID of the item to approve  
    * @return true if approval was successful, false otherwise  
    */  
   public boolean approveItem(int itemId) {  
       try {  
    	   return itemRepository.UpdateAccepted(itemId) && itemRepository.UpdateStatus(itemId, "Available");
       } catch (Exception e) {  
           e.printStackTrace();  
           System.err.println("Error approving item: " + e.getMessage());  
           return false;  
       }  
   }  

   /**  
    * Decline an item by its ID.  
    *  
    * @param itemId the ID of the item to decline  
    * @return true if declining was successful, false otherwise  
    */  
   public boolean declineItem(int itemId, String Reason) {  
       try {  
           return itemRepository.UpdateStatus(itemId, "Decline, " + Reason);
       } catch (Exception e) {  
           e.printStackTrace();  
           System.err.println("Error declining item: " + e.getMessage());  
           return false;  
       }  
   } 
    
    // Additional business logic methods can be added here
}
