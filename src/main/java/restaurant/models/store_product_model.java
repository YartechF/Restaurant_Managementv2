package restaurant.models;

import java.sql.PreparedStatement;

import restaurant.db.database;

public class store_product_model extends database {
    public void create_store_product(int storeID, int productID){
        String sql = "INSERT INTO store_product(productID, storeID)values(?,?)";
        try {
            PreparedStatement pst = getConnection().prepareStatement(sql);
            pst.setInt(1, productID);
            pst.setInt(2, storeID);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
