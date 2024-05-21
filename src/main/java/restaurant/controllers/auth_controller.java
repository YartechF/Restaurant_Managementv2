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
import javafx.stage.Window;
import restaurant.models.user;
import restaurant.models.user_model;
import restaurant.Main;
import restaurant.controllers.stuff_controller;

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

    void staff(user current_user, MouseEvent event) {
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

    @FXML
    void login_btn_e(MouseEvent event) {
        user current_user = login();
        if (current_user != null) {
            // show the stuff view
            // 1 admin, 2 stuff, 3 inventory_manager
            int admin = 1;
            int staff = 2;
            int inventory_manager = 3;
            if (current_user.getUsertypeID() == admin) {
                // flow for admin
            } else if (current_user.getUsertypeID() == staff) {
                staff(current_user, event);
            } else if (current_user.getUsertypeID() == inventory_manager) {

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
            // TODO Auto-generated catch block
            e.printStackTrace();
            return User;

        }

    }

}
