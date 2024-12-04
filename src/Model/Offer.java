package Model;

public class Offer {
	private int id;
	private int userId;
	private int itemId;
	private int amount;
	private boolean isAccepted;
	public Offer() {}
	public Offer(int userId, int itemId, int amount) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.amount = amount;
		this.setAccepted(false);
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAccepted() {
		return isAccepted;
	}
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}		
}
