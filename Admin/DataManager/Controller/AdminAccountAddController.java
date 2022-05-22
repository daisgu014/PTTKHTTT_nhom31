package Main.Admin.DataManager.Controller;

import Main.Admin.DataManager.Model.AccountInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Employee;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminAccountAddController implements Initializable {
     @FXML
    ComboBox<String> EmployeeId;
     @FXML
    private Label EmployeeName;
     @FXML
    private TextField txtUserName;
     @FXML
    private PasswordField textPassword;
     @FXML
     private PasswordField txtPasswordAgain;

     public static List<String> EmployeeIdArray = new ArrayList<>();
     ObservableList<String> EmployeeList;
     DAO dao;
     public void getEmployeeList() throws SQLException {
         EmployeeIdArray.clear();
         dao= new DAO();
         ResultSet rs = dao.executeQuery("SELECT EmployeeID FROM Employee");
         while (rs.next()){
             String EmployeeIDInDB = rs.getString(1);
             EmployeeIdArray.add(EmployeeIDInDB);
         }
     }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getEmployeeList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        EmployeeList= FXCollections.observableArrayList(EmployeeIdArray);
        EmployeeId.setItems(EmployeeList);

    }
     public void handleEvent(){
         EmployeeId.valueProperty().addListener((observableValue, s, t1) -> {
             try {
                 PrintEmployName(t1);
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         });
     }
    public void PrintEmployName(String id) throws SQLException {

             String name = null;
             dao = new DAO();
             ResultSet resultSet=dao.executeQuery("SELECT EmployeeName FROM Employee Where EmployeeID LIKE '"+id+"'");
             while (resultSet.next()){
                 name=resultSet.getString(1);
             }
             EmployeeName.setText(name);
             if(name==null){
                 System.out.println("khong co");
             }

    }

    public boolean checkPassword(){
         String password = textPassword.getText();
         String passwordAgain =txtPasswordAgain.getText();
         if(password.equalsIgnoreCase(passwordAgain)){
             return true;
         }
         return false;
    }
    public void AddAccount() throws SQLException {
         String id = EmployeeId.getValue();
         String UserName = txtUserName.getText();
         String password= textPassword.getText();
         dao = new DAO();
         dao.execute("INSERT INTO Account(AccountUsername, AccountPassword, EmployeeID) VALUES('"+UserName+"','"+password+"','"+id+"')");
         AdminAccountController adminAccountController = new AdminAccountController();
         adminAccountController.getDataAccount();
    }
    public boolean checkName(String Name) throws SQLException {
       AdminAccountController adminAccountController = new AdminAccountController();
       adminAccountController.getDataAccount();
        for(AccountInTable e : adminAccountController.accountInTableArrayList){
            if(e.getUsername().equalsIgnoreCase(Name)) return false;
        }
        return true;
    }
    public void excuteCheck() throws SQLException {
        if(!checkName(txtUserName.getText())){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            AddAccount();
        }
    }
}
