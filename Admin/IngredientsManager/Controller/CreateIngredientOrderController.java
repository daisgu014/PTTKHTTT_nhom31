package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IngredientApplicationModel;
import Main.Entity.Element.IncomeDetail;
import Main.Entity.Element.Ingredient;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateIngredientOrderController extends MasterController implements Initializable {
    @FXML
    private TableView<Ingredient> tableChoose;
    @FXML
    private TableView<IncomeDetail> tableOrder;

    @FXML
    private TableColumn<Ingredient, String> idCol;
    @FXML
    private TableColumn<Ingredient, String> nameCol;
    @FXML
    private TableColumn<Ingredient, String> typeCol;
    @FXML
    private TableColumn<Ingredient, Integer> priceCol;
    @FXML
    private TableColumn<Ingredient, String> producerCol;


    @FXML
    private TableColumn<IncomeDetail, String> idColOrd;
    @FXML
    private TableColumn<IncomeDetail, String> nameColOrd;
    @FXML
    private TableColumn<IncomeDetail, Integer> qtyCol;

    private IngredientApplicationModel model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = MasterController.model;
        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("ingredientType"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("incomePrice"));
        producerCol.setCellValueFactory(new PropertyValueFactory<>("producer"));
        tableChoose.setItems(IngredientApplicationModel.ingredientList);

        idColOrd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientId()));
        nameColOrd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientName()));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));

        this.model.createNewOrder();
        tableOrder.setItems(this.model.getCurrentChoices());
        editableCols();

    }

    private void editableCols() {
        qtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }
            @Override
            public Integer fromString(String s) {
                int value = model.getCurrentChoices().get(tableOrder.getSelectionModel().getSelectedIndex()).getOrderQty();
                try {
                    value = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Enter number please");
                    alert.show();
                }
                return value;
            }
        }));
        qtyCol.setOnEditCommit(e -> e.getTableView().getItems().get(
                e.getTablePosition().getRow()).setOrderQty(e.getNewValue()));

        this.tableOrder.setEditable(true);
    }


    public void addItem(){
        Ingredient i = tableChoose.getSelectionModel().getSelectedItem();
        if(i != null) {
            IncomeDetail incomeDetail = new IncomeDetail(i, 1);
            this.model.addChosenItem(incomeDetail);
        }
    }


    public void deleteItem(ActionEvent e) {
        IncomeDetail inD = tableOrder.getSelectionModel().getSelectedItem();
        if(inD != null) {
            this.model.removeChosenItem(inD);
        } else {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)e.getSource()).getScene().getWindow());
            File file = new File("Admin/DataManager/View/Alert.fxml");
            FXMLLoader fx = new FXMLLoader();
            try {
                fx.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(fx.load()));
                stage.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
//        System.out.println(model.getCurrentChoices().get(tableOrder.getSelectionModel().getSelectedIndex()).getOrderQty());
    }

    public void checkChoose(ActionEvent e) {
        if(this.model.checkChooseItem()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Fail");
            alert.setContentText("You have not chosen any items to order");
            alert.showAndWait();
        }
        else {
            screenTranfer.switchScene(e);
        }

    }
}
