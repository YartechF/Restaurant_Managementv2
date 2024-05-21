package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import restaurant.db.database;

public class product_model extends database {
    private PreparedStatement ps;

    public void create_product(String name, double price, int categoryID, String picture, String type) {
        try {
            String sql = "insert into tbl_product(name,price,categoryID,picture,type)values(?,?,?,?,?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, categoryID);
            ps.setString(4, picture);
            ps.setString(5, type);
            ps.executeUpdate();
            ps.close();
            getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // create update
    public void update_product(int id, String name, double price, int categoryID, String picture, String type) {
        try {
            String sql = "update tbl_product set name=?,price=?,categoryID=?,picture=? type=?where id=?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, categoryID);
            ps.setString(4, picture);
            ps.setString(5, type);
            ps.setInt(6, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // create retrieve all
    public ResultSet retrieve_all_product() {
        try {
            String sql = "select * from tbl_product";
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // create retrieve one
    public ResultSet retrieve_one_product(int id) {
        try {
            String sql = "select * from tbl_product where ID=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // add delete
    public void delete_product(int id) {
        try {
            String sql = "delete from tbl_product where ID=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet retrieve_product(int categoryID) {
        String sql = "SELECT * tbl_product where categoryID = ?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
