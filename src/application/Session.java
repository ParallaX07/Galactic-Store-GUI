package application;
/**
 * Session singleton class responsible for user and product management. 
 * Also contains methods for reading user input.
 */

import java.util.ArrayList;

import database.Store;
//import database.Store;
import models.Admin;
import models.Customer;
import models.Product;
import models.User;

public class Session {
    private static Session session = null;
    private ArrayList<Product> initalProduct = new ArrayList<Product>();
    private ArrayList<User> userList = new ArrayList<User>();


    private Session() {
        createDatabase();
    }

    private void createDatabase() {
        //todo add admin in user file
        userList.add(new Admin("Admin", 43, "Male", "admin@gmail.com", "password"));
        userList.add(new Customer("Customer1", 30, "Female", "customer1@gmail.com", "password"));
        userList.add(new Customer("Customer2", 345, "Male", "customer2@gmail.com", "password"));
        
        //checks if any users were loaded from file to userList
        if(Store.getAllUsers().size() < 1){
            for (User user : userList) {
                Store.getAllUsers().add(user);
            }
        }

        //create some default user in file
        //todo create same product with condition type used and lower the price
        initalProduct.add(new Product("Moon Rock Dust", 20, 5, "XVI", "Milky Way", "New"));
        initalProduct.add(new Product("Hydro-polymer Flux capacitor", 200, 5, "XVI", "Whirlpool", "New"));
        initalProduct.add(new Product("Carbonised Panels", 100, 5, "V", "Milky Way", "New"));
        initalProduct.add(new Product("Cyro-Cooled Quantum Processor", 370, 5, "V", "Whirlpool", "New"));
        initalProduct.add(new Product("Galactic Graviton Inductor", 560, 5, "IX", "Andromeda", "Used"));
        initalProduct.add(new Product("Xenon Gas-filled Fusion Core", 320, 5, "X", "Whirlpool", "New"));
        initalProduct.add(new Product("Neutrino Amplifier Array", 490, 5, "II", "Andromeda", "New"));
        initalProduct.add(new Product("Anti-Matter Reactor Chamber", 780, 5, "VII", "Andromeda", "New"));
        initalProduct.add(new Product("Hyperluminal Data Crystal", 120, 5, "III", "Whirlpool", "Used"));
        initalProduct.add(new Product("Quantum Entangled Transmitter", 490, 5, "II", "Andromeda", "New"));
        initalProduct.add(new Product("Hyperdimensional FLux capacitor", 490, 5, "II", "Milky Way", "New"));
        initalProduct.add(new Product("Dark Matter Injector", 490, 5, "II", "Andromeda", "New"));

        //checks if any products were loaded from file to productLists
        if(Store.getAllProducts().size() < 1){
            for (Product product : initalProduct) {
                Store.getAllProducts().add(product);
            }
        }

    }

    public static Session getSession(){
        if(session == null){
            session = new Session();
        }
        return session;
    }
}