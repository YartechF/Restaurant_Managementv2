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
