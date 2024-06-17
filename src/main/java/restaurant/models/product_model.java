package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import restaurant.db.database;

public class product_model extends database implements Initializable {
    private PreparedStatement ps;
    private ObservableList<product> ProductsList = FXCollections.observableArrayList();


    public void clear_product_list(){
        ProductsList.clear();
    }
    public ObservableList<AdminProductInventory> get_all_products_admin() throws SQLException{
        ObservableList<AdminProductInventory> api_list = FXCollections.observableArrayList();
        String sql = "select ID, name from tbl_product";
        PreparedStatement pst;
        pst =  getConnection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            AdminProductInventory API = new AdminProductInventory();
            API.setID(rs.getInt("ID"));
            API.setProductName(rs.getString("name"));
            api_list.add(API);
        }
        return api_list;
    }
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

            // Fetch the latest product list after insertion
            retrieve_all_product();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int create_product_get_generate_primary_key(String name, double price, String picture, String type) {
        try {
            String sql = "insert into tbl_product(name,price,picture)values(?,?,?)";
            ps = getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, picture);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }    
            ps.close();
            getConnection().close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void update_product(int id, String name, double price, int categoryID, String picture, String type) {
        try {
            String sql = "update tbl_product set name=?,price=?,categoryID=?,picture=?,type=? where id=?";
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

            // Fetch the latest product list after update
            retrieve_all_product();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<product> get_all_products() {
        return ProductsList;
    }

    public ObservableList<Product_table_cell> get_all_product_for_cell(){
        ObservableList<Product_table_cell> product_list = FXCollections.observableArrayList();
        try {
            String sql = "select * from tbl_product";
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ProductsList.clear(); // Clear the list to avoid duplicates
            while (rs.next()) {
                Product_table_cell Product = new Product_table_cell();
                Product.setID(rs.getInt("ID"));
                Product.setName(rs.getString("name"));
                Product.setCategory_ID(rs.getInt("categoryID"));
                Product.setPicture(rs.getString("picture"));
                Product.setType(rs.getString("type"));
                Product.setPrice(rs.getDouble("price"));
                product_list.add(Product);
            }
            return product_list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void retrieve_all_product() {
        try {
            String sql = "select * from tbl_product";
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ProductsList.clear(); // Clear the list to avoid duplicates
            while (rs.next()) {
                product Product = new product();
                Product.setID(rs.getInt("ID"));
                Product.setName(rs.getString("name"));
                Product.setCategory_ID(rs.getInt("categoryID"));
                Product.setPicture(rs.getString("picture"));
                Product.setType(rs.getString("type"));
                Product.setPrice(rs.getDouble("price"));
                ProductsList.add(Product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retreive_all_product_by_store(int storeID){
        String sql = "select productID from store_product where storeID = ?";
        try (PreparedStatement pst = getConnection().prepareStatement(sql)) {
            pst.setInt(1, storeID);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int productID = rs.getInt("productID");
                String sql2 = "select tbl_product.name,tbl_product.ID as ID,tbl_product.categoryID,tbl_product.picture,tbl_product.type,tbl_product.price,tbl_category.name as category from tbl_product INNER JOIN tbl_category on tbl_product.categoryID = tbl_category.ID where tbl_product.ID = ?";
                PreparedStatement pst2 = getConnection().prepareStatement(sql2);
                pst2.setInt(1, productID);
                ResultSet rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    System.out.println(rs2.getString("name"));
                    product Product = new product();
                    Product.setID(rs2.getInt("ID"));
                    Product.setName(rs2.getString("name"));
                    Product.setCategory_ID(rs2.getInt("categoryID"));
                    Product.setPicture(rs2.getString("picture"));
                    Product.setType(rs2.getString("type"));
                    Product.setPrice(rs2.getDouble("price"));
                    Product.setCategory(rs2.getString("category"));
                    ProductsList.add(Product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // retreive product in specific category
    public void retrieve_product_by_category(int categoryID,int storeID) throws SQLException {
            String sql1 = "select productID from tbl_product_categories where categoryID = ?";
            PreparedStatement pst = getConnection().prepareStatement(sql1);
            pst.setInt(1, categoryID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String sql2 = "select tbl_product.ID, tbl_product.name, tbl_product.price, tbl_product.picture from store_product INNER JOIN tbl_product on store_product.productID = tbl_product.ID where storeID = ? and productID = ?;";
                PreparedStatement pst2 = getConnection().prepareStatement(sql2);
                pst2.setInt(1, storeID);
                pst2.setInt(2, rs.getInt("productID"));
                ResultSet rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    product Product = new product();
                    Product.setID(rs2.getInt("ID"));
                    Product.setName(rs2.getString("name"));
                    Product.setPicture(rs2.getString("picture"));
                    Product.setPrice(rs2.getDouble("price"));
                    ProductsList.add(Product);
                }
                

            }   

            // ps = getConnection().prepareStatement(sql);
            // ps.setInt(1, categoryID);
            // ResultSet rs = ps.executeQuery();
            // while(rs.next()){
            //     product Product = new product();
            //     Product.setID(rs.getInt("ID"));
            //     Product.setName(rs.getString("name"));
            //     Product.setCategory_ID(rs.getInt("categoryID"));
            //     Product.setPicture(rs.getString("picture"));
            //     Product.setType(rs.getString("type"));
            //     Product.setPrice(rs.getDouble("price"));
            //     ProductsList.add(Product);
    }

    public ResultSet retrieve_one_product(int id) {
        try {
            String sql = "select * from tbl_product where ID=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete_product(int id) {
        try {
            String sql = "delete from tbl_product where ID=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();

            // Fetch the latest product list after deletion
            retrieve_all_product();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet retrieve_product(int categoryID) {
        String sql = "SELECT * FROM tbl_product where categoryID = ?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, categoryID);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
