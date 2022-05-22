package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Employee;
import Main.Entity.Element.WorkPosition;
import Main.Entity.Element.WorkType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminEmployeeEditController implements Initializable {
    @FXML
    ComboBox<String> textPosition;
    @FXML
    ComboBox<String> textType;
    @FXML
    private TextField textNameEmployee;
    @FXML
    private TextField textPhone;

    public ArrayList<String> PositionArray = new ArrayList<>();
    public ArrayList<String> TypeArray = new ArrayList<>();
    ObservableList<String> PositionList;
    ObservableList<String> TypeList;
    DAO dao;
    public void handleEvent(Employee selected){
        textNameEmployee.setText(selected.getEmployeeName());
        textPhone.setText(selected.getEmployeePhone());
        textPosition.setValue(selected.getPosition());
        textType.setValue(selected.getType());
    }
    public void getDataPosition() throws SQLException{
        dao = new DAO();
        DAO dao = new DAO();
        ResultSet rs = dao. executeQuery("SELECT WorkPositionName FROM WorkPosition");
        while(rs.next()){
            String WorkPositionName = rs.getString(1);
            PositionArray.add(WorkPositionName);
        }
    }
    public void getDataType() throws SQLException {
        dao = new DAO();
        ResultSet rs = dao. executeQuery("SELECT WorkTypeName FROM WorkType");
        while(rs.next()){
            String WorkTypeName = rs.getString(1);
            TypeArray.add(WorkTypeName);
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
        PositionList= FXCollections.observableArrayList(PositionArray);
        textPosition.setItems(PositionList);
        TypeList = FXCollections.observableArrayList(TypeArray);
        textType.setItems(TypeList);
    }

    public void EditEmployee(Employee employee) throws SQLException {
        String employeeID = employee.getEmployeeID();
        String employeeName = textNameEmployee.getText();
        String employeePhone = textPhone.getText();
        String WorkPosition = textPosition.getValue();
        String workType = textType.getValue();
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
        if((WorkPosition.equalsIgnoreCase("Manager") && workType.equalsIgnoreCase("Parttime"))||
                ((WorkPosition.equalsIgnoreCase("ShiftLeader") && workType.equalsIgnoreCase("Parttime")))){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("position");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            String PositionID = null;
            String TypeID = null;
            for(Main.Entity.Element.WorkPosition o: workPositionArrayList){
                if(o.getWorkPositionName().equalsIgnoreCase(WorkPosition)){
                    PositionID=o.getWorkPositionId();
                }
            }
            for(Main.Entity.Element.WorkType o: workTypeArrayList){
                if(o.getTypeName().equalsIgnoreCase(workType)){
                    TypeID=o.getTypeId();
                }
            }
            dao.execute("UPDATE Employee SET EmployeeName=N'"+employeeName+"',EmployeePhone='"+employeePhone+"', WorkPositionID='"+PositionID+"',WorkTypeID='"+TypeID+"'WHERE EmployeeID ='"+employeeID+"' " );
            AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
            adminEmployeeController.getDataEmployee();
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


}
