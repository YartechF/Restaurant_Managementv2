package restaurant.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import restaurant.models.user;
import restaurant.models.Invoice;
import restaurant.models.invoice_model;
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

    void product_loading() {
        // load the pos
        try {
            this.stuff_pos_view = new FXMLLoader(getClass().getResource("/restaurant/views/stuff_pos.fxml"));
            this.stuff_pos_root = this.stuff_pos_view.load();
            this.stuff_pos_controller = this.stuff_pos_view.getController();
            this.stuff_pos_controller.set_pos_currentuser(this.currentuser);

            this.stuff_pos_controller.product_load();

        } catch (IOException e) {
            // TODO Auto-generated catch block
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

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    @FXML
    private AnchorPane new_order_btn;

    @FXML
    private Pane stuff_page;

    @FXML
    void Order_e(MouseEvent event) {

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
            int generatedInvoiceID = this.invoicemodel.create_invoice(invoice);
            invoice.setID(1);
            stuff_pos_controller.pos_set_invoice(invoice);
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

    public void initializeStuff(user current_user) throws IOException {
        this.currentuser = current_user;
        this.invoicemodel = new invoice_model();
        product_loading();
        load_dialog_pane();
        this.personID = current_user.getPersonID();
        this.storeID = current_user.getStoreID();
        this.user_type_ID = current_user.getUsertypeID();
    }
}