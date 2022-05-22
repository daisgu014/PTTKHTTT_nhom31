package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IncomeReportsApplicationModel;
import Main.Admin.IngredientsManager.Model.IngredientApplicationModel;
import Main.Entity.Element.IncomeDetail;
import Main.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class FormIngredientOrderController extends MasterController implements Initializable {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField dateTxt;
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


    private Date now;
    private IngredientApplicationModel model;
    private IncomeReportsApplicationModel modelExtra;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = MasterController.model;
        this.modelExtra = MasterController.inRModel;
        now = new Date();
        dateTxt.setText(new SimpleDateFormat("dd-MM-yyyy").format(now));
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientId()));
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientName()));
        typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getIngredientType()));
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(
                data.getValue().getIngredientChoice().getIncomePrice())));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        producerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .getIngredientChoice().getProducer()));

        textFieldID.setText(MainApp.staff.getEmployeeID());
        textFieldName.setText(MainApp.staff.getEmployeeName());

        table.setItems(this.model.getCurrentChoices());
    }

    public void sendOrder(ActionEvent e) {
        this.model.getIncomeReport().setStatus("Waiting");
//        System.out.println(java.sql.Date.valueOf(
//                new SimpleDateFormat("yyyy-MM-dd").format(now)
//        ).getClass().getName());
        this.model.getIncomeReport().setOrderDate(java.sql.Date.valueOf(
                new SimpleDateFormat("yyyy-MM-dd").format(now)
        ));
        this.model.getIncomeReport().setEmployeeIdCreate(this.textFieldID.getText());
        this.model.getIncomeReport().setSupplier(this.textFieldSupplier.getText());

        this.model.saveIncomeReport();
        this.model.saveIncomeDetails();

        this.modelExtra.getWaitingInReport().add(this.model.getIncomeReport());
        this.modelExtra.getIncomeReports().add(this.model.getIncomeReport());


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Send Successfully");
        alert.showAndWait();
        screenTranfer.switchScene(e);
    }
}
