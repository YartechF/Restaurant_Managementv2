package restaurant.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import restaurant.models.store_model;
import restaurant.models.store_table_cell;
import restaurant.models.user_model;
import restaurant.models.user_person;
import restaurant.models.user_type;

public class admin_add_user_dialog_controller implements Initializable {
    private HashMap<String,Integer> storesID;
    private HashMap<String,Integer> usertypesID;
    @FXML
    private TextField contact_tf;

    @FXML
    private TextField name_tf;

    @FXML
    private TextField password_tf;

    @FXML
    private ChoiceBox<String> store_drop_down;

    @FXML
    private TextField username_tf;

    @FXML
    private ChoiceBox<String> usertype_dropdown;

    public user_person get_user_person_data(){
        user_person person = new user_person();
        person.setname(name_tf.getText());
        person.setcontact(contact_tf.getText());
        person.setusername(username_tf.getText());
        person.setpassword(password_tf.getText());
        person.setStoreID(storesID.get(store_drop_down.getValue()));
        person.setUsertypeID(usertypesID.get(usertype_dropdown.getValue()));
        return person;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        store_model StoreModel = new store_model();
        StoreModel.retrieve_all_store();
        ObservableList<store_table_cell> stores = StoreModel.getStores();

        this.storesID = new HashMap<>();
        this.usertypesID = new HashMap<>();

        for(store_table_cell s:stores){
            storesID.put(s.getName(),s.getID());
            store_drop_down.getItems().add(s.getName());
        }
        try {
            ObservableList<user_type> userTypes = new user_model().get_user_type();
            for(user_type u:userTypes){
                usertypesID.put(u.get_user_type(),u.get_ID());
                usertype_dropdown.getItems().add(u.get_user_type());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
