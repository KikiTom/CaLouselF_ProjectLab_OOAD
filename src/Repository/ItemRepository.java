package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Item;
import Repository.RepositoryInterface.*;

public class ItemRepository extends RepositoryInheritClass implements GetAll<Item>, GetById<Item>, Create<Item>, Update<Item>, Delete<Item>{

    public ItemRepository(Database database) {
        super(database);
    }
	
	@Override
	public List<Item> getAll() {
		List<Item> itemList = new ArrayList<>();
		
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM items";
           
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	int id = rs.getInt("Id");    
            	int userId = rs.getInt("UserId");
                String name = rs.getString("Name"); 
                String size = rs.getString("Size");              
                int price = rs.getInt("Price");
                String status = rs.getString("Status");
                String category = rs.getString("Category");
                Boolean isAccepted = rs.getBoolean("IsAccepted");
                
                // Create a new Item object and add it to the list
                Item item = new Item(name,isAccepted,status,category,size,price,userId);
                item.setId(id);
               
                itemList.add(item);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return itemList;
	}

	public List<Item> getByUserId(int userId){
		List<Item> itemList = new ArrayList<>();
		
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM items WHERE UserId = ?";
           
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	int id = rs.getInt("Id");    
                String name = rs.getString("Name"); 
                String size = rs.getString("Size");              
                int price = rs.getInt("Price");
                String status = rs.getString("Status");
                String category = rs.getString("Category");
                Boolean isAccepted = rs.getBoolean("IsAccepted");
                
                // Create a new Item object and add it to the list
                Item item = new Item(name,isAccepted,status,category,size,price,userId);
                item.setId(id);
                itemList.add(item);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return itemList;
	}
	
	@Override
	public Item getById(int id) {
		Item item = new Item();
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM items WHERE id = ?";
           
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {   
            	int userId = rs.getInt("UserId");
                String name = rs.getString("Name"); 
                String size = rs.getString("Size");              
                int price = rs.getInt("Price");
                String status = rs.getString("Status");
                String category = rs.getString("Category");
                Boolean isAccepted = rs.getBoolean("IsAccepted");

                item.setId(id);
                item.setName(name);
                item.setCategory(category);
                item.setIsAccepted(isAccepted);
                item.setPrice(price);
                item.setSize(size);
                item.setUserId(userId);
                item.setStatus(status);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return item;
	}

	@Override
	public boolean create(Item entity) {
		try (Connection connection = database.getConnection()) {
            String query = "INSERT INTO items (Name, Size, Price, Category, Status, IsAccepted, UserId) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getSize());
            stmt.setInt(3, entity.getPrice());
            stmt.setString(4, entity.getCategory());
            stmt.setString(5, entity.getStatus());
            stmt.setBoolean(6, entity.getIsAccepted());
            stmt.setInt(7, entity.getUserId());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean update(int id, Item entity) {
		try (Connection connection = database.getConnection()) {
		    String query = "UPDATE items SET Name = ?, Size = ?, Price = ?, Category = ?, Status = ?, IsAccepted = ?, UserId = ? WHERE Id = ?";
		    PreparedStatement stmt = connection.prepareStatement(query);

		    stmt.setString(1, entity.getName());
		    stmt.setString(2, entity.getSize());
		    stmt.setInt(3, entity.getPrice());
		    stmt.setString(4, entity.getCategory());
		    stmt.setString(5, entity.getStatus());
		    stmt.setBoolean(6, entity.getIsAccepted());
		    stmt.setInt(7, entity.getUserId());
		    stmt.setInt(8, id);

		    int rowsUpdated = stmt.executeUpdate();
		    return rowsUpdated > 0;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}
	}
	
	public boolean UpdateStatus(int id, String status) {
		try (Connection connection = database.getConnection()) {
		    String query = "UPDATE items SET Status = ? WHERE Id = ?";
		    PreparedStatement stmt = connection.prepareStatement(query);
		    
		    stmt.setString(1, status);
		    stmt.setInt(2, id);
		    
		    int rowsUpdated = stmt.executeUpdate();
		    return rowsUpdated > 0;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}
	}
	
	public boolean UpdateAccepted(int id) {
		try (Connection connection = database.getConnection()) {
		    String query = "UPDATE items SET IsAccepted = ? WHERE Id = ?";
		    PreparedStatement stmt = connection.prepareStatement(query);
		    
		    stmt.setBoolean(1, true);
		    stmt.setInt(2, id);
		    
		    int rowsUpdated = stmt.executeUpdate();
		    return rowsUpdated > 0;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try (Connection connection = database.getConnection()) {
		    String query = "DELETE FROM items WHERE Id = ?";
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
