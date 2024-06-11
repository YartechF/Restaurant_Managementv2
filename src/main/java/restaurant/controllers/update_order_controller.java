package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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
import restaurant.models.table_model;
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
    protected orders_update_model OrdersUpdateModel;
    private int invoiceID;
    private int order_row;
    private double src_sub_total;
    private invoice_model InvoiceModel;
    private stuff_pos_controller pos;
    private ingredient_cost_model IngredientCostModel;
    private int storeID;
    private DialogPane order_update_confirmation_dialog_pane;
    private OrderUpdateConfirmationController order_update_confirmation_controller;
    private DialogPane make_as_paid_confirmation_dialog;
    private stuff_controller staff_controller;
    private table_model TableModel;
    private int tableID;

    @FXML
    private ChoiceBox<?> category_choicebox;

    @FXML
    private TextField discount;

    @FXML
    private Button markAsPaidBtn;

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

    @FXML
    void back_btn_e(MouseEvent event) throws IOException, SQLException {
        this.staff_controller.Order_e(event);
    }

    //get mark as paid btn
    public Button get_mark_as_paid_btn() {
        return this.markAsPaidBtn;
    }

    public Boolean current_order_IsEmpty() {
        return this.OrdersUpdateModel.current_order_isEmpty();
    }
    
    @FXML
    void mark_as_paid_btn_e(MouseEvent event) throws IOException, SQLException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(this.make_as_paid_confirmation_dialog);
        Optional<ButtonType> clickedbutton = dialog.showAndWait();
        if(clickedbutton.get() == ButtonType.YES){
            this.InvoiceModel.mark_as_paid(this.invoiceID);
            this.staff_controller.Order_e(event);
            this.TableModel.update_table_isAvailable(this.tableID, 1);
            // this.TableModel.update_table_isAvailable();
        }else{
            //do nothing
        }
        //get the current in voiceID and mark as paid into database.

    }

    void load_mark_as_paid_confirmation_dialog_pane(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/restaurant/views/make_as_paid_confirmation.fxml"));
            this.make_as_paid_confirmation_dialog = fxmlLoader.load();
            this.order_update_confirmation_controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_invoiceID(int invoiceID) throws SQLException{
        this.invoiceID = invoiceID;
        set_discount(invoiceID);
        recent_load_orders();
    }

    public GridPane get_order_grid_pane() {
        return this.order_grid_pane;
    }
    
    void load_confirm_dialog_pane(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/restaurant/views/order_update_confirmation_dialog_pane.fxml"));
            this.order_update_confirmation_dialog_pane = fxmlLoader.load();
            this.order_update_confirmation_controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                order_on_create_controller OrderController = fxmlLoader.getController();

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
    void update_order_btn_e(MouseEvent event) throws SQLException, IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(this.order_update_confirmation_dialog_pane);
        Optional<ButtonType> clickedbutton = dialog.showAndWait();
        if(clickedbutton.get() == ButtonType.YES){
            OrdersUpdateModel.update_the_order_on_database(this.invoiceID);
            OrdersUpdateModel.clear_current_orders();
            this.staff_controller.Order_e(event);
        }else{
            //do nothing
        }
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
                        order_update_controller OrderController = fxmlLoadercurrentorder.getController();

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
                        

                        order Order = new order();
                        Order.setproduct(Product);
                        OrderController.setdata(Order);
                        OrderController.set_pos(this.pos);
                        OrderController.setRoot(order_pane);
                        OrderController.set_order_order_row(this.order_row);
                        OrderController.set_update_order_pos(this);
                        OrdersUpdateModel.add_current_order(Order);
                        order_grid_pane.add(order_pane, 0, this.order_row++);
                        this.product_load();
                        this.markAsPaidBtn.setDisable(true);
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

    public void set_staff_controller(stuff_controller staff_controller) {
        this.staff_controller = staff_controller;
    }
    public void set_tableID(int tableID){
        this.tableID = tableID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.OrdersUpdateModel = new orders_update_model();
        this.order_row = 0;
        this.set_onchange_discount(this.discount);
        this.InvoiceModel = new invoice_model();
        this.IngredientCostModel = new ingredient_cost_model();
        this.load_confirm_dialog_pane();
        this.load_mark_as_paid_confirmation_dialog_pane();
        this.TableModel = new table_model();
    }
}
