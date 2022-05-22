package Main.Admin.SalaryCalculate.Controller;

import Main.Entity.DataAccess.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EditSalary implements Initializable {
    @FXML
    private ComboBox<String> TypeList ;
    @FXML
    private ComboBox<String> PositionList;
    @FXML
    private TextField SalartyPerHour;

    private List<String> TypeNameArr = FXCollections.observableArrayList();
    private List<String> PositionNameArr = FXCollections.observableArrayList();
    private List<String> TypeIdArr = FXCollections.observableArrayList();
    private List<String> PositionIdArr = FXCollections.observableArrayList();

    private ObservableList<String> typeList;
    private ObservableList<String> positionList;

    public void getDataType() throws SQLException {
        TypeNameArr.clear();
        TypeIdArr.clear();
        DAO sql = new DAO();
        ResultSet rs = sql.executeQuery("SELECT * FROM WorkType");
        while (rs.next()){
            TypeNameArr.add(rs.getString(3));
            TypeIdArr.add(rs.getString(2));
        }
    }
    public void getDataPosition() throws SQLException {
        PositionNameArr.clear();
        PositionIdArr.clear();
        DAO sql = new DAO();
        ResultSet rs = sql.executeQuery("SELECT * FROM WorkPosition");
        while (rs.next()){
            PositionNameArr.add(rs.getString(3));
            PositionIdArr.add(rs.getString(2));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDataType();
            getDataPosition();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        typeList = FXCollections.observableArrayList(TypeNameArr);
        TypeList.setItems(typeList);
        positionList = FXCollections.observableArrayList(PositionNameArr);
        PositionList.setItems(positionList);
    }
    public void editSalary () throws SQLException, IOException {
        String positionId = null;
        String typeId = null;
        int salary = 0;
        try {
            salary = Integer.parseInt(SalartyPerHour.getText());
        }catch (Exception e){
            System.out.println(e);
        }
        for (int i=0; i<PositionNameArr.size();i++){
            if (PositionNameArr.get(i).equalsIgnoreCase(PositionList.getValue())){
                positionId = PositionIdArr.get(i);
            }
        }
        for (int i=0; i<TypeNameArr.size();i++){
            if (TypeNameArr.get(i).equalsIgnoreCase(TypeList.getValue())){
                typeId = TypeIdArr.get(i);
            }
        }
        DAO sql = new DAO();
        try{
            if (positionId==null || typeId==null || salary==0){
                AlertControllerSalary alert = new AlertControllerSalary();
                alert.AlertFailed();
            }else {
            sql.execute("UPDATE PositionType SET SalaryPerHour="+salary+" WHERE WorkPositionID='"+positionId+"' AND WorkTypeID='"+typeId+"' ");
            AlertControllerSalary alert = new AlertControllerSalary();
            alert.AlertSuccess();}
        }catch (Exception e){
            AlertControllerSalary alert = new AlertControllerSalary();
            alert.AlertSQL("../View/AlertSQL.fxml");
        }
        EmployeeList employeeList = new EmployeeList();
        employeeList.getDataEmployee();
    }
}
