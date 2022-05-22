package Main.Admin.SalaryCalculate.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class AddEmployyee implements Initializable{
    @FXML
    private ComboBox<String> positionList;
    @FXML
    private ComboBox<String> typeList;
    @FXML
    private TextField textEmpName;
    @FXML
    private TextField textEmpPhone;

    private List<String> positionNameArr = new ArrayList<>();
    private ObservableList<String> positionNameList;
    private List<String> typeNameArr    = new ArrayList<>();
    private ObservableList<String> typeNameList;
    private List<String> positionIDArr = new ArrayList<>();
    private ObservableList<String> positionIDList;
    private List<String> typeIDArr    = new ArrayList<>();
    private ObservableList<String> typeIDList;
    public void getPositionData() throws SQLException{
        positionNameArr.clear();
        positionIDArr.clear();
        DAO sql = new DAO();
        ResultSet rs = sql.executeQuery("SELECT *  FROM WorkPosition");
        while (rs.next()){
            positionNameArr.add(rs.getString(3));
            positionIDArr.add(rs.getString(2));
        }
    }
    public void getTypeData() throws  SQLException{
        typeNameArr.clear();
        typeIDArr.clear();
        DAO sql = new DAO();
        ResultSet rs = sql.executeQuery("SELECT * FROM WorkType");
        while (rs.next()){
            typeNameArr.add(rs.getString(3));
            typeIDArr.add(rs.getString(2));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getTypeData();
            getPositionData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        typeNameList = FXCollections.observableList(typeNameArr);
        positionNameList = FXCollections.observableList(positionNameArr);
        typeList.setItems(typeNameList);
        positionList.setItems(positionNameList);
    }
    public void AddEmployee() throws SQLException{
        String name = textEmpName.getText();
        String phone = textEmpPhone.getText();
        String type = typeList.getValue();
        String position = positionList.getValue();
        String typeID = null;
        String positionID= null;
        for(int i = 0; i<typeNameArr.size();i++){
            if (typeNameArr.get(i).equalsIgnoreCase(type)){
                typeID = typeIDArr.get(i);
            }
        }
        for(int i = 0; i<positionNameArr.size();i++){
            if (positionNameArr.get(i).equalsIgnoreCase(position)){
                positionID = positionIDArr.get(i);
            }
        }
        DAO sql = new DAO();
        sql.execute("INSERT INTO Employee(EmployeeName,EmployeePhone,WorkPositionID,WorkTypeID) " +
                "Values (N'"+name+"','"+phone+"','"+positionID+"','"+typeID+"')");
    }
}
