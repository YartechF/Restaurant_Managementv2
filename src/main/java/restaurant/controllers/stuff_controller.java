package restaurant.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import restaurant.models.user;
import restaurant.models.Invoice;
import restaurant.models.invoice_model;
import restaurant.models.order;
import restaurant.models.product_model;
import restaurant.models.store_ingredient_model;
import restaurant.models.table_model;
import restaurant.controllers.create_invoice_dialog_controller;
import javafx.scene.Node;

public class stuff_controller {
    private int storeID;
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
    private store_ingredient_model pos_store_ingredient_model;
    private stuff_pos_controller pos;
    private table_model tablemodel;

    void product_loading() throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurant/views/stuff_pos.fxml"));
        AnchorPane create_new_order_pane = fxmlLoader.load();
        stuff_pos_controller Stuff_Pos_Controller = fxmlLoader.getController();
        Stuff_Pos_Controller.set_pos_currentuser(this.currentuser);
        Stuff_Pos_Controller.product_load();
        Stuff_Pos_Controller.load_category();
        this.pos_product_model = Stuff_Pos_Controller.get_pos_product_model();
        this.pos_store_ingredient_model = Stuff_Pos_Controller.get_pos_store_ingredient_model();
        this.pos = Stuff_Pos_Controller.get_pos_stuff_controller();
        // load the pos
        

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
    private AnchorPane orders_btn;

    @FXML
    private AnchorPane Logout_btn;
    
    @FXML
    private AnchorPane table_btn;

    @FXML
    private AnchorPane dashboard_btn;

    @FXML
    void logout_e(MouseEvent event) {
        try {
        // Load the login view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/auth_view.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void delete_current_generated_invoice_on_database(){
        new invoice_model().delete_invoice(this.invoice.getID());
        this.tablemodel.update_table_isAvailable(this.invoice.getTableID(), 1);
    }


    @FXML
    void Order_e(MouseEvent event) throws IOException, SQLException {
        order_manage_controller.set_staff_controller(this);
        order_manage_controller.set_pos(this.pos);
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
    public void enable_buttons(){
        this.new_order_btn.setDisable(false);
        this.table_btn.setDisable(false);
        this.orders_btn.setDisable(false);
        this.dashboard_btn.setDisable(false);
        this.Logout_btn.setDisable(false);
    }
    public void disable_buttons(){
        this.new_order_btn.setDisable(true);
        this.table_btn.setDisable(true);
        this.orders_btn.setDisable(true);
        this.dashboard_btn.setDisable(true);
        this.Logout_btn.setDisable(true);
    }

    @FXML
    void pos_e(MouseEvent event) throws SQLException, IOException {// create new order
        createinvoicedialogcontroller.set_storeID(this.storeID);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(create_invoice_dialog);
        dialog.setTitle("Create New Order");
        Optional<ButtonType> clickedbutton = dialog.showAndWait();

        if (clickedbutton.get() == ButtonType.OK) {
            this.new_order_btn.setDisable(true);
            invoice = createinvoicedialogcontroller.getInvoice();
            invoice.setStoreID(this.currentuser.getStoreID());
            int generatedInvoiceID = new invoice_model().create_invoice(invoice);
            invoice.setID(generatedInvoiceID);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurant/views/stuff_pos.fxml"));
            AnchorPane create_new_order_pane = fxmlLoader.load();
            stuff_pos_controller Stuff_Pos_Controller = fxmlLoader.getController();
            Stuff_Pos_Controller.set_pos_currentuser(this.currentuser);
            Stuff_Pos_Controller.product_load();
            Stuff_Pos_Controller.load_category();
            this.pos_product_model = Stuff_Pos_Controller.get_pos_product_model();
            this.pos_store_ingredient_model = Stuff_Pos_Controller.get_pos_store_ingredient_model();
            this.pos = Stuff_Pos_Controller.get_pos_stuff_controller();

            Stuff_Pos_Controller.pos_set_invoice(invoice);
            Stuff_Pos_Controller.set_storeID(this.storeID);
            Stuff_Pos_Controller.setStuffController(this);
            this.tablemodel.set_table_status_not_available(invoice.getTableID());
            create_new_order_pane.setVisible(true);
            this.disable_buttons();
            this.stuff_page.getChildren().setAll(create_new_order_pane);
            createinvoicedialogcontroller.get_select_table().getItems().clear();
        } else if (clickedbutton.get() == ButtonType.CLOSE) {
            // create_new_order_pane.setVisible(false);
            createinvoicedialogcontroller.get_select_table().getItems().clear();
            dashboard_e(event);
        } else if (clickedbutton.get() == ButtonType.CANCEL) {
            // create_new_order_pane.setVisible(false);
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
        this.tablemodel = new table_model();
    }
}