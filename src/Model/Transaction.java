package Model;


public class Transaction {
	private int id;
	private int userId;
	private Item item;
	public Transaction() {}
	public Transaction(int userId, Item item) {
		super();
		this.setUserId(userId);
		this.setItem(item);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
}
