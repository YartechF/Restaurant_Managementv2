package restaurant.models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class product_multiple_category extends product {
    private ObservableList<Integer> categoriesID = FXCollections.observableArrayList();
    private ObservableList<Integer> storesID = FXCollections.observableArrayList();
    public void set_categories(int categoryID){
        categoriesID.add(categoryID);
    }
    public ObservableList<Integer> get_categories(){
        return categoriesID;
    }
    public void set_stores(int storeID){
        storesID.add(storeID);
    }
    public ObservableList<Integer> get_stores(){
        return storesID;
    }
}
