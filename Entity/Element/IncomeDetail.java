package Main.Entity.Element;

public class IncomeDetail {

    private Ingredient ingredientChoice;
    private int orderQty;
    private int receiveQty;

    public IncomeDetail() {
    }

    public IncomeDetail(Ingredient ingredientChoice, int orderQty) {
        this.ingredientChoice = ingredientChoice;
        this.orderQty = orderQty;
    }

    public IncomeDetail(Ingredient ingredientChoice, int orderQty, int receiveQty) {
        this.ingredientChoice = ingredientChoice;
        this.orderQty = orderQty;
        this.receiveQty = receiveQty;
    }

    public Ingredient getIngredientChoice() {
        return ingredientChoice;
    }

    public void setIngredientChoice(Ingredient ingredientChoice) {
        this.ingredientChoice = ingredientChoice;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(int receiveQty) {
        this.receiveQty = receiveQty;
    }
}
