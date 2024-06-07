package restaurant.controllers;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import restaurant.models.Invoice;
import restaurant.models.order;
import restaurant.models.product;
import restaurant.models.store_ingredient;
import restaurant.models.ingredient_cost;
import restaurant.models.ingredient_cost_model;
import restaurant.models.ingredient_model;

public class order_controller implements Initializable  {

    private order Order;
    private ArrayList<ingredient_cost> IngredientCost;
    private ingredient_cost_model IngredientCostModel;
    private stuff_pos_controller pos;
    private ingredient_cost_model Ingredient_Cost_Model;
    private ArrayList<store_ingredient> filtered_storeingredient;
    private ArrayList<store_ingredient> sim;
    private AnchorPane root;
    private int root_row;

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
    protected void set_order_order_row(int row){
        this.root_row = row;
    }
    protected void setRoot(AnchorPane root) {
        this.root = root;
    }

    protected void set_pos(stuff_pos_controller pos) throws SQLException {
        this.pos = pos;
        this.IngredientCostModel.retrive_ingredient_cost(Order.getproduct().getID());
        IngredientCost = IngredientCostModel.get_all_Ingredient_costs();
        for (ingredient_cost icost : IngredientCost) {
            System.out.println(icost.getIngredientID());
            System.out.println(icost.getQuantity());
            sim = this.pos.get_storeingredientmodel().get_store_Ingredients();
            for (store_ingredient SIM : sim) {
                if (this.pos.get_current_storeID() == SIM.getStoreID() && SIM.get_ingredientID() == icost.getIngredientID()) {
                    this.filtered_storeingredient.add(SIM);
                }
            }
        }
    }

    public void setdata(order Order) {
        this.Order = Order;
        update_order();
    }

    public void update_order() {
        this.qty.setText(String.valueOf(this.Order.getquantity()));
        this.product_name.setText(this.Order.getproduct().getName());
        this.product_price.setText("P" + String.valueOf(this.Order.getproduct().getPrice() * this.Order.getquantity()));
        File file = new File(this.Order.getproduct().getPicture());
        Image image = new Image(file.toURI().toString());
        this.product_img.setImage(image);
    }

    @FXML
    void delete_btn(MouseEvent event) {

    }

    @FXML
    void minus_e(MouseEvent event) throws SQLException {
        // Decrease order quantity only if it's greater than 1
        if (this.Order.getquantity() > 1) {
            for (store_ingredient sim : this.sim) {
                if (filtered_storeingredient.contains(sim)) {
                    for (ingredient_cost ic : this.IngredientCost) {
                        if (ic.getIngredientID() == sim.get_ingredientID()) {
                            System.out.println("Incrementing stock for ingredient " + sim.get_ingredientID() + " in store " + sim.getStoreID());
                            sim.set_stock(sim.get_stock() + ic.getQuantity()); // Increment stock
                            break;
                        }
                    }
                }
            }
            this.Order.setquantity(this.Order.getquantity() - 1); // Decrease order quantity
        } else {
            // Set order quantity to 1 if it's less than 1
            this.pos.get_order_grid_pane().getChildren().remove(this.root);
            this.pos.get_ordersmodel().delete_order(this.Order);
            this.root_row--;
            for (store_ingredient sim : this.sim) {
                if (filtered_storeingredient.contains(sim)) {
                    for (ingredient_cost ic : this.IngredientCost) {
                        if (ic.getIngredientID() == sim.get_ingredientID()) {
                            System.out.println("Incrementing stock for ingredient " + sim.get_ingredientID() + " in store " + sim.getStoreID());
                            sim.set_stock(sim.get_stock() + ic.getQuantity()); // Increment stock
                            break;
                        }
                    }
                }
            }
            this.Order.setquantity(this.Order.getquantity() - 1); 
        }
        update_order();
        this.pos.product_load();
    }


    @FXML
    void plus_e(MouseEvent event) throws SQLException {
        for (store_ingredient sim : this.sim) {
            if (filtered_storeingredient.contains(sim)) {
                for (ingredient_cost ic : this.IngredientCost) {
                    if (ic.getIngredientID() == sim.get_ingredientID()) {
                        System.out.println("Reducing stock for ingredient " + sim.get_ingredientID() + " in store " + sim.getStoreID());
                        sim.set_stock(sim.get_stock() - ic.getQuantity());
                        break;
                    }
                }
            }
        }
        this.Order.setquantity(this.Order.getquantity() + 1);
        update_order();
        this.pos.product_load();
    }

    public void printSim() {
        System.out.println("Printing all store_ingredient instances in sim:");
        for (store_ingredient sim : this.sim) {
            System.out.println("Store ID: " + sim.getStoreID() + ", Ingredient ID: " + sim.get_ingredientID() + ", Stock: " + sim.get_stock());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IngredientCostModel = new ingredient_cost_model();
        filtered_storeingredient = new ArrayList<store_ingredient>();
    }
}
