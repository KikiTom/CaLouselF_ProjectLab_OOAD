
package Controller;

import Model.WishList;
import Service.ItemService;
import Service.UserService;
import Service.WishlistService;
import View.CustomerWishlistView;
import View.popupView;
import javafx.stage.Stage;

import java.util.List;

public class CustomerWishlistController {
	private WishlistService wishlistService;  
    private ItemService itemService;  
    private UserService userService;  
    private CustomerWishlistView wishlistView;  
    private int userId;  
    private String username;  
    

    public CustomerWishlistController(  
            WishlistService wishlistService,   
            ItemService itemService,   
            UserService userService,  
            int userId,   
            String username  
        ) {  
            this.wishlistService = wishlistService;  
            this.itemService = itemService;  
            this.userService = userService;  
            this.userId = userId;  
            this.username = username;  
            
            // Initialize the view with username  
            Stage wishlistStage = new Stage();  
            this.wishlistView = new CustomerWishlistView(wishlistStage, username, this); 
            
            // Load and display wishlist  
            loadWishlist();  
        }  

    private void loadWishlist() {
        // Retrieve wishlist items from service
        List<WishList> wishlistItems = wishlistService.Getwishlistbyuserid(userId);
        
        // Update view with wishlist items
        wishlistView.updateWishlistItems(wishlistItems);
    }

    public void removeFromWishlist(WishList wishlist) {  
        String title = "Remove from Wishlist";  
        String message = "Are you sure you want to remove '" + wishlist.getItem().getName() + "' from your wishlist?";   
        
        if(popupView.getInstance().showConfirmationPopup(title, message)) {  
            // If user confirms, remove the wishlist item  
            boolean removed = wishlistService.removeFromWishlist(wishlist.getId());
            
            if (removed) {  
                // Reload wishlist after successful removal  
                loadWishlist();  
            } else {  
                // Optional: Show error popup if removal failed  
                popupView.getInstance().showErrorPopup(  
                    "Remove Failed",   
                    "Unable to remove item from wishlist. Please try again."  
                );  
            }  
        }  
    }  

    public void show() {
        wishlistView.show();
    }
    
    public void showhomescene() {
    	 Stage customerHomeStage = new Stage();
    	 CustomerHomeController homeController =   
                 new CustomerHomeController(username, itemService, userService);  
         homeController.show(customerHomeStage);
    }
}

