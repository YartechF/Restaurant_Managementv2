package restaurant.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import restaurant.models.ingredient_model;
import restaurant.models.product;
import restaurant.models.product_ingredient;

public class admin_add_product_requirements_dialog_controller implements Initializable {
    private int selected_productID;
    @FXML
    private ChoiceBox<String> ingredient_choice_box;
    @FXML
    private Label cost_type;
    @FXML
    private TextField quantity_tf;

    public int getSelected_productID(){
        return selected_productID;
    }
    public Double get_required_quantity(){
        return Double.parseDouble(quantity_tf.getText());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ingredient_model IngredientModel = new ingredient_model();
        ObservableList<String> ig = FXCollections.observableArrayList();
        try {
            ObservableList<product_ingredient> productList = new ingredient_model().get_all_ingredient();
            HashMap<String,Integer> ingredientList = new HashMap<>();
            HashMap<String,String> cost_type_list = new HashMap<>();

            for(product_ingredient p:productList){
                ingredientList.put(p.getName(),p.getID());
                ingredient_choice_box.getItems().add(p.getName());
                cost_type_list.put(p.getName(),p.get_cost_type());
                //if I select product in ingredient_choice_box
                //I want to get the product ID and put it in the ingredient_choice_box.getValue()
                //so that I can get the product ID and put it in the ingredient_choice_box.getValue()

            }
            ingredient_choice_box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                //I want to get the product ID and put it in the ingredient_choice_box.getValue()
                //so that I can get the product ID and put it in the ingredient_choice_box.getValue()
                selected_productID = ingredientList.get(newValue);
                cost_type.setText(cost_type_list.get(newValue));
                
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
            
        
    }


}
