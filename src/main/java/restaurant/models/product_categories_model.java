package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
