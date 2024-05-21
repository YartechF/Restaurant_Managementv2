package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import restaurant.db.database;

public class ingredient_cost_model extends database {
    private PreparedStatement ps;

    public void create_ingredient_cost(int ingredientID, int productID, double quantity) throws SQLException {
        String sql = """
                INSERT INTO `tbl_ingredient_cost` (`ingredientID`, `productID`, `quantity`) VALUES (?, ?, ?);
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, ingredientID);
        ps.setInt(2, productID);
        ps.setDouble(3, quantity);
        ps.executeUpdate();
        ps.close();
        getConnection().close();
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
        getConnection().close();
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
        getConnection().close();
    }

    // retrive all
    public ResultSet retrive_all_ingredient_cost() throws SQLException {
        String sql = """
                SELECT * FROM `tbl_ingredient_cost`;
                """;
        ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    // retrieve ingredients cost base on product
    public ResultSet get_ingredient_cost(int productID) throws SQLException {
        String sql = """
                SELECT * FROM `tbl_ingredient_cost` WHERE `productID` =?;
                """;
        ps = getConnection().prepareStatement(sql);
        ps.setInt(1, productID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

}