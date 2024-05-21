package restaurant.models;

public class Invoice {
    private int ID;
    private String costumer_name;
    private boolean istakeout;
    private int tableID;
    private double discount;
    private boolean ispaid;

    public Invoice() {
        istakeout = false;
        discount = 0;
        ispaid = false;
    }

    // create getters and setters
    public int getID() {
        return ID;
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

}
