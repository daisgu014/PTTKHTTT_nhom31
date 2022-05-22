package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IncomeReportsApplicationModel;
import Main.Admin.IngredientsManager.Model.IngredientApplicationModel;
import Main.Admin.IngredientsManager.Model.RecipesModel;
import javafx.event.ActionEvent;
import javafx.stage.Window;

public class MasterController {

    public static ScreenTranfer screenTranfer;

    public static IngredientApplicationModel model;
    public static IncomeReportsApplicationModel inRModel;
    public static RecipesModel recipesModel;

    public void changeScene(ActionEvent e){
//        recipesModel.getProductList().clear();
//        recipesModel.getProductList().addAll(recipesModel.getDao().getAllProduct());

        screenTranfer.switchScene(e);
    }

    public static void start() {
        MasterController.model = new IngredientApplicationModel();
        MasterController.recipesModel = new RecipesModel();
        MasterController.inRModel = new IncomeReportsApplicationModel();
        MasterController.screenTranfer = new ScreenTranfer();
    }


}
