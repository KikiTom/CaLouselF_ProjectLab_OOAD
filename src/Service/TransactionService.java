package Service;  

import Model.Item;
import Model.Transaction;
import Repository.ItemRepository;
import Repository.TransactionRepository;  
import java.util.List;  

public class TransactionService {  
    private TransactionRepository transactionRepository;  
    private ItemRepository itemRepository;
    private ItemService itemService;
    private UserService userService;
    
    // Constructor  
    public TransactionService(TransactionRepository transactionRepository, ItemRepository itemRepository) {  
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
    }
    
    public TransactionService(TransactionRepository transactionRepository) {
    	this.transactionRepository = transactionRepository;
    }
    
    public TransactionService(TransactionRepository transactionRepository, UserService userService) {  
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }
    
    public TransactionService(TransactionRepository transactionRepository
    		, ItemRepository itemRepository
    		, ItemService itemService) {  
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }
    

    /**  
     * Mendapatkan daftar transaksi berdasarkan ID item  
     *  
     * @param itemId ID dari item yang ingin dicari transaksinya  
     * @return List dari transaksi yang terkait dengan item tersebut  
     */  
    public List<Transaction> getTransactionsByItemId(int itemId) {  
        try {  
            return transactionRepository.getByItemId(itemId);  
        } catch (Exception e) {  
            System.err.println("Error in getTransactionsByItemId: " + e.getMessage());  
            e.printStackTrace();  
            return List.of(); // Return empty list on error  
        }  
    }  

    /**  
     * Mendapatkan daftar transaksi berdasarkan ID user  
     *  
     * @param userId ID dari user yang ingin dicari transaksinya  
     * @return List dari transaksi yang terkait dengan user tersebut  
     */  
    public List<Transaction> getTransactionsByUserId(int userId) {  
        try {  
            return transactionRepository.getByUserId(userId);  
        } catch (Exception e) {  
            System.err.println("Error in getTransactionsByUserId: " + e.getMessage());  
            e.printStackTrace();  
            return List.of(); // Return empty list on error  
        }  
    }  

    /**  
     * Mendapatkan detail transaksi berdasarkan ID transaksi  
     *  
     * @param transactionId ID dari transaksi yang ingin dicari detailnya  
     * @return Objek Transaction atau null jika tidak ditemukan  
     */  
    public Transaction getTransactionById(int transactionId) {  
        try {  
            return transactionRepository.getById(transactionId);  
        } catch (Exception e) {  
            System.err.println("Error in getTransactionById: " + e.getMessage());  
            e.printStackTrace();  
            return null;  
        }  
    }  

    /**  
     * Membuat transaksi baru  
     *  
     * @param transaction Objek Transaction yang akan disimpan  
     * @return boolean yang menandakan apakah transaksi berhasil dibuat  
     */  
    public boolean createTransaction(Transaction transaction) {  
        try {  
            return transactionRepository.create(transaction);  
        } catch (Exception e) {  
            System.err.println("Error in createTransaction: " + e.getMessage());  
            e.printStackTrace();  
            return false;  
        }  
    }
    
    /**  
     * Menghitung total transaksi (akumulasi harga) untuk semua item milik Seller   
     *  
     * @param username nama pengguna  
     * @return total nilai transaksi  
     */  
    public int getTotalTransactionValueByUsername(String username) {  
        try {  
            // Dapatkan semua item milik seller  
            List<Item> sellerItems = itemService.getItemsByUsername(username);  
            
            // Gunakan stream untuk menghitung total transaksi  
            return sellerItems.stream()  
                .flatMap(item -> {  
                    // Dapatkan transaksi untuk setiap item  
                    List<Transaction> itemTransactions = transactionRepository.getByItemId(item.getId());  
                    return itemTransactions.stream();  
                })  
                .filter(transaction -> transaction.getItem() != null)  
                .mapToInt(transaction -> transaction.getItem().getPrice())  
                .sum();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }

    /**  
     * Menghitung total transaksi keseluruhan (semua transaksi di sistem)  
     *  
     * @return total nilai transaksi keseluruhan  
     */  
    public int getTotalAllTransactionValue() {  
        try {  
            return itemRepository.getAll().stream()  
                .filter(Item::getIsAccepted)  // Hanya item yang sudah diterima  
                .mapToInt(Item::getPrice)  
                .sum();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /**  
     * Menghitung total transaksi untuk item tertentu  
     *  
     * @param itemId ID item  
     * @return total nilai transaksi untuk item tersebut  
     */  
    public int getTotalTransactionValueByItemId(int itemId) {  
        try {  
            List<Transaction> transactions = transactionRepository.getByItemId(itemId);  
            
            return transactions.stream()  
                .mapToInt(transaction -> transaction.getItem().getPrice())  
                .sum();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /**  
     * Mendapatkan jumlah transaksi untuk item tertentu  
     *  
     * @param itemId ID item  
     * @return jumlah transaksi untuk item tersebut  
     */  
    public int getTransactionCountByItemId(int itemId) {  
        try {  
            List<Transaction> transactions = transactionRepository.getByItemId(itemId);  
            return transactions.size();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }
}