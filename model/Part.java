/**
* Supplied class Part.java 
 */

package model;

/**
 * Class definition for abstract Part.
 * <br>Two possible subclasses are {@link InHouse} and {@link Outsourced}.
 * <br>RUNTIME ERROR - When a part is created on the Add Part Menu, it is instantiated
 * as either an InHouse part or an Outsourced part. When trying to configure the Modify Part Menu,
 * <br>I tried casting the part from one subclass to the other, which caused a runtime error.
 * <br>I solved this by instantiating a new "temporary" part when the save button is first clicked.
 * <br>The temporary part takes on all of the values currently provided in the controller's fields.
 * <br>Assuming no input errors are present, the "temporary" part replaces the original part that was passed to the ModifyPartController.
 * @author James Armstrong
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;    

    /**
     *
     * @param id ID number generated and passed to constructor
     * @param name Provided name for part
     * @param price Provided price for part
     * @param stock Current inventory of part
     * @param min Minimum allowable inventory level of part
     * @param max Maximum allowable inventory level of part
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for id
     * @return part's id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id sets part's id number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for name
     * @return part's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name sets part's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for price
     * @return part's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for price
     * @param price sets part's current price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Getter for stock
     * @return part's current inventory level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for stock
     * @param stock sets part's current inventory level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for min
     * @return part's lowest allowable inventory level
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for min
     * @param min sets part's lowest allowable inventory level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for max
     * @return part's highest allowable inventory level
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for max
     * @param max sets part's highest allowable inventory level
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}
