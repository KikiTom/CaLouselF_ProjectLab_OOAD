package Model;


public class Transaction {
	private int id;
	private int userId;
	private Item item;
	private String status;
	public Transaction() {}
	public Transaction(int userId, Item item,String status) {
		super();
		this.setUserId(userId);
		this.setItem(item);
		this.setStatus(status);
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
