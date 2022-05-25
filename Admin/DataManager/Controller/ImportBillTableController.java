package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
import Main.Entity.Element.ImportBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ImportBillTableController implements Initializable {
    @FXML
    private TableView<ImportBill> importBillTableView;
    @FXML
    private TableColumn<ImportBill,String> importBillIDTableColumn;
    @FXML
    private TableColumn<ImportBill, Date> createDateColumn;

    public static List<ImportBill> importBillList = new ArrayList<>();
    ObservableList<ImportBill> importBillObservableList;
    DAO dao;
    public void getImportBillData() throws SQLException {
        importBillList.clear();
        dao = new DAO();
        ResultSet rs = dao.executeQuery("SELECT * FROM ImportBill");
        while (rs.next()){
            String importBillID = rs.getString(2);
            Date CreateDate =rs.getDate(3);
            importBillList.add(new ImportBill(importBillID,CreateDate));
        }
    }
    public void changeSceneImportBillDetails(ActionEvent e) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ImportBill selected = importBillTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        if (selected == null) {
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane CategoryEditParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) CategoryEditParentView);
            dialog.show();
        } else {
            loader.setLocation(this.getClass().getResource("../View/importBillDetails.fxml"));
            Pane CategoryEditParentView = loader.load();
            ImportBillDetailsController importBillDetailsController = loader.getController();
            importBillDetailsController.displayImportBillDetails(selected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setDialogPane((DialogPane) CategoryEditParentView);
            dialog.showAndWait();
        }
    }
        public void GoBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("../CNPMCoffee/Admin/IngredientsManager/View/Admin.Employee.fxml"));
        Parent AdminViewParent = FXMLLoader.load(getClass().getResource("../View/Admin.ImportBill.fxml"));
        Scene scene = new Scene(AdminViewParent);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getImportBillData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        importBillObservableList= FXCollections.observableList(importBillList);
        importBillIDTableColumn.setCellValueFactory(new PropertyValueFactory<ImportBill, String>("importBillID"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<ImportBill,Date>("createDate"));
        importBillTableView.setItems(importBillObservableList);
    }
}
