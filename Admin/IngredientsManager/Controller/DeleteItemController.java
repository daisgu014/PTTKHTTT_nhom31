package Main.Admin.IngredientsManager.Controller;

import Main.Entity.Element.Ingredient;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DeleteItemController extends MasterController {
    private Ingredient i;

    private boolean isRemove;

    public Ingredient getI() {
        return i;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void removeItem(ActionEvent e) {
        this.isRemove = true;
        ((Stage) ((Node)e.getSource()).getScene().getWindow()).close();
    }

    public void doNotRemove(ActionEvent e) {
        this.isRemove = false;
        ((Stage) ((Node)e.getSource()).getScene().getWindow()).close();
    }
}
