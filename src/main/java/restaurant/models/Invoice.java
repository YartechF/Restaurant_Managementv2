package restaurant.models;

public class Invoice {
    private int ID;
    private String costumer_name;
    private boolean istakeout;
    private int tableID;
    private double discount;
    private boolean ispaid;
    private int storeID;
    private table Table;
    private double total_amount;

    public Invoice() {
        istakeout = false;
        discount = 0;
        ispaid = false;
    }

    // create getters and setters
    public int getID() {
        return ID;
    }
    public void setStoreID(int storeID){
        this.storeID = storeID;
    }
    
    public int getStoreID(){
        return this.storeID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCostumer_name() {
        return costumer_name;
    }

    public void setCostumer_name(String costumer_name) {
        this.costumer_name = costumer_name;
    }

    public boolean get_istakeout() {
        return istakeout;
    }

    public void setIstakeout(boolean istakeout) {
        this.istakeout = istakeout;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean get_ispaid() {
        return ispaid;
    }

    public void setIspaid(boolean ispaid) {
        this.ispaid = ispaid;
    }
    public void setTable(table Table){
        this.Table = Table;
    }
    public table getTable(){
        return this.Table;
    }
    public void Set_total_amount(double total_amount){
        this.total_amount =  total_amount;
    }
    public double get_total_amount(){
        return this.total_amount;
    }


}
