package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.RecipesModel;
import Main.Entity.Element.ProductRecipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeEditController implements Initializable {
    @FXML
    private ComboBox<String> comboBoxProductName;
    @FXML
    private ComboBox<String> comboBoxIngredientName;
    @FXML
    private TextField productIdTxtField;
    @FXML
    private TextField ingredientIdTxtField;
    @FXML
    private TextField ingredientQtyTxtField;
    @FXML
    private TextField productQtyTxtField;

    private RecipesModel model;

    private ProductRecipe pr;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = MasterController.recipesModel;
    }

    public void getChosenItem(ProductRecipe pr) {
        comboBoxProductName.getSelectionModel().select(model.findProductNameById(pr.getProductId()));
        comboBoxIngredientName.getSelectionModel().select(model.findIngredientNameById(pr.getIngredientId()));
        productIdTxtField.setText(pr.getProductId());
        ingredientIdTxtField.setText(pr.getIngredientId());
        ingredientQtyTxtField.setText(String.valueOf(pr.getIngredientQty()));
        productQtyTxtField.setText(String.valueOf(pr.getProductQty()));

        this.pr = pr;
    }

    public void editItem(ActionEvent e) {
        try {
            pr.setProductId(productIdTxtField.getText());
            pr.setIngredientId(ingredientIdTxtField.getText());
            pr.setIngredientQty(Integer.parseInt(ingredientQtyTxtField.getText()));
            pr.setProductQty(Integer.parseInt(productQtyTxtField.getText()));
            ((Stage) ((Node)e.getSource()).getScene().getWindow()).close();
        }catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Enter number for quantity please");
            alert.showAndWait();
        }

    }

    public ProductRecipe getPr() {
        return pr;
    }


}
