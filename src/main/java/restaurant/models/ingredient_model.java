package restaurant.models;

import java.sql.ResultSet;

import restaurant.db.database;

public class ingredient_model extends database {
    // create a method create_ingredient
    public void create_ingredient(String name, double stock, String description, int ingredient_type_ID) {
        try {
            // table name is tbl_ingredient fields name stock description ingredient_type_ID
            String sql = "INSERT INTO tbl_ingredient (name, stock, description, ingredient_type_ID) VALUES (?,?,?,?)";
            // create a statement
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setDouble(2, stock);
            statement.setString(3, description);
            statement.setInt(4, ingredient_type_ID);
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

    // create delete
    public void delete_ingredient(int ingredient_ID) {
        try {
            String sql = "DELETE FROM tbl_ingredient WHERE ingredient_ID =?";
            java.sql.PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setInt(1, ingredient_ID);
            statement.executeUpdate();
            statement.close();
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
