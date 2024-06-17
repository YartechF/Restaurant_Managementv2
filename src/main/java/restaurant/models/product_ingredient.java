package restaurant.models;

public class product_ingredient extends product{
    private String cost_type;
    
    public String get_cost_type(){
        return cost_type;
    }
    
    public void setCostType(String cost_type){
        this.cost_type = cost_type;
    }
}
