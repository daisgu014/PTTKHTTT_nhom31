package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IngredientApplicationModel;
import Main.Entity.Element.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class IngredientOrderController extends MasterController implements Initializable {

    @FXML
    private TableView<Ingredient> table;
    @FXML
    private TableColumn<Ingredient, String> idCol;
    @FXML
    private TableColumn<Ingredient, String> nameCol;
    @FXML
    private TableColumn<Ingredient, String> typeCol;
    @FXML
    private TableColumn<Ingredient, Integer> storageCol;
    @FXML
    private TableColumn<Ingredient, String> producerCol;
    @FXML
    private TableColumn<Ingredient, Integer> priceCol;


    private IngredientApplicationModel model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = MasterController.model;
        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("ingredientType"));
        storageCol.setCellValueFactory(new PropertyValueFactory<>("storage"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("incomePrice"));
        producerCol.setCellValueFactory(new PropertyValueFactory<>("producer"));
        table.setItems(IngredientApplicationModel.ingredientList);
    }

    public void openAddStage(ActionEvent e) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)e.getSource()).getScene().getWindow());
        File file = new File("Admin/IngredientsManager/View/IngredientAdd.fxml");

        FXMLLoader fx = new FXMLLoader();
        IngredientAddController controller;
        try {
            fx.setLocation(file.toURI().toURL());
            stage.setScene(new Scene(fx.load()));
            controller = fx.getController();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stage.showAndWait();
        if(controller.getI() != null) {
            this.model.addNewItem(controller.getI());
            table.setItems(IngredientApplicationModel.ingredientList);
        }
        controller.setI(null);
    }

    public void openEditStage(ActionEvent e) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)e.getSource()).getScene().getWindow());

        Ingredient i = table.getSelectionModel().getSelectedItem();

        File file;
        FXMLLoader fx = new FXMLLoader();

        if(i != null) {
            file = new File("Admin/IngredientsManager/View/IngredientEdit.fxml");
            IngredientEditController controller;
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                controller = fx.getController();
                controller.getChosenItem(i);
                stage.showAndWait();
                this.model.updateItem(table.getSelectionModel().getSelectedIndex(), controller.getI());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            file = new File("Admin/DataManager/View/Alert.fxml");
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                stage.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void openRemoveStage(ActionEvent e) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)e.getSource()).getScene().getWindow());
        Ingredient i = table.getSelectionModel().getSelectedItem();
        File file;
        FXMLLoader fx = new FXMLLoader();
        if(i != null) {
            file = new File("Admin/IngredientsManager/View/DeleteItemView.fxml");
            DeleteItemController controller;
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                controller = fx.getController();
                stage.showAndWait();
                if(controller.isRemove()) {
                    this.model.removeItem(i);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else {
            file = new File("Admin/DataManager/View/Alert.fxml");
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                stage.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void changeSceneRecipe(ActionEvent e) {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        File file = new File("Admin/IngredientsManager/View/RecipeView.fxml");
        try {
            loader.setLocation(file.toURI().toURL());
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        Parent IngredientViewParent = null;
        try {
            IngredientViewParent = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Scene scene = new Scene(IngredientViewParent);
        stage.setScene(scene);

    }

}
