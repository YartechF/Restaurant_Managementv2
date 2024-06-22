package restaurant.models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class product_multiple_category extends product {
    private ObservableList<Integer> categoriesID = FXCollections.observableArrayList();
    private ObservableList<Integer> storesID = FXCollections.observableArrayList();

    private ObservableList<category> categories = FXCollections.observableArrayList();
    private ObservableList<store_table_cell> stores = FXCollections.observableArrayList();

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
    public void set_categories(ObservableList<category> categories){
        this.categories = categories;
    }
    public ObservableList<category> get_categories_obj(){
        return categories;
    }
    public void set_stores(ObservableList<store_table_cell> stores){
        this.stores = stores;
    }
    public ObservableList<store_table_cell> get_stores_obj(){
        return stores;
    }

}
