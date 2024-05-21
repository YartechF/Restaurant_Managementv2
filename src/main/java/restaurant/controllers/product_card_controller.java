package restaurant.controllers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import restaurant.models.product;

public class product_card_controller {

    private int productID;

    @FXML
    private ImageView product_img;

    @FXML
    private Label product_name;

    @FXML
    private Label product_price;

    @FXML
    private Label product_stock;

    public void setdata(product Product) {
        this.productID = Product.getID();
        this.product_name.setText(Product.getName());
        this.product_price.setText("₱" + String.valueOf(Product.getPrice()));
        this.product_stock.setText("Stock: " + String.valueOf((int) Product.getStock()));
        File file = new File(Product.getPicture());
        Image image = new Image(file.toURI().toString());
        this.product_img.setImage(image);
    }

    public int getProductID() {
        return this.productID;
    }

}
