package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import restaurant.db.database;

public class invoice_model extends database implements Initializable{
    private PreparedStatement ps;


    public double getDiscount(int invoiceID) throws SQLException {
        String sql = "select discount from tbl_invoice where ID =?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("discount");
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().close();
            ps.close();
            return 0;
        }
    }
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
    public void setDiscount(double discount,int invoiceID) throws SQLException{
        String sql = "update tbl_invoice set discount = ? where ID =?";
        ps = getConnection().prepareStatement(sql);
        ps.setDouble(1, discount);
        ps.setInt(2, invoiceID);
        ps.executeUpdate();
        getConnection().close();
        ps.close();
    }
    public int create_invoice(Invoice invoice)
            throws SQLException {
        String sql = "INSERT INTO `tbl_invoice`(`costumer_name`, `istakeout`, `tableID`, `discount`, `ispaid`,`storeID`,`Date`) VALUES (?,?,?,?,?,?,Now())";
        ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, invoice.getCostumer_name());
        ps.setBoolean(2, invoice.get_istakeout());
        ps.setInt(3, invoice.getTableID());
        ps.setDouble(4, invoice.getDiscount());
        ps.setBoolean(5, invoice.get_ispaid());
        ps.setInt(6, invoice.getStoreID());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
