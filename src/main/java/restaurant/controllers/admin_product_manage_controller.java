package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import restaurant.models.Product_table_cell;
import restaurant.models.product;
import restaurant.models.product_categories_model;
import restaurant.models.product_model;
import restaurant.models.product_multiple_category;

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
        System.out.println("ogey");
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

                    if(Product.get_categories().size() == 0 || Product.getName().equals("") || Product.getPicture() == null||Product.getPrice() == 0.0){
                        System.out.println("error");
                        //add some error dialog with text fill out all product required
                        Dialog<ButtonType> error_dialog = new Dialog<>();
                        error_dialog.setTitle("ADD PRODUCT ERROR PLEASE FILL OUT PRODUCT REQUIRED");
                        error_dialog.setDialogPane(new DialogPane());
                        error_dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        error_dialog.showAndWait();

                    }else{
                        int generated_primary_key = new product_model().create_product_get_generate_primary_key(Product.getName(), Product.getPrice(), Product.getPicture(), null);
                
                        for(int i: Product.get_categories()){
                            new product_categories_model().create_product_categories(generated_primary_key,i);
                    }
                }
                } catch (Exception e) {
                    System.out.println("the price not set properly");
                    Dialog<ButtonType> error_dialog = new Dialog<>();
                    error_dialog.setTitle("ADD PRODUCT ERROR PLEASE FILL OUT PRODUCT REQUIRED");
                    error_dialog.setDialogPane(new DialogPane());
                    error_dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    error_dialog.showAndWait();

                }
                
                
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void setcellvalue(){
        product_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStringPrice()));
        product_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ProductModel = new product_model();
        setcellvalue();
        product_table.setItems(new product_model().get_all_product_for_cell());
        // product_table.setItems();
        
    }


}
