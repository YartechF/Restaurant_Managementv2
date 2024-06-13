package restaurant.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.models.store_model;
import restaurant.models.store_table_cell;
import restaurant.models.table_invoice;

public class store_management_controller implements Initializable {
    private store_model StoreModel;


    @FXML
    private TableColumn<store_table_cell, String> decription_col;

    @FXML
    private TableColumn<store_table_cell, String> store_name_col;

    @FXML
    private TableView<store_table_cell> store_table;

    @FXML
    void add_store_e(MouseEvent event) {

    }

    void SetCellValues() {
        this.store_name_col.setCellValueFactory(new PropertyValueFactory<store_table_cell, String>("name"));
        this.store_name_col.getStyleClass().add("centered-cell");
        this.decription_col.setCellValueFactory(new PropertyValueFactory<store_table_cell, String>("description"));
        this.decription_col.getStyleClass().add("centered-cell");
    }

    void load_store() throws SQLException{
        this.StoreModel.retrieve_all_store();
        ObservableList<store_table_cell> stores = this.StoreModel.getStores();

        // for(store_table_cell s:stores){
        //     System.out.println(s.getname());
        //     System.out.println(s.getdescription());
        // }

        this.store_table.setItems(stores);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.StoreModel = new store_model();
        SetCellValues();
        try {
            load_store();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
