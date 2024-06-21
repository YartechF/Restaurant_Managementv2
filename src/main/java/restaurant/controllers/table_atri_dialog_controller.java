package restaurant.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import restaurant.models.store_model;
import restaurant.models.store_table_cell;
import restaurant.models.table;

public class table_atri_dialog_controller implements Initializable{
    private store_model StoreModel;
    private HashMap <String, Integer> store_name_map;
    @FXML
    private TextField capacity_tf;

    @FXML
    private ChoiceBox<String> store_name_choice;

    @FXML
    private TextField store_name_tf;

    public table get_table_data(){
        table t = new table();
        t.setStoreID(this.store_name_map.get(this.store_name_choice.getValue()));
        t.setName(this.store_name_tf.getText());
        t.setCapacity(Integer.parseInt(this.capacity_tf.getText()));
        t.setAvailable(true);
        return t;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.StoreModel = new store_model();
        this.store_name_map = new HashMap <>();
        this.StoreModel.retrieve_all_store();
        for(store_table_cell s:this.StoreModel.getStores()){
            this.store_name_map.put(s.getName(), s.getID());
            store_name_choice.getItems().add(s.getName());
        }

        
    }

}
