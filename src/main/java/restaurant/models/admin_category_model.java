package restaurant.models;

import java.sql.ResultSet;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class admin_category_model extends category_model {
    
    public ObservableList<category> get_category_for_admin(){
        ObservableList<category> category_list = FXCollections.observableArrayList();
        ResultSet rs = super.retrieve_all_category();
        try{
            while(rs.next()){
                category Category = new category();
                Category.setId(rs.getInt("ID"));
                Category.setName(rs.getString("name"));
                category_list.add(Category);
            }
        return category_list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
