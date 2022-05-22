package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Employee;
import Main.Entity.Element.WorkPosition;
import Main.Entity.Element.WorkType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminEmployeeAddController implements Initializable {

    @FXML
    ComboBox<String> textPosition;
    @FXML
    ComboBox<String> textType;
    @FXML
    private TextField textNameEmployee;
    @FXML
    private TextField textPhone;

    public List<String> WorkPosition = new ArrayList<>();
    public List<String> Type = new ArrayList<>();
    ObservableList<String> WorkPositionList;
    ObservableList<String> TypeList;

    public void getDataPosition() throws SQLException {
        DAO dao = new DAO();
        ResultSet rs = dao. executeQuery("SELECT WorkPositionName FROM WorkPosition");
        while(rs.next()){
            String WorkPositionName = rs.getString(1);
            WorkPosition.add(WorkPositionName);
        }
    }
    public void getDataType() throws SQLException{
        DAO dao = new DAO();
        ResultSet rs = dao. executeQuery("SELECT WorkTypeName FROM WorkType");
        while(rs.next()){
            String WorkTypeName = rs.getString(1);
            Type.add(WorkTypeName);
        }
    }
    public boolean checkName(String Name) throws SQLException {
        AdminEmployeeController adminController = new AdminEmployeeController();
       adminController.getDataEmployee();
        for(Employee e : adminController.EmployeeTempList){
            if(e.getEmployeeName().equalsIgnoreCase(Name)) return false;
        }
        return true;
    }
    public void excuteCheck() throws SQLException {
        if(!checkName(textNameEmployee.getText())){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            AddEmployee();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDataPosition();
            getDataType();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        WorkPositionList = FXCollections.observableArrayList(WorkPosition);
        textPosition.setItems(WorkPositionList);
        TypeList=FXCollections.observableArrayList(Type);
        textType.setItems(TypeList);

    }
    public void AddEmployee() throws SQLException {
        String NameEmployee = textNameEmployee.getText();
        String Phone = textPhone.getText();
        String Position = textPosition.getValue();
        String type = textType.getValue();
        if((Position.equalsIgnoreCase("Manager") && type.equalsIgnoreCase("Parttime"))||
                ((Position.equalsIgnoreCase("ShiftLeader") && type.equalsIgnoreCase("Parttime")))){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("position");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            DAO dao = new DAO();
            ResultSet rs = dao.executeQuery("SELECT * FROM WorkPosition");
            ArrayList<Main.Entity.Element.WorkPosition> workPositionArrayList = new ArrayList<>();
            while (rs.next()){
                workPositionArrayList.add(new WorkPosition(rs.getString(2), rs.getString(3)));
            }
            ResultSet rs1 = dao.executeQuery("SELECT * FROM WorkType");
            ArrayList<Main.Entity.Element.WorkType> workTypeArrayList = new ArrayList<>();
            while ((rs1.next())){
                workTypeArrayList.add(new WorkType(rs1.getString(2), rs1.getString(3)));
            }
            String PositionID = null;
            String TypeID = null;
            for(Main.Entity.Element.WorkPosition o: workPositionArrayList){
                if(o.getWorkPositionName().equalsIgnoreCase(Position)){
                    PositionID=o.getWorkPositionId();
                }
            }
            for(Main.Entity.Element.WorkType o: workTypeArrayList){
                if(o.getTypeName().equalsIgnoreCase(type)){
                    TypeID=o.getTypeId();
                }
            }
            Employee employee = new Employee(NameEmployee, Phone, Position, type);
            AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
            dao.execute("INSERT INTO Employee (EmployeeName,EmployeePhone, WorkPositionID, WorkTypeID) " + "VALUES(N'" + employee.getEmployeeName() + "','" + employee.getEmployeePhone() + "','" + PositionID + "','" + TypeID + "')");
            adminEmployeeController.getDataEmployee();
        }


    }


}
