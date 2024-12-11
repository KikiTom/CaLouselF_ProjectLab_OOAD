package Service;

import java.util.ArrayList;
import java.util.List;

import Model.Item;
import Model.Offer;
import Repository.OfferRepository;

public class OfferService {
	private ItemService itemService;
	private UserService userService;
	private TransactionService transactionService;
	private OfferRepository offerRepository;
	
	
	public OfferService(OfferRepository offerRepository,UserService userService,ItemService itemService,TransactionService transactionService) {
		this.offerRepository = offerRepository;
		this.itemService = itemService;
		this.transactionService = transactionService;
		this.userService = userService;
	}
	
	public List<Offer> getOfferedItemsForSeller(String username) {
	    try {
	        // Mendapatkan daftar item berdasarkan username
	        List<Item> listItemSeller = itemService.getItemsByUsername(username);
	        
	        // Daftar untuk menyimpan semua offers yang cocok
	        List<Offer> listOffer = new ArrayList<>();
	        
	        // Iterasi setiap item dari seller
	        for (Item item : listItemSeller) {
	            // Mencari offers berdasarkan item.id
	            List<Offer> offersForItem = offerRepository.getOfferByItemId(item.getId());
	            listOffer.addAll(offersForItem);
	        }
	        
	        // Mengembalikan daftar offer yang telah difilter
	        return listOffer;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("Error in getItemsByUsername: " + e.getMessage());  
	        return List.of();
	    }
	}
	
	
	
	

}
