package User;

import java.util.ArrayList;
import java.util.Scanner;
import item.Item;

public class Staff extends User {
	
	private Scanner scanner = new Scanner(System.in);
	
	public Staff (String username,String password,int role)
	{
		super(username,password,role);
	}
	
	public Item createItem(ArrayList <Item> items )
	{
		boolean check = true;
		int itemNo = 0;
		String itemName = null;
		double itemPrice = 0;
		int itemQuantity = 0;
		
		do{
			try {
			System.out.print("Enter item no: ");
			
			itemNo = Integer.parseInt(scanner.nextLine());
		
			for (int i =0; i< items.size(); i++)
			{
				if (itemNo == items.get(i).getNo())
				{
					do {
					System.out.println("Item number already exist.");
					System.out.print("Enter item no: ");
					itemNo = Integer.parseInt(scanner.nextLine());
					}while(itemNo == items.get(i).getNo());
				}	
			}
		System.out.print("Enter item name: ");
		itemName = scanner.nextLine();
		do {
		System.out.print("Enter item price: ");
		itemPrice = Double.parseDouble(scanner.nextLine());
		System.out.print("Enter item quantity: ");
		itemQuantity = Integer.parseInt(scanner.nextLine());
		check = false;
		}while(check == true);
		
			}
			catch(NumberFormatException exception)
  		     {
  		    	 System.out.print("\nOnly Numbers ! Please try again\n");
  		     }
			
		}while(check == true);
	
		Item item = new Item(itemNo, itemName,itemPrice,itemQuantity);
		System.out.print("\nItem have added!\n");
		return item;
		
	}
	
	public void updateItem(ArrayList <Item> items,Customer customer) {
		boolean check = true;
		int itemNo = 0;
		String itemName = null;
		double itemPrice = 0;
		int itemQuantity = 0;
		int uchoice = 0;
		boolean done = false;
		
		String updateMenu= ("\n1. Update Item Price & Quantity\n"
                +"2. Delete Item\n"
                +"3. Back to Menu\n");
		
		
		
		do {
		customer.showItem(items);
        System.out.println(updateMenu);
		System.out.print("Option: ");

		 try{
			
		uchoice = Integer.parseInt(scanner.nextLine());      
	           
		while(uchoice < 1 || uchoice >3) {
			System.out.print("\nError! Incorrect choice.\n");
			 System.out.println(updateMenu);
			 System.out.print("Option: ");
			 uchoice = Integer.parseInt(scanner.nextLine());
		}
		done = true;
		 }
		 
		 catch(NumberFormatException exception)
	     {
	    	 System.out.print("\nOnly Numbers ! Please try again\n");
	     }
	switch(uchoice) {
		case 1:
			do {
	            try {
	                System.out.print("Enter item no: ");
	                itemNo = Integer.parseInt(scanner.nextLine());

	                for (Item item : items) {
	                    if (itemNo == item.getNo()) {
	                        System.out.print("Enter item new price: ");
	                        itemPrice = Double.parseDouble(scanner.nextLine());
	                        item.setPrice(itemPrice);
	                        System.out.print("Enter item new quantity: ");
	                        itemQuantity = Integer.parseInt(scanner.nextLine());
	                        item.setQuantity(itemQuantity);
	                        check = false;
	                        break; // Exiting loop once item is found and updated
	                    }
	                }
	                if (check) {
	                    System.out.println("Item not found. Please enter a valid item number.");
	                }
	            } catch (NumberFormatException exception) {
	                System.out.println("Invalid input! Please enter a valid number.");
	            }

	        } while (check);
			break;
			
		case 2:
			check = false;
			try {
				System.out.print("Enter item no: ");
			   itemNo = Integer.parseInt(scanner.nextLine());

			    boolean itemFound = false;
			    for (int i = 0; i < items.size(); i++) {
			        if (itemNo == items.get(i).getNo()) {
			            items.remove(i); // Remove the item at index i
			            itemFound = true;
			            System.out.println("Item deleted successfully.");
			            break;
			        }
			    }

			    if (!itemFound) {
			        System.out.println("Item not found.");
			    }
			}
			catch(NumberFormatException exception){
			   System.out.print("\nOnly Numbers ! Please try again");
			}
			if ( check == false)
				 System.out.println("The item ID does not exist !!\n");
			 if (check == true)
				 System.out.println("The item has been deleted !!\n");
			uchoice =0;
			break;
			
		case 3:
			System.out.println("\nExiting...");
			uchoice =0;
			break;
		}
		}while(done == false);
	}
	
	public void generateReport(ArrayList<Item> items, Customer customer) {
	    System.out.println("\nReport Statement:");
	    System.out.println("----------------");
	    double sum = 0 ;
	    ArrayList<Double> paidBills = customer.getPaidBills();
        for (int i = 0; i < paidBills.size(); i++) {
            System.out.println("Bill " + (i + 1) + ": $" + paidBills.get(i));
            sum += paidBills.get(i);
        }
        
        if (sum==0) {
        	System.out.println("No Sales Existed!");
        }
        else {
        System.out.println("----------------");
        System.out.println("Total Sales: $" + sum);
        }
        System.out.println("----------------");
	}
}

