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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller for Add Part Menu
 *
 * @author James Armstrong
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    
    @FXML
    private TextField addPartIdTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartCostTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartMachineOrCompanyTxt;

    

    @FXML
    private ToggleGroup inHouseOutsource;

    @FXML
    private RadioButton inHouseRadioNo;

    @FXML
    private RadioButton inHouseRadioYes;

    @FXML
    private Text inHouseText;

    @FXML
    void setInHouseNo(ActionEvent event) {
        inHouseText.setText("Company Name:");
    }

    @FXML
    void setInHouseYes(ActionEvent event) {
        inHouseText.setText("Machine ID:");
    }
    

    @FXML
    private Button addPartSaveBtn;
    
    @FXML
    @SuppressWarnings("empty-statement")
    void addPartSaveAction(ActionEvent event) throws IOException {
        
        
        int compareId = 1;
        int existingId;
        int id = 1;
        if (!(Inventory.getAllParts().isEmpty())) {
            for (Part part : Inventory.getAllParts()) {
                existingId = part.getId();
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
        String name = addPartNameTxt.getText();
        double price = -1;
        int stock = -1;
        int max = -1;
        int min = -1;
        int machineId = -1;
        String companyName = "";
        
        
        Alert alert = new Alert(Alert.AlertType.ERROR);

        
        String message = "The following error(s) were found:";
        String error = "";
        
        if (name.isEmpty()) {
            error = error + "\r\n\r\nNo name was provided.";
        }
        
        try {
            price = Double.parseDouble(addPartCostTxt.getText());
            if (price < 0.01) {
                error = error + "\r\n\r\nPrice must be 0.01 or higher.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for price. Price must be numeric with positive values only.";
        }
        
        try {
            stock = Integer.parseInt(addPartInvTxt.getText());
            if (stock < 0) {
                error = error + "\r\n\r\nInventory amount cannot be negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for inventory; must be a non-negative integer.";
        }
        
        try {
            max = Integer.parseInt(addPartMaxTxt.getText());
            if (max < 1) {
                error = error + "\r\n\r\nMaximum amount cannot be 0 or negative.";
            }
        }
        catch (Exception NumberFormatException) {
            error = error + "\r\n\r\nInvalid or no value given for maximum amount; must be a positive integer.";
        }
        
        try {
            min = Integer.parseInt(addPartMinTxt.getText());
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
        
        
        
        
        if (inHouseRadioYes.isSelected()) {
                try {
                    machineId = Integer.parseInt(addPartMachineOrCompanyTxt.getText());
                    if (machineId < 1) {
                        error = error + "\r\n\r\nMachine ID must be a positive integer.";
                    }
                }
                catch (Exception NumberFormatException) {
                    error = error + "\r\n\r\nInvalid or no value given for In-House part's machine ID; must be a positive integer.";
                }
        }
        else {
            companyName = companyName + addPartMachineOrCompanyTxt.getText();
            if (companyName.isEmpty()) {
                error = error + "\r\n\r\nNo Company Name was provided for the outsourced part.";
            }
        }
        
        
        if (!(error.isEmpty())) {
            message = message + error;
            alert.setTitle("Invalid Input");
            alert.setContentText(message);
            alert.showAndWait();
        }
        else {
            Alert alertSave = new Alert(Alert.AlertType.CONFIRMATION, "Save this part and return to the Main Menu?");
        
            Optional<ButtonType> result = alertSave.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (inHouseRadioYes.isSelected()) {
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else {
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
    }
    
    
    @FXML
    private Button addPartCancelBtn;

    @FXML
    void addPartCancelAction(ActionEvent event) throws IOException {
        
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
