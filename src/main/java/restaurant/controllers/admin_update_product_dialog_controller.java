package restaurant.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
import restaurant.models.store_model;
import restaurant.models.store_table_cell;
import restaurant.models.table;

public class admin_update_product_dialog_controller implements Initializable {
    private admin_category_model CategoryModel;
    private ObservableList<category> product_category_list;
    private ObservableList<store_table_cell> product_store_list;
    private FileChooser fil_chooser;
    private String product_img_file_path;
    private product_multiple_category Product;
    private ObservableList<Integer> ingredientID_list;
    private ObservableList<Integer> storeID_list;
    private store_model StoreModel;
    private ArrayList<String> sql_commands;
    private ObservableList<category> categories_obj;
    private ObservableList<store_table_cell> stores_obj;
    private int current_productID;

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
    private TableColumn<category, String> category_col;

    @FXML
    private TableView<category> category_table;

    @FXML
    private TableColumn<store_table_cell, String> product_store_col;

    @FXML
    private TableView<store_table_cell> product_store_table;

    @FXML
    private TableColumn<store_table_cell, String> store_col;

    @FXML
    private TableView<store_table_cell> store_table;

    
    public ArrayList<String> get_sql_commands(){
        return this.sql_commands;
    }

    void setcellvalue(){
        category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        product_category_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        product_store_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        store_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }

    void set_product_category(){
        for(category p : this.product_category_list){
            product_category_table.getItems().add(p);
        }
    }
    void set_product_store(){
        for(store_table_cell s : this.product_store_list){
            product_store_table.getItems().add(s);
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
        this.Product.setID(this.current_productID);
        this.Product.setName(this.product_name_tf.getText());
        this.Product.setPrice(Double.parseDouble(this.price_tf.getText()));
        this.Product.setPicture(this.product_img_file_path);
        return this.Product;
    }

    public void set_product(product_multiple_category product){
        this.Product = product;
        this.product_name_tf.setText(product.getName());
        this.price_tf.setText(String.valueOf(product.getPrice()));
        this.product_img_file_path = product.getPicture();
        File file = new File(this.product_img_file_path);
        Image image = new Image(file.toURI().toString());
        product_img.setImage(image);
        this.ingredientID_list = product.get_categories();
        this.storeID_list = product.get_stores();
        this.product_category_list = product.get_categories_obj();
        this.product_store_list = product.get_stores_obj();

        this.product_category_table.getItems().setAll(product.get_categories_obj());
        this.product_store_table.getItems().setAll(product.get_stores_obj());
        this.current_productID = product.getID();

        for(category c: this.product_category_list){
            ingredientID_list.add(c.getId());
        }
        for(store_table_cell s:this.product_store_list){
            this.storeID_list.add(s.getID());
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Product = new product_multiple_category();
        this.CategoryModel = new admin_category_model();
        this.StoreModel = new store_model();
        this.sql_commands = new ArrayList<>();


        
        setcellvalue();

        this.fil_chooser = new FileChooser();

        this.product_category_list = FXCollections.observableArrayList();



        this.StoreModel.retrieve_all_store();
        HashMap<String,Integer> storeIDMap = new HashMap<>();
        this.storeID_list = FXCollections.observableArrayList();
        this.product_store_list = FXCollections.observableArrayList();
        for(store_table_cell s :this.StoreModel.getStores()){
            storeIDMap.put(s.getName(), s.getID());
            store_table.getItems().add(s);
        }
        /////set store double click store click add store to list
        store_table.setRowFactory(tv -> {
            TableRow<store_table_cell> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    store_table_cell rowData = row.getItem();
                    int num_check = storeIDMap.get(rowData.getName());
                    if(storeID_list.contains(num_check)){
                        System.out.println("store_exist");
                    }else{
                        System.out.println("add");
                        product_store_table.getItems().clear();
                        this.product_store_list.add(rowData);
                        set_product_store();
                        storeID_list.add(rowData.getID());
                        String sql = "insert into store_product (productID, storeID) values ("+this.current_productID+", "+rowData.getID()+")";
                        this.sql_commands.add(sql);
                    }
                }
            });
            return row ;
        });
        ///////////////////////////////////

        //show all or retreve the categories from the database
        ObservableList<category> category_list = this.CategoryModel.get_category_for_admin();
        HashMap<String,Integer> categoriesIDMap = new HashMap<>();
        this.ingredientID_list = FXCollections.observableArrayList();
        for(category c:category_list){
            categoriesIDMap.put(c.getName(), c.getId());
            category_table.getItems().add(c);
        }

        //create a categorytable double row listener
        category_table.setRowFactory(tv -> {
            TableRow<category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    category rowData = row.getItem();
                    int num_check = categoriesIDMap.get(rowData.getName());
                    if(ingredientID_list.contains(num_check)){
                        System.out.println("category_exist");
                    }else{
                        product_category_table.getItems().clear();
                        this.product_category_list.add(rowData);
                        set_product_category();
                        System.out.println("category_not_exist");
                        ingredientID_list.add(rowData.getId());
                        String sql = "insert into tbl_product_categories (productID, categoryID) values ("+this.current_productID+", "+rowData.getId()+")";
                        this.sql_commands.add(sql);
                    }
                }
            });
            return row ;
        });
/// remove category from the list when double click on the table
        product_category_table.setRowFactory(tv -> {
            TableRow<category> product_category_table_row = new TableRow<>();
            product_category_table_row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! product_category_table_row.isEmpty()) ) {
                    category rowData = product_category_table_row.getItem();
                    ingredientID_list.remove(categoriesIDMap.get(rowData.getName()));
                    product_category_list.remove(rowData);
                    product_category_table.getItems().clear();
                    set_product_category();
                    String sql = "delete from tbl_product_categories where categoryID = "+rowData.getId()+" and productID = "+this.current_productID;
                    this.sql_commands.add(sql);
                    
                }
            });
            return product_category_table_row ;
        });
////// remove store from the list when double click on the table 
        product_store_table.setRowFactory(tv -> {
            TableRow<store_table_cell> product_store_table_row = new TableRow<>();
            product_store_table_row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! product_store_table_row.isEmpty()) ) {
                    store_table_cell rowData = product_store_table_row.getItem();
                    storeID_list.remove(storeIDMap.get(rowData.getName()));
                    product_store_list.remove(rowData);
                    product_store_table.getItems().clear();
                    set_product_store();
                    String sql = "delete from store_product where storeID = "+rowData.getID()+" and productID = "+this.current_productID;
                    this.sql_commands.add(sql);
                }
            });
            return product_store_table_row ;
        });


        

        
        

    }


}
