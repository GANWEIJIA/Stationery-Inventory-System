package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import User.Customer;
import User.Staff;
import User.User;
import file.InventoryManager;
import file.Login;
import item.Item;

public class Test {
    public static void main(String[] args)throws IOException, FileNotFoundException {
        Login loginSystem = new Login();
        ArrayList<Item> items = InventoryManager.loadInventory();
        Scanner scanner = new Scanner(System.in);
        User user=null;
        Staff staff = new Staff(null, null, 0);
        Customer customer = new Customer(null, null, 0);
        
        boolean checkExit = false;
        int choice =0;
        int loggedInChoice = 0;
        Item item = new Item(0, null, 0, 0);
        String mainMenu = (
                "\nStationary Order System: \n"
                +"1. Create Account\n"
                +"2. Login\n"
                +"3. Exit\n");
     	
        String staffMenu= ("\n1. Add Item\n"
                +"2. Generate Report\n"
                +"3. Update Inventory\n"
                +"4. Log Out\n");
        
        String customerMenu= ("\n1. Order Item\n"
                +"2. Edit Order\n"
                +"3. Pay Bill\n"
                +"4. Log Out\n");
        
        
        
        boolean done = false;
        	
        do {
            System.out.println(mainMenu);
			System.out.print("Option: ");
			 try{
			choice = Integer.parseInt(scanner.nextLine());      
		           
			while(choice < 1 || choice >3) {
				System.out.print("\nError! Incorrect choice.\n");
				 System.out.println(mainMenu);
				 System.out.print("Option: ");
				 choice = Integer.parseInt(scanner.nextLine());
			}
			done = true;
			 }
			 
			 catch(NumberFormatException exception)
		     {
		    	 System.out.print("\nOnly Numbers ! Please try again");
		     }
			 System.out.println();
	
			 boolean cdone = false;
			 
            switch (choice) {
            
                case 1:
                    System.out.print("Enter username: ");
                    String newUsername = scanner.nextLine();
                    //add username verify
                    System.out.print("Enter password: ");
                    String newPassword = scanner.nextLine();
                    loginSystem.createAccount(newUsername, newPassword);
                    choice =0;
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                 
                    if (loginSystem.login(username, password)) {
                        System.out.println("Welcome, " + username + "!");
                        int role = loginSystem.getLogInRole();
                        user = new User(username, password, role);

                        boolean checkLogOut = false;
                        if (role == 0) {
                        do{
                            System.out.println("What you want to do");
                            System.out.println(staffMenu);
                			System.out.print("Option: ");
                			loggedInChoice =0;
                			try {
                            	loggedInChoice = Integer.parseInt(scanner.nextLine());
                            	
                            	while(choice < 1 || choice >4) {
                    				System.out.print("\nError! Incorrect choice.\n");
                    				 System.out.println(staffMenu);
                    				 System.out.print("Option: ");
                    				 loggedInChoice = Integer.parseInt(scanner.nextLine());
                    			}
                            	
                            	}
                			 catch(NumberFormatException exception)
               		     {
               		    	 System.out.print("\nOnly Numbers ! Please try again\n");
               		     }
                			
                			switch(loggedInChoice) {
                			case 1:
                				item = staff.createItem(items);
                				items.add(item);
                				loggedInChoice =0;
                				break;
                			case 2:
                				staff.generateReport(items,customer);
                				loggedInChoice =0;
                				break;
                			case 3:
                				if(items.isEmpty())
                        			System.out.println("No Inventory Exist");
                        		else
                        			staff.updateItem(items,customer);
                				loggedInChoice = 0;
                            break;
                			case 4: 
                				break;
                			}
                			
                            }while(loggedInChoice != 4);
                        } 
                            if (role == 1) {
                            	do {
                            		loggedInChoice =0;
                            		System.out.println("What you want to do");
                                    System.out.println(customerMenu);
                        			System.out.print("Option: ");
                        			
                        			try {
                                    	loggedInChoice = Integer.parseInt(scanner.nextLine());
                                    	
                                    	while(loggedInChoice < 1 || loggedInChoice >4) {
                            				System.out.print("\nError! Incorrect choice.\n");
                            				 System.out.println(customerMenu);
                            				 System.out.print("Option: ");
                            				 loggedInChoice = Integer.parseInt(scanner.nextLine());
                                    	}
                                    	
                                	}
                    			 catch(NumberFormatException exception)
                   		     {
                   		    	 System.out.print("\nOnly Numbers ! Please try again\n");
                   		     }
                            	
                        			switch(loggedInChoice) {
                        			case 1:
                        				if(customer.checkOrder()== true)
                                    		System.out.println("Order is already existed");
                                    	else
                                    	{
                                    		if (!items.isEmpty())
                                    		{
                                    		customer.addItems(items);
                                    		}
                                    		else
                                    			System.out.println("Inventory is empty.");
                                    	}
                        				loggedInChoice = 0;
                                    break;
                                    
                        			case 2:
                        				if(customer.checkOrder() == false)
                                			System.out.println("No order Existed");
                                		else
                                			customer.editOrder(items);
                        				loggedInChoice = 0;
                                    break;
                        			case 3:
                        				if(customer.checkOrder() == false)
                                			System.out.println("No order Existed");
                                		else
                                		{
                                			customer.generateBillStatement(items);
                                			customer.payBill(items, customer);
                                		}
                        				loggedInChoice = 0;
                                    break;
                                    
                        			case 4:
                                        checkLogOut = true;
                                        break;
                                        }
                        			
                                    }while(loggedInChoice != 4);
                                }           
                
           
            }
                case 3:
                    System.out.println("Exiting...");
                    checkExit = true;
                    

        }
    }while(choice !=3);
        System.exit(0);
}
}




