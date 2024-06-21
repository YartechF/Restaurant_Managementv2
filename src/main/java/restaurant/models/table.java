package restaurant.models;

import restaurant.controllers.reports_controller;

public class table {
    private int ID;
    private String name;
    private boolean isActive;
    private boolean isAvailable;
    private int StoreID;
    private int capacity;
    private String store_name;

    public String get_storename(){
        return store_name;
    }
    public void set_store_name(String store_name){
        this.store_name = store_name;
    }
    // create getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public String GetStringIsAvailable(){
        if(!isAvailable){
            return "Unavailable";
        }
        return "Available";
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }

    public int getCapacity() {
        return capacity;
    }
    public String getStringCapacity(){
        return String.valueOf(this.capacity);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
