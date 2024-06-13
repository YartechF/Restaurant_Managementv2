package restaurant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class create_store_controller {

    @FXML
    private TextField description_tf;

    @FXML
    private TextField storename_ft;

    public String getDescriptionValue(){
        return description_tf.getText();
    }
    public String getStorenameValue(){
        return storename_ft.getText();
    }

}
