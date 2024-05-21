package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import restaurant.db.database;

public class sales_model extends database {
    private PreparedStatement ps;

    public void create_sales(String product_name, double price, int storeID)
            throws SQLException {
        try {
            String sql = "insert into tbl_sales(product_name,price,transanction_date,storeID)values(?,?,Now(),?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, product_name);
            ps.setDouble(2, price);
            ps.setInt(3, storeID);
            ps.executeUpdate();
            ps = null;
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
            getConnection().close();
        }

    }

    public ResultSet get_sales() {
        try {
            String sql = "select * from tbl_sales";
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
