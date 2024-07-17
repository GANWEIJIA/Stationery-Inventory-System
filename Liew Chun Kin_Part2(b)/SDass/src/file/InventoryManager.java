package file;
import java.io.*;
import java.util.ArrayList;

import item.Item;

public class InventoryManager {
    private static final String INVENTORY_FILE = "inventory.txt";

    // Method to load inventory from a file
    public static ArrayList<Item> loadInventory() throws IOException {
        ArrayList<Item> items = new ArrayList<>();
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            System.out.println("Inventory file not found. Creating...");
            file.createNewFile();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                int no = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int quantity = Integer.parseInt(parts[3]);
                items.add(new Item(no, name, price, quantity));
            }
        } catch (IOException e) {
            System.out.println("Error occurred while loading inventory: " + e.getMessage());
        }
        return items;
    }

    // Method to save inventory to a file
    public static void saveInventory(ArrayList<Item> items) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            for (Item item : items) {
                printWriter.println(item.getNo() + "," + item.getName() + "," + item.getPrice() + "," + item.getQuantity());
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving inventory: " + e.getMessage());
        }
    }
}
