package restaurant.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimerTask;

import com.mysql.cj.protocol.ResultsetRow;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import restaurant.models.Invoice;
import restaurant.models.invoice_model;
import restaurant.models.order;
import restaurant.models.order_manager_invoice_model;
import restaurant.models.orders_model;
import restaurant.models.product_inventory;
import restaurant.models.table_invoice;
import restaurant.models.user;
import java.util.Timer;

public class order_manager_controller implements Initializable {
    private user current_user;
    private order_manager_invoice_model InvoiceModel;
    private Timer timer;
    private orders_model OrdersModel;
    private int Item_order_row;
    private int current_selected_invoiceID;

    @FXML
    private TableColumn<table_invoice, String> invoice_id_col;

    @FXML
    private Button mark_as_done_btn;

    @FXML
    private GridPane order_grid_pane;

    @FXML
    private TableView<table_invoice> order_table;

    @FXML
    private TableColumn<table_invoice, String> pending_orders_col;

    @FXML
    private Label storename;

    @FXML
    private Label table;

    @FXML
    private TableColumn<table_invoice, String> table_col;

    @FXML
    private TableColumn<table_invoice, String> total_product_col;

    @FXML
    void mark_as_done_btn_e(MouseEvent event) {
        // TODO Auto-generated method stub
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Mark as done");
        dialog.setHeaderText("Are you sure you want to mark this order as done?");
        dialog.setContentText("If you click yes, the order will be marked as done.");
        //add yes button
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
        
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.YES) {
            System.out.println("yes");
            try {
                this.InvoiceModel.mark_as_done(this.current_selected_invoiceID);
                this.fetchLatestData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void SetCurrentUser(user currentuser){
        this.current_user = currentuser;
        this.storename.setText("Order Manager "+currentuser.getStorename());
        refresh();
    }
    public void refresh(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
        @Override
        public void run() {
            fetchLatestData();
        }
        };
        timer.scheduleAtFixedRate(task, 0, 5000); // Fetch data every 5 seconds
    }
    public void fetchLatestData(){
        this.InvoiceModel.retreive_all_invoice_by_storeID(this.current_user.getStoreID());
        // set cell data
        SetCellValue();
        this.order_table.setItems(this.InvoiceModel.getTableInvoice());

    }
    void SetCellValue(){
        this.invoice_id_col.setCellValueFactory(new PropertyValueFactory<table_invoice, String>("ID"));// or bill number
        this.invoice_id_col.getStyleClass().add("centered-cell");
        this.table_col.setCellValueFactory(new PropertyValueFactory<table_invoice, String>("table"));
        this.table_col.getStyleClass().add("centered-cell");
        this.pending_orders_col.setCellValueFactory(new PropertyValueFactory<table_invoice, String>("pending_orders"));
        this.pending_orders_col.getStyleClass().add("centered-cell");
        this.total_product_col.setCellValueFactory(new PropertyValueFactory<table_invoice, String>("total_product"));
        this.total_product_col.getStyleClass().add("centered-cell");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.InvoiceModel = new order_manager_invoice_model();
        this.OrdersModel = new orders_model();
        this.order_table.setRowFactory(tv -> {
            TableRow<table_invoice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    this.order_grid_pane.getChildren().clear();
                    this.InvoiceModel.get_orders().clear();
                    table_invoice selectedrow = row.getItem();
                    // Perform action on the selected product
                    this.table.setText(selectedrow.getTable()+" Orders");
                    try {
                        Item_order_row = 0;
                        this.InvoiceModel.retrieve_orders_on_order_manager(Integer.parseInt(selectedrow.getID()));
                        this.current_selected_invoiceID = Integer.parseInt(selectedrow.getID());
                        ObservableList<order> Orders = this.InvoiceModel.get_orders();
                        

                        for(order o :Orders){
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurant/views/order_item_view.fxml"));
                            AnchorPane OrderItem = fxmlLoader.load();
                            order_item_view_controller OrderItemController = fxmlLoader.getController();
                            OrderItemController.setData(o);
                            this.order_grid_pane.add(OrderItem, 0, Item_order_row++);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
                }
            }); 
            return row;
        });
        
        
    }
}
