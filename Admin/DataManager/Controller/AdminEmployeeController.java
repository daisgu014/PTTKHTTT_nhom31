package Main.Admin.DataManager.Controller;


import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Employee;
import Main.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminEmployeeController implements Initializable {
    @FXML
    TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> idColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> phoneColumn;
    @FXML
    private TableColumn<Employee, String> PositionColumn;
    @FXML
    private TableColumn<Employee, String> typeColumn;
    @FXML
    private TextField txtSearchName;
   public static List<Employee> EmployeeTempList = new ArrayList<>();
    private ObservableList<Employee> EmployeeList;
    public void getDataEmployee() throws SQLException {
        EmployeeTempList.clear();
        DAO dao = new DAO();
        ResultSet rs = dao.executeQuery("SELECT Employee.EmployeeID,Employee.EmployeeName, Employee.EmployeePhone, WorkPosition.WorkPositionName, WorkType.WorkTypeName \n" +
                "FROM Employee, WorkPosition, WorkType\n" +
                "Where Employee.WorkPositionID=WorkPosition.WorkPositionID \n" +
                "AND Employee.WorkTypeID = WorkType.WorkTypeID");
        while (rs.next()){
            String EmployeeId = rs.getString(1);
            String EmployeeName= rs.getString(2);
            String EmployeePhone = rs.getString(3);
            String EmployeePosition = rs.getString(4);
            String EmployeeWorkType = rs.getString(5);
            Employee employee = new Employee(EmployeeId,EmployeeName,EmployeePhone,EmployeePosition,EmployeeWorkType);
            EmployeeTempList.add(employee);
        }
    }
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDataEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        EmployeeList = FXCollections.observableArrayList(EmployeeTempList);
        idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePhone"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("type"));
        table.setItems(EmployeeList);
        SearchNameAutoFill();

    }
    public void changeSceneAddEvent(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/Admin.Employee.Add.fxml"));
        Pane EmployeeAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) EmployeeAddViewParent);
        dialog.initStyle(StageStyle.TRANSPARENT);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        AdminEmployeeAddController adminEmployeeAddController = loader.getController();
        if(ClickedButton.get()== ButtonType.APPLY){
            adminEmployeeAddController.AddEmployee();
            table.setItems(FXCollections.observableArrayList(EmployeeTempList));
            table.refresh();
        }else if(ClickedButton.get()==ButtonType.CLOSE){
            dialog.close();
        }
    }
    public void changeSceneEditEvent(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected==null){
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane EditParentView = loader.load();
            Dialog<ButtonType> dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setDialogPane((DialogPane) EditParentView);
            dialog.showAndWait();
        }else{
            loader.setLocation(this.getClass().getResource("../View/Admin.Employee.Edit.fxml"));
            Pane EmployeeAddViewParentEdit = loader.load();
            AdminEmployeeEditController controller= loader.getController();
            controller.handleEvent(selected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setDialogPane((DialogPane) EmployeeAddViewParentEdit);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if(clickedButton.get() == ButtonType.APPLY){
           controller.EditEmployee(selected);
                table.setItems(FXCollections.observableArrayList(EmployeeTempList));
                table.refresh();
            }else if(clickedButton.get()==ButtonType.CLOSE){
                dialog.close();
            }
        }
        }

    public void changeSceneDeleteEvent(ActionEvent e) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Employee selected = table.getSelectionModel().getSelectedItem();
        if(selected ==null){
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane EmployeeAlertParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) EmployeeAlertParentView);
            dialog.showAndWait();
        }else{
            loader.setLocation(this.getClass().getResource("../View/Admin.Delete.fxml"));
            Pane EmployeeDeleteParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) EmployeeDeleteParentView);
            Optional<ButtonType> ClickedButton = dialog.showAndWait();
            AdminDeleteController adminDeleteController = loader.getController();
            if(ClickedButton.get()==ButtonType.YES){
                adminDeleteController.DeleteEmployee(selected);
                table.setItems(FXCollections.observableArrayList(EmployeeTempList));
                table.refresh();
            }

        }

    }
    public void GoBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/Admin.fxml"));
        Parent AdminViewParent = loader.load();
        Scene scene = new Scene(AdminViewParent);
        stage.setScene(scene);
    }
    public void SearchName(ActionEvent e) throws SQLException {
        this.getDataEmployee();
        List<Employee> Array = new ArrayList<>();
        String pattern = ".*" + txtSearchName.getText() + ".*";
        for(Employee o : EmployeeTempList){
            if(o.getEmployeeName().toLowerCase().matches(pattern.toLowerCase())){
                Array.add(o);
            }
        }
       table.setItems(FXCollections.observableArrayList(Array));
       table.refresh();
    }
    public void SearchNameAutoFill(){
        this.txtSearchName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                List<Employee>Array = new ArrayList<>();
                String pattern = ".*" + t1 + ".*";
                for(Employee o : EmployeeTempList){
                    if(o.getEmployeeName().toLowerCase().matches(pattern.toLowerCase())){
                       Array.add(o);
                    }
                }
               table.setItems(FXCollections.observableArrayList(Array));
                table.refresh();
            }
        });
    }
}
