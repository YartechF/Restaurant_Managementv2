package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurant.db.database;

public class table_model extends database {
    private PreparedStatement ps;

    public void create_table(String name, boolean isActive, boolean isAvailable, int storeID, int capacity) {
        String sql = "INSERT INTO `tbl_table`(`name`, `isActive`, `isAvailable`, `storeID`, `capacity`) VALUES (?,?,?,?,?)";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setBoolean(2, isActive);
            ps.setBoolean(3, isAvailable);
            ps.setInt(4, storeID);
            ps.setInt(5, capacity);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
            System.out.println("Table Created");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // update table where table id set is available
    public void update_table_isAvailable(int tableID, int isAvailable) {
        String sql = "UPDATE `tbl_table` SET `isAvailable`=? WHERE `ID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, isAvailable);
            ps.setInt(2, tableID);
            ps.executeUpdate();
            System.out.println("Table Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update table where table id set is_active
    public void update_table_isActive(int tableID, boolean isActive) {
        String sql = "UPDATE `tbl_table` SET `isActive`=? WHERE `tableID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setBoolean(1, isActive);
            ps.setInt(2, tableID);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
            System.out.println("Table Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update capacity
    public void update_table_capacity(int tableID, int capacity) {
        String sql = "UPDATE `tbl_table` SET `capacity`=? WHERE `tableID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, capacity);
            ps.setInt(2, tableID);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
            System.out.println("Table Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void set_table_status_not_available(int tableID) {
        String sql = "UPDATE `tbl_table` SET `isAvailable`= 0 WHERE `ID`=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, tableID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // retrieve table where ID
    public ResultSet retrieve_table(int storeID) throws SQLException {
        String sql = "select * from tbl_table where storeID = ? and isAvailable = 1";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, storeID);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().close();
            ps.close();
            return null;
        }

    }
    public ObservableList<table> retrieve_all_table() throws SQLException {
        ObservableList<table> tableList = FXCollections.observableArrayList();
        
        String sql = "select t.name as table_name, t.ID as tableID, t.isAvailable, t.capacity, s.name store_name from tbl_table as t inner join tbl_store as s on t.storeID = s.ID";
        try {
            ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                table Table =  new table();
                Table.setID(rs.getInt("tableID"));
                Table.setName(rs.getString("table_name"));
                Table.setAvailable(rs.getBoolean("isAvailable"));
                Table.setCapacity(rs.getInt("capacity"));
                Table.set_store_name(rs.getString("store_name"));
                tableList.add(Table);
            }
            return tableList;
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().close();
            ps.close();
            return null;
        }

    }

}
