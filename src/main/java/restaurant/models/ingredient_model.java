package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurant.db.database;

public class ingredient_model extends database {
    
    // create a method create_ingredient
    public void create_ingredient(String name, String description, int ingredient_cost_typeID, int Is_per_pcs) {
        try {
            // table name is tbl_ingredient fields name stock description ingredient_type_ID
            String sql = "INSERT INTO `tbl_ingredient`(`name`, `description`, `ingredient_cost_typeID`, `IsProductIngredient`, `Is_per_pcs`) VALUES (?,?,?,?,?)";
            // create a statement
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, ingredient_cost_typeID);
            statement.setInt(4, 1);
            statement.setInt(5, Is_per_pcs);
            statement.executeUpdate();
            statement.close();
            getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // create a method retrieve all ingredient
    public ResultSet retrieve_all_ingredient() {
        try {
            String sql = "SELECT * FROM tbl_ingredient";
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            // add resultset
            
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ObservableList<Admin_product_ingredient> get_Ingredient_costs_by_productID(int ID){
        ObservableList<Admin_product_ingredient> dishes_list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT tbl_ingredient_cost.ID as ID,tbl_ingredient.name as ingredient_name,tbl_ingredient_cost.quantity FROM tbl_ingredient_cost INNER JOIN tbl_ingredient on tbl_ingredient_cost.ingredientID = tbl_ingredient.ID where tbl_ingredient_cost.productID = ?";
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setInt(1, ID);
            ResultSet rs = statement.executeQuery();
            // add resultset
            while (rs.next()) {
                Admin_product_ingredient AdminProductIngredient = new Admin_product_ingredient();
                AdminProductIngredient.set_id(rs.getInt("ID"));
                AdminProductIngredient.set_product_name(rs.getString("ingredient_name"));
                AdminProductIngredient.set_required_quantity(rs.getString("quantity"));
                dishes_list.add(AdminProductIngredient);
            }
            
            return dishes_list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<AdminProductInventory> retrieve_all_ingredient_by_search(String search_productname){
        ObservableList<AdminProductInventory> ProductInventoryList = FXCollections.observableArrayList();
        ProductInventoryList.clear();
        String sql = "SELECT tbl_ingredient.ID as ID, tbl_ingredient.name as productname, tbl_ingredient.description, tbl_ingredient_cost_type.name as cost_type FROM tbl_ingredient INNER JOIN tbl_ingredient_cost_type on tbl_ingredient.ingredient_cost_typeID = tbl_ingredient_cost_type.ID WHERE tbl_ingredient.name LIKE ?";
        try (PreparedStatement pst = getConnection().prepareStatement(sql)) {
            pst.setString(1, search_productname+"%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                AdminProductInventory ProductInventory = new AdminProductInventory();
                ProductInventory.setID(rs.getInt("ID"));
                ProductInventory.setProductName(rs.getString("productname"));
                ProductInventory.setDescription(rs.getString("description"));
                ProductInventory.setCostType(rs.getString("cost_type"));
                ProductInventoryList.add(ProductInventory);
            }
            
           return ProductInventoryList; 
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            return null;
        }
        
    }
    
    // create a method retrieve specific ingredient
    public ResultSet retrieve_specific_ingredient(int ingredient_ID) {
        try {
            String sql = "SELECT * FROM tbl_ingredient WHERE ingredient_ID =?";
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setInt(1, ingredient_ID);
            ResultSet rs = statement.executeQuery();
            // add resultset
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // create update
    public void update_ingredient(int ingredient_ID, String name, double stock, String description,
            int ingredient_type_ID) {
        try {
            String sql = "UPDATE tbl_ingredient SET name =?, stock =?, description =?, ingredient_type_ID =? WHERE ingredient_ID =?";
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setDouble(2, stock);
            statement.setString(3, description);
            statement.setInt(4, ingredient_type_ID);
            statement.setInt(5, ingredient_ID);
            statement.executeUpdate();
            statement.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<product_ingredient> get_all_ingredient() throws SQLException{
        String sql = "SELECT tbl_ingredient.name,tbl_ingredient.ID,tbl_ingredient_cost_type.name as cost_type FROM tbl_ingredient inner join tbl_ingredient_cost_type on tbl_ingredient.ingredient_cost_typeID = tbl_ingredient_cost_type.ID;";
        PreparedStatement pst = getConnection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ObservableList<product_ingredient> productList = FXCollections.observableArrayList();
        while (rs.next()) {
            product_ingredient product = new product_ingredient();
            product.setName(rs.getString("name"));
            product.setID(rs.getInt("ID"));
            product.setCostType(rs.getString("cost_type"));
            productList.add(product);
        }
        return productList;
    }   

    // create delete
    public void delete_ingredient(int ingredient_ID) {
        try {
            String sql1 = "DELETE FROM tbl_ingredient_cost WHERE ingredientID =?";
            PreparedStatement ps2 = getConnection().prepareStatement(sql1);
            ps2.setInt(1, ingredient_ID);
            ps2.executeUpdate();
            
            String sql2 = "DELETE FROM tbl_store_ingredient WHERE ingredientID =?";
            PreparedStatement ps3 = getConnection().prepareStatement(sql2);
            ps3.setInt(1, ingredient_ID);
            ps3.executeUpdate();

            String sql3 = "DELETE FROM tbl_ingredient WHERE ID =?";
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql3);
            statement.setInt(1, ingredient_ID);
            statement.executeUpdate();
            statement.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
