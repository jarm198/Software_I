/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class definition for Outsourced parts.
 *
 * Outsourced is a subclass of abstract {@link Part} class.
 * @author James Armstrong
 */
public class Outsourced extends Part {
    
    private String companyName;

    /**
     * Constructor for new Outsourced parts
     * 
     * @param id ID number generated and passed to constructor
     * @param name Provided name of Outsourced part
     * @param price Provided price of Outsourced part
     * @param stock Provided inventory level of Outsourced part
     * @param min Minimum amount allowed in inventory
     * @param max Maximum amount allowed in inventory
     * @param companyName Name of providing company for outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for companyName
     * 
     * @param companyName sets the company name of associated part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * Getter for companyName
     * 
     * @return returns the company name of associated part
     */
    public String getCompanyName() {
        return companyName;
    }

    
    
}
