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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddWorkPosition implements Initializable {
    @FXML
    private ComboBox<String> TypeList;
    @FXML
    private  ComboBox<String> LevelList;
    @FXML
    private TextField positionName;
    @FXML
    private TextField salaryPerHour;
    private List<String> typeNameArr = new ArrayList<>();
    private List<String> typeIdArr = new ArrayList<>();
    private List<String> positionArr = new ArrayList<>();
    private ObservableList<String> typeList;
    private  ObservableList<String> levelList = FXCollections.observableArrayList("1","2");
    public void  getDataType() throws SQLException {
        typeIdArr.clear();
        typeNameArr.clear();
        DAO sql = new DAO();
        ResultSet rs = sql.executeQuery("SELECT * FROM WorkType");
        while (rs.next()){
            typeNameArr.add(rs.getString(3));
            typeIdArr.add(rs.getString(2));
        }
    }
    public void addPosition() throws SQLException, IOException {
        DAO sqlposition = new DAO();
        DAO sqlpositiontype = new DAO();
        positionArr.clear();
        String pName = positionName.getText();
        String pID = null;
        String tName = TypeList.getValue();
        String tID = null;
        int level = 0;
        for (int i =0; i<typeNameArr.size();i++){
            if (typeNameArr.get(i).equals(tName)){
                tID = typeIdArr.get(i);
            }
        }
        int salary = 0;
        try {
            salary = Integer.parseInt(salaryPerHour.getText());
            level = Integer.parseInt(LevelList.getValue());
            sqlposition.execute("INSERT INTO WorkPosition(WorkPositionName,WorkPositionLVL) VALUES('"+pName+"','"+level+"') ");
            DAO sql = new DAO();
            ResultSet rs = sql.executeQuery("SELECT WorkPositionID FROM WorkPosition WHERE WorkPositionName='"+pName+"'");
            while (rs.next()){
                positionArr.add(rs.getString(1));
            }
            pID = positionArr.get(0);
            sqlpositiontype.execute("INSERT INTO PositionType(WorkPositionID,WorkTypeID,SalaryPerHour) VALUES('"+pID+"','"+tID+"','"+salary+"')");
            AlertControllerSalary alert = new AlertControllerSalary();
            alert.AlertSuccess();
        }catch (Exception e){
            AlertControllerSalary alert = new AlertControllerSalary();
            alert.AlertFailed();
        }
        if (salary==0 || pID==null || pName==null || level==0){

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDataType();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        typeList = FXCollections.observableArrayList(typeNameArr);
        TypeList.setItems(typeList);
        LevelList.setItems(levelList);
    }
}
