package Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Offer;
import Repository.RepositoryInterface.GetAll;
import Repository.RepositoryInterface.GetById;
import Repository.RepositoryInterface.Create;

public class OfferRepository extends RepositoryInheritClass implements GetAll<Offer>, GetById<Offer>, Create<Offer> {
	private Database database;

    public OfferRepository(Database database) {
        super(database);
    }

	@Override
	public boolean create(Offer entity) {
		try (Connection connection = database.getConnection()) {
            String query = "INSERT INTO items (UserId, ItemId, Amount, Status, IsAccepted) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getItemId());
            stmt.setInt(3, entity.getAmount());
            stmt.setString(4, entity.getStatus());
            stmt.setBoolean(5, entity.isAccepted());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public Offer getById(int id) {
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM offers Where Id = ?";
            
            Offer offer = new Offer();
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
            	    
            	int userId = rs.getInt("UserId");
            	int itemId = rs.getInt("ItemId");
                int amount = rs.getInt("Amount");
                String status = rs.getString("Status");
                Boolean isAccepted = rs.getBoolean("IsAccepted");
                               
                offer.setId(id);
                offer.setUserId(userId);
                offer.setItemId(itemId);
                offer.setAmount(amount);
                offer.setStatus(status);
                offer.setAccepted(isAccepted);
            }
            return offer;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Offer> getAll() {
		List<Offer> offerList = new ArrayList<>();
		
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM offers";
           
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	int id = rs.getInt("Id");    
            	int userId = rs.getInt("UserId");
            	int itemId = rs.getInt("ItemId");
                int amount = rs.getInt("Amount");
                String status = rs.getString("Status");
                Boolean isAccepted = rs.getBoolean("IsAccepted");
               
                Offer offer = new Offer(userId,itemId,amount,status);
                offer.setAccepted(isAccepted);
                offer.setId(id);
                offerList.add(offer);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return offerList;
	}
	
	public List<Offer> getOfferByItemId(int itemId) {
		List<Offer> offerList = new ArrayList<>();
		
		try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM offers WHERE ItemId = ?";
           
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	int id = rs.getInt("Id");    
            	int userId = rs.getInt("UserId");
                int amount = rs.getInt("Amount");
                String status = rs.getString("Status");
                Boolean isAccepted = rs.getBoolean("IsAccepted");
               
                Offer offer = new Offer(userId,itemId,amount,status);
                offer.setAccepted(isAccepted);
                offer.setId(id);
                offerList.add(offer);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return offerList;
	}
	
	public boolean updateAcception(int id) {
		try (Connection connection = database.getConnection()) {
            String query = "UPDATE offers SET IsAccepted = ? Where Id = ?";
           
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
	
	public boolean updateStatus(int id, String status) {
		try (Connection connection = database.getConnection()) {
            String query = "UPDATE offers SET Status = ? Where Id = ?";
           
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
    
}
