package database;
/**
 * Static class serving as a data store for users and products.
 */

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.Product;
import models.User;

public class Store implements Serializable {

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ArrayList<String> allGalaxies = new ArrayList<String>();
    private static ArrayList<String> allPlanets = new ArrayList<String>();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void setAllProducts(ArrayList<Product> productList) {
        allProducts = FXCollections.observableArrayList(productList);
    }
    
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static ArrayList<String> getAllGalaxies() {
        for (Product product : allProducts) {
            if (!allGalaxies.contains(product.getGalaxy())) {
                allGalaxies.add(product.getGalaxy());
            }
        }
        return allGalaxies;
    } 

    public static ArrayList<String> getAllPlanets() {
        for (Product product : allProducts) {
            if (!allPlanets.contains(product.getPlanet())) {
                allPlanets.add(product.getPlanet());
            }
        }
        return allPlanets;
    }
    
    public static ArrayList<Product> getProductsByGalaxy(String galaxyName) {
        ArrayList<Product> productsInGalaxy = new ArrayList<Product>();
        for (Product product : allProducts) {
            if (product.getGalaxy().equals(galaxyName)) {
                productsInGalaxy.add(product);
            }
        }
        return productsInGalaxy;
    }

    public static ArrayList<Product> getProductsByPlanet(String planetName) {
        ArrayList<Product> productsInPlanet = new ArrayList<Product>();
        for (Product product : allProducts) {
            if (product.getPlanet().equals(planetName)) {
                productsInPlanet.add(product);
            }
        }
        return productsInPlanet;
    }
    

    public static Product searchItem(String name) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    
    public static User login(String email, String password) throws Exception {
        boolean validEmail = false, validPassword = false;
		for (User user : Store.getAllUsers()) {  
            if (user.getEmail().equals(email)) {
                validEmail = true;
            }
            if (user.getPassword().equals(password)) {
                validPassword = true;
            }
			if(validEmail && validPassword) {
                return user;
            }
		}
        if (!validEmail){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Email");
                alert.setHeaderText(null);
                alert.setContentText("User not found.\nPlease recheck your credentials.");
                alert.showAndWait();
                return null;
        }

        if(validEmail && !validPassword){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Password");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect password.\nPlease recheck your credentials.");
                alert.showAndWait();
                return null;
        }
		throw new Exception("User not found");
	}
}
