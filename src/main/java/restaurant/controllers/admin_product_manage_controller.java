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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import restaurant.models.Product_table_cell;
import restaurant.models.product;
import restaurant.models.product_categories_model;
import restaurant.models.product_model;
import restaurant.models.product_multiple_category;
import restaurant.models.store_model;
import restaurant.models.store_product_model;

public class admin_product_manage_controller implements Initializable {
    private product_model ProductModel;

    @FXML
    private TableColumn<Product_table_cell, String> category_col;

    @FXML
    private TableColumn<Product_table_cell, String> price_col;

    @FXML
    private TableColumn<Product_table_cell, String> product_name_col;

    @FXML
    private TableView<Product_table_cell> product_table;

    @FXML
    private AnchorPane productcard_img;

    @FXML
    private ChoiceBox<Product_table_cell> store_choice_box;

    @FXML
    void add_product_e(MouseEvent event) {
        boolean Is_error = false;
        while(!Is_error){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/admin_add_product_dialog.fxml"));
                DialogPane admin_add_product_dialog = loader.load();
                admin_add_product_dialog_controller add_product_controller = loader.getController();
    
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(admin_add_product_dialog);
    
                Optional<ButtonType> result = dialog.showAndWait();
    
                if(result.get() == ButtonType.OK){
                    try {
                        product_multiple_category Product = add_product_controller.get_created_product();
                    //print all Product
    
                        if(Product.get_categories().size() == 0 || Product.get_stores().size() == 0 || Product.getName().equals("") || Product.getPicture() == null||Product.getPrice() == 0.0){
                            System.out.println("error");
                            //add some error dialog with text fill out all product required
                            Dialog<ButtonType> dialogerr = new Dialog<>();
                            ButtonType cancelButton = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
                            dialogerr.setTitle("Update Required Quantity");
                            dialogerr.setHeaderText("Please enter product requirements");
                            dialogerr.getDialogPane().getButtonTypes().addAll(cancelButton);
                            Optional<ButtonType> res = dialogerr.showAndWait();
                            
                            if (result.isPresent() && result.get() == cancelButton) {
                                dialogerr.close();
                                dialog.close();
                                Is_error = true;
                            }
                            // Create a text field for entering the new quantity
    
                        }else{
                            int generated_primary_key = new product_model().create_product_get_generate_primary_key(Product.getName(), Product.getPrice(), Product.getPicture(), null);
                    
                            for(int i: Product.get_categories()){
                                new product_categories_model().create_product_categories(generated_primary_key,i);
                            }
                            for(int s :Product.get_stores()){
                                new store_product_model().create_store_product(s, generated_primary_key);
                            }   
    
                        }
                    } catch (Exception e) {
                        Dialog<ButtonType> dialogerr = new Dialog<>();
                            ButtonType cancelButton = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
                            dialogerr.setTitle("Update Required Quantity");
                            dialogerr.setHeaderText("Please enter product requirements");
                            dialogerr.getDialogPane().getButtonTypes().addAll(cancelButton);
                            Optional<ButtonType> res = dialogerr.showAndWait();
                            
                            if (result.isPresent() && result.get() == cancelButton) {
                                dialog.close();
                                dialogerr.close();
                                Is_error = true;
                            }
                    }
                    
                    
                }else if(result.get() == ButtonType.CANCEL){
                    dialog.close();
                    Is_error = true;

                }
    
    
    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        

    }


    void setcellvalue(){
        this.price_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStringPrice()));
        this.price_col.getStyleClass().add("centered-cell");
        this.product_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        this.product_name_col.getStyleClass().add("centered-cell");
        this.category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        this.category_col.getStyleClass().add("centered-cell");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ProductModel = new product_model();
        setcellvalue();
        product_table.setItems(new product_model().get_all_product_for_cell());
        
        // product_table.setItems();

        //set product_table 2 click row lestener

        product_table.setRowFactory(tv -> {
            TableRow<Product_table_cell> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Product_table_cell rowData = row.getItem();
                    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/admin_update_product_dialog.fxml"));
                        DialogPane admin_add_product_dialog = loader.load();
                        admin_update_product_dialog_controller update_product_controller = loader.getController();


                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(admin_add_product_dialog);
                        ButtonType updateButton = new ButtonType("Update", ButtonData.OK_DONE);
                        ButtonType deleteButton = new ButtonType("Delete", ButtonData.FINISH);
                        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                        dialog.getDialogPane().getButtonTypes().addAll(updateButton, deleteButton,cancel);

                        Optional<ButtonType> result = dialog.showAndWait();
                        if(result.isPresent()){
                            if(result.get() == updateButton){
                                System.out.println("update");
                            }else if (result.get() == deleteButton){
                                new product_model().admin_delete_product(rowData.getID());
                            }
                            else if(result.get() == cancel){
                                dialog.close();
                            }
                        }

                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        
        
    }



}
