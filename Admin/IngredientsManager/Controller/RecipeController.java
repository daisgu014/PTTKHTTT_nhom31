package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.RecipesModel;
import Main.Entity.Element.ProductRecipe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RecipeController extends MasterController implements Initializable{

    @FXML
    private ComboBox<String> comboBoxProductName;
    @FXML
    private TextField result;
    @FXML
    private TableView<ProductRecipe> table;
    @FXML
    private TableColumn<ProductRecipe, String> productIdCol;
    @FXML
    private TableColumn<ProductRecipe, String> productNameCol;
    @FXML
    private TableColumn<ProductRecipe, String> ingredientIdCol;
    @FXML
    private TableColumn<ProductRecipe, String> ingredientNameCol;
    @FXML
    private TableColumn<ProductRecipe, Integer> ingredientQtyCol;
    private RecipesModel model;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = MasterController.recipesModel;
//        Arrange Combo-box list of product name
        this.comboBoxProductName.setItems(this.model.getProductNameList());

    }

    public void displayChosenItem(ActionEvent e) {
        int index = comboBoxProductName.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            try {
                this.model.getRecipeOfChosenItem(this.model.getProductIdList().get(index));
                productIdCol.setCellValueFactory(new PropertyValueFactory<ProductRecipe, String>("productId"));
                productNameCol.setCellValueFactory(data -> new SimpleStringProperty(
                        this.model.findProductNameById(data.getValue().getProductId())));
                ingredientIdCol.setCellValueFactory(new PropertyValueFactory<ProductRecipe, String>("ingredientId"));
                ingredientNameCol.setCellValueFactory(data -> new SimpleStringProperty(
                        this.model.findIngredientNameById(data.getValue().getIngredientId())));
                ingredientQtyCol.setCellValueFactory(new PropertyValueFactory<ProductRecipe, Integer>("ingredientQty"));
                if(this.model.getRecipesList().size() > 0) {
                    result.setText(String.valueOf(this.model.getRecipesList().get(0).getProductQty()));
                }

                this.table.setItems(this.model.getRecipesList());
                this.table.getItems().addListener(new ListChangeListener<ProductRecipe>() {
                    @Override
                    public void onChanged(Change<? extends ProductRecipe> change) {
                        if(model.getRecipesList().size() > 0) {
                            result.setText(String.valueOf(model.getRecipesList().get(0).getProductQty()));
                        }
                    }
                });
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }


//        productNameCol.setCellValueFactory(data -> new SimpleStringProperty(((new DAO() {
//            String findName(){
//                for(Product p : getAllProduct()) {
//                    if(p.getProductId().equalsIgnoreCase(data.getValue().getProductId())) {
//                        return p.getProductName();
//                    }
//                }
//                return null;
//            }
//        }).findName())));

    }

    public void openAddStage(ActionEvent e) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)e.getSource()).getScene().getWindow());
        File file = new File("Admin/IngredientsManager/View/RecipeAdd.fxml");

        FXMLLoader fx = new FXMLLoader();
        RecipeAddController controller;
        try {
            fx.setLocation(file.toURI().toURL());
            stage.setScene(new Scene(fx.load()));
            controller = fx.getController();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stage.showAndWait();
        if(controller.getPr() != null) {
            this.model.addNewItem(controller.getPr());
//            this.table.setItems(this.model.getRecipesList());
//            this.table.getItems().addListener((ListChangeListener<ProductRecipe>) change -> {
//                if(model.getRecipesList().size() > 0) {
//                    result.setText(String.valueOf(model.getRecipesList().get(0).getProductQty()));
//                }
//            });

            controller.setPr(null);
        }
    }

    public void openEditStage(ActionEvent e) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)e.getSource()).getScene().getWindow());

        ProductRecipe pr = table.getSelectionModel().getSelectedItem();

        File file;
        FXMLLoader fx = new FXMLLoader();

        if(pr != null) {
            file = new File("Admin/IngredientsManager/View/RecipeEdit.fxml");
            RecipeEditController controller;
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                controller = fx.getController();
                controller.getChosenItem(pr);
                stage.showAndWait();
                this.model.updateItem(table.getSelectionModel().getSelectedIndex(), controller.getPr());
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
        ProductRecipe rp = table.getSelectionModel().getSelectedItem();
        File file;
        FXMLLoader fx = new FXMLLoader();
        if(rp != null) {
            file = new File("Admin/IngredientsManager/View/DeleteItemView.fxml");
            DeleteItemController controller;
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                controller = fx.getController();
                stage.showAndWait();
                if(controller.isRemove()) {
                    this.model.removeItem(rp);
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

    public void saveResult(ActionEvent e) {
        try {
            int qty = Integer.parseInt(result.getText());
            this.model.saveResult(qty);
            int index = comboBoxProductName.getSelectionModel().getSelectedIndex();
            try {
                this.model.getRecipeOfChosenItem(this.model.getProductIdList().get(index));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Enter number for result please");
            alert.showAndWait();
        }
    }
}
