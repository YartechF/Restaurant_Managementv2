package restaurant.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurant.models.Inventory_store_ingredient;
import restaurant.models.bill;
import restaurant.models.product_inventory;
import restaurant.models.store_ingredient_model;
import restaurant.models.user;

public class Inventory_Controller implements Initializable {
    private Inventory_store_ingredient StoreIngredientModel;
    private user current_user;

    @FXML
    private TableColumn<product_inventory, String> decription_col;

    @FXML
    private TableColumn<product_inventory, String> ingredient_col;

    @FXML
    private TableColumn<product_inventory, String> product_name_col;

    @FXML
    private TableColumn<product_inventory, String> stock_col;

    @FXML
    private TableView<product_inventory> inventory_table;

    @FXML
    private TextField search;

    public void SetCurrentUser(user currentUser){
        this.current_user = currentUser;
        this.load_store_Ingredient();
        this.inventory_table.setItems(this.StoreIngredientModel.get_Product_inventoryList());
    }
    
    void load_store_Ingredient(){
        try {
            this.StoreIngredientModel.retrieve_store_ingredient(current_user.getStoreID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void SetCellValue() {
        product_name_col.setCellValueFactory(new PropertyValueFactory<product_inventory, String>("Product_Name"));// or bill number
        product_name_col.getStyleClass().add("centered-cell");

        decription_col.setCellValueFactory(new PropertyValueFactory<product_inventory, String>("Description"));// or bill number
        decription_col.getStyleClass().add("centered-cell");

        stock_col.setCellValueFactory(new PropertyValueFactory<product_inventory, String>("Stock"));// or bill number
        stock_col.getStyleClass().add("centered-cell");
    }

    private void filterInventoryTable(String searchText) {
    // Get the filtered list of product_inventory based on the search text
    ObservableList<product_inventory> filteredList = StoreIngredientModel.getFilteredProductInventoryList(searchText);

    // Update the inventory_table with the filtered list
    inventory_table.setItems(filteredList);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.StoreIngredientModel = new Inventory_store_ingredient();
        this.SetCellValue();
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
                    // Add your custom logic here
                    
                }
            });
            return row;
        });
    }


}