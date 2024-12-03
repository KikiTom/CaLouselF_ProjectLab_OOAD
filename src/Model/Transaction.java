package Model;


public class Transaction {
	private int id;
	private int userId;
	private int itemId;
	public Transaction() {}
	public Transaction(int userId,int itemId) {
		super();
		this.setUserId(userId);
		this.setItemId(itemId);
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
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
