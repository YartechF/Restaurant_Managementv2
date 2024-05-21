package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import restaurant.db.database;

public class invoice_model extends database {
    private PreparedStatement ps;

    public ResultSet retrieve_orders(int invoiceID) throws SQLException {
        String sql = "select productID quantity from tbl_orders where invoiceID = ?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().close();
            ps.close();
            return null;
        }

    }

    public int create_invoice(Invoice invoice)
            throws SQLException {
        String sql = "INSERT INTO `tbl_invoice`(`costumer_name`, `istakeout`, `tableID`, `discount`, `ispaid`) VALUES (?,?,?,?,?)";
        ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, invoice.getCostumer_name());
        ps.setBoolean(2, invoice.get_istakeout());
        ps.setInt(3, invoice.getTableID());
        ps.setDouble(4, invoice.getDiscount());
        ps.setBoolean(5, invoice.get_ispaid());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        ps.close();
        getConnection().close();
        return 0;
    }

    public void invoice_set_paid(int invoiceID, boolean ispaid) {
        String sql = "UPDATE `tbl_invoice` SET `ispaid`=? WHERE `invoiceID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setBoolean(1, ispaid);
            ps.setInt(2, invoiceID);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void invoice_set_discount(int invoiceID, double discount) {
        String sql = "UPDATE `tbl_invoice` SET `discount`=? WHERE `invoiceID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setDouble(1, discount);
            ps.setInt(2, invoiceID);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete
    public void delete_invoice(int invoiceID) {
        String sql = "DELETE FROM `tbl_invoice` WHERE `invoiceID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
