package Model;

import java.util.List;

public class WishList {
	private int id;
	List<Item> itemList;
	public WishList(List<Item> itemList) {
		super();
		this.itemList = itemList;
	}
	public WishList(int id, List<Item> itemList) {
		super();
		this.id = id;
		this.itemList = itemList;
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
	
	
}
