package restaurant.models;

public class user_person extends user {

    private String username;
    private String password;
    private String name;
    private String address;
    private String contact;
    private String usertype;


    //create getters and setters
    public void setname(String name){
        this.name = name;
    }
    public String getname(){
        return name;
    }
    public void setaddress(String address){
        this.address = address;
    }
    public String getaddress(){
        return address;
    }
    public void setcontact(String contact){
        this.contact = contact;
    }
    public String getcontact(){
        return contact;
    }
    public void setusertype(String usertype){
        this.usertype = usertype;
    }
    public String get_usertype(){
        return usertype;
    }
    public void setusername(String username){
        this.username = username;
    }
    public String getusername(){
        return username;
    }
    public void setpassword(String password){
        this.password =  password;
    }
    public String getpassword(){
        return password;
    }
}
