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
	
	public OfferService(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
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
	
	public int getAmountOffer(int itemId) {  
	    try {  
	        // Dapatkan semua offers  
	        List<Offer> allOffers = offerRepository.getAll();  
	        
	        // Jika list kosong, kembalikan 0  
	        if (allOffers == null || allOffers.isEmpty()) {  
	            return 0;  
	        }  
	        
	        // Cari offer pertama yang sesuai kriteria  
	        Offer matchingOffer = allOffers.stream()  
	            .filter(offer ->   
	                offer.getItemId() == itemId &&  // Cocokkan item ID  
	                !offer.isAccepted() &&          // Belum diterima  
	                "Pending".equalsIgnoreCase(offer.getStatus()) // Status pending  
	            )  
	            .findFirst()                        // Ambil yang pertama  
	            .orElse(null);                      // Jika tidak ada, kembalikan null  
	        
	        // Kembalikan amount jika offer ditemukan, jika tidak kembalikan 0  
	        return matchingOffer != null ? matchingOffer.getAmount() : 0;  
	        
	    } catch (Exception e) {  
	        // Log error untuk debugging  
	        System.err.println("Error in getAmountOffer: " + e.getMessage());  
	        e.printStackTrace();  
	        
	        // Kembalikan 0 jika terjadi error  
	        return 0;  
	    }  
	}
	
	public boolean makeNewOffer(int itemId, int userId, int amount) {  
	    try {  
	        // Validasi input  
	        if (itemId <= 0 || userId <= 0 || amount <= 0) {  
	            System.err.println("Invalid input: itemId, userId, or amount is not positive");  
	            return false;  
	        }  

	        // Cek apakah sudah ada offer sebelumnya untuk item ini  
	        List<Offer> allOffers = offerRepository.getAll();  
	        
	        // Cari offer yang belum diterima untuk item ini  
	        Offer existingOffer = allOffers.stream()  
	            .filter(offer ->   
	                offer.getItemId() == itemId &&  
	                !offer.isAccepted() &&   
	                "Pending".equalsIgnoreCase(offer.getStatus())  
	            )  
	            .findFirst()  
	            .orElse(null);  

	        // Buat objek Offer baru  
	        Offer newOffer = new Offer();  
	        newOffer.setItemId(itemId);  
	        newOffer.setUserId(userId);  
	        newOffer.setAmount(amount);  
	        newOffer.setStatus("Pending");  
	        newOffer.setAccepted(false);  

	        // Jika sudah ada offer sebelumnya, update offer tersebut  
	        if (existingOffer != null) {  
	            // Update offer yang sudah ada  
	            boolean updated = offerRepository.update(existingOffer.getId(), userId, amount);  
	            
	            if (updated) {  
	                System.out.println("Existing offer updated successfully");  
	                return true;  
	            } else {  
	                System.err.println("Failed to update existing offer");  
	                return false;  
	            }  
	        }   
	        // Jika belum ada offer, buat offer baru  
	        else {  
	            boolean created = offerRepository.create(newOffer);  
	            
	            if (created) {  
	                System.out.println("New offer created successfully");  
	                return true;  
	            } else {  
	                System.err.println("Failed to create new offer");  
	                return false;  
	            }  
	        }  
	    } catch (Exception e) {  
	        // Log error untuk debugging  
	        System.err.println("Error in makeNewOffer: " + e.getMessage());  
	        e.printStackTrace();  
	        return false;  
	    }  
	}
	
}
