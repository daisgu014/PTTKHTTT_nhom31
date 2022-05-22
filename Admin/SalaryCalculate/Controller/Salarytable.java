package Main.Admin.SalaryCalculate.Controller;

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

import javax.swing.text.TabableView;
import java.io.IOException;

public class Salarytable implements Initializable{
    @FXML
    private  ComboBox<String> ComboBoxMonth;
    @FXML
    private TableView<Employee> employeeTableSalaryView;
    @FXML
    private TableColumn<Employee, String> employeeTableSLRColumnName;
    @FXML
    private TableColumn<Employee, String> employeeTableSLRColumnID;
    @FXML
    private TableColumn<Employee, String> employeeTableSLRColumnType;
    @FXML
    private TableColumn<Employee, String> employeeTableSLRColumnPosition;
    @FXML
    private TableColumn<Employee, Integer> employeeTableSLRColumnSalaryPerHour;
    @FXML
    private TableColumn<Employee, Integer> employeeTableSLRColumnDate;
    @FXML
    private TableColumn<Employee, Integer> employeeTableSLRColumnHour;
    @FXML
    private TableColumn<Employee, Integer> employeeTableSLRColumnSalary;

    public static List<Employee> SalaryTableViewList = new ArrayList<>();
    private  ObservableList<Employee> SalaryTable;
    ObservableList<String> monthlist = FXCollections.observableArrayList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    public void getDataSalary() throws SQLException{
            SalaryTableViewList.clear();
            DAO sqlEMP = new DAO();
            int month ;
            if(ComboBoxMonth.getValue()==null){
                month = Month("May");
            }else {
                month = Month(ComboBoxMonth.getValue());
            }
            ResultSet emp = sqlEMP.executeQuery("(SELECT Employee.EmployeeID,Employee.EmployeeName,\n" +
                    "WorkPosition.WorkPositionName,WorkType.WorkTypeName,PositionType.SalaryPerHour\n" +
                    "FROM Employee,WorkPosition,WorkType,PositionType\n" +
                    "WHERE Employee.WorkPositionID=WorkPosition.WorkPositionID \n" +
                    "\t\tAND Employee.WorkTypeID = WorkType.WorkTypeID \n" +
                    "\t\tAND Employee.WorkPositionID=PositionType.WorkPositionID \n" +
                    "\t\tAND Employee.WorkTypeID =PositionType.WorkTypeID)\n");
            while (emp.next()){
                String employeeID = emp.getString(1);
                String employeeName = emp.getString(2);
                String employeePosition = emp.getString(4);
                String employeeType = emp.getString(3);
                int    employeeSalaryPerHour = emp.getInt(5);
                int    date = CountDate(employeeID,month);
                int    hour = CountHour(employeeID,month);
                int salary;
                if(employeeType.equals("Parttime")){
                    salary = employeeSalaryPerHour*hour;
                }else {
                    salary = employeeSalaryPerHour*8*date;
                }
                Employee newEmployee = new Employee(employeeID,employeeName,employeePosition,employeeType,employeeSalaryPerHour,date,hour,salary);
                SalaryTableViewList.add(newEmployee);
            }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDataSalary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SalaryTable = FXCollections.observableArrayList(SalaryTableViewList);
        employeeTableSLRColumnID.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
        employeeTableSLRColumnName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        employeeTableSLRColumnPosition.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePosition"));
        employeeTableSLRColumnType.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));
        employeeTableSLRColumnSalaryPerHour.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeSalary"));
        employeeTableSLRColumnDate.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("workingDate"));
        employeeTableSLRColumnHour.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("workingHour"));
        employeeTableSLRColumnSalary.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("totalSalary"));
        employeeTableSalaryView.setItems(SalaryTable);
        ComboBoxMonth.setItems(monthlist);
    }
    public void changeMonth(ActionEvent event) throws SQLException {
        getDataSalary();
        System.out.println(ComboBoxMonth.getValue());
        SalaryTable = FXCollections.observableArrayList(SalaryTableViewList);
        employeeTableSLRColumnID.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
        employeeTableSLRColumnName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        employeeTableSLRColumnPosition.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePosition"));
        employeeTableSLRColumnType.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));
        employeeTableSLRColumnSalaryPerHour.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeSalary"));
        employeeTableSLRColumnDate.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("workingDate"));
        employeeTableSLRColumnHour.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("workingHour"));
        employeeTableSLRColumnSalary.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("totalSalary"));
        employeeTableSalaryView.setItems(SalaryTable);
        ComboBoxMonth.setItems(monthlist);
    }
    public static int Month(String x){
        int tmp;
        switch (x){
            case "Jan":
                tmp=1;
                break;
            case "Feb":
                tmp=2;
                break;
            case "Mar":
                tmp =3;
                break;
            case "Apr":
                tmp=4;
                break;
            case "May":
                tmp=5;
                break;
            case "Jun":
                tmp=6;
                break;
            case "Jul":
                tmp=7;
                break;
            case "Aug":
                tmp=8;
                break;
            case "Sep":
                tmp=9;
                break;
            case "Oct":
                tmp=10;
                break;
            case "Nov":
                tmp=11;
                break;
            case "Dec" :
                tmp=12;
                break;
            default:
                tmp=1;
                break;
        }
        return  tmp;
    }
    public int CountDate(String empID, int month) throws  SQLException{
        DAO sqlCountDate = new DAO();
        ResultSet date = sqlCountDate.executeQuery("SELECT COUNT(DailyDate) FROM DailyWork\n" +
                "WHERE DATEPART(MONTH,DailyDate)='"+month+"' AND EmployeeID='"+empID+"';");
        int kq = 0;
        while (date.next()){
            kq = date.getInt(1);
        }
        return kq;
    }
    public int CountHour(String empID, int month) throws  SQLException{
        DAO sqlCountHour = new DAO();
        ResultSet hour = sqlCountHour.executeQuery("SELECT SUM(WorkingHour) FROM DailyWork\n" +
                "WHERE DATEPART(MONTH,DailyDate)='"+month+"' AND EmployeeID='"+empID+"';");
        int kq = 0;
        while (hour.next()){
            kq = hour.getInt(1);
        }
        return kq;
    }
    public void changeSceneTimekeeping(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Timekeeping.fxml"));
        Parent ProductViewParent = loader.load();
        Scene scene = new Scene(ProductViewParent);
        stage.setScene(scene);
    }
    public void changeSceneEmployee(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/EmployeeList.fxml"));
        Parent ProductViewParent = loader.load();
        Scene scene = new Scene(ProductViewParent);
        stage.setScene(scene);
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
