package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class orders_update_model extends orders_model {
    private PreparedStatement ps;
    private double subtotal;
    // parent class is the recent_order

    ObservableList<order> recent_orders = FXCollections.observableArrayList();//recent order
    ObservableList<order> current_orders = FXCollections.observableArrayList();//current order
    
    @Override
    public boolean order_exist(product Product) {
        for (order o : current_orders) {
            if (o.getproduct().getID() == Product.getID()) {
                return true;
            }
        }
        return false;
    }
    public ObservableList<order> get_recent_order(){
        return recent_orders;
    }
    public void add_recent_order(order Order) {
        recent_orders.add(Order);
    }
    public void add_current_order(order Order){
        current_orders.add(Order);
    }
    @Override
    public double get_sub_total(){
        this.subtotal = 0;
        for(order o : recent_orders){
            this.subtotal += o.getproduct().getPrice() * o.getquantity();
        }
        for(order o : current_orders){
            this.subtotal += o.getproduct().getPrice() * o.getquantity();
        }
        return this.subtotal;
    }

    public ResultSet GetOrderByInvoiceID(int invoiceID) {
        String sql = "SELECT tbl_product.ID as productID, tbl_product.name as product_name, tbl_product.picture as product_img, tbl_product.price as product_price, tbl_orders.quantity, tbl_orders.Isdone FROM tbl_orders inner join tbl_product on tbl_orders.productID = tbl_product.ID WHERE `invoiceID` =?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            setOrder(invoiceID);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    public void setOrder(int invoiceID) {
        String sql = "SELECT tbl_product.ID as productID, tbl_product.name as product_name, tbl_product.picture as product_img, tbl_product.price as product_price, tbl_orders.quantity, tbl_orders.Isdone FROM tbl_orders inner join tbl_product on tbl_orders.productID = tbl_product.ID WHERE `invoiceID` =?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                order Order = new order();
                product Product = new product();
                Order.setquantity(rs.getInt("quantity"));
                Product.setID(rs.getInt("productID"));
                Product.setName(rs.getString("product_name"));
                Product.setPrice(rs.getDouble("product_price"));
                Order.setproduct(Product);
                this.recent_orders.add(Order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
