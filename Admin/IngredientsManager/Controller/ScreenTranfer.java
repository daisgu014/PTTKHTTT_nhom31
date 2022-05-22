package Main.Admin.IngredientsManager.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ScreenTranfer {

    private final HashMap<String, String> url = new HashMap<>() {
        {
            put("incomeCfmBtn", "Admin/IngredientsManager/View/ConfirmOrder.fxml");
            put("orderListBtn", "Admin/IngredientsManager/View/OrderList.fxml");
            put("newOrderBtn", "Admin/IngredientsManager/View/CreateIngredientOrder.fxml");
            put("backWarehouseBtn", "Admin/IngredientsManager/View/IngredientOrder.fxml");
            put("frmIngredientOrder", "Admin/IngredientsManager/View/FormIngredientOrder.fxml");
//            put("backAdminBtn", "Admin/DataManager/View/Admin.fxml");
//            put("recipeBtn", "Admin/IngredientsManager/View/RecipeView.fxml");

        }
    };

    private HashMap<String, Scene> sceneList;

    public ScreenTranfer() {
        this.initSceneList();
    }

    private void initSceneList() {
        this.sceneList = new HashMap<>();
        for(String btn : this.url.keySet()) {
            try {
                sceneList.put(btn, new Scene(FXMLLoader.load(new File(url.get(btn)).toURI().toURL())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Scene getScene(Button btn) {
        return sceneList.get(btn.getId());
    }

    public void switchScene(ActionEvent e){
        Stage owner = ((Stage) ((Node)e.getSource()).getScene().getWindow());
        ((Stage) ((Node)e.getSource()).getScene().getWindow()).setScene(getScene((Button) e.getSource()));
        owner.centerOnScreen();
    }


}
