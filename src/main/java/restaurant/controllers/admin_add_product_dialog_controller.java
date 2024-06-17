package restaurant.controllers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import restaurant.models.admin_category_model;
import restaurant.models.category;
import restaurant.models.category_model;
import restaurant.models.product;
import restaurant.models.product_multiple_category;

public class admin_add_product_dialog_controller implements Initializable {
    private admin_category_model CategoryModel;
    private ObservableList<category> product_category_list;
    private FileChooser fil_chooser;
    private String product_img_file_path;
    private product_multiple_category Product;
    private ObservableList<Integer> ingredientID_list;


    @FXML
    private ScrollPane category_box;

    @FXML
    private Button insert_img_btn;

    @FXML
    private TextField price_tf;

    @FXML
    private TableColumn<category, String> product_category_col;

    @FXML
    private TableView<category> product_category_table;

    @FXML
    private ImageView product_img;

    @FXML
    private TextField product_name_tf;

    @FXML
    private TextField search_category_tf;
    
    @FXML
    private TableColumn<category, String> category_col;

    @FXML
    private TableView<category> category_table;

    void setcellvalue(){
        category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        product_category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }

    void set_product_category(){
        for(category p : this.product_category_list){
            product_category_table.getItems().add(p);
        }
    }

    @FXML
    void Insert_img_btn(KeyEvent event) {
        System.out.println("gsdfg");
        File selectedFile = fil_chooser.showOpenDialog(null);
        if (selectedFile != null) {
            this.product_img_file_path = selectedFile.getAbsolutePath();
            File file = new File(this.product_img_file_path);
            Image image = new Image(file.toURI().toString());
            product_img.setImage(image);
        }else{
            System.out.println("no file path");
        }
    }

    public product_multiple_category get_created_product(){
        this.Product.setName(this.product_name_tf.getText());
        this.Product.setPrice(Double.parseDouble(this.price_tf.getText()));
        this.Product.setPicture(this.product_img_file_path);
        for(int i :ingredientID_list){
            this.Product.set_categories(i);
        }
        return this.Product;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Product = new product_multiple_category();
        this.CategoryModel = new admin_category_model();
        setcellvalue();

        this.fil_chooser = new FileChooser();

        this.product_category_list = FXCollections.observableArrayList();



        ObservableList<category> category_list = this.CategoryModel.get_category_for_admin();
        HashMap<String,Integer> categoriesID = new HashMap<>();
        this.ingredientID_list = FXCollections.observableArrayList();
        for(category c:category_list){
            categoriesID.put(c.getName(), c.getId());
            category_table.getItems().add(c);
        }

        //create a categorytable double row listener
        category_table.setRowFactory(tv -> {
            TableRow<category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    category rowData = row.getItem();
                    int num_check = categoriesID.get(rowData.getName());
                    if(ingredientID_list.contains(num_check)){
                        System.out.println("category_exist");
                    }else{
                        product_category_table.getItems().clear();
                        this.product_category_list.add(rowData);
                        set_product_category();
                        System.out.println("category_not_exist");
                        ingredientID_list.add(rowData.getId());
                    }
                }
            });
            return row ;
        });

        product_category_table.setRowFactory(tv -> {
            TableRow<category> product_category_table_row = new TableRow<>();
            product_category_table_row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! product_category_table_row.isEmpty()) ) {
                    category rowData = product_category_table_row.getItem();
                    ingredientID_list.remove(categoriesID.get(rowData.getName()));
                    product_category_list.remove(rowData);
                    product_category_table.getItems().clear();
                    set_product_category();
                }
            });
            return product_category_table_row ;
        });


        

        search_category_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the inventory table based on the search text
            
        });
        

    }


}
