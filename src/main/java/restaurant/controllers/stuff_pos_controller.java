package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import restaurant.models.product;
import restaurant.models.product_model;
import restaurant.models.store_ingredient;
import restaurant.models.store_ingredient_model;
import restaurant.models.user;
import restaurant.models.ingredient_cost_model;
import restaurant.models.invoice_model;
import restaurant.models.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Collections;
import java.util.HashMap;

import restaurant.controllers.order_controller;
import restaurant.models.Invoice;
import restaurant.models.category_model;
import restaurant.models.orders_model;
import restaurant.models.ingredient_cost;
import restaurant.models.category;
import restaurant.controllers.order_succ_controller;

public class stuff_pos_controller implements Initializable {
    private String storename;
    private product_model productmodel;
    private user current_user;
    private order_controller ordercontroller;
    private FXMLLoader order_view;
    private AnchorPane order_view_root;
    private ingredient_cost_model ingredient_cost;
    private store_ingredient_model storeingredientmodel;
    private int order_row;
    private Invoice invoice;
    private orders_model ordersmodel;
    private int storeID;
    private double src_sub_total;
    private category_model CategoryModel;
    private ArrayList<category> categories;
    private Map<String, Integer> categoryMap;
    private invoice_model InvoiceModel;
    private Parent order_succesful_card;
    


    public stuff_pos_controller() throws SQLException, IOException {

        this.ingredient_cost = new ingredient_cost_model();

        order_row = 0;
        ordersmodel = new orders_model();
    }

    protected void pos_set_invoice(Invoice invoice) {
        this.invoice = invoice;
    }
    protected void set_storeID(int storeID){
        this.storeID = storeID;
    }

    protected store_ingredient_model get_storeingredientmodel(){
        return this.storeingredientmodel;
    }

    protected int get_current_storeID(){
        return this.storeID;
    }

    @FXML
    private ChoiceBox<String> category_choicebox;

    @FXML
    private GridPane order_grid_pane;

    @FXML
    private MenuButton category;

    @FXML
    private GridPane product_grid;

    @FXML
    private ScrollPane product_scroll;

    @FXML
    private TextField productsearch_tf;

    @FXML
    private Label store_name;

    @FXML
    private Label subtotal;

    @FXML
    private Label total;

    @FXML
    private TextField discount;

    @FXML
    private HBox no_order_hbox;

    public GridPane get_order_grid_pane() {
        return this.order_grid_pane;
    }
    @FXML
    void cancel_order_e(MouseEvent event) {
        System.out.println("cancel order");
        
    }
    public Parent load_order_successfuly_card() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/restaurant/views/create_order_successfuly.fxml"));
        return fxmlLoader.load();
    }
    
    @FXML
    void create_new_order_e(MouseEvent event) throws IOException{
        int orderCount = this.ordersmodel.get_orders().size();
        if(orderCount == 0){
            no_order_hbox.setVisible(true);
        }else{
            for (order listOrder : this.ordersmodel.get_orders()) {
                listOrder.setIsdone(true);
                ordersmodel.create_order(listOrder);

                ResultSet rs;
                try {
                    rs = ingredient_cost.retrive_ingredient_cost_by_productID(listOrder.getproduct().getID());
                    while (rs.next()) {
                        if(listOrder.getproduct().getID() == rs.getInt("productID")){
                            double ingredient_cost_quantity = rs.getDouble("quantity");
                            int product_order_qty = listOrder.getquantity();
                            double totalcost = ingredient_cost_quantity * product_order_qty;
                            storeingredientmodel.cost_ingredient_by_storeID(this.current_user.getStoreID(), rs.getInt("ingredientID"), totalcost);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            Dialog<ButtonType> dialog = new Dialog<>();
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                dialog.getDialogPane().setContent(order_succesful_card);
                Optional<ButtonType> result = dialog.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.out.println("OK button is clicked");        
                }
            this.ordersmodel.get_orders().clear();
            no_order_hbox.setVisible(false);
        }
        
        
    }
    protected void load_category() throws SQLException{
        
        ResultSet rs = this.CategoryModel.retrieve_all_category();
        while(rs.next()){
            category Category = new category();
            int categoryID = rs.getInt("ID");
            String category_name = rs.getString("name");
            Category.setId(categoryID);
            Category.setName(category_name);
            this.categories.add(Category);
            this.categoryMap.put(category_name, categoryID);
        }
        for(category c : this.categories){
            this.category_choicebox.getItems().add(c.getName());
        }
    }
    void setTotal(){
        /**
         * Calculates the total amount due after applying a discount to the subtotal.
         * 
         * This code is responsible for parsing the subtotal and discount values from
         * text fields,
         * calculating the total amount due by subtracting the discount from the
         * subtotal, and
         * updating the total text field with the formatted total amount.
         */
        this.ordersmodel.updateSubtotal();
        double st = this.src_sub_total;
        double dis = Double.parseDouble(this.discount.getText());
        double total = st - dis;
        this.total.setText("P" + String.valueOf(total));
    }

    public void set_pos_currentuser(user current_user) {
        this.current_user = current_user;
        this.storeID = this.current_user.getStoreID();
    }

    void set_onchange_discount(TextField discount) {
        /**
         * Listens for changes to the discount text property and updates the total
         * accordingly.
         * If the new discount value is greater than or equal to the current discount
         * value, no action is taken.
         * If the discount text is empty, no action is taken.
         * Otherwise, the `setTotal()` method is called to update the total.
         */
        this.discount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // discount > new value
                if (Double.parseDouble(discount.getText()) > Double.parseDouble(discount.getText())) {
                    //no action
                } else if (newValue.isEmpty()) {
                    //no action
                } else if (discount.getText().isEmpty()) {
                    //no action
                } else {
                    setTotal();
                }

            }

        });
    }
    public product_model get_pos_product_model(){
        return this.productmodel;
    }
    public void product_load() throws SQLException {
        this.ordersmodel.updateSubtotal();
        src_sub_total = ordersmodel.get_sub_total();
        this.subtotal.setText("P"+src_sub_total);
        this.setTotal();
        
        ObservableList<product> products = this.productmodel.get_all_products();
        int row = 1;
        int column = 0;
    
        // Clear the product grid before reloadiang products
        product_grid.getChildren().clear();
    
        /**
         * Loads and displays product cards in a grid layout, handling click events to
         * create new orders.
         * This code is responsible for the following tasks:
         * - Loads product card UI elements and sets their data
         * - Filters ingredient costs and store ingredients based on the current product
         * - Calculates the stock available for each product
         * - Adds event handlers to the product cards to handle click events
         * - Creates new order UI elements and updates the order grid, order model, and
         * total subtotal when a product is clicked
         */
        try {
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/restaurant/views/product_card.fxml"));
                AnchorPane product_card = fxmlLoader.load();
                product_card_controller productcardcontroller = fxmlLoader.getController();

                ArrayList<ingredient_cost> all_ingredients_cost = this.ingredient_cost.get_all_Ingredient_costs();
                ArrayList<ingredient_cost> filtered_Ingredient_costs = new ArrayList<>();
                ArrayList<store_ingredient> st = this.storeingredientmodel.get_store_Ingredients();
                ArrayList<store_ingredient> filtered_storeingredientmodel = new ArrayList<>();
                ArrayList<Double> calculate_stock = new ArrayList<>();
                // Filter ingredient costs and store ingredients
                for (int ii = 0; ii < all_ingredients_cost.size(); ii++) {
                    int productID = all_ingredients_cost.get(ii).getProductID();
                    if (productID == products.get(i).getID()) {
                        filtered_Ingredient_costs.add(all_ingredients_cost.get(ii));
                    }
                }

                for (store_ingredient ST : st) {
                    if (ST.getStoreID() == this.storeID) {
                        filtered_storeingredientmodel.add(ST);
                    }
                }

                // Calculate product stock
                for (int iii = 0; iii < filtered_Ingredient_costs.size(); iii++) {
                    int ProductingredientID = filtered_Ingredient_costs.get(iii).getIngredientID();
                    double ingredient_cost_per_current_product = filtered_Ingredient_costs.get(iii).getQuantity();

                    for (store_ingredient fst : filtered_storeingredientmodel) {
                        if (fst.get_ingredientID() == ProductingredientID) {
                            double productfromingredient = fst.get_stock() / ingredient_cost_per_current_product;
                            calculate_stock.add(productfromingredient);
                        }
                    }
                }

                double product_stock = Collections.min(calculate_stock);
                products.get(i).setStock((int) product_stock);
                productcardcontroller.setdata(products.get(i));

                // Add event handler for product card click
                int index = i;
                product_card.setOnMouseClicked(event -> {
                    if (this.ordersmodel.order_exist(products.get(index))) {
                        System.out.println("order exist");
                    } else {
                        try {
                            FXMLLoader orderfxmlLoader = new FXMLLoader();
                            orderfxmlLoader.setLocation(getClass().getResource("/restaurant/views/order_view.fxml"));
                            AnchorPane orderPane = orderfxmlLoader.load();
                            order_controller order_controller = orderfxmlLoader.getController();
                            for (ingredient_cost fic : filtered_Ingredient_costs) {
                                for (store_ingredient fsi : filtered_storeingredientmodel) {
                                    if (fsi.get_ingredientID() == fic.getIngredientID()) {
                                        double newstock = fsi.get_stock() - fic.getQuantity();
                                        System.out.println("Old Stock: " + fsi.get_stock());
                                        fsi.set_stock(newstock);
                                    }
                                }
                            }
                            order Order = new order();
                            
                            Order.setinvoice(this.invoice);
                            Order.setproduct(products.get(index));
                            order_controller.setdata(Order);
                            order_controller.setRoot(orderPane);
                            order_controller.set_order_order_row(this.order_row);
                            order_grid_pane.add(orderPane, 0, this.order_row++);
                            this.ordersmodel.add_order(Order);
                            this.ordersmodel.updateSubtotal();
                            order_controller.set_pos(this);
                            this.subtotal.setText("P" + src_sub_total);
                            this.setTotal();
                            product_load();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                if (column == 4) {
                    column = 0;
                    row++;
                }

                // Add the product card to the grid
                this.product_grid.add(product_card, column++, row);
                GridPane.setMargin(product_card, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public orders_model get_ordersmodel() {
        return this.ordersmodel;
    }

    /**
     * Sets a listener on the category choice box that updates the product list when
     * a new category is selected.
     * When a new category is selected, the product list is cleared and the products
     * for the selected category are retrieved and loaded.
     */
    public void set_category_listener() {
        this.category_choicebox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        this.productmodel.clear_product_list();
                        int categoryID = this.categoryMap.get(newValue);
                        this.productmodel.retrieve_product_by_category(categoryID);
                        product_load();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

        /**
     * Initializes the controller by retrieving and setting up the necessary models
     * and data.
     * This method is called when the controller is first loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.productmodel = new product_model();
        this.productmodel.retrieve_all_product();
        this.ingredient_cost = new ingredient_cost_model();
        try {
            this.ingredient_cost.retrive_all_ingredient_cost();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.storeingredientmodel = new store_ingredient_model();
        try {
            this.storeingredientmodel.retrieve_all_storeIngredient();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.ordersmodel = new orders_model();
        set_onchange_discount(this.discount);
        this.categories = new ArrayList<>();
        this.CategoryModel = new category_model();
        this.categoryMap = new HashMap<>();
        this.set_category_listener();
        this.InvoiceModel = new invoice_model();
        try {
            this.order_succesful_card = load_order_successfuly_card();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}