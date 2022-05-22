package Main.Admin.DataManager.Model;

import Main.Entity.Element.Product;

public class ProductInTable {
   private String productID;
   private String productName;
   private String categoryName;
   private int priceByS;
   private int priceByM;
   private int priceByL;

    public ProductInTable(int priceByS, int priceByM, int priceByL) {
        this.priceByS = priceByS;
        this.priceByM = priceByM;
        this.priceByL = priceByL;
    }

    public ProductInTable(Product product) {
        this.productID = product.getProductId();
        this.productName = product.getProductName();
        this.categoryName = product.getCategoryName();
        product.getPriceList().forEach((element)->{
            if(element.getSize().equalsIgnoreCase("S")){
                this.priceByS =element.getPrice();
            }else if(element.getSize().equalsIgnoreCase("M")){
                this.priceByM=element.getPrice();
            }else{
                this.priceByL=element.getPrice();
            }
        });
    }

    public ProductInTable(String productId, String productName, String categoryName,  int priceByS, int priceByM, int priceByL) {
        this.productID = productId;
        this.productName =productName;
        this.categoryName =categoryName;
        this.priceByS = priceByS;
        this.priceByM = priceByM;
        this.priceByL = priceByL;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public int getPriceByS() {
        return priceByS;
    }

    public void setPriceByS(int priceByS) {
        this.priceByS = priceByS;
    }

    public int getPriceByM() {
        return priceByM;
    }

    public void setPriceByM(int priceByM) {
        this.priceByM = priceByM;
    }

    public int getPriceByL() {
        return priceByL;
    }

    public void setPriceByL(int priceByL) {
        this.priceByL = priceByL;
    }
}
