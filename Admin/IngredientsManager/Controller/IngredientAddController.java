package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IngredientApplicationModel;
import Main.Entity.Element.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class IngredientAddController extends MasterController {

    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField typeTxtField;
    @FXML
    private TextField priceTxtField;
    @FXML
    private TextField producerTxtField;

    private Ingredient i;



    public void addNewIngredient(ActionEvent e) {
        try {
            i = new Ingredient(nameTxtField.getText(), typeTxtField.getText(),
                    Integer.parseInt(priceTxtField.getText()), producerTxtField.getText());
            ((Stage) ((Node)e.getSource()).getScene().getWindow()).close();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Enter number for price please");
            alert.showAndWait();
        }

    }

    public Ingredient getI() {
        return i;
    }
    public void setI(Ingredient i) {
        this.i = i;
    }
}
