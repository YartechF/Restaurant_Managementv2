package restaurant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class update_table_controller {

    @FXML
    private TextField desciption_tf;

    @FXML
    private TextField tablename_tf;


    public void setTable_name(String tablename){
        this.tablename_tf.setText(tablename);
    }
    public void set_description(String description){
        this.desciption_tf.setText(description);
    }
    public String get_tablename(){
        return this.tablename_tf.getText();
    }
    public String getDescription(){
        return this.desciption_tf.getText();
    }
}
