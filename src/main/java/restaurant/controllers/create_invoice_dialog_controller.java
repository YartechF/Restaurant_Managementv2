package restaurant.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import restaurant.models.table_model;
import restaurant.models.Invoice;
import restaurant.models.table;;

public class create_invoice_dialog_controller implements Initializable {

    private table_model table;
    private int storeID;

    @FXML
    private TextField costomername_tf;

    @FXML
    private ChoiceBox<String> order_type;

    private String[] OrderType = { "Dine In", "Take Out" };

    @FXML
    private ChoiceBox<String> select_table;

    private Invoice invoice;

    public ChoiceBox<String> get_select_table() {
        return select_table;
    }

    public void set_storeID(int storeID) {
        this.storeID = storeID;
        load_table();
    }

    public Invoice getInvoice() {
        invoice.setCostumer_name(costomername_tf.getText());
        if (order_type.getValue() == "Dine In") {
            invoice.setIstakeout(false);
        } else {
            invoice.setIstakeout(true);
        }
        // i want to get the storeID in select table class oject
        return invoice;
    }

    void load_table() {
        try {
            ResultSet rs = table.retrieve_table(storeID);
            while (rs.next()) {
                table Table = new table();
                Table.setID(rs.getInt("ID"));
                Table.setName(rs.getString("name"));
                Table.setCapacity(rs.getInt("capacity"));
                String value = Table.getName() + " " + "C" + Table.getCapacity();
                select_table.getItems().add(value);
                // add action lestener if value something then do something
                select_table.getSelectionModel().selectedItemProperty()
                        .addListener((observable, oldValue, newValue) -> {
                            if (value.equals(newValue)) {
                                System.out.println(Table.getID());
                                invoice.setTableID(Table.getID());
                            }
                        });

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoice = new Invoice();
        table = new table_model();
        this.order_type.getItems().addAll(OrderType);
        order_type.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Take Out".equals(newValue)) {
                select_table.setDisable(true); // Disable the choice box
                select_table.getSelectionModel().clearSelection();
                costomername_tf.setVisible(true);
            } else {
                costomername_tf.setVisible(false);
                select_table.setDisable(false); // Enable the choice box
            }
        });
    }

}