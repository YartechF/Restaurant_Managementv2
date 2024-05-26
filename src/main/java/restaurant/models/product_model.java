package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import restaurant.db.database;

public class product_model extends database implements Initializable {
    private PreparedStatement ps;
    private ArrayList<product> ProductsList = new ArrayList<>();

    public void create_product(String name, double price, int categoryID, String picture, String type) {
        try {
            String sql = "insert into tbl_product(name,price,categoryID,picture,type)values(?,?,?,?,?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, categoryID);
            ps.setString(4, picture);
            ps.setString(5, type);
            ps.executeUpdate();
            ps.close();
            getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // create update
    public void update_product(int id, String name, double price, int categoryID, String picture, String type) {
        try {
            String sql = "update tbl_product set name=?,price=?,categoryID=?,picture=? type=?where id=?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, categoryID);
            ps.setString(4, picture);
            ps.setString(5, type);
            ps.setInt(6, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<product> get_all_products() {
        return ProductsList;
    }

    // create retrieve all
    public void retrieve_all_product() {
        try {
            String sql = "select * from tbl_product";
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                product Product = new product();
                Product.setID(rs.getInt("ID"));
                Product.setName(rs.getString("name"));
                Product.setCategory_ID(rs.getInt("categoryID"));
                Product.setPicture(rs.getString("picture"));
                Product.setType("type");
                Product.setPrice(rs.getDouble("price"));
                ProductsList.add(Product);
                System.out.println(Product.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // create retrieve one
    public ResultSet retrieve_one_product(int id) {
        try {
            String sql = "select * from tbl_product where ID=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // add delete
    public void delete_product(int id) {
        try {
            String sql = "delete from tbl_product where ID=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet retrieve_product(int categoryID) {
        String sql = "SELECT * tbl_product where categoryID = ?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
