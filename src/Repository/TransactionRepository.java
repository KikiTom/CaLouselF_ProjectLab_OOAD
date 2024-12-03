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
            String query = "INSERT INTO items (UserId, ItemId) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getItemId());
            
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
            String query = "SELECT * FROM transactions Where Id = ?";
            
            Transaction transaction = new Transaction();
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
        	    
            	int userId = rs.getInt("UserId");
            	int itemId = rs.getInt("ItemId");           	
                               
                transaction.setId(id);
                transaction.setUserId(userId);
                transaction.setItemId(itemId);
            }
            return transaction;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

	public List<Transaction> getByUserId(int Id){
		List<Transaction> transactionList = new ArrayList<>();
		
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM transactions JOIN items ON transactions.itemId = items.Id Where transactions.userId = ?";
            
            
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Id);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	Transaction transaction = new Transaction();
            	
            	int id = rs.getInt("Id");    
            	int userId = rs.getInt("UserId");
            	int itemId = rs.getInt("UserId");
            	//String status = rs.getString("Status");
                               
                transaction.setId(id);
                transaction.setUserId(userId);
                transaction.setUserId(itemId);
                transactionList.add(transaction);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		
		return transactionList;
	}
}
