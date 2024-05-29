package restaurant.models;


public class order {
    private Invoice invoice;
    private product Product;
    private int quantity;

    

    public order() {
        this.quantity = 1;
    }

    public void setinvoiceID(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getinvoiceID() {
        return this.invoice;
    }

    public void setproductID(product Product) {
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
