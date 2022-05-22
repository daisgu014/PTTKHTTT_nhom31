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

public class RecipeAddController extends MasterController implements Initializable{

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
    private ProductRecipe pr;
    private RecipesModel model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = MasterController.recipesModel;
        this.comboBoxProductName.setItems(model.getProductNameList());
        this.comboBoxIngredientName.setItems(model.getIngredientNameList());
    }

    public void modifyProductId(ActionEvent e) {
        int index = comboBoxProductName.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            productIdTxtField.setText(this.model.getProductIdList().get(index));
        }
    }

    public void modifyIngredientId(ActionEvent e) {
        int index = comboBoxIngredientName.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            ingredientIdTxtField.setText(this.model.getIngredientIdList().get(index));
        }
    }

    public void addNewIngredient(ActionEvent e) {
        if(productIdTxtField.getText() == "" || ingredientIdTxtField.getText() == "" ||
                ingredientQtyTxtField.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Not be empty input please");
            alert.showAndWait();
        } else {
            try {
                int i;
                if(productQtyTxtField.getText() == "") {
                    i = 0;
                } else {
                    i = Integer.parseInt(productQtyTxtField.getText());
                }
                pr = new ProductRecipe(
                        productIdTxtField.getText(),
                        ingredientIdTxtField.getText(),
                        i,
                        Integer.parseInt(ingredientQtyTxtField.getText())
                );
                ((Stage) ((Node)e.getSource()).getScene().getWindow()).close();

            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Enter number for quantity please");
                alert.showAndWait();
            }

        }
    }

    public ProductRecipe getPr() {
        return pr;
    }

    public void setPr(ProductRecipe pr) {
        this.pr = pr;
    }
}
