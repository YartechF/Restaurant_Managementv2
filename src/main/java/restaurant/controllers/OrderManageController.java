package restaurant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrderManageController {
    private int storeID;

    @FXML
    private TableColumn<?, ?> action;

    @FXML
    private TableColumn<?, ?> bill_no;

    @FXML
    private TableColumn<?, ?> data_time;

    @FXML
    private TableView<?> order_table;

    @FXML
    private TableColumn<?, ?> paid_status;

    @FXML
    private TableColumn<?, ?> total_amount;

    @FXML
    private TableColumn<?, ?> total_product;
    public void setStoreID(int storeID){
        this.storeID = storeID;
    }
}