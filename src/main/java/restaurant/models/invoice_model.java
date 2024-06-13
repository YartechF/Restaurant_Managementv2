package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import restaurant.db.database;

public class invoice_model extends database implements Initializable{
    private PreparedStatement ps;

    private ObservableList<table_invoice> TableInvoice = FXCollections.observableArrayList();

    public ObservableList<table_invoice> getTableInvoice() {
        return TableInvoice;
    }

    public void retreive_all_invoice_by_storeID(int storeID){// use for order_manager_controller
        TableInvoice.clear();
        String invoice_sql = "select tbl_invoice.ID, tbl_table.name as tablename,tbl_invoice.istakeout, tbl_invoice.Date from tbl_invoice INNER JOIN tbl_table on tbl_invoice.tableID = tbl_table.ID where tbl_invoice.storeID =? and ispaid= 0 ORDER BY Date";
        String orders_sql2 = "select sum(quantity) as total_products ,sum(!Isdone) as total_pending_order from tbl_orders where invoiceID = ? and !Isdone";
        try {
            ps = getConnection().prepareStatement(invoice_sql);
            ps.setInt(1, storeID);
            ResultSet invoice_rs = ps.executeQuery();


            while(invoice_rs.next()){
                table_invoice t = new table_invoice();
                t.setID(invoice_rs.getString("ID"));
                t.setDate(invoice_rs.getString("Date"));
                t.setTable(invoice_rs.getString("tablename"));
                PreparedStatement ps2 = getConnection().prepareStatement(orders_sql2);
                ps2.setInt(1,invoice_rs.getInt("ID"));
                ResultSet orders_rs = ps2.executeQuery();
                if (orders_rs.next()) {
                    t.setTotal_product(orders_rs.getString("total_products"));
                    t.setPending_orders(orders_rs.getString("total_pending_order"));
                } else {
                    t.setTotal_product("0");
                    t.setPending_orders("0");
                }
                TableInvoice.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        String sql = "DELETE FROM `tbl_invoice` WHERE `ID`=?";
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

    public void mark_as_paid(int invoiceID){
        String sql = "UPDATE `tbl_invoice` SET `ispaid`=1 WHERE `ID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
