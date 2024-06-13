package restaurant.models;


import javafx.beans.property.SimpleStringProperty;

public class table_invoice{
    private SimpleStringProperty ID;
    private SimpleStringProperty table;
    private SimpleStringProperty total_product;
    private SimpleStringProperty pending_orders;
    private String date;
    // getters and setters
    public String getID() {
        return ID.get();
    }

    public void setID(String Product_Name) {
        if (this.ID == null) {
            this.ID = new SimpleStringProperty();
        }
        this.ID.set(Product_Name);
    }

    public String getTable() {
        return table.get();
    }

    public void setTable(String table) {
        if (this.table == null) {
            this.table = new SimpleStringProperty();
        }
        this.table.set(table);
    }
    
    public String getTotal_product() {
        return total_product.get();
    }

    public void setTotal_product(String total_product) {
        if (this.total_product == null) {
            this.total_product = new SimpleStringProperty();
        }
        this.total_product.set(total_product);
    }

    public String getPending_orders() {
        return pending_orders.get();
    }

    public void setPending_orders(String pending_orders) {
        if (this.pending_orders == null) {
            this.pending_orders = new SimpleStringProperty();
        }
        this.pending_orders.set(pending_orders);
    }

    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }


}