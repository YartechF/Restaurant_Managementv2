package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurant.db.database;

public class product_categories_model extends database {
    public void create_product_categories(int productID,int categoryID){
        try {
            String sql = "INSERT INTO tbl_product_categories(productID, categoryID) VALUES (?,?)";
            PreparedStatement pst = getConnection().prepareStatement(sql);
            pst.setInt(1, productID);
            pst.setInt(2, categoryID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<category> get_product_category_by_productID(int productID){
        ObservableList<category> list = FXCollections.observableArrayList();
        String sql = "SELECT c.name as category_name, c.ID as categoryID FROM tbl_product_categories tpc INNER JOIN tbl_category as c on tpc.categoryID = c.ID WHERE productID = ?";
        try {
            PreparedStatement pst = getConnection().prepareStatement(sql);
            pst.setInt(1, productID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                category c = new category();
                c.setName(rs.getString("category_name"));
                c.setId(rs.getInt("categoryID"));
                list.add(c);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
}
