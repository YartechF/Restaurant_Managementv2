package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurant.db.database;

public class bill_data extends database{
    private ObservableList<bill> bill_data = FXCollections.observableArrayList();
    private PreparedStatement ps;
    public void setData(bill Bill){
        this.bill_data.add(Bill);
    }
    public void set_invoice_by_storeID(int storeID){
        String sql = "SELECT tbl_invoice.discount, tbl_invoice.tableID, tbl_invoice.ID, tbl_invoice.Date, tbl_invoice.costumer_name, tbl_invoice.istakeout, tbl_invoice.ispaid, tbl_table.ID AS table_ID, tbl_table.name AS table_name FROM tbl_invoice INNER JOIN tbl_table ON tbl_invoice.tableID = tbl_table.ID WHERE tbl_invoice.storeID = ? ORDER BY Date DESC;";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, storeID);
            ResultSet invoice_rs = ps.executeQuery();
            while(invoice_rs.next()){
                String sql2 = "select sum(quantity) as total_product,sum(tbl_product.price * tbl_orders.quantity) as total_bill from tbl_orders inner join tbl_product on tbl_orders.productID = tbl_product.ID where invoiceID =?";
                try {
                    ps = getConnection().prepareStatement(sql2);
                    ps.setInt(1, invoice_rs.getInt("ID"));
                    ResultSet orders_rs = ps.executeQuery();
                    while(orders_rs.next()){
                        //creating a bill object the bill ID is the invoice ID
                        bill Bill = new bill();
                        Bill.setID(invoice_rs.getString("ID"));
                        Bill.setDate(invoice_rs.getString("Date"));
                        Bill.setTotal_product(orders_rs.getString("total_product"));
                        Bill.setTotal_amount(String.valueOf(orders_rs.getDouble("total_bill") - invoice_rs.getDouble("discount")));
                        Bill.setPaid_status(invoice_rs.getBoolean("ispaid"));
                        Bill.setTable(invoice_rs.getString("table_name"));
                        Bill.setTableID(invoice_rs.getInt("tableID"));
                        Bill.set_order_type(invoice_rs.getBoolean("istakeout"));
                        this.bill_data.add(Bill);
                    }

                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<bill> getBill_data() {
        return bill_data;
    }


}
