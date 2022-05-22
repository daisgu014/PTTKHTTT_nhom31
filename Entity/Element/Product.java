package Main.Entity.Element;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Product {
    private String productId;
    private String productName;
    private String categoryName;
    private int productPrice;
    private int storage;
    private String SupplierName;

    public Product(String productId, String productName, String categoryName, int productPrice, int storage) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.productPrice = productPrice;
        this.storage = storage;
    }

    public Product(String productId, String productName, String categoryName, int productPrice, int storage, String supplierName) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.productPrice = productPrice;
        this.storage = storage;
        SupplierName = supplierName;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }
}
