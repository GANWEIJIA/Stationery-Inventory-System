package item;

public class OrderItem {
    private int itemId;
    private String itemName;
    private int quantity;

    public OrderItem(int itemId, String itemName, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity)
    {
    	this.quantity = quantity;
    }
}