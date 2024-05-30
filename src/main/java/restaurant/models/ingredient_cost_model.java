package restaurant.models;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import restaurant.db.database;

public class ingredient_cost_model extends database implements Initializable {
    private PreparedStatement ps;

    private ArrayList<ingredient_cost> Ingredient_cost = new ArrayList<>();

    public void create_ingredient_cost(int ingredientID, int productID, double quantity) throws SQLException {
        String sql = """
                INSERT INTO `tbl_ingredient_cost` (`ingredientID`, `productID`, `quantity`) VALUES (?,?,?);
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, ingredientID);
        ps.setInt(2, productID);
        ps.setDouble(3, quantity);
        ps.executeUpdate();
        ps.close();
    }

    public void update_ingredient_cost(int ingredientID, int productID, double quantity) throws SQLException {
        String sql = """
                UPDATE `tbl_ingredient_cost` SET `ingredientID` =?, `productID` =?, `quantity` =? WHERE `ingredientID` =? AND `productID` =?;
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, ingredientID);
        ps.setInt(2, productID);
        ps.setDouble(3, quantity);
        ps.setInt(4, ingredientID);
        ps.setInt(5, productID);
        ps.executeUpdate();
        ps.close();
    }

    public void delete_ingredient_cost(int ingredientID, int productID) throws SQLException {
        String sql = """
                DELETE FROM `tbl_ingredient_cost` WHERE `ingredientID` =? AND `productID` =?;
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, ingredientID);
        ps.setInt(2, productID);
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<ingredient_cost> get_all_Ingredient_costs() {
        return Ingredient_cost;
    }

    // retrive all
    public void retrive_all_ingredient_cost() throws SQLException {
        String sql = """
                SELECT * FROM `tbl_ingredient_cost`;
                """;
        ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ingredient_cost Ingredientcost = new ingredient_cost();
            Ingredientcost.setID(rs.getInt("ID"));
            Ingredientcost.setIngredientID(rs.getInt("ingredientID"));
            Ingredientcost.setProductID(rs.getInt("productID"));
            Ingredientcost.setQuantity(rs.getDouble("quantity"));
            Ingredient_cost.add(Ingredientcost);
        }
        ps.close();
    }

    // retrieve ingredients cost base on product
    public void retrive_ingredient_cost(int productID) throws SQLException {
        String sql = """
                SELECT * FROM `tbl_ingredient_cost` where productID =? ;
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, productID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ingredient_cost Ingredientcost = new ingredient_cost();
            Ingredientcost.setID(rs.getInt("ID"));
            Ingredientcost.setIngredientID(rs.getInt("ingredientID"));
            Ingredientcost.setProductID(rs.getInt("productID"));
            Ingredientcost.setQuantity(rs.getDouble("quantity"));
            Ingredient_cost.add(Ingredientcost);
        }
        ps.close();
    }

    public ResultSet retrive_ingredient_cost_by_productID(int productID) throws SQLException {
        String sql = """
                SELECT * FROM `tbl_ingredient_cost` where productID =? ;
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, productID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    //create cost_ingredient
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}