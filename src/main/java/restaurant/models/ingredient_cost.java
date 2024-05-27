package restaurant.models;

public class ingredient_cost {
    private int ID;
    private double quantity;
    private int productID;
    private int ingredientID;

    // create gettings and setters
    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return this.ID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

}
