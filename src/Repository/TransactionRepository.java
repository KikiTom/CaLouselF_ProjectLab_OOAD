package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Item;
import Model.Transaction;
import Repository.RepositoryInterface.GetById;
import Repository.RepositoryInterface.Create;

public class TransactionRepository extends RepositoryInheritClass implements GetById<Transaction>, Create<Transaction>{
	
    public TransactionRepository(Database database) {
        super(database);
    }
	
    @Override
    public boolean create(Transaction entity) {
    	try (Connection connection = database.getConnection()) {
            String query = "INSERT INTO transaction (UserId, ItemId, Status) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getItem().getId());
            stmt.setString(3, entity.getStatus());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public Transaction getById(int id) {
		try (Connection connection = database.getConnection()) {
			String query = ""
            		+ "SELECT transaction.Id, transaction.ItemId, items.Name, items.Size, items.Price, items.Category, transaction.Status, items.IsAccepted "
            		+ "FROM transactions JOIN items ON transaction.ItemId = items.Id "
            		+ "Where transaction.Id = ?";
            
            Transaction transaction = new Transaction();
            Item item = new Item();
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
        	    
            	int userId = rs.getInt("UserId");
            	int itemId = rs.getInt("ItemId");
            	String name = rs.getString("Name");
            	String size = rs.getString("Size");
            	int price = rs.getInt("Price");
            	String category = rs.getString("Name");
            	String status = rs.getString("Status");
            	Boolean isAccepted = rs.getBoolean("IsAccepted");
            	
                transaction.setId(id);
                transaction.setUserId(userId);
                
                item.setId(itemId);
                item.setName(name);
                item.setSize(size);
                item.setCategory(category);
                item.setPrice(price);
                item.setStatus(status);
                item.setIsAccepted(isAccepted);
                
                transaction.setItem(item);
            }
            return transaction;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

	public List<Transaction> getByItemId(int itemId){  
	    List<Transaction> transactionList = new ArrayList<>();  
	    
	    try (Connection connection = database.getConnection()) {  
	        String query = ""  
	            + "SELECT transaction.Id, transaction.UserId, transaction.Status, " // Added transaction.Status  
	            + "items.Id AS ItemId, items.Name, items.Size, items.Price, items.Category, items.IsAccepted "  
	            + "FROM transaction JOIN items ON transaction.ItemId = items.Id "  
	            + "WHERE transaction.ItemId = ?";   
	              
	        PreparedStatement stmt = connection.prepareStatement(query);  
	        stmt.setInt(1, itemId);  
	        ResultSet rs = stmt.executeQuery();  
	        
	        while(rs.next()) {  
	            Transaction transaction = new Transaction();  
	            Item item = new Item();  
	            
	            int id = rs.getInt("Id");  
	            int userId = rs.getInt("UserId");  
	            String status = rs.getString("Status"); // Correctly retrieve transaction status  
	            
	            int retrievedItemId = rs.getInt("ItemId");  
	            String name = rs.getString("Name");  
	            String size = rs.getString("Size");  
	            int price = rs.getInt("Price");  
	            String category = rs.getString("Category"); // Fixed category retrieval  
	            Boolean isAccepted = rs.getBoolean("IsAccepted");  
	            
	            transaction.setId(id);  
	            transaction.setUserId(userId);  
	            transaction.setStatus(status); // Set transaction status  
	            
	            item.setId(retrievedItemId);  
	            item.setName(name);  
	            item.setSize(size);  
	            item.setCategory(category);  
	            item.setPrice(price);  
	            item.setIsAccepted(isAccepted);  
	            
	            transaction.setItem(item);  
	            
	            transactionList.add(transaction);  
	            
	             
	        }  
	        
	        return transactionList;  
	        
	    } catch (SQLException e) {  
	        e.printStackTrace();  
	        return new ArrayList<>(); // Return empty list instead of null  
	    }  
	}  
	
	public List<Transaction> getByUserId(int Id){  
	    List<Transaction> transactionList = new ArrayList<>();  
	    
	    try (Connection connection = database.getConnection()) {  
	        String query = ""  
	            + "SELECT transaction.Id AS TransactionId, transaction.UserId, items.Id AS ItemId, "  
	            + "items.Name, items.Size, items.Price, items.Category, transaction.Status, items.IsAccepted "  
	            + "FROM transaction JOIN items ON transaction.ItemId = items.Id "  
	            + "WHERE transaction.UserId = ?";  
	              
	        PreparedStatement stmt = connection.prepareStatement(query);  
	        stmt.setInt(1, Id);  
	        ResultSet rs = stmt.executeQuery();  
	        
	        while(rs.next()) {  
	            Transaction transaction = new Transaction();  
	            Item item = new Item();  
	            
	            int transactionId = rs.getInt("TransactionId");  
	            int userId = rs.getInt("UserId");  
	            int itemId = rs.getInt("ItemId");  
	            String name = rs.getString("Name");  
	            String size = rs.getString("Size");  
	            int price = rs.getInt("Price");  
	            String category = rs.getString("Category"); // Fixed category retrieval  
	            String status = rs.getString("Status");  
	            Boolean isAccepted = rs.getBoolean("IsAccepted");  
	            
	            transaction.setId(transactionId);  
	            transaction.setUserId(userId);  
	            
	            item.setId(itemId);  
	            item.setName(name);  
	            item.setSize(size);  
	            item.setCategory(category);  
	            item.setPrice(price);  
	            item.setStatus(status);  
	            item.setIsAccepted(isAccepted);  
	            
	            transaction.setItem(item);  
	            
	            transactionList.add(transaction);  
	        }  
	        
	    } catch (SQLException e) {  
	        e.printStackTrace();  
	        return new ArrayList<>(); // Return empty list instead of null  
	    }  
	    
	    return transactionList;  
	}  
}
