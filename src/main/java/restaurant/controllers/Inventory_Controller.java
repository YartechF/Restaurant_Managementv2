package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import restaurant.models.Inventory_store_ingredient;
import restaurant.models.bill;
import restaurant.models.product_inventory;
import restaurant.models.store_ingredient_model;
import restaurant.models.user;
import javafx.scene.Node;

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

    @FXML
    void logout_btn_e(MouseEvent event) {
    try {
        // Load the login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/auth_view.fxml"));
        Parent loginRoot = loader.load();
        Scene loginScene = new Scene(loginRoot);

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        currentStage.setScene(loginScene);
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

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

        stock_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStock()));
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
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurant/views/update_product_stock_on_inventory.fxml"));
                        DialogPane update_stock_dialog = fxmlLoader.load();
                        update_product_stock_on_inventory_controller UpdateProductStockController = fxmlLoader.getController();

                        UpdateProductStockController.set_product_name(selectedProduct.getProduct_Name());
                        UpdateProductStockController.set_stock(selectedProduct.getStock());

                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(update_stock_dialog);
                        Optional<ButtonType> result = dialog.showAndWait();

                        

                        if(result.get() == ButtonType.APPLY){
                            double updated_stock = UpdateProductStockController.get_updated_stock_tf_value();
                            if(selectedProduct.getcost_type() != "pcs"){
                                updated_stock = updated_stock * 1000;
                            }  
                            selectedProduct.setStock(String.valueOf(updated_stock));
                            this.StoreIngredientModel.update_store_ingredient_from_inventory(selectedProduct.getID(),updated_stock);

                            //active the search listener
                            this.StoreIngredientModel.get_Product_inventoryList().clear();
                            this.load_store_Ingredient();
                            filterInventoryTable(search.getText());
                        }
                        else if(result.get() == ButtonType.CANCEL){
                            //
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }


}