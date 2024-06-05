package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import restaurant.models.product;
import restaurant.models.product_model;
import restaurant.models.Invoice;
import restaurant.models.order;
import restaurant.models.orders_model;

public class update_order_controller implements Initializable{
    private product_model pos_product_model;
    private orders_model OrderModel;
    private order Order;
    private int invoiceID;

    @FXML
    private ChoiceBox<?> category_choicebox;

    @FXML
    private TextField discount;

    @FXML
    private HBox no_order_hbox;

    @FXML
    private GridPane order_grid_pane;

    @FXML
    private ChoiceBox<?> paid_status;

    @FXML
    private GridPane product_grid;

    @FXML
    private ScrollPane product_scroll;

    @FXML
    private Label subtotal;

    @FXML
    private Label total;

    public void set_invoiceID(int invoiceID){
        this.invoiceID = invoiceID;
        System.out.println(this.invoiceID);
        load_orders();
    }
    void load_orders(){
        order_grid_pane.getChildren().clear();
        ResultSet rs = this.OrderModel.GetOrderByInvoiceID(invoiceID);
        int order_col = 0;
        int order_row = 0;
        try {
            while(rs.next()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/restaurant/views/order_view.fxml"));
                AnchorPane order_pane = fxmlLoader.load();
                order_controller OrderController = fxmlLoader.getController();

                order Order = new order();
                product Product = new product();
                Product.setID(rs.getInt("productID"));
                Product.setName(rs.getString("product_name"));
                Product.setPrice(rs.getDouble("product_price"));
                Product.setPicture(rs.getString("product_img"));
                Product.setPrice(rs.getDouble("product_price"));
                Order.setquantity(rs.getInt("quantity"));
                Order.setproduct(Product);
                OrderController.setdata(Order);
                OrderModel.AddRecentOrder(Order);
                order_pane.setDisable(true);
                order_grid_pane.add(order_pane, order_col, order_row++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update_order_btn_e(MouseEvent event) {
        
    }
    void product_load() throws IOException{
        ObservableList<product> Products = this.pos_product_model.get_all_products();

        int row = 1;
        int column = 0;

        this.product_grid.getChildren().clear();
        for(product p : Products){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/restaurant/views/product_card.fxml"));
            AnchorPane product_card_pane = fxmlLoader.load();
            product_card_controller Product_Card_Controller = fxmlLoader.getController();
            if(column == 4){
                column = 0;
                row++;
            }
            Product_Card_Controller.setdata(p);
            product_card_pane.setOnMouseClicked(event -> {
                //add order
            });
            this.product_grid.add(product_card_pane, column++, row);
            GridPane.setMargin(product_card_pane, new Insets(10));
        }
    }

    public void set_pos_product_model(product_model pos_product_model) throws IOException{
        this.pos_product_model = pos_product_model;
        this.product_load();
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.OrderModel = new orders_model();
    }
}
