package restaurant.models;

public class AdminProductInventory {
    private int ID;
    private String productName;
    private String description;
    private String costType;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setCostType(String costType) {
        this.costType = costType;
    }
    public String getCostType() {
        return costType;
    }
}
