package Model;


public class WishList {
	private int id;
	private int userId;
	private Item item;
	public WishList() {

	}
	public WishList(Item item) {
		super();
		this.setItem(item);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
