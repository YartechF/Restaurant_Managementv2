package restaurant.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import restaurant.models.Invoice;
import restaurant.models.order;
import restaurant.models.product;
import restaurant.models.ingredient_cost;
import restaurant.models.ingredient_cost_model;

public class order_controller {

    private order Order;
    private ingredient_cost IngredientCost;
    private ingredient_cost_model IngredientCostModel;

    @FXML
    private ImageView delete_btn;

    @FXML
    private ImageView minus;

    @FXML
    private ImageView plus;

    @FXML
    private ImageView product_img;

    @FXML
    private Label product_name;

    @FXML
    private Label product_price;

    @FXML
    private Label qty;

    public void setdata(order Order) {
        this.Order = Order;
        System.out.println("this is the orders of invoice" + this.Order.getinvoiceID() + this.Order.getproductID());
    }

    @FXML
    void delete_btn(MouseEvent event) {

    }

    @FXML
    void minus_btn(MouseEvent event) {
    }

    @FXML
    void plus_btn(MouseEvent event) {

    }

}
