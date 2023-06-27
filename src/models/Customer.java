package models;
/**
 * Represents a customer in the system.
 * A customer can browse and purchase products.
 * This class extends from the User class and implements the Actions interface.
 */
import java.util.ArrayList;
import java.util.Optional;
import database.FileHandler;
import database.Store;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class Customer extends User {
	
    private ArrayList<CartProduct> inCart = new ArrayList<CartProduct>();
    private ArrayList<CartProduct> orderHistory = new ArrayList<CartProduct>();
    private double totalBill = 0;

    public Customer(String name, int age, String gender, String email, String password) {
        super(name, age, gender, email, password);
    }

    public double getTotalBill() {
        totalBill = 0;
        if(inCart.isEmpty()){
            return totalBill;
        }
        for (CartProduct product : this.inCart) {
            this.totalBill += product.getProduct().getPrice() * product.getQuantity();
        }
        return this.totalBill;
    }

    public ArrayList<CartProduct> getInCart() {
        return this.inCart;
    }

    public void setInCart(ArrayList<CartProduct> inCart) {
        this.inCart = inCart;
    }

    public ArrayList<CartProduct> getOrderHistory() {
        return this.orderHistory;
    }

    public void setOrderHistory(ArrayList<CartProduct> orderHistory) {
        this.orderHistory = orderHistory;
    }


    
    //check if item exists, in cart and in stock
    public boolean itemExists(String name){
        Product product = Store.searchItem(name);
        if (product == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Item does not exist");
            alert.showAndWait();
        }
        if (product != null) return true;
        else return false;
    }

    //addToCart for GUI, it'll take in a string and int from text fields from customer and add the product to inCart
    public void addToCart(String name, int amount) {
        if (amount < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Amount cannot be negative.");
            alert.showAndWait();
            return;
        }
        
        Product product = Store.searchItem(name);
        if (amount > product.getStock()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not enough in stock");
            alert.setHeaderText(null);
            alert.setContentText("Only " + product.getStock() + " of " + product.getName() + " available.");
            alert.showAndWait();
            return;
        }

        for (CartProduct cartProduct : inCart) {
            if (cartProduct.getProduct().getName().equals(product.getName())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item already in cart");
                alert.setHeaderText(null);
                alert.setContentText("Item already in cart. Please remove or update cart item.");
                alert.showAndWait();
                return;
            }
        }

        this.inCart.add(new CartProduct(product, amount));
        product.setStock(product.getStock() - amount);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added to cart");
        alert.setHeaderText(null);
        alert.setContentText(amount + " of " + product.getName() + " has been added to cart");
        alert.showAndWait();
        FileHandler.saveUsersToFile();
        FileHandler.saveProductsToFile();

    }

    //removeFromCart for GUI, it'll take in a string from text fields from customer and remove the product from inCart
    public void removeFromCart(String name) {
        Product product = Store.searchItem(name);
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Item does not exist");
            alert.showAndWait();
            return;
        }
        for (CartProduct cartProduct : inCart) {
            if (cartProduct.getProduct().getName().equals(product.getName())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm remove");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to remove " + product.getName() + " from cart?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    inCart.remove(cartProduct);
                    product.setStock(product.getStock() + cartProduct.getQuantity());
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Removed");
                    alert.setHeaderText(null);
                    alert.setContentText("\"" + product.getName() + "\" has been removed");
                    alert.showAndWait();
                    FileHandler.saveUsersToFile();
                    FileHandler.saveProductsToFile();
                    return;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Item not in cart.");
        alert.showAndWait();
    }

    //updateCartItem for GUI, it'll take in a string and int from text fields from customer and update the product in inCart
    public void updateCartItem(String name, int amount) {
        Product product = Store.searchItem(name);
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Item does not exist.");
            alert.showAndWait();
            return;
        }

        if (amount < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Amount cannot be negative.");
            alert.showAndWait();
            return;
        }

        int currentAmount = 0;
    

        for (CartProduct cartProduct : inCart) {
            if (cartProduct.getProduct().getName().equals(product.getName())) {
                currentAmount = cartProduct.getQuantity();
                int addOrRemove = amount - currentAmount;
                if (addOrRemove > product.getStock()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Not enough in stock");
                    alert.setHeaderText(null);
                    alert.setContentText("Only " + product.getStock() + " of " + product.getName() + " available.\nPlease choose another amount.");
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm update");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to update " + product.getName() + " in cart?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    cartProduct.setQuantity(amount);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Updated");
                    alert.setHeaderText(null);
                    alert.setContentText("\"" + product.getName() + "\" has been updated");
                    alert.showAndWait();
                    
                    product.setStock(product.getStock() - addOrRemove);
                    cartProduct.setQuantity(amount);

                    FileHandler.saveUsersToFile();
                    FileHandler.saveProductsToFile();
                    return;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Item not in cart");
        alert.showAndWait();
    }

    public void confirmPurchase(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm purchase");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to confirm purchase?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (CartProduct cartProduct : inCart) {
                orderHistory.add(cartProduct);
            }
            inCart.clear();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Purchase confirmed");
            alert.setHeaderText(null);
            alert.setContentText("Your total order for: " + this.totalBill + " has been confirmed");
            alert.showAndWait();
        }
        FileHandler.saveProductsToFile();
        FileHandler.saveUsersToFile();
    }

}
