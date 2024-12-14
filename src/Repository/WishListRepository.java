package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.WishList;
import Model.Item;
import Repository.RepositoryInterface.Create;
import Repository.RepositoryInterface.Delete;

public class WishListRepository extends RepositoryInheritClass implements Create<WishList>,Delete<WishList>{

	public WishListRepository(Database database) {
		super(database);
		// TODO Auto-generated constructor stub
	}
	
	public List<WishList> getWishListByUserId(int userId){
		List<WishList> wishlistList = new ArrayList<>();
		
		try (Connection connection = database.getConnection()) {
			String query = ""  
	                + "SELECT wishlist.Id AS WishlistId, " // Ubah nama kolom untuk menghindari konflik  
	                + "items.Id AS ItemId, " // Tambahkan alias untuk ItemId  
	                + "items.Name, items.Size, items.Price, items.Category, items.Status, items.IsAccepted "  
	                + "FROM wishlist JOIN items ON wishlist.ItemId = items.Id "  
	                + "WHERE wishlist.UserId = ?";  
                  
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	WishList wishlist = new WishList();
            	Item item = new Item();
            	
            	int id = rs.getInt("WishlistId"); 
            	int itemId = rs.getInt("ItemId");
            	String name = rs.getString("Name");
            	String size = rs.getString("Size");
            	int price = rs.getInt("Price");
            	String category = rs.getString("Category");
            	String status = rs.getString("Status");
            	Boolean isAccepted = rs.getBoolean("IsAccepted");
            	
            	wishlist.setId(id);
            	wishlist.setUserId(userId);
                
                item.setId(itemId);
                item.setName(name);
                item.setSize(size);
                item.setCategory(category);
                item.setPrice(price);
                item.setStatus(status);
                item.setIsAccepted(isAccepted);
                
                wishlist.setItem(item);
                
                wishlistList.add(wishlist);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		
		return wishlistList;
	}
	
	@Override
	public boolean create(WishList entity) {
		try (Connection connection = database.getConnection()) {
            String query = "INSERT INTO wishlist (UserId, ItemId) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getItem().getId());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	@Override
	public boolean delete(int id) {  
	    try (Connection connection = database.getConnection()) {  
	        // Correct SQL DELETE syntax  
	        String query = "DELETE FROM wishlist WHERE Id = ?";  
	        PreparedStatement stmt = connection.prepareStatement(query);  
	        
	        stmt.setInt(1, id);  
	        
	        int rowsDeleted = stmt.executeUpdate();  
	        return rowsDeleted > 0;  
	    } catch (SQLException e) {  
	        e.printStackTrace();  
	        return false;   
	    }  
	}  
}
