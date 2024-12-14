package Service;

import java.util.ArrayList;
import java.util.List;

import Model.Item;
import Model.WishList;
import Repository.WishListRepository;

public class WishlistService {
	private WishListRepository wishlistRepository;
	
	public WishlistService(WishListRepository wishlistRepository) {
		this.wishlistRepository = wishlistRepository;
	}
	
	public boolean Addtowishlist(int userid, Item item) {  
	    // Get the user's current wishlist 
	    List<WishList> wishlistuser = wishlistRepository.getWishListByUserId(userid);  
	   
	    // Cek apakah wishlist kosong atau null  
	    if (wishlistuser == null) {  
	        wishlistuser = new ArrayList<>();  
	    }  
	    
	    // Check if the item is already in the wishlist  
	    boolean itemExists = wishlistuser.stream()  
	        .anyMatch(wishlist -> wishlist.getItem().getId() == item.getId());  
	    
	    // If item is already in wishlist, return false  
	    if (itemExists) {  
	        return false;  
	    }  
	    
	    // Create a new WishList object  
	    WishList newWishlistItem = new WishList(userid, item);  
	        
	    // Attempt to create the new wishlist entry  
	    try {  
	        return wishlistRepository.create(newWishlistItem);  
	    } catch (Exception e) {  
	        // Log the error  
	        System.err.println("Error adding item to wishlist: " + e.getMessage());  
	        return false;  
	    }  
	} 
	
	public List<WishList> Getwishlistbyuserid(int userid) {  
	    try {  
	        // Retrieve the wishlist for the specified user  
	        List<WishList> wishlistuser = wishlistRepository.getWishListByUserId(userid);  
	        
	        // If no wishlist is found, return an empty list instead of null  
	        if (wishlistuser == null) {  
	            return new ArrayList<>();  
	        }  
	        
	        return wishlistuser;  
	    } catch (Exception e) {  
	        // Log the error for debugging purposes  
	        System.err.println("Error retrieving wishlist for user ID " + userid + ": " + e.getMessage());  
	        
	        // Return an empty list in case of any exception  
	        return new ArrayList<>();  
	    }  
	}
	
	public boolean removeFromWishlist(int wishlistId) {  
	    try {    
	        return wishlistRepository.delete(wishlistId);  
	    } catch (Exception e) {  
	        System.err.println("Error removing item from wishlist: " + e.getMessage());  
	        return false;  
	    }  
	}  

}
