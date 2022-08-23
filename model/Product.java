/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Part;

/**
 * Class definition for Products.
 * Each Product has an attribute <code>associatedParts</code> that is not set
 * by the constructor. associatedParts is an observable array list of parts.
 * 
 * @author James Armstrong
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id ID number generated for the product
     * @param name Name provided for the product
     * @param price Current price of the product
     * @param stock Current inventory level of the product
     * @param min Lowest allowable inventory level of the product
     * @param max Highest allowable inventory level of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Setter for id
     * @param id sets product's id number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for name
     * @param name sets product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for price
     * @param price sets product's current price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter for stock
     * @param stock sets product's current inventory level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter for min
     * @param min sets product's lowest allowable inventory level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter for max
     * @param max sets product's highest allowable inventory level
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Getter for id
     * @return product's id number
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for name
     * @return product's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for price
     * @return product's current price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter for stock
     * @return product's current inventory level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Getter for min
     * @return product's lowest allowable inventory level
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter for max
     * @return product's highest allowable inventory level
     */
    public int getMax() {
        return max;
    }
    
    /**
     * This method adds a part to the product's associatedParts ObservableList
     * @param part the part to be added to the product's associatedParts
     */
    public void addAssociatedPart(Part part)  {
        associatedParts.add(part);
    }
    
    /**
     * This method removes a part from the product's associatedParts ObservableList
     * @param selectedAssociatedPart the part to be removed from associatedParts
     * @return true if the part is removed, false if part is not removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)  {
        try {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    /**
     * Getter for associatedParts
     * @return ObservableList of all parts currently associated with the product
     */
    public ObservableList<Part> getAllAssociatedParts()  {
        return associatedParts;
    }
    
}
