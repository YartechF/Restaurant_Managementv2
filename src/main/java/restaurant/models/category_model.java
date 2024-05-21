package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import restaurant.db.database;

public class category_model extends database {
    private PreparedStatement ps;

    public void create_category(String name) {
        try {
            String sql = "INSERT INTO tbl_category (name) VALUES (?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update_category(int id, String name) {
        try {
            // the name of the is tbl_category 1 parameter fields ID and food
            String sql = "UPDATE tbl_category SET name =? WHERE id =?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete_category(int id) {
        // the name of the is tbl_category 1 parameter fields ID and food
        try {
            String sql = "DELETE FROM tbl_category WHERE id =?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // create fuction retrieve all category return type resultset
    public ResultSet retrieve_all_category() {
        try {
            String sql = "SELECT * FROM tbl_category";
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            return null;
        }

    }

    // create fuction retrieve specific category return type resultset
    public ResultSet retrieve_specific_category(int id) {
        try {
            String sql = "SELECT * FROM tbl_category WHERE id =?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
