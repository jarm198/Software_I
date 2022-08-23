/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class definition for InHouse parts.
 * InHouse is a subclass of abstract {@link Part} class.
 * @author James Armstrong
 */
public class InHouse extends Part {
    
    private int machineId;

    /**
     * Constructor for new InHouse parts
     * 
     * @param id ID number generated and passed to constructor
     * @param name Provided name of InHouse part
     * @param price Provided price of InHouse part
     * @param stock Provided inventory level of InHouse part
     * @param min Minimum amount allowed in inventory
     * @param max Maximum amount allowed in inventory
     * @param machineId ID of machine used to produce InHouse part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter for machineId
     * @param machineId sets the machine ID of the associated part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    /**
     * Getter for machineId
     * @return returns machine ID of the associated part
     */
    public int getMachineId() {
        return machineId;
    }

    
    
}
