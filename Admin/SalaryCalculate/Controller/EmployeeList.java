package Main.Admin.SalaryCalculate.Controller;

import Main.Admin.DataManager.Controller.AdminProductAddController;
import Main.Admin.SalaryCalculate.Controller.Employee;
import Main.Entity.DataAccess.DAO;
import  java.util.ResourceBundle;
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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EmployeeList implements  Initializable{
    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private TableColumn<Employee, String> employeeIDtable;
    @FXML
    private TableColumn<Employee, String> employeeNametable;
    @FXML
    private TableColumn<Employee, String> employeePhonetable;
    @FXML
    private TableColumn<Employee, String> employeeTypetable;
    @FXML
    private TableColumn<Employee, String> employeePositiontable;
    @FXML
    private TableColumn<Employee, Integer> employeeSalaryPerHourtable;
    public static List<Employee> employeeTableViewList = new ArrayList<>();
    private  ObservableList<Employee> employeeTable;
    public void getDataEmployee() throws SQLException{
        DAO sql = new DAO();
        employeeTableViewList.clear();
        ResultSet rs = sql.executeQuery("(SELECT Employee.EmployeeID,Employee.EmployeeName,Employee.EmployeePhone,\n" +
                "WorkPosition.WorkPositionName,WorkType.WorkTypeName,PositionType.SalaryPerHour\n" +
                "FROM Employee,WorkPosition,WorkType,PositionType\n" +
                "WHERE Employee.WorkPositionID=WorkPosition.WorkPositionID \n" +
                "\t\tAND Employee.WorkTypeID = WorkType.WorkTypeID \n" +
                "\t\tAND Employee.WorkPositionID=PositionType.WorkPositionID \n" +
                "\t\tAND Employee.WorkTypeID =PositionType.WorkTypeID)\n");
        while(rs.next()){
            String employeeID = rs.getString(1);
            String employeeName = rs.getString(2);
            String employeePhone = rs.getString(3);
            String employeeType = rs.getString(5);
            String employeePosition = rs.getString(4);
            int employeeSalaryPerHour = rs.getInt(6);
            Employee newEmployee = new Employee(employeeID,employeeName,employeePhone,employeeType,employeePosition,employeeSalaryPerHour);
            employeeTableViewList.add(newEmployee);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.getDataEmployee();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        employeeTable= FXCollections.observableArrayList(employeeTableViewList);
            employeeIDtable.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
            employeeNametable.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
            employeePhonetable.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePhone"));
            employeeTypetable.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));
            employeePositiontable.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePosition"));
            employeeSalaryPerHourtable.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeSalary"));
        employeeTableView.setItems(employeeTable);
    }
    public void changeSceneTimekeeping(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Timekeeping.fxml"));
        Parent EmployeeViewParent = loader.load();
        Scene scene = new Scene(EmployeeViewParent);
        stage.setScene(scene);
    }

    public void changeSceneSalarytable(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Salarytable.fxml"));
        Parent ProductViewParent = loader.load();
        Scene scene = new Scene(ProductViewParent);
        stage.setScene(scene);
    }
    public void changSceneAddEmployee(ActionEvent e) throws  IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/addEmployyee.fxml"));
        Pane ProductAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) ProductAddViewParent);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        AddEmployyee AddController = loader.getController();
        if(ClickedButton.get()==ButtonType.APPLY){
            AddController.AddEmployee();
            employeeTableView.setItems(FXCollections.observableArrayList(employeeTableViewList));
            employeeTableView.refresh();
        }
        if(ClickedButton.get()==ButtonType.CANCEL){
            dialog.close();
        }
    }
    public void changSceneAddPosition(ActionEvent e) throws  IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/AddWorkPosition.fxml"));
        Pane ProductAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) ProductAddViewParent);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        AddWorkPosition AddController = loader.getController();
        if(ClickedButton.get()==ButtonType.APPLY){
            AddController.addPosition();
//            employeeTableView.setItems(FXCollections.observableArrayList(employeeTableViewList));
//            employeeTableView.refresh();
        }
        if(ClickedButton.get()==ButtonType.CANCEL){
            dialog.close();
        }
    }
    public void changSceneEditSalary(ActionEvent e) throws  IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/EditSalary.fxml"));
        Pane ProductAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) ProductAddViewParent);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        EditSalary AddController = loader.getController();
        if(ClickedButton.get()==ButtonType.APPLY){
            AddController.editSalary();
            employeeTableView.setItems(FXCollections.observableArrayList(employeeTableViewList));
            employeeTableView.refresh();
        }
        if(ClickedButton.get()==ButtonType.CANCEL){
            dialog.close();
        }
    }
    public void GoBack(ActionEvent e) throws IOException {
        System.out.println(123);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../../Admin/DataManager/View/Admin.fxml"));
        Parent EmployeeViewParent = loader.load();
        Scene scene = new Scene(EmployeeViewParent);
        stage.setScene(scene);
    }
}
