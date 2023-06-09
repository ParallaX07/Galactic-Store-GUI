package database;
/**
 * Utility class for loading and saving user and product data from/to files.
 */

import java.io.*;
import java.util.ArrayList;

import models.Product;
import models.User;

public class FileHandler {

    public static void loadUsersFromFile() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("userListFile.bin"))) {
            @SuppressWarnings("unchecked")
            ArrayList<User> loadedUsers = (ArrayList<User>) input.readObject();

            for (User user : loadedUsers) {
                Store.getAllUsers().add(user);
            }

        } catch (Exception e) {
            System.out.println("Loading users failed: " + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public static void saveUsersToFile() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("userListFile.bin"))) {
            ArrayList<User> userList = new ArrayList<>(Store.getAllUsers());
            output.writeObject(userList);
        } catch (Exception e) {
            System.out.println("Saving users failed: " + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public static void saveProductsToFile() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("productListFile.bin"))) {
            ArrayList<Product> productList = new ArrayList<>(Store.getAllProducts());
            output.writeObject(productList);
        } catch (Exception e) {
            System.out.println("Saving products failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadProductFromFile() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("productListFile.bin"))) {
            @SuppressWarnings("unchecked")
            ArrayList<Product> loadedProducts = (ArrayList<Product>) input.readObject();
            Store.setAllProducts(loadedProducts);
        } catch (Exception e) {
            System.out.println("Loading products failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
