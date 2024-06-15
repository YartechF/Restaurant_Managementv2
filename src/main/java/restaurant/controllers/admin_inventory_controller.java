package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.models.ingredient_model;
import restaurant.models.product_inventory;

public class admin_inventory_controller implements Initializable {

    private ingredient_model IngredientModel;

    @FXML
    private Button add_btn;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<product_inventory, String> cost_type_col;

    @FXML
    private TableColumn<product_inventory, String> desription_col;

    @FXML
    private TableColumn<product_inventory, String> productname_col;

    @FXML
    private TableView<product_inventory> inventory_table;

    @FXML
    void add_btn_e(MouseEvent event) {
        // ingredient
        
    }
    private void setCellValue(){
        this.productname_col.setCellValueFactory(new PropertyValueFactory<product_inventory, String>("Product_Name"));// or bill number
        this.productname_col.getStyleClass().add("centered-cell");

        this.desription_col.setCellValueFactory(new PropertyValueFactory<product_inventory, String>("Description"));// or bill number
        this.desription_col.getStyleClass().add("centered-cell");

        this.cost_type_col.setCellValueFactory(new PropertyValueFactory<product_inventory, String>("Cost_Type"));// or bill number
        this.cost_type_col.getStyleClass().add("centered-cell");
    }

    private void filterInventoryTable(String searchText) {
    // Get the filtered list of product_inventory based on the search text
    // ObservableList<product_inventory> filteredList = StoreIngredientModel.getFilteredProductInventoryList(searchText);

    // // Update the inventory_table with the filtered list
    // inventory_table.setItems(filteredList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.IngredientModel = new ingredient_model();

        setCellValue();
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the inventory table based on the search text
            filterInventoryTable(newValue);
        });
        inventory_table.setRowFactory(tv -> {
            TableRow<product_inventory> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    product_inventory selectedProduct = row.getItem();
                    // Perform action on the selected product
                    System.out.println("Selected Product: " + selectedProduct.getID());

                }
            });
            return row;
        });


    }



}
