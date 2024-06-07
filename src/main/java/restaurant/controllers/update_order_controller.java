package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import restaurant.models.store_ingredient;
import restaurant.models.store_ingredient_model;
import restaurant.models.Invoice;
import restaurant.models.ingredient_cost;
import restaurant.models.ingredient_model;
import restaurant.models.invoice_model;
import restaurant.models.order;
import restaurant.models.orders_model;
import restaurant.models.orders_update_model;
import restaurant.models.ingredient_cost_model;
import restaurant.controllers.order_update_controller;

public class update_order_controller implements Initializable{
    private product_model pos_product_model;
    private orders_update_model OrdersUpdateModel;
    private int invoiceID;
    private int order_row;
    private double src_sub_total;
    private invoice_model InvoiceModel;
    private stuff_pos_controller pos;
    private ingredient_cost_model IngredientCostModel;
    private int storeID;

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

    public void set_invoiceID(int invoiceID) throws SQLException{
        this.invoiceID = invoiceID;
        set_discount(invoiceID);
        recent_load_orders();
    }
    
    void set_discount(int invoiceID) throws SQLException{
        discount.setText(String.valueOf(InvoiceModel.getDiscount(invoiceID)));
        double sub_tot = Double.parseDouble(subtotal.getText());
        double disc = InvoiceModel.getDiscount(invoiceID);
        total.setText(String.valueOf(sub_tot - disc));
        
    }
    void recent_load_orders() throws SQLException{
        order_grid_pane.getChildren().clear();
        ResultSet rs = this.OrdersUpdateModel.GetOrderByInvoiceID(invoiceID);
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
                OrdersUpdateModel.add_recent_order(Order);
                order_pane.setDisable(true);
                order_grid_pane.add(order_pane, order_col, order_row++);
            }
            /**
             * Sets the current order row index based on the number of recent orders.
             * This ensures that new orders are added to the end of the order grid.
             */
            this.order_row = this.OrdersUpdateModel.get_recent_order().size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.discount.setText(String.valueOf(InvoiceModel.getDiscount(invoiceID)));//setting default discount By current invoiceID
        update_total();
    }

    @FXML
    void update_order_btn_e(MouseEvent event) {
        
    }
    void update_total() throws SQLException{
        double subtotal = OrdersUpdateModel.get_sub_total();
        double Discount = Double.parseDouble(this.discount.getText());
        double Total = subtotal - Discount;
        this.total.setText(String.valueOf(Total));
        this.subtotal.setText(String.valueOf(subtotal));
    }
    void setTotal(){
        /**
         * Calculates the total amount due after applying a discount to the subtotal.
         * 
         * This code is responsible for parsing the subtotal and discount values from
         * text fields,
         * calculating the total amount due by subtracting the discount from the
         * subtotal, and
         * updating the total text field with the formatted total amount.
         */
        // this.OrderModel.updateSubtotal();
        double st = this.src_sub_total;
        double dis = Double.parseDouble(this.discount.getText());
        double total = st - dis;
        this.total.setText("P" + String.valueOf(total));
    }
    void set_onchange_discount(TextField discount) {
        /**
         * Listens for changes to the discount text property and updates the total
         * accordingly.
         * If the new discount value is greater than or equal to the current discount
         * value, no action is taken.
         * If the discount text is empty, no action is taken.
         * Otherwise, the `setTotal()` method is called to update the total.
         */
        this.discount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // discount > new value
                if (Double.parseDouble(discount.getText()) > Double.parseDouble(discount.getText())) {
                    //no action
                } else if (newValue.isEmpty()) {
                    //no action
                } else if (discount.getText().isEmpty()) {
                    //no action
                } else {
                    try {
                        update_total();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        });
    }

    public void setStoreID(int storeID){
        this.storeID = storeID;
    }

    void product_load() throws IOException, SQLException{// load recent order
        this.pos.product_load();
        update_total();
        ObservableList<product> Products = this.pos.get_pos_product_model().get_all_products();
        // this.pos_product_model.get_all_products();

        int row = 1;
        int column = 0;

        this.product_grid.getChildren().clear();
        for(product Product : Products){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/restaurant/views/product_card.fxml"));
            AnchorPane product_card_pane = fxmlLoader.load();
            product_card_controller Product_Card_Controller = fxmlLoader.getController();
            
            
            if(column == 4){
                column = 0;
                row++;
            }

            Product_Card_Controller.setdata(Product);

            product_card_pane.setOnMouseClicked(event -> {
                if (OrdersUpdateModel.order_exist(Product)) {
                    System.out.println("the order is exist");
                }else{
                    try {
                        FXMLLoader fxmlLoadercurrentorder = new FXMLLoader();
                        fxmlLoadercurrentorder.setLocation(getClass().getResource("/restaurant/views/order_view.fxml"));
                        AnchorPane order_pane = fxmlLoadercurrentorder.load();
                        order_controller OrderController = fxmlLoadercurrentorder.getController();

                        IngredientCostModel.retrive_ingredient_cost(Product.getID());
                        ArrayList<ingredient_cost> IngredientCost = IngredientCostModel.get_all_Ingredient_costs();
                        for(ingredient_cost IngredienCost : IngredientCost){
                            System.out.println("IngredientID: " + IngredienCost.getIngredientID());
                            System.out.println("I Cost : " + IngredienCost.getQuantity());
                            ArrayList<store_ingredient> StoreIngredient = this.pos.get_storeingredientmodel().get_store_Ingredients();
                            for(store_ingredient ST:StoreIngredient){
                                if(ST.getStoreID() == 1){
                                    if(IngredienCost.getIngredientID() == ST.get_ingredientID()){
                                        System.out.println("old stock"+ST.get_stock());
                                        double newstock = ST.get_stock() - IngredienCost.getQuantity();
                                        System.out.println("new stock "+newstock);
                                        ST.set_stock(newstock);

                                    }
                                }
                            }
                        }
                        // clear the arraylist after use
                        

                        order Order = new order();
                        Order.setproduct(Product);
                        
                        OrderController.setdata(Order);
                        OrderController.set_pos(this.pos);
                        OrdersUpdateModel.add_current_order(Order);
                        order_grid_pane.add(order_pane, 0, this.order_row++);
                        
                        this.product_load();
                        IngredientCost.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
                }
                
                
            });
            this.product_grid.add(product_card_pane, column++, row);
            GridPane.setMargin(product_card_pane, new Insets(10));
        }
    }


    public void set_pos(stuff_pos_controller pos) throws IOException, SQLException{
        this.pos = pos;
        this.product_load();
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.OrdersUpdateModel = new orders_update_model();
        order_row = 0;
        this.set_onchange_discount(this.discount);
        this.InvoiceModel = new invoice_model();
        this.IngredientCostModel = new ingredient_cost_model();
    }
}
