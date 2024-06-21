package restaurant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class update_product_stock_on_inventory_controller {
    private String cost_type;
    @FXML
    private Label product_name;

    @FXML
    private Label recent_stock;

    @FXML
    private TextField updated_stock_tf;

    public void set_product_name(String product_name){
        this.product_name.setText("Product Name: "+product_name);
    }
    public void set_stock(String stock){
        this.recent_stock.setText("Current Stock: "+stock);
    }
    public double get_updated_stock_tf_value(){
        return Double.parseDouble(updated_stock_tf.getText());
    }
}