package restaurant.models;

public class user_type {
    private String user_type;
    private int ID;
    
    public void set_user_type(String user_type){
        this.user_type = user_type;
    }
    public String get_user_type(){
        return user_type;
    }
    public void set_ID(int ID){
        this.ID = ID;
    }
    public int get_ID(){
        return ID;
    }
}
