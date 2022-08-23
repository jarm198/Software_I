/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class definition for Inventory.
 * All inventory members and methods are static.
 * <br>Four ObservableLists are used:
 * <br>allParts is an ObservableList of all parts currently in inventory.
 * <br>allProducts is an ObservableList of all products currently in inventory.
 * <br>filteredParts is an ObservableList of parts used to populate table views based on ID or name searches.
 * <br>filteredProducts is an ObservableList of products used to populate table views based on ID or name searches.
 * @author James Armstrong
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    
    /**
     * This method adds a new part to the allParts ObservableList.
     * @param newPart part to be added to the inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    /**
     * This method adds a new product to the allProducts ObservableList
     * @param newProduct product to be added to the inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    /**
     * This method adds a part to the filteredParts ObservableList
     * @param newPart part to be added to the filtered list
     */
    public static void addFilteredPart(Part newPart) {
        filteredParts.add(newPart);
    }
    
    /**
     * This method adds a product to the filteredProducts ObservableList
     * @param newProduct product to be added to the filtered list
     */
    public static void addFilteredProduct(Product newProduct) {
        filteredProducts.add(newProduct);
    }
    
    /**
     * This is an overloaded method used to lookup parts.
     * @param partId ID number to lookup; will return one result or none.
     * @return part with associated ID number, if any; if ID not found, returns null
     */
    public static Part lookupPart(int partId) {
        filteredParts.clear();
        for (Part part : Inventory.getAllParts()) {
            if(part.getId() == partId)
                return part;
        }
        return null;
    }
    
    /**
     * This is an overloaded method used to lookup products.
     * @param productId ID number to lookup; will return one result or none.
     * @return product with associated ID number, if any; if ID not found, returns null
     */
    public static Product lookupProduct(int productId) {
        filteredProducts.clear();
        for (Product product : Inventory.getAllProducts()) {
            if(product.getId() == productId)
                return product;
        }
        return null;
    }
    
    /**
     * This is an overloaded method used to lookup parts.
     * @param partName Case-sensitive string used to lookup a full or partial part name.
     * <br>Each matching part in inventory is added to filteredParts ObservableList
     * @return filteredParts ObservableList is returned after all parts in inventory are checked.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        filteredParts.clear();
        for (Part part : Inventory.getAllParts())
            if (part.getName().indexOf(partName) != -1)
                filteredParts.add(part);
        return filteredParts;
    }
    
    /**
     * This is an overloaded method used to lookup products.
     * @param productName Case-sensitive string used to lookup a full or partial product name.
     * <br>Each matching product in inventory is added to filteredProducts ObservableList
     * @return filteredProducts ObservableList is returned after all products in inventory are checked.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        filteredProducts.clear();
        for (Product product : Inventory.getAllProducts())
            if (product.getName().indexOf(productName) != -1)
                filteredProducts.add(product);
        return filteredProducts;
    }
    
    /**
     * This method replaces one part in inventory with another.
     * <br>It is used for the ModifyPartController.
     * <br>Thus its purpose, more accurately, is to update a part in inventory.
     * @param index index number of part that is to be replaced
     * @param selectedPart new part that is to replace part at provided index
     */
    public static void updatePart(int index, Part selectedPart) {
        Inventory.getAllParts().set(index, selectedPart);
    }
    
    /**
     * This method replaces one product in inventory with another.
     * <br>It is used for the ModifyProductController.
     * <br>Thus its purpose, more accurately, is to update a product in inventory.
     * @param index index number of product that is to be replaced
     * @param selectedProduct new product that is to replace product at provided index
     */
    public static void updateProduct(int index, Product selectedProduct) {
        Inventory.getAllProducts().set(index, selectedProduct);
    }
    
    /**
     * This method finds a part in inventory and deletes it.
     * @param selectedPart part selected from parts table view intended to be deleted
     * <br>If search box for parts table view is empty, selected part is deleted from allParts Observable List.
     * <br>If a search is present, the ID number of the selected part must be obtained from the filtered list,
     * which is then used to find the part in inventory.
     * @return true if the selected part is successfully deleted, otherwise returns false
     */
    public static boolean deletePart(Part selectedPart) {
        try {
            try {
                Inventory.getAllParts().remove(selectedPart);
                return true;
            }
            catch(Exception e) {
                int removeId;
                removeId = Inventory.getFilteredParts().get(selectedPart.getId()).getId();
                Inventory.getAllParts().remove(lookupPart(removeId));
                return true;
            }
        }
        catch(Exception e) {
            return false;
        }
    }
    
    /**
     * This method finds a product in inventory and deletes it.
     * @param selectedProduct product selected from products table view intended to be deleted
     * <br>If search box for products table view is empty, selected product is deleted from allProducts Observable List.
     * <br>If a search is present, the ID number of the selected product must be obtained from the filtered list,
     * which is then used to find the product in inventory.
     * @return true if the selected product is successfully deleted, otherwise returns false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        try {
            try {
                Inventory.getAllProducts().remove(selectedProduct);
                return true;
            }
            catch(Exception e) {
                int removeId;
                removeId = Inventory.getFilteredProducts().get(selectedProduct.getId()).getId();
                Inventory.getAllProducts().remove(lookupProduct(removeId));
                return true;
            }
        }
        catch(Exception e) {
            return false;
        }
    }
    
    /**
     * Getter for allParts
     * @return ObservableList of all parts currently in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    /**
     * Getter for allProducts
     * @return ObservableList of all products currently in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    /**
     * Getter for filteredParts
     * @return ObservableList of all parts in the filtered list
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }
    
    /**
     * Getter for filteredProducts
     * @return ObservableList of all products in the filtered list
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }
}
