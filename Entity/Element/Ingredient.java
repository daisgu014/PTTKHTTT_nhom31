package Main.Entity.Element;

public class Ingredient {
    private String ingredientId;
    private String ingredientName;
    private String ingredientType;
    private int storage;
    private int incomePrice;
    private String producer;


    public Ingredient() {
    }

    public Ingredient(String ingredientId, String ingredientName, String ingredientType) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
    }

    public Ingredient(String ingredientName, String ingredientType, int incomePrice, String producer) {
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.incomePrice = incomePrice;
        this.producer = producer;
    }

    public Ingredient(String ingredientId, String ingredientName, String ingredientType, int storage, int incomePrice, String producer) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.storage = storage;
        this.incomePrice = incomePrice;
        this.producer = producer;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(String ingredientType) {
        this.ingredientType = ingredientType;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getIncomePrice() {
        return incomePrice;
    }

    public void setIncomePrice(int incomePrice) {
        this.incomePrice = incomePrice;
    }
}
