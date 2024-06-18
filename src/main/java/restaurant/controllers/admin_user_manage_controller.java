package restaurant.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurant/views/admin_add_user_dialog.fxml"));
        try {
            DialogPane add_user_dialog = fxmlLoader.load();
            admin_add_user_dialog_controller AdminAddUserDialog = fxmlLoader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(add_user_dialog);
            dialog.setTitle("Add User");
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.get() == ButtonType.OK){
                user_person UserPerson = AdminAddUserDialog.get_user_person_data();
                //for loop
                int generatedID = new user_model().register_personal_info(UserPerson.getname(), UserPerson.getaddress(), null, UserPerson.getcontact());
                new user_model().register_user(UserPerson.getusername(), UserPerson.getpassword(), generatedID, UserPerson.getUsertypeID(), UserPerson.getStoreID());


            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.set_table_cell_value();
        try {
            users_table.getItems().addAll(new user_model().get_user_person());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
