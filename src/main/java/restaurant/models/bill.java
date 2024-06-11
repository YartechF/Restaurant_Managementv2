package restaurant.models;

import javafx.beans.property.SimpleStringProperty;

public class bill {
    
    private SimpleStringProperty ID;
    private SimpleStringProperty date;
    private SimpleStringProperty total_product;
    private SimpleStringProperty total_amount;
    private SimpleStringProperty paid_status;
    private SimpleStringProperty table;
    private SimpleStringProperty order_type;
    private int tableID;

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }
    public int getTableID() {
        return this.tableID;
    }
    public String getID() {
        return ID.get();
    }
    public void setID(String ID) {
        if (this.ID == null) {
            this.ID = new SimpleStringProperty();
        }
        this.ID.set(ID);
    }
    public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        if (this.date == null) {
            this.date = new SimpleStringProperty();
        }
        this.date.set(date);
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
    public String getTotal_amount() {
        return total_amount.get();
    }
    public void setTotal_amount(String total_amount) {
        if (this.total_amount == null) {
            this.total_amount = new SimpleStringProperty();
        }
        this.total_amount.set(total_amount);
    }
    public String getPaid_status() {
        return paid_status.get();
    }
    public void setPaid_status(boolean Ispaid) {
        if (this.paid_status == null) {
            this.paid_status = new SimpleStringProperty();
        }
        if(Ispaid == true){
            this.paid_status.set("Paid");
        }else{
            this.paid_status.set("Not Paid");
        }

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
    public String getOrder_type() {
        return order_type.get();
    }
    public void set_order_type(boolean Istakeout){
        if (this.order_type == null) {
            this.order_type = new SimpleStringProperty();
        }
        if(Istakeout == true){
            this.order_type.set("Takeout");
        }else{
            this.order_type.set("Dine-in");
        }
    }

}
//create getters and setters



