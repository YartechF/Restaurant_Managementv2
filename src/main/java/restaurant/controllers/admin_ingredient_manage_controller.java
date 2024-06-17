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
import restaurant.models.Admin_product_ingredient;
import restaurant.models.ingredient_cost_model;
import restaurant.models.ingredient_model;
import restaurant.models.product_model;

public class admin_ingredient_manage_controller implements Initializable {

    private ingredient_model IngredientModel;
    private product_model ProductModel;
    private int current_selected_product_menuID;

    @FXML
    private TableView<AdminProductInventory> Product_Dishes_table;

    @FXML
    private TableColumn<AdminProductInventory, String> product_dishes_col;

    @FXML
    private TableColumn<Admin_product_ingredient, String> product_name_col;

    @FXML
    private TableView<Admin_product_ingredient> product_table;

    @FXML
    private TableColumn<Admin_product_ingredient, String> required_quantity_col;

    @FXML
    private Label selectedProduct;

    @FXML
    private Button add_btn;

    @FXML
    void add_btn_click_e(MouseEvent event) throws IOException {
            // Load the next scene (e.g., stuff screen)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/admin_add_product_requirements_dialog.fxml"));
        DialogPane add_requirements = loader.load();
        admin_add_product_requirements_dialog_controller dialog_controller = loader.getController();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Product Requirements");
        dialog.setDialogPane(add_requirements);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.get() == ButtonType.OK){
            int ingredientID = dialog_controller.getSelected_productID();
            double required_quantity = dialog_controller.get_required_quantity();
            System.out.println(dialog_controller.getSelected_productID());
            System.out.println(dialog_controller.get_required_quantity());

            if (ingredientID == 0 || required_quantity < 0){
                System.out.println("err");
            }else{
                try {
                    new ingredient_cost_model().create_ingredient_cost(ingredientID, current_selected_product_menuID, required_quantity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void setcellvalue(){
        product_dishes_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        product_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_product_name()));
        required_quantity_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_required_quantity()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hello");
        this.IngredientModel = new ingredient_model();
        this.ProductModel = new product_model();
        setcellvalue();
        try {
            this.Product_Dishes_table.setItems(this.ProductModel.get_all_products_admin());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        Product_Dishes_table.setRowFactory(tv -> {
        TableRow<AdminProductInventory> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                AdminProductInventory rowData = row.getItem();
                product_table.setItems(IngredientModel.get_Ingredient_costs_by_productID(rowData.getID()));
                // Create a dialog
                current_selected_product_menuID = rowData.getID();
                this.selectedProduct.setText(rowData.getProductName());
                add_btn.setDisable(false);

                //INSERT INTO `tbl_ingredient_cost` (`ID`, `ingredientID`, `productID`, `quantity`) VALUES (NULL, '6', '23', '0.25');
            }
        });
        return row;
    });

   product_table.setRowFactory(tv -> {
    TableRow<Admin_product_ingredient> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
            Admin_product_ingredient rowData = row.getItem();

            // Create a dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update Required Quantity");
            dialog.setHeaderText("Enter the new required quantity");

            // Create a text field for entering the new quantity
            TextField quantityField = new TextField();
            quantityField.setText(rowData.get_required_quantity());

            // Create action buttons
            ButtonType updateButton = new ButtonType("Update", ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            ButtonType deleteButton = new ButtonType("Delete", ButtonData.APPLY);
            // Set the button types and content for the dialog
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton,deleteButton);
            dialog.getDialogPane().setContent(quantityField);

            // Handle the result of the dialog
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == updateButton) {
                System.out.println("update");
                // Update the required quantity
                String newQuantity = quantityField.getText();
                // Perform the update logic here
                try {
                    new ingredient_cost_model().update_ingredient_cost_qty(rowData.get_id(), Double.parseDouble(newQuantity));
                    product_table.getItems().clear();
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                }
                // You can call a method to update the required quantity in your model or database
            } else if (result.isPresent() && result.get() == cancelButton) {
                // Cancel the update
                System.out.println("Update canceled");
            }
            else if (result.isPresent() && result.get() == deleteButton) {
                // Cancel the update
                try {
                    new ingredient_cost_model().delete_ingredient_cost(rowData.get_id());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    return row;
});


        
        
        
    }


}
