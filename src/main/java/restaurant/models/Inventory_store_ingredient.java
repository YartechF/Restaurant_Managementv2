package restaurant.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory_store_ingredient extends store_ingredient_model{
    private PreparedStatement ps;

    private ObservableList<product_inventory> ProductInventoryList = FXCollections.observableArrayList();

    public ObservableList<product_inventory> getFilteredProductInventoryList(String Text){
        ObservableList<product_inventory> FilteredProductInventoryList = FXCollections.observableArrayList();
        for (product_inventory ProductInventory : this.ProductInventoryList) {
            if (ProductInventory.getProduct_Name().toLowerCase().contains(Text.toLowerCase())) {
                FilteredProductInventoryList.add(ProductInventory);
            }
        }
        return FilteredProductInventoryList;
    }

    @Override
    public void retrieve_store_ingredient(int storeID) throws SQLException {
        String sql = "select tbl_store_ingredient.ID, stock, tbl_ingredient.name as product_name, tbl_ingredient.isProductIngredient, tbl_ingredient.description from tbl_store_ingredient INNER JOIN tbl_ingredient on tbl_store_ingredient.ingredientID = tbl_ingredient.ID where storeID =?";
        this.ps = getConnection().prepareStatement(sql);
        this.ps.setInt(1, storeID);
        ResultSet rs = this.ps.executeQuery();
        while (rs.next()) {
            product_inventory ProductInventory = new product_inventory();
            ProductInventory.setID(rs.getInt("ID"));
            ProductInventory.setDescription(rs.getString("description"));
            ProductInventory.setProduct_Name(rs.getString("product_name"));
            ProductInventory.setStock(String.valueOf(rs.getDouble("stock")));
            ProductInventoryList.add(ProductInventory);
        }
    }
    public ObservableList<product_inventory> get_Product_inventoryList() {
        return ProductInventoryList;
    }
}
