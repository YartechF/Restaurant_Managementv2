package restaurant.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class pay_controller implements Initializable{

    @FXML
    private TextField amount_ft;

    @FXML
    private Label change;

    @FXML
    private Label total;

    @FXML
    private HBox sufficient_amount;

    public void setTotal(String total){
        this.total.setText(total);
        

        this.amount_ft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    amount_ft.setText(oldValue);
                    return;
                }
                calculate_change(newValue);
                
            }
        });

        amount_ft.setText("0");
        
    }

    public void set_sufficient_amount_visible(){
        this.sufficient_amount.setVisible(true);
    }

    public void calculate_change(String newValue){
        double enteredAmount = newValue.isEmpty() ? 0 : Double.parseDouble(newValue);
        double totalAmount = Double.parseDouble(total.getText()); // Assuming total is in the format "P123.45"

        double changee = enteredAmount - totalAmount;
        change.setText(String.valueOf(changee));
    }

    public Double getchange(){
        return Double.valueOf(change.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}