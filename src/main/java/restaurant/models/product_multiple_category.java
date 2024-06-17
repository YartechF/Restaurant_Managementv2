package restaurant.models;

import java.util.ArrayList;

public class product_multiple_category extends product {
    private ArrayList<Integer> categoriesID = new ArrayList<>();

    public void set_categories(int categoryID){
        categoriesID.add(categoryID);
    }
    public ArrayList<Integer> get_categories(){
        return categoriesID;
    }
}
