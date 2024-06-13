package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurant.db.database;

public class order_manager_invoice_model extends invoice_model {
    PreparedStatement ps;
    private ObservableList<order> orders = FXCollections.observableArrayList();

    public ObservableList<order> get_orders(){
        return orders;
    }
    public void mark_as_done(int invoiceID) throws SQLException {
        String sql = "Update tbl_orders set Isdone = 1 where invoiceID =?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void retrieve_orders_on_order_manager(int invoiceID) throws SQLException {
        String sql = "select tbl_product.name as product_name, tbl_product.picture as img, quantity from tbl_orders inner join tbl_product on tbl_orders.productID = tbl_product.ID where invoiceID = ? and Isdone = 0";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                order o = new order();
                o.setproduct(new product());
                o.getproduct().setName(rs.getString("product_name"));
                o.getproduct().setPicture(rs.getString("img"));
                o.setquantity(rs.getInt("quantity"));
                orders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
