package Main.Admin.IngredientsManager.Controller;

import Main.Admin.IngredientsManager.Model.IncomeReportsApplicationModel;
import Main.Entity.Element.IncomeReport;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ConfirmOrderController extends MasterController implements Initializable {
    @FXML
    private TableView<IncomeReport> table;
    @FXML
    private TableColumn<IncomeReport, String> numberOrdCol;
    @FXML
    private TableColumn<IncomeReport, String> idEmployeeCol;
    @FXML
    private TableColumn<IncomeReport, String> nameEmployeeCol;
    @FXML
    private TableColumn<IncomeReport, String> dateCol;
    private IncomeReportsApplicationModel model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = MasterController.inRModel;
        numberOrdCol.setCellValueFactory(new PropertyValueFactory<IncomeReport, String>("reportId"));
        idEmployeeCol.setCellValueFactory(new PropertyValueFactory<IncomeReport, String>("employeeIdCreate"));
        nameEmployeeCol.setCellValueFactory(data -> {
            try {
                return new SimpleStringProperty(model.getDao().
                        findEmployeeNameById(data.getValue().getEmployeeIdCreate()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(
                data.getValue().getOrderDate())));

        this.table.setItems(this.model.getWaitingInReport());
    }

    public void chooseItem(MouseEvent e) {
        IncomeReport id = this.table.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)e.getSource()).getScene().getWindow());
        stage.setX(300);
        stage.setY(50);
        File file;
        FXMLLoader loader = new FXMLLoader();
        if(id != null) {
            this.model.getIncomeDetails(id);
            file = new File("Admin/IngredientsManager/View/ConfirmDetail.fxml");
            try {
                loader.setLocation(file.toURI().toURL());
                stage.setScene(new Scene(loader.load()));
                stage.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Fail");
            alert.setContentText("Select row, please");
            alert.showAndWait();
        }
    }


}
