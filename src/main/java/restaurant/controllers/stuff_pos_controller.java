package restaurant.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import restaurant.models.product;
import restaurant.models.product_model;
import restaurant.models.store_ingredient_model;
import restaurant.models.user;
import restaurant.models.ingredient_cost_model;
import restaurant.models.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import restaurant.controllers.order_controller;
import restaurant.models.Invoice;
import restaurant.models.orders_model;
import restaurant.models.ingredient_cost;

public class stuff_pos_controller {
    private String storename;
    private product_model productmodel;
    private user current_user;
    private order_controller ordercontroller;
    private FXMLLoader order_view;
    private AnchorPane order_view_root;
    private ingredient_cost_model ingredient_cost;
    private store_ingredient_model storeingredientmodel;
    private int order_row;
    private Invoice invoice;
    private orders_model ordersmodel;

    public stuff_pos_controller() throws SQLException, IOException {
        this.productmodel = new product_model();
        this.ingredient_cost = new ingredient_cost_model();
        this.storeingredientmodel = new store_ingredient_model();
        order_row = 0;
        ordersmodel = new orders_model();
    }

    public void pos_set_invoice(Invoice invoice) {
        this.invoice = invoice;
        System.out.println("the current invoice is: " + this.invoice.getID());
        update_orders();
    }

    @FXML
    private GridPane order_grid_pane;

    @FXML
    private MenuButton category;

    @FXML
    private GridPane product_grid;

    @FXML
    private ScrollPane product_scroll;

    @FXML
    private TextField productsearch_tf;

    @FXML
    private Label store_name;

    void update_orders() {
        try {
            FXMLLoader fxmlLoader5 = new FXMLLoader();
            fxmlLoader5.setLocation(getClass().getResource("/restaurant/views/order_view.fxml"));
            AnchorPane order = fxmlLoader5.load();
            order_controller ordercontroller = fxmlLoader5.getController();
            ResultSet rs = this.ordersmodel.retrieve_order(this.invoice);
            while (rs.next()) {
                order Order = new order();
                Order.setinvoiceID(rs.getInt("invoiceID"));
                Order.setproductID(rs.getInt("productID"));
                Order.setquantity(rs.getInt("quantity"));
                ordercontroller.setdata(Order);
            }
            System.out.println(invoice.getID());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void set_pos_currentuser(user current_user) {
        this.current_user = current_user;
    }

    public void product_load() {
        ResultSet rs = this.productmodel.retrieve_all_product();
        int row = 1;
        int column = 0;
        try {
            while (rs.next()) {
                product Product = new product();
                // setting the product
                Product.setID(rs.getInt("ID"));
                Product.setName(rs.getString("name"));
                Product.setPrice(rs.getInt("price"));
                Product.setCategory_ID(rs.getInt("categoryID"));
                Product.setPicture(rs.getString("picture"));
                /// ---------------------------------------
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/restaurant/views/product_card.fxml"));
                AnchorPane product_card = fxmlLoader.load();
                product_card_controller productcardcontroller = fxmlLoader.getController();

                // the stock base on the store
                // Assuming product class has a getId() method
                // Assuming ingredient_cost_model class has a method getIngredientCost(int
                // productID)

                ResultSet rs1 = this.ingredient_cost.get_ingredient_cost(Product.getID());

                ArrayList<Double> calculated_product_stock = new ArrayList<>();
                while (rs1.next()) {
                    int ingredient_ID = rs1.getInt("ingredientID");
                    int ingredient_per_product = rs1.getInt("quantity");
                    ResultSet rs2 = this.storeingredientmodel.get_ingredient_stock(this.current_user.getStoreID(),
                            ingredient_ID);
                    if (rs2.next()) {
                        double stock_of_ingredient = rs2.getDouble("stock");
                        calculated_product_stock.add(stock_of_ingredient / ingredient_per_product);
                    }
                    double product_stock = Collections.min(calculated_product_stock);
                    Product.setStock(product_stock);
                }
                // Now you can use ingredientCost variable

                productcardcontroller.setdata(Product);

                // add product card event
                // when product card clicked
                product_card.setOnMouseClicked(event -> {
                    System.out.println("you click product card");
                });

                if (column == 4) {
                    column = 0;
                    row++;
                }

                // add the product card in grid
                this.product_grid.add(product_card, column++, row);
                GridPane.setMargin(product_card, new Insets(10));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}