package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import restaurant.db.database;

public class company_model extends database {
    private PreparedStatement ps;

    public void create_company(String name) {
        String sql = "INSERT INTO `tbl_company`(`name`) VALUES (?)";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // update
    public void update_company(int id, String name) {
        String sql = "UPDATE `tbl_company` SET `name`=? WHERE `id`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
