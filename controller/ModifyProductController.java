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
 * FXML Controller for Modify Product Menu
 *
 * @author James Armstrong
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    Product currentProduct = new Product(-1,"notUsed",-1,-1,-1,-1);
    
    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductNameTxt;
    
    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductCostTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    
    
    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TableView<Part> modifyProductAllPartsTable;
    
    @FXML
    private TableColumn<Part, Integer> allPartsIdModCol;

    @FXML
    private TableColumn<Part, String> allPartsNameModCol;

    @FXML
    private TableColumn<Part, Integer> allPartsInvModCol;

    @FXML
    private TableColumn<Part, Double> allPartsCostModCol;

    @FXML
    private Button addPartToProductModBtn;

    @FXML
    void addPartToProductModAction(ActionEvent event) {
        currentProduct.addAssociatedPart(modifyProductAllPartsTable.getSelectionModel().getSelectedItem());
    }
    
    
    
    @FXML
    private TableView<Part> currentProductCompositionModTable;

    @FXML
    private TableColumn<Part, Integer> compIdModCol;

    @FXML
    private TableColumn<Part, String> compNameModCol;

    @FXML
    private TableColumn<Part, Integer> compInvModCol;

    @FXML
    private TableColumn<Part, Double> compCostModCol;

    @FXML
    private Button removePartFromProductModBtn;

    @FXML
    void removePartFromProductModAction(ActionEvent event) {
        Alert alertRemove = new Alert(Alert.AlertType.CONFIRMATION, "Remove selected part from this product?");

        Optional<ButtonType> result = alertRemove.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentProduct.deleteAssociatedPart(currentProductCompositionModTable.getSelectionModel().getSelectedItem());
        }
    }
    
    
    
    @FXML
    private Button modifyProductSaveBtn;

    @FXML
    void modifyProductSaveAction(ActionEvent event) throws IOException {
        int id;
        id = Integer.parseInt(modifyProductIdTxt.getText());
        String name = modifyProductNameTxt.getText();
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
            price = Double.parseDouble(modifyProductCostTxt.getText());
            if (price < 0.01) {
                error = error + "\r\n\r\nCost must be 0.01 or higher.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for price. Price must be numeric with positive values only.";
        }
        
        try {
            stock = Integer.parseInt(modifyProductInvTxt.getText());
            if (stock < 0) {
                error = error + "\r\n\r\nInventory amount cannot be negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for inventory; must be a non-negative integer.";
        }
        
        try {
            max = Integer.parseInt(modifyProductMaxTxt.getText());
            if (max < 1) {
                error = error + "\r\n\r\nMaximum amount cannot be 0 or negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for maximum amount; must be a positive integer.";
        }
        
        try {
            min = Integer.parseInt(modifyProductMinTxt.getText());
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
            Alert alertSave = new Alert(Alert.AlertType.CONFIRMATION, "Save these modifications and return to the main menu?");

            Optional<ButtonType> result = alertSave.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                currentProduct.setId(id);
                currentProduct.setName(modifyProductNameTxt.getText());
                currentProduct.setPrice(Double.parseDouble(modifyProductCostTxt.getText()));
                currentProduct.setStock(Integer.parseInt(modifyProductInvTxt.getText()));
                currentProduct.setMax(Integer.parseInt(modifyProductMaxTxt.getText()));
                currentProduct.setMin(Integer.parseInt(modifyProductMinTxt.getText()));

                int index;
                index = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(id));
                Inventory.updateProduct(index, currentProduct);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    @FXML
    private Button modifyProductCancelBtn;

    @FXML
    void modifyProductCancelAction(ActionEvent event) throws IOException {
        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION, "Cancel modification and return to the main menu?\r\n\r\nThis will discard any changes.");

        Optional<ButtonType> result = alertCancel.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    /**
     *
     * @param product
     */
    public void populateProductFields(Product product) {
        modifyProductIdTxt.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        modifyProductCostTxt.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));
        for (Part part : product.getAllAssociatedParts()) {
            currentProduct.addAssociatedPart(part);
        }
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        modifyProductAllPartsTable.setItems(Inventory.getAllParts());
        
        
        modifyProductSearchTxt.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                modifyProductAllPartsTable.setItems(Inventory.getAllParts());
            }
            
            if (!(Inventory.getFilteredParts().isEmpty())) {
                Inventory.getFilteredParts().clear();
            }
            
            try {
                Part testPart;
                testPart = Inventory.lookupPart(Integer.parseInt(newText));
                if (testPart == null) {
                    Inventory.getFilteredProducts().clear();
                    modifyProductAllPartsTable.setItems(Inventory.getFilteredParts());
                }
                else {
                    Inventory.addFilteredPart(Inventory.lookupPart(Integer.parseInt(newText)));
                    modifyProductAllPartsTable.setItems(Inventory.getFilteredParts());
                    if (Inventory.getFilteredParts().size() == 1)
                        modifyProductAllPartsTable.getSelectionModel().select(0);
                }
            }
            catch(Exception NumberFormatException) {
                modifyProductAllPartsTable.setItems(Inventory.lookupPart(newText));
                if (Inventory.getFilteredParts().size() == 1)
                    modifyProductAllPartsTable.getSelectionModel().select(0);
            }
            
            if (Inventory.getFilteredParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No parts found based on current search criteria.");
                alert.showAndWait();
            }
            /*
            try {
                Inventory.addFilteredPart(Inventory.lookupPart(Integer.parseInt(newText)));
                modifyProductAllPartsTable.setItems(Inventory.getFilteredParts());
                if (Inventory.getFilteredParts().size() == 1)
                    modifyProductAllPartsTable.getSelectionModel().select(0);
            }
            catch(Exception e) {
                modifyProductAllPartsTable.setItems(Inventory.lookupPart(newText));
                if (Inventory.getFilteredParts().size() == 1)
                    modifyProductAllPartsTable.getSelectionModel().select(0);
            }
            */
        });
        
        allPartsIdModCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameModCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvModCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsCostModCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        currentProductCompositionModTable.setItems(currentProduct.getAllAssociatedParts());
        
        compIdModCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        compNameModCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        compInvModCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        compCostModCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
