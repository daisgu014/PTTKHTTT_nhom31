package Main.Entity.Element;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Product {
    private String productId;
    private String productName;
    private String categoryName;

    private ArrayList<ProductPrice> priceList;

    public Product() {
        priceList = new ArrayList<>();
    }

    public Product(String productId, String productName, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.priceList = new ArrayList<>();
    }

    public Product(String productId, String productName, String categoryName, ArrayList<ProductPrice> priceList) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.priceList = priceList;
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

    public ArrayList<ProductPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(ArrayList<ProductPrice> priceList) {
        this.priceList = priceList;
    }
}
