package restaurant.models;
 
public class Product_table_cell extends product {
    
    public String getStringPrice(){
        return String.valueOf(super.getPrice());
    }

}
