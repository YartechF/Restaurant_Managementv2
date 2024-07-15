package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import restaurant.db.database;

public class sales_chart_model extends database{
    private ObservableList<XYChart.Data<String, Number>> getOrdersData() throws SQLException {
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
    
        // Create an instance of your database model class
        String sql = "SELECT MONTHNAME(i.Date) AS month, SUM(p.price * o.quantity) AS total_sales, AVG(i.discount) AS discount " +
                     "FROM tbl_orders o " +
                     "JOIN tbl_product p ON o.productID = p.ID " +
                     "JOIN tbl_invoice i ON o.invoiceID = i.ID " +
                     "GROUP BY MONTH(i.Date)";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
    
        try {
            double totalYearlySales = 0.0;
            while (rs.next()) {
                String month = rs.getString("month");
                double totalSales = rs.getDouble("total_sales");
                double discount = rs.getDouble("discount");
                double salesAfterDiscount = totalSales - (totalSales * discount / 100);
                totalYearlySales += salesAfterDiscount;
    
                data.add(new XYChart.Data<>(month, salesAfterDiscount));
            }
    
            // Add the total yearly sales as a separate data point
            data.add(new XYChart.Data<>("Total", totalYearlySales));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return data;

        
    }
    public ObservableList<XYChart.Data<String, Number>> getdata() throws SQLException{
        return this.getOrdersData();
    }
}
