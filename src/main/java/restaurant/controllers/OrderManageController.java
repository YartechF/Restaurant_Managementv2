package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.google.protobuf.Internal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import restaurant.models.invoice_model;
import restaurant.models.product;
import restaurant.models.product_model;
import restaurant.models.Invoice;
import restaurant.models.bill;
import restaurant.models.bill_data;
import restaurant.controllers.stuff_controller;

public class OrderManageController implements Initializable {
    private int storeID;
    private bill_data Billdata;
    private stuff_controller StuffController;
    ObservableList<Invoice> invoice = FXCollections.observableArrayList();
    private AnchorPane update_order_pane;
    private update_order_controller update_order_controller;
    private product_model pos_product_model;

    @FXML
    private TableColumn<bill, String> action;

    @FXML
    private TableColumn<bill, String> bill_no;//or invoice id

    @FXML
    private TableColumn<bill, String> data_time;

    @FXML
    private TableView<bill> order_table;

    @FXML
    private TableColumn<bill, String> paid_status;

    @FXML
    private TableColumn<bill, String> total_amount;

    @FXML
    private TableColumn<bill, String> total_product;

    @FXML
    private TableColumn<bill, String> table;

    @FXML
    private TableColumn<bill, String> order_type;

    void load_update_order() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/restaurant/views/update_order.fxml"));
        this.update_order_pane = fxmlLoader.load();
        this.update_order_controller = fxmlLoader.getController();
    }
    public void set_pos_product_model(product_model pos_product_model) throws IOException{
        this.pos_product_model = pos_product_model;
        this.update_order_controller.set_pos_product_model(this.pos_product_model);
    }
    

    public void set_staff_controller(stuff_controller StuffController){
        this.StuffController = StuffController;
    }
    
    public void setStoreID(int storeID){
        this.storeID = storeID;
    }
    void setcellvalue(){
        bill_no.setCellValueFactory(new PropertyValueFactory<bill, String>("ID"));// or bill number
        bill_no.getStyleClass().add("centered-cell");
        data_time.setCellValueFactory(new PropertyValueFactory<bill, String>("date"));
        data_time.getStyleClass().add("centered-cell");
        paid_status.setCellValueFactory(new PropertyValueFactory<bill, String>("paid_status"));
        paid_status.getStyleClass().add("centered-cell");
        total_amount.setCellValueFactory(new PropertyValueFactory<bill, String>("total_amount"));
        total_amount.getStyleClass().add("centered-cell");
        total_product.setCellValueFactory(new PropertyValueFactory<bill, String>("total_product"));
        total_product.getStyleClass().add("centered-cell");
        table.setCellValueFactory(new PropertyValueFactory<bill, String>("table"));
        table.getStyleClass().add("centered-cell");
        order_type.setCellValueFactory(new PropertyValueFactory<bill, String>("order_type"));
        order_type.getStyleClass().add("centered-cell");
    }
    public bill_data OrderManageControllerBillData(){
        return Billdata;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Billdata = new bill_data();
        setcellvalue();
        Billdata.set_invoice_by_storeID(this.storeID);
        this.order_table.setItems(Billdata.getBill_data());

        

        order_table.setRowFactory(tv -> {
            TableRow<bill> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    bill selectedBill = row.getItem();
                    // Perform action on the selected bill
                    //the bill id is invoiceID
                    System.out.println("Selected Bill: " + selectedBill.getID());//the bill ID is invoiceID
                    this.StuffController.get_stuff_page().getChildren().clear();
                    this.StuffController.get_stuff_page().getChildren().setAll(update_order_pane);
                    this.update_order_controller.set_invoiceID(Integer.valueOf(selectedBill.getID()));
                }
            });
            row.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    row.getStyleClass().add("selected-row");
                } else {
                    row.getStyleClass().remove("selected-row");
                }
            });
            return row;
        });
        try {
            load_update_order();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}