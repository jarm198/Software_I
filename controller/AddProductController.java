/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller for Add Product Menu
 *
 * @author James Armstrong
 */
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    Product currentProduct = new Product(-1,"notUsed",-1,-1,-1,-1);
    
    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductNameTxt;
    
    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductCostTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    
    
    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> addProductAllPartsTable;
    
    @FXML
    private TableColumn<Part, Integer> allPartsIdCol;

    @FXML
    private TableColumn<Part, String> allPartsNameCol;

    @FXML
    private TableColumn<Part, Integer> allPartsInvCol;

    @FXML
    private TableColumn<Part, Double> allPartsCostCol;

    @FXML
    private Button addPartToProductBtn;
    
    
    @FXML
    void addPartToProductAction(ActionEvent event) {
        currentProduct.addAssociatedPart(addProductAllPartsTable.getSelectionModel().getSelectedItem());
    }
    
    
    
    @FXML
    private TableView<Part> currentProductCompositionTable;

    @FXML
    private TableColumn<Part, Integer> compIdCol;

    @FXML
    private TableColumn<Part, String> compNameCol;

    @FXML
    private TableColumn<Part, Integer> compInvCol;

    @FXML
    private TableColumn<Part, Double> compCostCol;

    @FXML
    private Button removePartFromProductBtn;

    @FXML
    void removePartFromProductAction(ActionEvent event) {
        Alert alertRemove = new Alert(Alert.AlertType.CONFIRMATION, "Remove selected part from this product?");

        Optional<ButtonType> result = alertRemove.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentProduct.deleteAssociatedPart(currentProductCompositionTable.getSelectionModel().getSelectedItem());
        }
    }
    
    
    
    @FXML
    private Button addProductSaveBtn;

    @FXML
    void addProductSaveAction(ActionEvent event) throws IOException {
        
        
        int compareId = 1;
        int existingId;
        int id = 1;
        if (!(Inventory.getAllProducts().isEmpty())) {
            for (Product product : Inventory.getAllProducts()) {
                existingId = product.getId();
                if (compareId < existingId) {
                    id = compareId;
                    break;
                }
                else {
                    compareId++;
                    id++;
                }    
            }
        }
        String name = addProductNameTxt.getText();
        double price = -1;
        int stock = -1;
        int max = -1;
        int min = -1;
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        String message = "The following error(s) were found:";
        String error = "";
        
        if (name.isEmpty()) {
            error = error + "\r\n\r\nNo name was provided.";
        }
        
        try {
            price = Double.parseDouble(addProductCostTxt.getText());
            if (price < 0.01) {
                error = error + "\r\n\r\nCost must be 0.01 or higher.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for price. Price must be numeric with positive values only.";
        }
        
        try {
            stock = Integer.parseInt(addProductInvTxt.getText());
            if (stock < 0) {
                error = error + "\r\n\r\nInventory amount cannot be negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for inventory; must be a non-negative integer.";
        }
        
        try {
            max = Integer.parseInt(addProductMaxTxt.getText());
            if (max < 1) {
                error = error + "\r\n\r\nMaximum amount cannot be 0 or negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for maximum amount; must be a positive integer.";
        }
        
        try {
            min = Integer.parseInt(addProductMinTxt.getText());
            if (min < 0) {
                error = error + "\r\n\r\nMinimum amount cannot be negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for minimum amount; must be a non-negative integer.";
        }
        
        if ((max >= 1) && (min >= 0) && (min >= max)) {
            error = error + "\r\n\r\nInvalid value(s) entered for minimum and maximum amounts; maximum must be greater than minimum.";
        }
        
        if ((min >= 0) && (stock >= 0) && (min > stock)) {
            error = error + "\r\n\r\nInvalid value(s) given; current inventory cannot be below the provided minimum.";
        }
        
        if ((max >= 1) && (stock >=0) && (stock > max)) {
            error = error + "\r\n\r\nInvalid value(s) given; current inventory cannot be above the provided maximum.";
        }
        
        
        if (!(error.isEmpty())) {
            message = message + error;
            alert.setTitle("Invalid Input");
            alert.setContentText(message);
            alert.showAndWait();
        }
        else {
            Alert alertSave = new Alert(Alert.AlertType.CONFIRMATION, "Save this product and return to the Main Menu?");
        
            Optional<ButtonType> result = alertSave.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                name = addProductNameTxt.getText();
                price = Double.parseDouble(addProductCostTxt.getText());
                stock = Integer.parseInt(addProductInvTxt.getText());
                max = Integer.parseInt(addProductMaxTxt.getText());
                min = Integer.parseInt(addProductMinTxt.getText());

                Inventory.addProduct(new Product(id, name, price, stock, min, max));
                for (Part part : currentProduct.getAllAssociatedParts()) {
                    Inventory.lookupProduct(id).addAssociatedPart(part);
                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    @FXML
    private Button addProductCancelBtn;

    @FXML
    void addProductCancelAction(ActionEvent event) throws IOException {
        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION, "Cancel addition and return to the main menu?\r\n\r\nThis will discard all entered information.");

        Optional<ButtonType> result = alertCancel.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductAllPartsTable.setItems(Inventory.getAllParts());
        
        addProductSearchTxt.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                Inventory.getFilteredParts().clear();
                addProductAllPartsTable.setItems(Inventory.getAllParts());
            }
            
            if (!(Inventory.getFilteredParts().isEmpty())) {
                Inventory.getFilteredParts().clear();
            }
            
            try {
                Part testPart;
                testPart = Inventory.lookupPart(Integer.parseInt(newText));
                if (testPart == null) {
                    Inventory.getFilteredProducts().clear();
                    addProductAllPartsTable.setItems(Inventory.getFilteredParts());
                }
                else {
                    Inventory.addFilteredPart(Inventory.lookupPart(Integer.parseInt(newText)));
                    addProductAllPartsTable.setItems(Inventory.getFilteredParts());
                    if (Inventory.getFilteredParts().size() == 1)
                        addProductAllPartsTable.getSelectionModel().select(0);
                }
            }
            catch(Exception NumberFormatException) {
                addProductAllPartsTable.setItems(Inventory.lookupPart(newText));
                if (Inventory.getFilteredParts().size() == 1)
                    addProductAllPartsTable.getSelectionModel().select(0);
            }
            
            if (Inventory.getFilteredParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No parts found based on current search criteria.");
                alert.showAndWait();
            }
        });
        
        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        currentProductCompositionTable.setItems(currentProduct.getAllAssociatedParts());
        
        compIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        compNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        compInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        compCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    

    
    
}
