package restaurant.models;

import javafx.beans.property.SimpleStringProperty;

public class product_inventory {
    private SimpleStringProperty Product_Name;
    private SimpleStringProperty Description;
    private SimpleStringProperty Stock;
    private SimpleStringProperty Is_ingredient;
    private int ID;
    //create getters and setters for each variable
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getProduct_Name() {
        return Product_Name.get();
    }
    public void setProduct_Name(String Product_Name) {
        if (this.Product_Name == null) {
            this.Product_Name = new SimpleStringProperty();
        }
        this.Product_Name.set(Product_Name);
    }
    public String getDescription() {
        return Description.get();
    }
    public void setDescription(String Description) {
        if (this.Description == null) {
            this.Description = new SimpleStringProperty();
        }
        this.Description.set(Description);
    }
    public String getStock() {
        return Stock.get();
    }
    public void setStock(String Stock) {
        if (this.Stock == null) {
            this.Stock = new SimpleStringProperty();
        }
        this.Stock.set(Stock);
    }

}
