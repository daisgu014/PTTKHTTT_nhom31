package Main.Sales.ReportEndDay.Model;

public class ProductCancel {
    private String idProduct;
    private String nameProduct;
    private int qtyProduct;
    public ProductCancel() {
    }
    public ProductCancel(String idProduct, String nameProduct, int qtyProduct) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.qtyProduct = qtyProduct;
    }
    public String getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
    public String getNameProduct() {
        return nameProduct;
    }
    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
    public int getQtyProduct() {
        return qtyProduct;
    }
    public void setQtyProduct(int qtyProduct) {
        this.qtyProduct = qtyProduct;
    }
}