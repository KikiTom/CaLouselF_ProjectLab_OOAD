package Model;

import Repository.UserRepository;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Item {
	private int id;
	private String name;
	private String size;
	private int price;
	private String category;
	private String status;
	private Boolean isAccepted;
	private int userId;
	
	private BooleanProperty selected = new SimpleBooleanProperty(false);
	private User user;
	private UserRepository userRepository;
	
	public Item() {}
	public Item(String name, Boolean isAccepted, String status,String category, String size, int price, int userId) {
		super();
		this.isAccepted = isAccepted;
		this.name = name;
		this.category = category;
		this.status = status;
		this.size = size;
		this.price = price;
		this.userId = userId;
	}
	
	public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setUserRepository(UserRepository userRepository) {  
	    this.userRepository = userRepository;  
	}
	
	public User getUser() {
		return userRepository.getUserById(this.userId);
	}
	
}