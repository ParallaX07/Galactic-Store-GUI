package models;
/**
 * Represents a product available for purchase.
 * Each product has a name, price, quantity, sector, galaxy, and condition.
 */

import java.io.Serializable;

public class Product implements Serializable{
    private String name;
    private double price;
    private int stock;
    private String planet;
    private String galaxy;
    private String condition;

    public Product(String name, double price, int stock, String planet, String galaxy, String condition) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.planet = planet;
        this.galaxy = galaxy;
        this.condition = condition;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPlanet() {
        return this.planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getGalaxy() {
        return this.galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String toString(){
        return "Name: " + name + "\nPrice: " + price + "\nStock: " + stock + "\nPlanet: " + planet + "\nGalaxy: " + galaxy + "\nCondition: " + condition;
    }

}
