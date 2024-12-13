package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	    // Daftar untuk menyimpan semua offers yang cocok  
	    List<Offer> listOffer = new ArrayList<>();  
	    
	    try {  
	        // Mendapatkan semua offers dari repository  
	        List<Offer> allOffers = offerRepository.getAll();   
	        
	        // Mendapatkan daftar item berdasarkan username  
	        List<Item> listItemSeller = itemService.getItemsByUsername(username);  
	        
	        // Jika listItemSeller kosong, kembalikan list kosong  
	        if (listItemSeller == null || listItemSeller.isEmpty()) {  
	            return listOffer;  
	        }  
	        
	        // Filter offers yang statusnya "Pending" dan belum diterima  
	        List<Offer> pendingOffers = allOffers.stream()  
	            .filter(offer ->   
	                "Pending".equalsIgnoreCase(offer.getStatus()) &&   
	                !offer.isAccepted()  
	            )  
	            .collect(Collectors.toList());  
	        
	        // Iterasi setiap offer pending  
	        for (Offer offer : pendingOffers) {  
	            // Cari item yang sesuai dengan offer  
	            for (Item item : listItemSeller) {  
	                // Cocokkan item.id dengan offer.itemId  
	                if (item.getId() == offer.getItemId()) {  
	                    listOffer.add(offer);  
	                    break; // Keluar dari inner loop setelah menemukan item  
	                }  
	            }  
	        }  
	        
	        // Mengembalikan daftar offer yang telah difilter  
	        return listOffer;  
	    } catch (Exception e) {  
	        // Log error untuk debugging  
	        System.err.println("Error in getOfferedItemsForSeller: " + e.getMessage());  
	        e.printStackTrace();  
	        
	        // Kembalikan list kosong  
	        return listOffer;  
	    }  
	}
	
	public boolean acceptOffer(int offerid) {  
	    try {  
	        // Pertama, update status offer menjadi "Accepted"  
	        boolean statusUpdated = offerRepository.updateStatus(offerid, "Accepted");  
	        
	        // Jika update status berhasil  
	        if (statusUpdated) {  
	            // Kemudian update acceptance  
	            boolean updateisaccepted = offerRepository.updateAcception(offerid);  
	            
	            // Jika update acceptance berhasil  
	            if (updateisaccepted) {  
	                return true;  
	            } else {  
	                // Jika update acceptance gagal, kembalikan status sebelumnya  
	                offerRepository.updateStatus(offerid, "Pending"); // Atau status sebelumnya  
	                return false;  
	            }  
	        } else {  
	            return false;  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        System.err.println("Error Accepting offer: " + e.getMessage());  
	        return false;  
	    }  
	}  
	
	public boolean declineOffer(int offerid, String reason) {  
	    try {  
	        // Pastikan reason tidak null atau kosong  
	        if (reason == null || reason.trim().isEmpty()) {  
	            reason = "No reason provided";  
	        }  

	        // Update status dengan alasan penolakan  
	        boolean statusUpdated = offerRepository.updateStatus(offerid, "Declined: " + reason);  
	        
	        // Jika update status berhasil  
	        if (statusUpdated) {  
	            // Optional: Tambahkan log atau notifikasi  
	            return true;  
	        } else {  
	            // Gagal update status  
	            System.err.println("Failed to update offer status for offer ID: " + offerid);  
	            return false;  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        System.err.println("Error declining offer: " + e.getMessage());  
	        return false;  
	    }  
	}  
	
	

}
