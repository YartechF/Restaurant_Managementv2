package restaurant.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import restaurant.models.user_model;
import restaurant.models.user_person;

public class admin_user_manage_controller implements Initializable {

    @FXML
    private TableColumn<user_person, String> Store_name_col;

    @FXML
    private TableColumn<user_person, String> contact_col;

    @FXML
    private TableColumn<user_person, String> name_col;

    @FXML
    private TableColumn<user_person, String> password_col;

    @FXML
    private TableColumn<user_person, String> username_col;

    @FXML
    private TableView<user_person> users_table;

    @FXML
    private TableColumn<user_person, String> usertype_col;

    private void set_table_cell_value(){
            Store_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStorename()));
            contact_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcontact()));
            name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getname()));
            password_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getpassword()));
            username_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getusername()));
            usertype_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_usertype()));    
    }
    @FXML
    void add_user_btn_e(MouseEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new user_model();
    }

}
