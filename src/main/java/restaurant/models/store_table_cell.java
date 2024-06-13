package restaurant.models;

public class store_table_cell {
    private int ID;
    private String name;
    private String description;

    // Getters and setters
    public String getName() {
        return name;
    }
    public int getID(){
        return this.ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
