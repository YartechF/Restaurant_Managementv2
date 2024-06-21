package restaurant.models;

public class Admin_product_ingredient {
    private int id;
    private String product_name;
    private String required_quantity;
    private String cost_type;


    public void set_cost_type(String cost_type){
        this.cost_type = cost_type;
    }
    public String get_cost_type(){
        return cost_type;
    }
    public void set_id(int id){
        this.id = id;
    }
    public int get_id(){
        return id;
    }
    public void set_product_name(String product_name){
        this.product_name = product_name;
    }  
    public String get_product_name(){
        return product_name;
    }
    public void set_required_quantity(String required_quantity){
        this.required_quantity = required_quantity;
    }
    public String get_required_quantity(){
        return required_quantity+" "+cost_type;
    }
}
