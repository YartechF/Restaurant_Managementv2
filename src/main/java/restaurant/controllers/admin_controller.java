package restaurant.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class admin_controller {

    @FXML
    private AnchorPane Logout_btn;

    @FXML
    private AnchorPane admin_page;

    @FXML
    private AnchorPane category_btn;

    @FXML
    private AnchorPane dashboard_btn;

    @FXML
    private AnchorPane groups_btn;

    @FXML
    private AnchorPane products_btn;

    @FXML
    private AnchorPane report_btn;

    @FXML
    private AnchorPane store_btn;

    @FXML
    private AnchorPane Inventory;

    @FXML
    void category_e(MouseEvent event) {

    }

    @FXML
    void dashboard_e(MouseEvent event) {

    }

    @FXML
    void groups_e(MouseEvent event) {

    }

    @FXML
    void Inventory_e(MouseEvent event) throws IOException {
        
    }
    

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

    @FXML
    void products_e(MouseEvent event) {

    }

    @FXML
    void reports_e(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/restaurant/views/reports_view.fxml"));
        AnchorPane reportPane = fxmlLoader.load();
        reports_controller ReportsController = fxmlLoader.getController();

        ReportsController.init();
        this.admin_page.getChildren().setAll(reportPane);
    }

    @FXML
    void store_e(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/store_view.fxml"));
        AnchorPane storepane = loader.load();
        store_management_controller StoreManagementController = loader.getController();

        this.admin_page.getChildren().setAll(storepane);
    }

}
