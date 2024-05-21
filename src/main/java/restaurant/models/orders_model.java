package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import restaurant.db.database;
import restaurant.models.order;
import restaurant.models.Invoice;

public class orders_model extends database {
    private PreparedStatement ps;

    public void create_orders(order Order) throws SQLException {
        String sql = "INSERT INTO `tbl_orders`(`invoiceID`, `productID`, `quantity`) VALUES (?,?,?)";
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, Order.getinvoiceID());
        ps.setInt(2, Order.getproductID());
        ps.setInt(3, Order.getquantity());
        ps.executeUpdate();
    }

    public ResultSet retrieve_order(Invoice invoice) {
        String sql = "SELECT * from tbl_orders where invoiceID = ?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
