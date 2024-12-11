package Controller;  

import Model.Item;  
import Service.ItemService;  
import Service.UserService;  
import View.SellerEditItemView;  
import View.popupView;  
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;  

public class SellerEditItemController {  
    private UserService userService;  
    private Stage currentStage;  
    private String username;  
    private Item itemToEdit;  
    private ItemService itemService;  
    private SellerEditItemView editItemView;  
    private SellerHomeController parentController; // Optional reference to parent controller  

    public SellerEditItemController(  
            UserService userService,   
            Stage currentStage,   
            String username,   
            Item itemToEdit,   
            ItemService itemService) {  
        this.userService = userService;  
        this.currentStage = currentStage;  
        this.username = username;  
        this.itemToEdit = itemToEdit;  
        this.itemService = itemService;  
        
        // Initialize edit view  
        this.editItemView = new SellerEditItemView(itemToEdit);  
    }
    

    public void showEditItemView() {  
        Platform.runLater(() -> {  
            Stage editStage = new Stage();  
            editStage.initModality(Modality.WINDOW_MODAL);  
            editStage.initOwner(currentStage);  
            editStage.setTitle("Edit Item");  

            // Buat instance SellerEditItemView  
            editItemView = new SellerEditItemView(itemToEdit);  
            
            // Set delete handler  
            editItemView.setDeleteItemHandler(itemToDelete -> {  
                // Tampilkan konfirmasi menggunakan popupView  
                boolean confirmDelete = popupView.getInstance().showConfirmationPopup(  
                    "Delete Item",   
                    "Are you sure you want to delete this item?"  
                );  
                
                // Jika konfirmasi di-accept  
                if (confirmDelete) {  
                    // Panggil method delete dari parent controller  
                    if (parentController != null) {  
                        itemService.deleteitembyid(itemToDelete.getId());  
                        parentController.refreshParentView();  
                        editStage.close(); // Tutup stage setelah delete  
                    }  
                }  
            });  
            
            // Buat scene dengan ukuran yang lebih besar  
            Scene scene = editItemView.createEditItemScene();  
            editStage.setScene(scene);  

            // Atur ukuran minimum dan maksimum  
            editStage.setMinWidth(500);  
            editStage.setMinHeight(450);   

            // Izinkan resize  
            editStage.setResizable(true);  

            // Tambahkan ikon-ikon standar  
            editStage.initStyle(StageStyle.DECORATED);  

            // Setup save dan cancel button actions  
            setupEditItemActions(editStage);  
            
            editStage.show();  
        });  
    }  

    private void setupEditItemActions(Stage editStage) {  
        // Save button action  
        editItemView.getSaveButton().setOnAction(event -> {  
            try {  
                // Validate input first  
                if (!editItemView.validateInput()) {  
                    return; // Stop if validation fails  
                }  

                // Update item with new values  
                updateItemFromView();  

                // Attempt to save the updated item  
                boolean updateSuccess = itemService.updateItem(itemToEdit);  

                if (updateSuccess) {  
                    // Show success popup  
                    popupView.getInstance().showSuccessPopup(  
                        "Update Successful",   
                        "Item updated successfully"  
                    );  

                    // Close the edit stage  
                    editStage.close();  

                 // Panggil method refresh pada parent controller  
                    if (parentController != null) {  
                        parentController.refreshParentView(); 
                    } else {  
                        System.out.println("No parent controller set for refresh");  
                    }  
                } else {  
                    // Show error if update fails  
                    popupView.getInstance().showErrorPopup(  
                        "Update Failed",   
                        "Unable to update item"  
                    );  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
                popupView.getInstance().showErrorPopup(  
                    "Error",   
                    "An unexpected error occurred"  
                );  
            }  
        });  

        // Cancel button action  
        editItemView.getCancelButton().setOnAction(event -> {  
            editStage.close();  
        });  
    }  

    private void updateItemFromView() {  
        // Update item fields from view inputs  
        itemToEdit.setName(editItemView.getNameField().getText().trim());  
        itemToEdit.setCategory(editItemView.getCategoryComboBox().getValue());  
        
        // Parse price, handling potential number format exceptions  
        try {  
            double price = Double.parseDouble(editItemView.getPriceField().getText().trim());  
            itemToEdit.setPrice((int) price);  
        } catch (NumberFormatException e) {  
            throw new IllegalArgumentException("Invalid price format");  
        }  

        itemToEdit.setSize(editItemView.getSizeField().getText().trim());  
          
    }  


    // Optional: Method to set parent controller for direct refresh  
    public void setParentController(SellerHomeController parentController) {  
        this.parentController = parentController;  
    }  
}