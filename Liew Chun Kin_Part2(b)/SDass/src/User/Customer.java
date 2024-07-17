package User;

import java.util.*;

import file.InventoryManager;
import item.Item;
import item.OrderItem;

public class Customer extends User{
    ArrayList<OrderItem> orderItems = new ArrayList<>();
    private ArrayList<Double> paidBills = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    
    
    public Customer(String username,String password,int role) {
    	super(username,password,role);
    }
 

    public boolean checkOrder()
    {
    	  if (orderItems.isEmpty()) 
    		  return false;
    	  else 
    		return true;
    }
    
    
    
    public void showItem(ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
        } else {
            System.out.println("Items available:");
            for (Item item : items) {
                System.out.println("Item No: " + item.getNo() + ", Name: " + item.getName() + ", Price: $" + item.getPrice() + ", Quantity: " + item.getQuantity());
                if(item.getQuantity()==0) {
                	System.out.println("Items Sold Out");
                }
                
            }
           
        }
        
    }

    public void addItems(ArrayList<Item> items) {
        String nextPurchase = null;

        do {
            boolean checkItemNumber = false;
            boolean checkQuantity = false;
            int arrayListPosition = -1; // Initialize with an invalid value
            showItem(items);

            System.out.println("Which item do you want to buy? Please enter the item number to add the item:\nEnter the Sold out number to quit");
            int itemNo = 0;

            do {
                System.out.print("Item Number: ");
                try {
                itemNo = Integer.parseInt(scanner.nextLine());
                }
                catch(NumberFormatException exception)
      		     {
      		    	 System.out.print("\nOnly Numbers! Please try again.\n");
      		     }

                // Check if the item number exists in the items list
                for (int i = 0; i < items.size(); i++) {
                    if (itemNo == items.get(i).getNo()) {
                    	if(items.get(i).getQuantity() == 0) {
                    		System.out.print("\nItem Sold Out.\n");
                    		return;
                    	}
                        checkItemNumber = true;
                        arrayListPosition = i;
                        break;
                    }
                }

                if (!checkItemNumber) {
                    System.out.println("Try with Another Number\n");
                    
                }
            } while (!checkItemNumber);

            do {
                System.out.print("Quantity: ");
                try {
                int quantity = Integer.parseInt(scanner.nextLine());
               
                // Check if the requested quantity exceeds the available quantity
                if (items.get(arrayListPosition).getQuantity() < quantity) {
                    System.out.println("Exceeding available quantity.");
                } else {
                    // If the item already exists in the order, update its quantity
                    boolean itemExistsInOrder = false;
                    for (OrderItem orderItem : orderItems) {
                        if (orderItem.getItemId() == items.get(arrayListPosition).getNo()) {
                            orderItem.setQuantity(orderItem.getQuantity() + quantity);
                            itemExistsInOrder = true;
                            break;
                        }
                    }

                    // If the item doesn't exist in the order, add it to the order
                    if (!itemExistsInOrder) {
                        orderItems.add(new OrderItem(items.get(arrayListPosition).getNo(), items.get(arrayListPosition).getName(), quantity));
                    }

                    // Deduct the quantity from the available stock
                    items.get(arrayListPosition).setQuantity(items.get(arrayListPosition).getQuantity() - quantity);
                    checkQuantity = true;
                }
            }
            catch(NumberFormatException exception)
 		     {
 		    	 System.out.print("\nPlease try again!\n");
 		     }
            } while (!checkQuantity);

            System.out.print("Do you still have items to add? (Press y for yes or any button to exit): ");
            
                nextPurchase = scanner.nextLine();
                
        } while (nextPurchase.equals("y"));
        System.out.print("Proceeding...\n");
    }


    public void generateBillStatement(ArrayList<Item> items) {
        System.out.println("Bill Statement:");
        System.out.println("----------------");

        // Print details of each ordered item
        for (OrderItem orderedItem : orderItems) {
            for (Item item : items) {
                if (orderedItem.getItemId() == item.getNo()) {
                    System.out.println("Item Name: " + item.getName());
                    System.out.println("Quantity: " + orderedItem.getQuantity());
                    System.out.println("Price per unit: $" + item.getPrice());
                    System.out.println("Total Price: $" + (orderedItem.getQuantity() * item.getPrice()));
                    System.out.println("----------------");
                    break;
                }
            }
        }

        // Print total bill amount
        System.out.println("Total Bill: $"+calculateBill (items));
        System.out.println("----------------");
    }

    public double calculateBill(ArrayList<Item> items) {
        double totalBill = 0;

        for (OrderItem orderItem : orderItems) {
            totalBill += orderItem.getQuantity() * getItemPrice(items, orderItem.getItemId());
        }
        return totalBill;
       
    }

    public void payBill(ArrayList<Item> items,Customer customer) {
        System.out.print("Please enter credit card number ( 16 digit only , no spaces needed ): \n");
        
        try {
        	String cardNumberStr = scanner.nextLine();
        if(cardNumberStr.length() != 16 ) {
        	System.out.print("\n16 Digit only\tPlease Try Again\n");
        	return;
        }
        System.out.print("Please enter credit card csv: ");
        int csv =  Integer.parseInt(scanner.nextLine());
        System.out.println("Bill paid successfully. Thank you for your purchase!");
        double billAmount=0; 
        billAmount = calculateBill(items);
        paidBills.add(billAmount);
        InventoryManager.saveInventory(items);
        orderItems.clear();
        }
        catch(NumberFormatException exception)
	     {
	    	 System.out.print("\nPlease try again!\n");
	    
	     }
    }
    
    
    public ArrayList<Double> getPaidBills(){
        return paidBills;
    }

    private double getItemPrice(ArrayList<Item> items, int itemId) {
        // You need to implement logic to fetch the price of the item based on its ID
        // This could involve querying a database or fetching from a list of items
        // For demonstration purposes, let's assume a simple lookup in a list of items
        // Replace this with actual logic in your application
        for (Item item : items) {
            if (item.getNo() == itemId) {
                return item.getPrice();
            }
        }
        return 0; // If item is not found, return 0
    }
    
    public void editOrder(ArrayList<Item> items) {
    	String editMenu= ("\n1. Cancel Order\n"
                +"2. Delete or Add Item\n"
                +"3. Back to Menu\n");
    	int choice=0;
    	
    	do {
    		showItem(items);
    		System.out.println("\nYour Item in Order:");
            for (OrderItem orderItem : orderItems) {
                System.out.println("Item ID: " + orderItem.getItemId() + ", Name: " + orderItem.getItemName() + ", Quantity: " + orderItem.getQuantity());
            }
            System.out.println(editMenu);
			System.out.print("Option: ");
			
			try {
			choice = Integer.parseInt(scanner.nextLine());
			
			while(choice < 1 || choice >3) {
				System.out.print("\nError! Incorrect choice.\n");
				 System.out.println(editMenu);
				 System.out.print("Option: ");
				 choice = Integer.parseInt(scanner.nextLine());
			}
			}
			catch(NumberFormatException exception)
		     {
		    	 System.out.print("\nOnly Numbers ! Please try again\n");
		     }
			
			switch (choice) {
            
            case 1:
            	if(items.isEmpty())
            		System.out.print("\nOrder is cancelled\n");
            	else {
            for (Item item : items) {
            	for (OrderItem orderItem : orderItems) {
            		if (orderItem.getItemId() == item.getNo()) {
                            item.setQuantity(item.getQuantity() + orderItem.getQuantity());
                        }
                    }
                }

                // Clear the order items list
                orderItems.clear();
                System.out.println("Order has cancel! \n");
            	 choice =0;
            	}
            	 break;
            case 2:
            	String orderModifyMenu= ("\n1. Delete Item\n"
                        +"2. Add Item\n"
                        +"3. Back to Menu\n");
            	choice =0;
            	int mchoice=0;
            	
            	do {
                    System.out.println(orderModifyMenu);
        			System.out.print("Option: ");
        			
        			try {
        			mchoice = Integer.parseInt(scanner.nextLine());
        			
        			while(mchoice < 1 || mchoice >3) {
        				System.out.print("\nError! Incorrect choice.\n");
        				 System.out.println(orderModifyMenu);
        				 System.out.print("Option: ");
        				 mchoice = Integer.parseInt(scanner.nextLine());
        			}
        			}
        			catch(NumberFormatException exception)
        		     {
        		    	 System.out.print("\nOnly Numbers ! Please try again");
        		     }
        			
        			switch (mchoice) {
                    
                    case 1:
                    	System.out.println("Items in the order:");
                        for (OrderItem orderItem : orderItems) {
                            System.out.println("Item ID: " + orderItem.getItemId() + ", Name: " + orderItem.getItemName() + ", Quantity: " + orderItem.getQuantity());
                        }

                        System.out.print("Enter the ID of the item you want to delete: ");
                        try {
                        int cancelItemId = Integer.parseInt(scanner.nextLine());

                        boolean itemFound = false;
                        for (OrderItem orderItem : orderItems) {
                            if (orderItem.getItemId() == cancelItemId) {
                                // Find the corresponding item in the items list to restore its quantity
                                for (Item item : items) {
                                    if (item.getNo() == cancelItemId) {
                                        item.setQuantity(item.getQuantity() + orderItem.getQuantity());
                                        break;
                                    }
                                }

                                orderItems.remove(orderItem);
                                itemFound = true;
                                break;
                            }
                        }

                        if (!itemFound) {
                            System.out.println("Item not found in the order.");
                        } else {
                            System.out.println("Item removed from the order and quantity restored.");
                        }
                        }
                        catch(NumberFormatException exception)
           		     {
           		    	 System.out.print("\nOnly Numbers ! Please try again\n");
           		     }
                        
                        mchoice=0;
                        choice =0;
                        break;
                    case 2:
                    	     addItems(items);
                    	     mchoice=0;
                    	     choice=0;
                    	     break;
                    case 3:
                    	System.out.println("\nLogging Out...");
                    	break;
			}
            	}while(mchoice !=3);
            case 3:
            	 System.out.println("Exiting...");
            	 break;
    }
	
    }while(choice !=3);
    
    
   
}

}