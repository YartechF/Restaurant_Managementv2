package restaurant.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import restaurant.models.AdminProductInventory;
import restaurant.models.category;
import restaurant.models.category_model;
import restaurant.models.ingredient_cost_model;

public class admin_manage_category_controller implements Initializable {

    @FXML
    private TableColumn<category, String> category_col;

    @FXML
    private TableView<category> category_table;

    @FXML
    void add_category_btn_e(MouseEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle("");
                    dialog.setHeaderText("Create_Category");

                    // Create a text field for entering the new quantity
                    TextField quantityField = new TextField();

                    // Create action buttons
                    ButtonType create = new ButtonType("Create", ButtonData.OK_DONE);
                    ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    // Set the button types and content for the dialog
                    dialog.getDialogPane().getButtonTypes().addAll(create, cancel);
                    dialog.getDialogPane().setContent(quantityField);

                    // Handle the result of the dialog
                    Optional<ButtonType> result = dialog.showAndWait();
                    if (result.isPresent() && result.get() == create) {
                        System.out.println("update");
                        // Update the required quantity
                        String newQuantity = quantityField.getText();
                        // Perform the update logic here
                        try {
                            new category_model().create_category(newQuantity);
                            category_table.getItems().clear();
                            this.category_table.getItems().addAll(new category_model().get_categoryOBList());

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        // You can call a method to update the required quantity in your model or database
                        } else if (result.isPresent() && result.get() == cancel) {
                            // Cancel the update
                            System.out.println("create cancel canceled");
                        }
    }

    private void setcellvalue(){
        category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setcellvalue();
        this.category_table.getItems().addAll(new category_model().get_categoryOBList());

        //
            category_table.setRowFactory(tv -> {
            TableRow<category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    category rowData = row.getItem();
                    //create update dialog
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle("");
                    dialog.setHeaderText("Update Category");

                    // Create a text field for entering the new quantity
                    TextField quantityField = new TextField();
                    quantityField.setText(rowData.getName());

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
                            new category_model().update_category(rowData.getId(), newQuantity);
                            category_table.getItems().clear();
                            this.category_table.getItems().addAll(new category_model().get_categoryOBList());

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        // You can call a method to update the required quantity in your model or database
                        } else if (result.isPresent() && result.get() == cancelButton) {
                            // Cancel the update
                            System.out.println("Update canceled");
                            
                        }
                        else if (result.isPresent() && result.get() == deleteButton) {
                            new category_model().delete_category(rowData.getId());
                            category_table.getItems().clear();
                            this.category_table.getItems().addAll(new category_model().get_categoryOBList());
                        }


                        //INSERT INTO `tbl_ingredient_cost` (`ID`, `ingredientID`, `productID`, `quantity`) VALUES (NULL, '6', '23', '0.25');
                    }
            });
            return row;
        });
    }

}
