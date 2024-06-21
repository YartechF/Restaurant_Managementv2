package restaurant.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddAdminProductInventoryController implements Initializable {

    @FXML
    private ChoiceBox<String> cost_type;

    @FXML
    private TextField descrption_tf;

    @FXML
    private TextField product_name_tf;

    @FXML
    private ChoiceBox<String> stock_type;

    private String[] cost_type_list = {"per pcs","per grams"};
    private String[] stock_type_list = {"per pcs","per kilos"};
    //costtype 2pcs 1mg
    //isperpcs 

    public String getCostType(){
        return cost_type.getValue();
    }
    public String getStockType(){
        return stock_type.getValue();
    }
    public String getDescription(){
        return descrption_tf.getText();
    }
    public String getProductname(){
        return product_name_tf.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // add the cost_type 
        cost_type.getItems().addAll(cost_type_list);
        stock_type.getItems().addAll(stock_type_list);
    }
}
