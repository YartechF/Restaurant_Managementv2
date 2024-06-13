package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurant.db.database;

public class store_model extends database {
    private PreparedStatement ps;

    private ObservableList<store_table_cell> stores = FXCollections.observableArrayList();

    // create crud on store the table name is tbl_store the field is name and
    // description mysql

    public ObservableList<store_table_cell> getStores(){
        return this.stores;
    }

    public void create_store(String name, String description) {
        try {
            String sql = "insert into tbl_store(name,description)values(?,?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
            System.out.println("store created");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // create update store method
    public void update_store(int id, String name, String description) {
        try {
            String sql = "update tbl_store set name=?,description=? where id=?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
            getConnection().close();
            System.out.println("store updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // create method return type ResultSet retrieve all tbl_store
    public ResultSet retrieve_all_store() {
        try {
            String sql = "select * from tbl_store";
            ps = getConnection().prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            System.out.println("store retrieved");
            while(rs.next()){
                store_table_cell s = new store_table_cell();
                s.setID(rs.getInt("ID"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("decription"));
                stores.add(s);
            }
            
            
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // create method retrieve specific id tbl_store return type resultset
    public ResultSet retrieve_specific_store(int id) {
        try {
            String sql = "select * from tbl_store where id=?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
