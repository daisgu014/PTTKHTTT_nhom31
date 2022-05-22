package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IncomeReportsApplicationModel;
import Main.Entity.Element.IncomeDetail;
import Main.Entity.Element.IncomeReport;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ConfirmDetailController extends MasterController implements Initializable {
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField date;
    @FXML
    private TextField textFieldSupplier;


    @FXML
    private TableView<IncomeDetail> table;
    @FXML
    private TableColumn<IncomeDetail, String> idCol;
    @FXML
    private TableColumn<IncomeDetail, String> nameCol;
    @FXML
    private TableColumn<IncomeDetail, String> typeCol;
    @FXML
    private TableColumn<IncomeDetail, String> priceCol;
    @FXML
    private TableColumn<IncomeDetail, String> producerCol;
    @FXML
    private TableColumn<IncomeDetail, Integer> qtyCol;
    @FXML
    private TableColumn<IncomeDetail, Integer> receiveQtyCol;
    private IncomeReportsApplicationModel model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = MasterController.inRModel;

        this.displayChosenItem(this.model.getCurrentIncomeRe());
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientId()));
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientName()));
        typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientType()));
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue()
                .getIngredientChoice().getIncomePrice())));
        producerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getProducer()));
        qtyCol.setCellValueFactory(new PropertyValueFactory<IncomeDetail, Integer>("orderQty"));
        receiveQtyCol.setCellValueFactory(new PropertyValueFactory<IncomeDetail, Integer>("receiveQty"));

        this.getDetails(this.model.getIncomeDetails());
        this.editableCols();

    }

    public void editableCols() {
        receiveQtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }
            @Override
            public Integer fromString(String s) {
                int value = model.getIncomeDetails().get(table.getSelectionModel().getSelectedIndex()).getReceiveQty();
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

//        receiveQtyCol.setOnEditCommit(e -> e.getTableView().getItems().get(
//                e.getTablePosition().getRow()).setReceiveQty(e.getNewValue()));
        receiveQtyCol.setOnEditCommit(e -> {e.getTableView().getItems().get(
                e.getTablePosition().getRow()).setReceiveQty(e.getNewValue());
            System.out.println(model.getIncomeDetails().get(table.getSelectionModel().getSelectedIndex())
                    .getReceiveQty());
        });

        this.table.setEditable(true);
    }

    public void displayChosenItem(IncomeReport i) {
        textFieldID.setText(i.getEmployeeIdCreate());
        try {
            textFieldName.setText(this.model.getDao().findEmployeeNameById(i.getEmployeeIdCreate()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        textFieldSupplier.setText(i.getSupplier());
        Date date = i.getOrderDate();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        this.date.setText(strDate);
    }

    public void getDetails(ObservableList<IncomeDetail> incomeDetails) {
        this.table.setItems(incomeDetails);
    }

    public void confirmOrder(ActionEvent e) {
        this.model.updateRecQtyOfOrder();
        ((Stage) ((Node)e.getSource()).getScene().getWindow()).close();
    }


}
