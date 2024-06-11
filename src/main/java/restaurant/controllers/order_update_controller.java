package restaurant.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import restaurant.models.ingredient_cost;
import restaurant.models.store_ingredient;

public class order_update_controller extends order_on_create_controller {
    private update_order_controller UpdateOrderController;


    
    public void set_update_order_pos(update_order_controller UpdateOrderController){
        this.UpdateOrderController = UpdateOrderController;
    }
    @Override
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
        UpdateOrderController.get_mark_as_paid_btn().setDisable(true);
        try {
            this.UpdateOrderController.product_load();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
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
            this.UpdateOrderController.get_order_grid_pane().getChildren().remove(this.root);
            this.UpdateOrderController.OrdersUpdateModel.delete_order(Order);
            // this.UpdateOrderController
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
        try {
            this.UpdateOrderController.product_load();
            if(UpdateOrderController.current_order_IsEmpty()){
                UpdateOrderController.get_mark_as_paid_btn().setDisable(false);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    
    
}
