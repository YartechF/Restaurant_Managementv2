package restaurant.models;

public class order {
    private int invoiceID;
    private int productID;
    private int quantity;

    public order() {
        this.quantity = 1;
    }

    public void setinvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getinvoiceID() {
        return this.invoiceID;
    }

    public void setproductID(int productID) {
        this.productID = productID;
    }

    public int getproductID() {
        return this.productID;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public int getquantity() {
        return this.quantity;
    }
}
