package file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import User.User;

public class Login {
    private List<User> credentials; // List to store User objects
    private File credentialsFile;
    private int loginRole;

    public Login() {
        credentials = new ArrayList<>();
        credentialsFile = new File("credentials.txt");
        createCredentialsFileIfNotExists();
        loadCredentialsFromFile(); // Load existing credentials from file
    }
    
    private void createCredentialsFileIfNotExists() {
        if (!credentialsFile.exists()) {
            try {
                if (credentialsFile.createNewFile()) {
                    System.out.println("Credentials file created.");
                } else {
                    System.out.println("Failed to create credentials file.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating credentials file.");
                e.printStackTrace();
            }
        }
    }

    private void loadCredentialsFromFile() {
        try (Scanner fileScanner = new Scanner(credentialsFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":");
                String username = parts[0];
                String password = parts[1];
                int role = Integer.parseInt(parts[2]);
                credentials.add(new User(username, password, role));
            }
        } catch (FileNotFoundException e) {
            // If file doesn't exist, it will be created when saving credentials
            System.out.println("Credentials file not found. A new file will be created.");
        }
    }

    private void saveCredentialsToFile(String username, String password, int role) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(credentialsFile, true))) {
            writer.println(username + ":" + password + ":" + role);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error occurred while saving credentials to file.");
            e.printStackTrace();
        }
    }

    public boolean createAccount(String username, String password) {
    	for (User user : credentials){
    	if(user.getUsername().equals(username)) {
    		System.out.println("Username has already exist");
    		return false;
    	}
    	}
    		credentials.add(new User(username, password, 1));
    		saveCredentialsToFile(username, password, 1);
    		System.out.println("Account created successfully for user: " + username);
    		return true;
    	}
    
    public boolean login(String username, String password) {
        for (User user : credentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                setLogInRole(user.getRole());
                return true;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
        return false;
    }

    private void setLogInRole(int role) {
        loginRole = role;
    }

    public int getLogInRole() {
        return loginRole;
    }

    public List<User> getCredentials() {
        return credentials;
    }
}