package Main.Entity.Element;

public class ProductRecipe {
    private String productId;
    private String ingredientId;
    private int productQty;
    private int ingredientQty;

    public ProductRecipe() {
    }

    public ProductRecipe(String productId, String ingredientId, int productQty, int ingredientQty) {
        this.productId = productId;
        this.ingredientId = ingredientId;
        this.productQty = productQty;
        this.ingredientQty = ingredientQty;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getIngredientQty() {
        return ingredientQty;
    }

    public void setIngredientQty(int ingredientQty) {
        this.ingredientQty = ingredientQty;
    }
}
