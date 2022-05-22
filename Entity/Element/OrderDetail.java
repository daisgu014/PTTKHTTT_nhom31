package Main.Entity.Element;

public class OrderDetail {
    private Product productChoice;
    private String size;
    private int quantity;

    private int price;

    public OrderDetail() {
    }

    public OrderDetail(Product productChoice, String size, int quantity) {
        this.productChoice = productChoice;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }


    public OrderDetail(Product productChoice, String size, int quantity, int price) {
        this.productChoice = productChoice;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product getProductChoice() {
        return productChoice;
    }

    public void setProductChoice(Product productChoice) {
        this.productChoice = productChoice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
