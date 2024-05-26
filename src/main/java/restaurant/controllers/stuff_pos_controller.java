package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import restaurant.models.store_ingredient;
import restaurant.models.store_ingredient_model;
import restaurant.models.user;
import restaurant.models.ingredient_cost_model;
import restaurant.models.order;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Collections;
import restaurant.controllers.order_controller;
import restaurant.models.Invoice;
import restaurant.models.orders_model;
import restaurant.models.ingredient_cost;

public class stuff_pos_controller implements Initializable {
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
    private int storeID;

    public stuff_pos_controller() throws SQLException, IOException {

        this.ingredient_cost = new ingredient_cost_model();

        order_row = 0;
        ordersmodel = new orders_model();
    }

    public void pos_set_invoice(Invoice invoice) {
        this.invoice = invoice;
    }
    public void set_storeID(int storeID){
        this.storeID = storeID;
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

    public void set_pos_currentuser(user current_user) {
        this.current_user = current_user;
        this.storeID = this.current_user.getStoreID();
    }

    public void product_load() throws SQLException {
        ArrayList<product> products = this.productmodel.get_all_products();
        int row = 1;
        int column = 0;
        try {
            for (int i = 0; i < products.size(); i++) {
                /// ---------------------------------------
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/restaurant/views/product_card.fxml"));
                AnchorPane product_card = fxmlLoader.load();
                product_card_controller productcardcontroller = fxmlLoader.getController();

                // the stock base on the store
                // Assuming product class has a getId() method
                // Assuming ingredient_cost_model class has a method getIngredientCost(int
                // productID)
                ArrayList<ingredient_cost> all_ingredients_cost = this.ingredient_cost.get_all_Ingredient_costs();// retrieve

                ArrayList<ingredient_cost> filtered_Ingredient_costs = new ArrayList<>();
                ArrayList<store_ingredient> st = this.storeingredientmodel.get_store_Ingredients();//stock of all store
                ArrayList<store_ingredient> filtered_storeingredientmodel = new ArrayList<>();//ingredients filtered base current store
                ArrayList<Double> calculate_stock = new ArrayList<>(); 
                
                for (int ii = 0; ii < all_ingredients_cost.size(); ii++) {
                    int productID = all_ingredients_cost.get(ii).getProductID();
                    if (productID == products.get(i).getID()) {
                        filtered_Ingredient_costs.add(all_ingredients_cost.get(ii));
                    }
                }
                
                for (store_ingredient ST : st) {
                    if(ST.getStoreID() == this.storeID){
                        filtered_storeingredientmodel.add(ST);
                    }
                }
                
                for(store_ingredient fsi :filtered_storeingredientmodel){
                    System.out.println("ingredient ID:"+fsi.get_ingredientID()+"from "+fsi.getStoreID());
                }

                for (int iii = 0; iii < filtered_Ingredient_costs.size(); iii++) {// filter the ingredients cost in current product
                    // System.out.println("product: "+products.get(i).getName() + " ingredientID:"
                    //         + filtered_Ingredient_costs.get(iii).getIngredientID()+"cost: "+filtered_Ingredient_costs.get(iii).getQuantity());//output product: Burger ingredient:4

                            int ProductingredientID = filtered_Ingredient_costs.get(iii).getIngredientID();// ingredient for current product
                            int ingredient_cost_per_current_product = filtered_Ingredient_costs.get(iii).getQuantity();

                            for(store_ingredient fst : filtered_storeingredientmodel){
                                if (fst.get_ingredientID() == ProductingredientID) {
                                    double productfromingredient = fst.get_stock() / ingredient_cost_per_current_product;
                                    // System.out.println(products.get(i).getName()+" "+productfromingredient);
                                    calculate_stock.add(productfromingredient);
                                }
                            }

                            
                }
                System.out.println("the stock of "+products.get(i).getName()+" "+Collections.min(calculate_stock));
                double product_stock = Collections.min(calculate_stock);
                products.get(i).setStock(product_stock);
                productcardcontroller.setdata(products.get(i));
                
                //getting the current stock base on current product
                //productfromingredient = stock of ingredient / ingredient per current product
                //product stock base on current store = get the min {productfromingredient,productfromingredient,productfromingredient}


                productcardcontroller.setdata(products.get(i));

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.productmodel = new product_model();
        this.productmodel.retrieve_all_product();
        this.ingredient_cost = new ingredient_cost_model();
        try {
            this.ingredient_cost.retrive_all_ingredient_cost();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.storeingredientmodel = new store_ingredient_model();
        try {
            this.storeingredientmodel.retrieve_all_storeIngredient();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}