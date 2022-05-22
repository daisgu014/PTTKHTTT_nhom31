package Main.Sales.ReportStatistic.Model;

public class Revenue {
    private String date;
    private String numOrder;
    private String numProduct;
    private String totalPrice;
    public Revenue() {
    } 
    public Revenue(String date, String numOrder, String numProduct, String totalPrice) {
        this.date = date;
        this.numOrder = numOrder;
        this.numProduct = numProduct;
        this.totalPrice = totalPrice;
    }
    public String getNumOrder() {
        return numOrder;
    }
    public void setNumOrder(String numOrder) {
        this.numOrder = numOrder;
    }
    public String getNumProduct() {
        return numProduct;
    }
    public void setNumProduct(String numProduct) {
        this.numProduct = numProduct;
    }
    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
