package restaurant.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import restaurant.models.user;
import restaurant.models.Invoice;
import restaurant.models.invoice_model;
import restaurant.models.product_model;
import restaurant.controllers.create_invoice_dialog_controller;

public class stuff_controller {
    private int storeID;
    private FXMLLoader stuff_pos_view;
    private stuff_pos_controller stuff_pos_controller;
    private AnchorPane stuff_pos_root;
    private int personID;
    private String storename;
    private int user_type_ID;
    private user currentuser;
    private invoice_model invoicemodel;
    private DialogPane create_invoice_dialog;
    private Invoice invoice;
    private create_invoice_dialog_controller createinvoicedialogcontroller;
    private FXMLLoader order_manage_fxmlloader;
    private AnchorPane ordermanage_pane;
    private OrderManageController order_manage_controller;
    private product_model pos_product_model;

    void product_loading() throws SQLException {
        // load the pos
        try {
            this.stuff_pos_view = new FXMLLoader(getClass().getResource("/restaurant/views/stuff_pos.fxml"));
            this.stuff_pos_root = this.stuff_pos_view.load();
            this.stuff_pos_controller = this.stuff_pos_view.getController();
            this.stuff_pos_controller.set_pos_currentuser(this.currentuser);
            this.stuff_pos_controller.product_load();
            this.stuff_pos_controller.load_category();
            this.pos_product_model = this.stuff_pos_controller.get_pos_product_model();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void load_dialog_pane() throws IOException {
        FXMLLoader fxmlLoader3 = new FXMLLoader();
        fxmlLoader3.setLocation(getClass().getResource("/restaurant/views/create_invoice_dialog_view.fxml"));
        create_invoice_dialog = fxmlLoader3.load();
        this.createinvoicedialogcontroller = fxmlLoader3.getController();
        // get the controller
        
    }
    void load_order_manage() throws IOException{
        this.order_manage_fxmlloader = new FXMLLoader(getClass().getResource("/restaurant/views/Manage_order_view.fxml"));
        this.ordermanage_pane = this.order_manage_fxmlloader.load();
        this.order_manage_controller = this.order_manage_fxmlloader.getController();
    }



    @FXML
    private AnchorPane new_order_btn;

    @FXML
    private Pane stuff_page;

    @FXML
    void Order_e(MouseEvent event) throws IOException {
        order_manage_controller.set_staff_controller(this);
        order_manage_controller.set_pos_product_model(this.pos_product_model);
        order_manage_controller.OrderManageControllerBillData().getBill_data().clear();
        order_manage_controller.OrderManageControllerBillData().set_invoice_by_storeID(this.storeID);
        this.stuff_page.getChildren().setAll(ordermanage_pane);

    }

    public Pane get_stuff_page(){
        return this.stuff_page;
    }
    @FXML
    void dashboard_e(MouseEvent event) {
        System.out.println("dashboard");
    }

    @FXML
    void pos_e(MouseEvent event) throws SQLException {// create new order
        createinvoicedialogcontroller.set_storeID(this.storeID);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(create_invoice_dialog);
        dialog.setTitle("Create New Order");
        Optional<ButtonType> clickedbutton = dialog.showAndWait();

        if (clickedbutton.get() == ButtonType.OK) {
            this.new_order_btn.setDisable(true);
            invoice = createinvoicedialogcontroller.getInvoice();
            invoice.setStoreID(this.currentuser.getStoreID());
            int generatedInvoiceID = this.invoicemodel.create_invoice(invoice);
            invoice.setID(generatedInvoiceID);
            this.stuff_pos_controller.pos_set_invoice(invoice);
            this.stuff_pos_controller.set_storeID(this.storeID);
            this.stuff_page.getChildren().setAll(stuff_pos_root);
            stuff_pos_root.setVisible(true);
            createinvoicedialogcontroller.get_select_table().getItems().clear();
        } else if (clickedbutton.get() == ButtonType.CLOSE) {
            stuff_pos_root.setVisible(false);
            createinvoicedialogcontroller.get_select_table().getItems().clear();
            dashboard_e(event);
        } else if (clickedbutton.get() == ButtonType.CANCEL) {
            stuff_pos_root.setVisible(false);
            createinvoicedialogcontroller.get_select_table().getItems().clear();
            dashboard_e(event);
        }

    }

    @FXML
    void table_e(MouseEvent event) {

    }

    public void initializeStuff(user current_user) throws IOException, SQLException {
        this.currentuser = current_user;
        this.invoicemodel = new invoice_model();
        product_loading();
        load_dialog_pane();
        this.personID = current_user.getPersonID();
        this.storeID = current_user.getStoreID();
        this.user_type_ID = current_user.getUsertypeID();
        this.order_manage_fxmlloader = new FXMLLoader();
        load_order_manage();
    }
}