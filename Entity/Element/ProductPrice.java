package Main.Entity.Element;

public class ProductPrice {

    private String productId;
    private String size;
    private int price;

    public ProductPrice() {
    }

    public ProductPrice(String productId, String size, int price) {
        this.productId = productId;
        this.size = size;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
