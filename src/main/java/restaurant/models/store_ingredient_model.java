package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import restaurant.db.database;

public class store_ingredient_model extends database {
    private PreparedStatement ps;

    ArrayList<store_ingredient> old_storeingredients = new ArrayList<>();
    ArrayList<store_ingredient> StoreIngredients = new ArrayList<>();

    public void create_store_ingredient(int ingredientID, int storeID, double stock) throws SQLException {
        String sql = "INSERT INTO `tbl_store_ingredient`(`ingredientID`, `storeID`, `stock`) VALUES (?,?,?)";
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setInt(1, ingredientID);
        this.ps.setInt(2, storeID);
        this.ps.setDouble(3, stock);
        this.ps.executeUpdate();
        this.ps.close();
        this.getConnection().close();
    }

    public void update_store_ingredient(int ingredientID, int storeID, double stock) throws SQLException {
        String sql = "UPDATE `tbl_store_ingredient` SET `stock`=? WHERE `ingredientID`=? AND `storeID`=?";
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setDouble(1, stock);
        this.ps.setInt(2, ingredientID);
        this.ps.setInt(3, storeID);
        this.ps.executeUpdate();
        this.ps.close();
        this.getConnection().close();
    }

    public void delete_store_ingredient(int ingredientID, int storeID) throws SQLException {
        String sql = "DELETE FROM `tbl_store_ingredient` WHERE `ingredientID`=? AND `storeID`=?";
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setInt(1, ingredientID);
        this.ps.setInt(2, storeID);
        this.ps.executeUpdate();
        this.ps.close();
        this.getConnection().close();
    }

    // retrieve all

    // retrieve specific use storeID to find
    public ResultSet retrieve_ingredient_stock(int storeID) throws SQLException {
        String sql = """
                select stock, tbl_ingredient.name as Ingredient_Name from tbl_store_ingredient
                inner join tbl_ingredient on tbl_store_ingredient.ingredientID = tbl_ingredient.ID
                where storeID =?
                    """;
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setInt(1, storeID);
        ResultSet rs = this.ps.executeQuery();
        return rs;
    }

    // get stock specific ingredientID
    public ResultSet get_ingredient_stock(int storeID, int ingredientID) throws SQLException {
        String sql = """
                SELECT stock FROM tbl_store_ingredient where storeID = ? AND ingredientID = ?;
                """;
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setInt(1, storeID);
        this.ps.setInt(2, ingredientID);
        ResultSet rs = this.ps.executeQuery();
        return rs;
    }

    public void retrieve_all_storeIngredient() throws SQLException {
        String sql = "select * from tbl_store_ingredient";
        this.ps = getConnection().prepareStatement(sql);
        ResultSet rs = this.ps.executeQuery();
        while (rs.next()) {
            store_ingredient StoreIngredient = new store_ingredient();
            StoreIngredient.setID(rs.getInt("ID"));
            StoreIngredient.setStoreID(rs.getInt("storeID"));
            StoreIngredient.set_ingredientID(rs.getInt("ingredientID"));
            StoreIngredient.set_stock(rs.getDouble("stock"));
            StoreIngredients.add(StoreIngredient);
            old_storeingredients.add(StoreIngredient);
        }
    }

    public void cost_ingredient_by_storeID(int storeID, int ingredientID, double qty) throws SQLException{
        String sql = "UPDATE tbl_store_ingredient set stock = stock -? where storeID =? and ingredientID =?";
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setDouble(1, qty);
        this.ps.setInt(2, storeID);
        this.ps.setInt(3, ingredientID);
        this.ps.executeUpdate();
        this.ps.close();
    }

    public ArrayList<store_ingredient> get_old_storeingredients(){
        return old_storeingredients;
    }

    public ArrayList<store_ingredient> get_store_Ingredients() {
        return StoreIngredients;
    }
}
