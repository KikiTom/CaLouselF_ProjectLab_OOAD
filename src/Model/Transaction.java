package Model;

import java.util.List;

public class Transaction {
	private int id;
	private List<Item> itemList;
	private int total;
	public Transaction(List<Item> itemList, int total) {
		super();
		this.itemList = itemList;
		this.total = total;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
