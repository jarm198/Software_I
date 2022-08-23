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
import static model.Inventory.deletePart;
import static model.Inventory.deleteProduct;
import model.Part;
import model.Product;

/**
 * FXML Controller for Main Menu
 * 
 * @author James Armstrong
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField partsInvSearchTxt;

    @FXML
    private TableView<Part> partsTableMain;

    @FXML
    private TableColumn<Part, Integer> partsTblIdCol;

    @FXML
    private TableColumn<Part, String> partsTblNameCol;

    @FXML
    private TableColumn<Part, Integer> partsTblInvCol;

    @FXML
    private TableColumn<Part, Double> partsTblCostCol;

    @FXML
    private Button partsGotoAddMenuBtn;

    @FXML
    private Button partsGotoModifyMenuBtn;

    @FXML
    private Button partsDeleteBtn;

    
    @FXML
    private TextField productsInvSearchTxt;

    @FXML
    private TableView<Product> productsTableMain;

    @FXML
    private TableColumn<Part, Integer> productsTblIdCol;

    @FXML
    private TableColumn<Part, String> productsTblNameCol;

    @FXML
    private TableColumn<Part, Integer> productsTblInvCol;

    @FXML
    private TableColumn<Part, Double> productsTblPriceCol;
    
    @FXML
    private Button productsGotoAddMenuBtn;

    @FXML
    private Button productsGotoModifyMenuBtn;

    @FXML
    private Button productsDeleteBtn;

    @FXML
    private Button exitProgramBtn;

    
    @FXML
    void partsGotoAddMenuAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void partsGotoModifyMenuAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartMenu.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.populatePartFields(partsTableMain.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception InvocationTargetException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
    }

    @FXML
    void partsDeleteAction(ActionEvent event) {
        if (partsTableMain.getSelectionModel().getSelectedItem() instanceof Part) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                deletePart(partsTableMain.getSelectionModel().getSelectedItem());
                partsInvSearchTxt.clear();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No part is selected.");
            alert.showAndWait();
        }
    }

    
    @FXML
    void productsGotoAddMenuAction(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    
    @FXML
    void productsGotoModifyMenuAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.populateProductFields(productsTableMain.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception InvocationTargetException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        }
    }
    
    @FXML
    void productsDeleteAction(ActionEvent event) {
        if (productsTableMain.getSelectionModel().getSelectedItem() instanceof Product) {
            if (productsTableMain.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    deleteProduct(productsTableMain.getSelectionModel().getSelectedItem());
                    productsInvSearchTxt.clear();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Selected product cannot be deleted because it has associated parts.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No product is selected.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void exitProgramAction(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    
    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableMain.setItems(Inventory.getAllParts());
        
        partsInvSearchTxt.textProperty().addListener((obs, oldText, newText) -> {
            
            if (newText.isEmpty()) {
                Inventory.getFilteredParts().clear();
                partsTableMain.setItems(Inventory.getAllParts());
            }
            
            if (!(Inventory.getFilteredParts().isEmpty())) {
                Inventory.getFilteredParts().clear();
            }
            
            try {
                Part testPart;
                testPart = Inventory.lookupPart(Integer.parseInt(newText));
                if (testPart == null) {
                    Inventory.getFilteredProducts().clear();
                    partsTableMain.setItems(Inventory.getFilteredParts());
                }
                else {
                    Inventory.addFilteredPart(Inventory.lookupPart(Integer.parseInt(newText)));
                    partsTableMain.setItems(Inventory.getFilteredParts());
                    if (Inventory.getFilteredParts().size() == 1)
                        partsTableMain.getSelectionModel().select(0);
                }
            }
            catch(Exception NumberFormatException) {
                partsTableMain.setItems(Inventory.lookupPart(newText));
                if (Inventory.getFilteredParts().size() == 1)
                    partsTableMain.getSelectionModel().select(0);
            }
            
            if (Inventory.getFilteredParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No parts found based on current search criteria.");
                alert.showAndWait();
            }
        });
        
        partsTblIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTblNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTblInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsTblCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        productsTableMain.setItems(Inventory.getAllProducts());
        
        productsInvSearchTxt.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                Inventory.getFilteredProducts().clear();
                productsTableMain.setItems(Inventory.getAllProducts());
            }
            
            if (!(Inventory.getFilteredProducts().isEmpty())) {
                Inventory.getFilteredProducts().clear();
            }
            
            
            try {
                Product testProd;
                testProd = Inventory.lookupProduct(Integer.parseInt(newText));
                if (testProd == null) {
                    Inventory.getFilteredProducts().clear();
                    productsTableMain.setItems(Inventory.getFilteredProducts());
                }
                else {
                    Inventory.addFilteredProduct(Inventory.lookupProduct(Integer.parseInt(newText)));
                    productsTableMain.setItems(Inventory.getFilteredProducts());
                    if (Inventory.getFilteredProducts().size() == 1) {
                        productsTableMain.getSelectionModel().select(0);
                    }
                }
            }
            catch(Exception NumberFormatException) {
                productsTableMain.setItems(Inventory.lookupProduct(newText));
                if (Inventory.getFilteredProducts().size() == 1) {
                    productsTableMain.getSelectionModel().select(0);
                }
            }
            
            if (Inventory.getFilteredProducts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No products found based on current search criteria.");
                alert.showAndWait();
            }
        });
        
        productsTblIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsTblNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsTblInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsTblPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
}
