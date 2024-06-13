package restaurant.controllers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import restaurant.models.order;

public class order_item_view_controller {

    private order Order;

    @FXML
    private ImageView product_img;

    @FXML
    private Label product_name;

    @FXML
    private Label quantity;

    @FXML
    private Label status;
    
    public void setData(order Order){
        this.Order = Order;
        update_order();
    }
    private void update_order(){
        product_name.setText(Order.getproduct().getName());
        quantity.setText(String.valueOf(Order.getquantity()));
        File file = new File(this.Order.getproduct().getPicture());
        Image image = new Image(file.toURI().toString());
        this.product_img.setImage(image);
    }
}
