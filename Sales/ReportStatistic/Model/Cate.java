package Main.Sales.ReportStatistic.Model;

public class Cate {
    private String categoryId;
    private String categoryName;
    private String categoryQty;
    public Cate() {
    }
    public Cate(String categoryId, String categoryName, String categoryQty) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryQty = categoryQty;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryQty() {
        return categoryQty;
    }
    public void setCategoryQty(String categoryQty) {
        this.categoryQty = categoryQty;
    }  
}
