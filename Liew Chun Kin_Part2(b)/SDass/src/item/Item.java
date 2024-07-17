package item;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private int no;

    public Item(int no ,String name, double price, int quantity) {
    	this.no = no;
    	this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
    	return quantity;
    }
    
    public int getNo() {
    	return no;
    }
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
    public void setPrice(double price) {
    	this.price = price;
    }
}
