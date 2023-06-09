package models;
import database.FileHandler;
import database.Store;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * Represents an administrator in the system.
 * An admin can manage products and has all rights in the system.
 * This class extends from the User class and implements the Actions interface.
 */

public class Admin extends User {
    public Admin(String name, int age, String gender, String email, String password) {
        super(name, age, gender, email, password);
    }

    public void addProduct(String name, double price, int stock, String galaxy, String planet, String condition){
        //if product already exists, show error message
            if (Store.searchItem(name) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Product " + name + " already in store.");
                alert.showAndWait();
            } else {
                Product newProduct = new Product(name, price, stock, galaxy, planet, condition);
                Store.addProduct(newProduct);  // Add the new product to the store
                FileHandler.saveProductsToFile();
                //productListView.getItems().add(newProduct);  // Add the new product to the list view
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Product Added");
                alert.setHeaderText(null);
                alert.setContentText("Product " + name + " has been added.");
                alert.showAndWait();
            }
        FileHandler.saveProductsToFile();
    }

    public void removeProduct(String searchInput){
        //check product not in store and display error message
        Product product = Store.searchItem(searchInput);
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Product " + searchInput + " not in store.");
            alert.showAndWait();
        } else if (searchInput == null || searchInput.trim().isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.INFORMATION);
            emptyAlert.setTitle("Empty");
            emptyAlert.setHeaderText(null);
            emptyAlert.setContentText("Please enter the name of the item to be removed.");
            emptyAlert.showAndWait();
        } else {
            Store.getAllProducts().remove(product);  // remove item from Store
            FileHandler.saveProductsToFile();  // remove item from ListView
            // Show a dialog with a message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Removed");
            alert.setHeaderText(null);
            alert.setContentText("Product " + searchInput + " has been removed.");
            alert.showAndWait();
            
        }
        FileHandler.saveProductsToFile();
    }

    public void updateProduct(Product product, TextField tfname, TextField tfprice, TextField tfstock, TextField tfgalaxy, TextField tfplanet, TextField tfcondition){
        if (tfname.getText() != null) {
            String oldName = product.getName();
            product.setName(tfname.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Updated");
            alert.setHeaderText(null);
            alert.setContentText("Product Name: " + oldName + " has been updated to " + product.getName());
            alert.showAndWait();
        }

        if (tfgalaxy.getText() != null) {
            String oldGalaxy = product.getGalaxy();
            product.setGalaxy(tfgalaxy.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Updated");
            alert.setHeaderText(null);
            alert.setContentText("Product Galaxy: " + oldGalaxy + " has been updated to " + product.getGalaxy());
            alert.showAndWait();

        }

        if (tfplanet.getText() != null) {
            String oldPlanet = product.getPlanet();
            product.setPlanet(tfplanet.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Updated");
            alert.setHeaderText(null);
            alert.setContentText("Product Planet: " + oldPlanet + " has been updated to " + product.getPlanet());
            alert.showAndWait();
        }
        
        if (tfcondition.getText() != null) {
            String oldCondition = product.getCondition();
            product.setCondition(tfcondition.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Updated");
            alert.setHeaderText(null);
            alert.setContentText("Product Condition: " + oldCondition + " has been updated to " + product.getCondition());
            alert.showAndWait();
        }
        
        if (tfprice.getText() != null && !tfprice.getText().isEmpty()) {
            try {
                Double oldPrice = product.getPrice();
                product.setPrice(Double.parseDouble(tfprice.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Updated");
                alert.setHeaderText(null);
                alert.setContentText("Product Price: " + oldPrice + " has been updated to " + product.getPrice());
                alert.showAndWait();

            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please ensure you've entered the correct values in the fields.");
                alert.showAndWait();
            }
        }

        if (tfstock.getText() != null && !tfstock.getText().isEmpty()) {
            try {
                int oldStock = product.getStock();
                product.setStock(Integer.parseInt(tfstock.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Updated");
                alert.setHeaderText(null);
                alert.setContentText("Product Stock: " + oldStock + "has been updated to " + product.getStock());
                alert.showAndWait();

            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please ensure you've entered the correct values in the fields.");
                alert.showAndWait();
            }
        }
        FileHandler.saveProductsToFile();
    }
}