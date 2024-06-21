package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import restaurant.models.table;
import restaurant.models.table_model;

public class table_controller implements Initializable {
    private table Table;
    @FXML
    private Button add_table_btn;

    @FXML
    private TableColumn<table, String> capacity_col;

    @FXML
    private TableColumn<table, String> status_col;

    @FXML
    private TableColumn<table, String> table_col;

    @FXML
    private TableView<table> table_table_view;

    @FXML
    private TableColumn<table, String> store_col;

    public Button get_add_btn(){
        return add_table_btn;
    }
    public table get_table(){
        return Table;
    }

    private void setcellvalue(){
        this.table_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        this.table_col.getStyleClass().add("centered-cell");
        this.capacity_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStringCapacity()));
        this.capacity_col.getStyleClass().add("centered-cell");
        this.status_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().GetStringIsAvailable()));
        this.status_col.getStyleClass().add("centered-cell");
        this.store_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        this.store_col.getStyleClass().add("centered-cell");
    }

    @FXML
    void add_table_e(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/table_atri_dialog.fxml"));
        try {
            DialogPane table_atri_dialog = loader.load();
            table_atri_dialog_controller TableAtriDialogController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(table_atri_dialog);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
               table t = TableAtriDialogController.get_table_data();
               //print all
            //    System.out.println(t.getName());
            //    System.out.println(t.isActive());
            //    System.out.println(t.isAvailable());
            //    System.out.println(t.getStoreID());
            //    System.out.println(t.getCapacity());

               new table_model().create_table(t.getName(), true, true, t.getStoreID(), t.getCapacity());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Table = new table();
        setcellvalue();
        try {
            table_table_view.getItems().addAll(new table_model().retrieve_all_table());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
