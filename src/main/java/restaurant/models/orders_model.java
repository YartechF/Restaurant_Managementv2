package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import restaurant.db.database;
import restaurant.models.order;
import restaurant.models.Invoice;

public class orders_model extends database {
    private PreparedStatement ps;

    private ArrayList<order> orders = new ArrayList<>();
    private double subtatal = 0;

    public void add_order(order Order) {
        orders.add(Order);
    }
    public void delete_order(order Order){
        orders.remove(Order);
    }
    //check order exist
    public boolean order_exist(product Product) {
        for (order o : orders) {
            if (o.getproduct().getID() == Product.getID()) {
                return true;
            }
        }
        return false;
    }
    public double get_sub_total(){
        return subtatal;
    }
    public void updateSubtotal(){
        subtatal = 0;
        for(order o : orders){
            double product_subtotal = o.getproduct().getPrice() * o.getquantity();
            subtatal += product_subtotal;
        }
    }

    
}
