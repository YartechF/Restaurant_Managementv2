package restaurant.models;

public class user {
    private int storeID;
    private int usertypeID;
    private int personID;
    private String storename;
    private String usertype;

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getUsertypeID() {
        return usertypeID;
    }

    public void setUsertypeID(int usertypeID) {
        this.usertypeID = usertypeID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

}
