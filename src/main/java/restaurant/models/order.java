package restaurant.models;


public class order {
    private Invoice invoice;
    private product Product;
    private int quantity;
    private boolean isdone;

    
    public void setIsdone(boolean isdone) {
        this.isdone = isdone;
    }

    public boolean getIsdone() {
        return this.isdone;
    }

    public order() {
        this.quantity = 1;
    }

    public void setinvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getinvoice() {
        return this.invoice;
    }

    public void setproduct(product Product) {
        this.Product = Product;
    }

    public product getproduct() {
        return this.Product;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public int getquantity() {
        return this.quantity;
    }
}
