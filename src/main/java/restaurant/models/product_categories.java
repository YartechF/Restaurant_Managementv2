package restaurant.models;

import javafx.collections.ObservableList;

class product_categories{
    private int productID;
    private int categoryID;
    private ObservableList<category> categories;
    public ObservableList<product> products;

    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public int getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public ObservableList<category> getCategories() {
        return categories;
    }
    public void setCategories(ObservableList<category> categories) {
        this.categories = categories;
    }
    public ObservableList<product> getProducts() {
        return products;
    }
    public void setProducts(ObservableList<product> products) {
        this.products = products;
    }
}