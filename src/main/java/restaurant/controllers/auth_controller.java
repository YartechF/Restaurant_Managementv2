package restaurant.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import restaurant.models.user;
import restaurant.models.user_model;

public class auth_controller {
    private user User;
    private user_model User_model;
    private FXMLLoader stuff_view;
    private AnchorPane stuff;
    private stuff_controller Stuffcontroller;

    public auth_controller() {
        this.User_model = new user_model();
        this.User = new user();
    }

    /**
     * Retrieves the controller for the "stuff_view" FXML view.
     *
     * @return the Stuffcontroller instance, or null if an IOException occurs while
     *         loading the FXML file.
     */
    stuff_controller get_stuff_view_controller() {
        try {
            this.stuff_view = new FXMLLoader(getClass().getResource("/restaurant/views/stuff_view.fxml"));
            this.stuff = this.stuff_view.load();
            Stuffcontroller = this.stuff_view.getController();
            return Stuffcontroller;
        } catch (IOException e) {
            e.printStackTrace();
            return Stuffcontroller;
        }

    }

    @FXML
    private Button login_btn;

    @FXML
    private TextField password_tf;

    @FXML
    private TextField username_tf;

    /**
     * Handles the staff functionality for the current user.
     * This method loads the "stuff_view.fxml" scene, initializes the stuff
     * controller, and sets the new scene on the current stage.
     *
     * @param current_user the current user
     * @param event        the mouse event that triggered this method
     * @throws SQLException if there is an issue accessing the database
     */
    void staff(user current_user, MouseEvent event) throws SQLException {
        try {
            // Load the next scene (e.g., stuff screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/stuff_view.fxml"));
            Parent nextRoot = loader.load();
            Scene nextScene = new Scene(nextRoot);

            // Access the stuff controller methods
            stuff_controller stuffController = loader.getController();
            stuffController.initializeStuff(current_user); // Example method call

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            currentStage.setScene(nextScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void inventory_manager(user current_user, MouseEvent event) throws SQLException {
        try {
            // Load the next scene (e.g., stuff screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/inventory.fxml"));
            Parent nextRoot = loader.load();
            Scene nextScene = new Scene(nextRoot);

            // Access the stuff controller methods
            Inventory_Controller Inventory_controller = loader.getController();
            Inventory_controller.SetCurrentUser(current_user); // Example method call

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            currentStage.setScene(nextScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void order_manager(user current_user, MouseEvent event) throws SQLException {
        try {
            // Load the next scene (e.g., stuff screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/order_manager.fxml"));
            Parent nextRoot = loader.load();
            Scene nextScene = new Scene(nextRoot);

            order_manager_controller OrderManagerController = loader.getController();
            OrderManagerController.SetCurrentUser(current_user); // Example method call

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(nextScene);
            currentStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    void admin(user current_user, MouseEvent event) throws SQLException {
        try {
            // Load the next scene (e.g., stuff screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant/views/admin_view.fxml"));
            Parent nextRoot = loader.load();
            Scene nextScene = new Scene(nextRoot);

            admin_controller AdminController = loader.getController(); // Example method call

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(nextScene);
            currentStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the login button click event.
     * Attempts to log in the user and redirects to the appropriate view based on
     * the user's role.
     *
     * @param event The mouse event that triggered the login button click.
     * @throws SQLException If there is an error accessing the database during the
     *                      login process.
     */
    @FXML
    void login_btn_e(MouseEvent event) throws SQLException {
        user current_user = login();
        if (current_user != null) {
            // show the stuff view
            // 1 admin, 2 stuff, 3 inventory_manager
            int admin = 1;
            int staff = 2;
            int inventory_manager = 3;
            int order_manager = 4;
            if (current_user.getUsertypeID() == admin) {
                admin(current_user, event);
            } else if (current_user.getUsertypeID() == staff) {
                staff(current_user, event);
            } else if (current_user.getUsertypeID() == inventory_manager) {
                inventory_manager(current_user, event);
            }else if(current_user.getUsertypeID() == order_manager){
                //order manager
                order_manager(current_user, event);
            }
        } else {
            System.out.println("Login Fail");
        }
    }

    user login() {
        try {
            ResultSet rs = User_model.login(username_tf.getText(), password_tf.getText());
            if (rs.next()) {
                User.setPersonID(rs.getInt("personID"));
                User.setUsertype(rs.getString("usertype"));
                User.setUsertypeID(rs.getInt("usertypeID"));
                User.setStoreID(rs.getInt("storeID"));
                User.setStorename(rs.getString("storename"));
                return User;

            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return User;

        }

    }

}
