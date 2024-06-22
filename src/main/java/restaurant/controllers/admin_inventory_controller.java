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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.models.AdminProductInventory;
import restaurant.models.Inventory_store_ingredient;
import restaurant.models.ingredient_model;
import restaurant.models.product_inventory;
import restaurant.models.table_invoice;
import javafx.scene.control.cell.PropertyValueFactory;

public class admin_inventory_controller implements Initializable {

    private ingredient_model IngredientModel;

    @FXML
    private Button add_btn;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<AdminProductInventory, String> cost_type_col;

    @FXML
    private TableColumn<AdminProductInventory, String> desription_col;

    @FXML
    private TableColumn<AdminProductInventory, String> productname_col;

    @FXML
    private TableView<AdminProductInventory> inventory_table;

    @FXML
    void add_btn_e(MouseEvent event) throws IOException {
        // ingredient
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurant/views/add_admin_product_inventory.fxml"));
        DialogPane add_product_inventory_pane = fxmlLoader.load();
        AddAdminProductInventoryController AAPIC = fxmlLoader.getController();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(add_product_inventory_pane);

        dialog.setTitle("Add Product Inventory");
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.get() == ButtonType.OK) {
            //print all
            String productname = AAPIC.getProductname();

            String description = AAPIC.getDescription();

            String cost_type = AAPIC.getCostType();

            String stock_type = AAPIC.getStockType();

            if(cost_type.equals("per pcs")){
                cost_type = "2";
            }
            else if(cost_type.equals("per mg")){
                cost_type = "1";
            }

            if(stock_type.equals("per pcs")){
                stock_type = "1";
            }
            else if(stock_type.equals("per kg")){
                stock_type = "0";
            }

            
            new ingredient_model().create_ingredient(productname,description,Integer.parseInt(cost_type),Integer.parseInt(stock_type));
            

            
            
        }
    }
    private void setCellValue(){
        //set the admin_product_inventory
        productname_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        desription_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        cost_type_col.setCellValueFactory(new PropertyValueFactory<>("costType"));
    }

    private void filterInventoryTable(String searchText) {
        inventory_table.setItems(this.IngredientModel.retrieve_all_ingredient_by_search(searchText));

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.IngredientModel = new ingredient_model();
        filterInventoryTable(search.getText());
        setCellValue();
        
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the inventory table based on the search text
            filterInventoryTable(newValue);
        });
        // add row clicked listener
        inventory_table.setRowFactory(tv -> {
        TableRow<AdminProductInventory> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                AdminProductInventory rowData = row.getItem();

                // Create a dialog
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Product Inventory");
                dialog.setHeaderText(null);
                // add a Textfield
                TextField tf = new TextField();
                tf.setText(rowData.getProductName());
                // Create update and delete buttons
                ButtonType updateButton = new ButtonType("Update", ButtonData.OK_DONE);
                ButtonType deleteButton = new ButtonType("Delete", ButtonData.FINISH);
                ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                // Set the dialog content and buttons
                dialog.getDialogPane().setContent(new Label("Select an action for the product: " + rowData.getProductName()));
                dialog.getDialogPane().getButtonTypes().addAll(updateButton, deleteButton,cancel);
                dialog.getDialogPane().setContent(tf);

                // Show the dialog and handle the result
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == updateButton) {
                        // Handle update action
                        System.out.println("Update: " + rowData.getProductName());
                        new ingredient_model().update_ingredientname(rowData.getID(), tf.getText());
                    } else if (result.get() == deleteButton) {
                        // Handle delete action
                        System.out.println("Delete: " + rowData.getProductName());
                        new ingredient_model().delete_ingredient(rowData.getID());
                        filterInventoryTable(search.getText());
                    }else if (result.get() == cancel) {
                        // Handle delete action
                        dialog.close();
                    }
                }
            }
        });
        return row;
    });


    }



}
