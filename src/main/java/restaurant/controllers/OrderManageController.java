package restaurant.controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import restaurant.models.Invoice;

public class OrderManageController implements Initializable {
    private int storeID;

    ObservableList<Invoice> invoice = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Invoice, ImageView> action;

    @FXML
    private TableColumn<Invoice, Integer> bill_no;//or invoice id

    @FXML
    private TableColumn<Invoice, String> data_time;

    @FXML
    private TableView<Invoice> order_table;

    @FXML
    private TableColumn<Invoice, String> paid_status;

    @FXML
    private TableColumn<Invoice, Double> total_amount;

    @FXML
    private TableColumn<Invoice, Integer> total_product;

    @FXML
    private TableColumn<Invoice, String> table;

    public void setStoreID(int storeID){
        this.storeID = storeID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }



}