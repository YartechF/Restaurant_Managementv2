package restaurant.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product_table_cell extends product {
    ObservableList<String> categories = FXCollections.observableArrayList();
    public String getStringPrice(){
        return "₱"+String.valueOf(super.getPrice());
    }
    public void setcategories(String category){
        categories.add(category);
    }
    public void concat_categories(){

        for(String c:categories){
            super.setCategory(super.getCategory()+"  "+c);
        }

    }

}
