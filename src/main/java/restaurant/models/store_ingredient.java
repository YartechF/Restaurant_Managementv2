package restaurant.models;

public class store_ingredient {
    private int ID;
    private int ingredientID;
    private double stock;
    private int storeID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }

    public int get_ingredientID() {
        return ingredientID;
    }

    public void set_ingredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void set_stock(double stock) {
        this.stock = stock;
    }

    public double get_stock() {
        return this.stock;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getStoreID() {
        return this.storeID;
    }
}
