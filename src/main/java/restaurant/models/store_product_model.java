package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public void delete_store_product(int storeID, int productID){
        String sql = "DELETE FROM store_product WHERE productID =? AND storeID =?";
        try {
            PreparedStatement pst = getConnection().prepareStatement(sql);
            pst.setInt(1, productID);
            pst.setInt(2, storeID);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<store_table_cell> get_store_products(int productID){
        ObservableList<store_table_cell> list = FXCollections.observableArrayList();
        String sql = "SELECT s.name as store_name ,s.ID as store_ID FROM store_product as sp inner join tbl_store as s on sp.storeID = s.ID WHERE sp.productID = ?";
        try {
            PreparedStatement pst = getConnection().prepareStatement(sql);
            pst.setInt(1, productID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                store_table_cell s = new store_table_cell();
                s.setID(rs.getInt("store_ID"));
                s.setName(rs.getString("store_name"));
                list.add(s);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
}
