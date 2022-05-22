package Main.Helpers.SignIn;

import Main.Admin.DataManager.Controller.AdminEmployeeController;
import Main.Admin.IngredientsManager.Controller.MasterController;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Employee;
import Main.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SigninController {

    @FXML
    private TextField userTxf;

    @FXML
    private PasswordField passTxf;

    @FXML
    void checkSignIn(ActionEvent event) {
        String user = userTxf.getText();
        String pass = passTxf.getText();
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.initStyle(StageStyle.TRANSPARENT);
        try {
            a.getDialogPane().getStylesheets().add(new File("Helpers/SignIn/Alert.css").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if(user.equals("") || pass.equals("")){
            a.setContentText("Please enter username and password");
            a.show();
        }
        else{
            DAO dao = new DAO();
            ResultSet rs = dao.executeQuery(
                    "select a.AccountUsername, a.AccountPassword, e.EmployeeName,e.EmployeeID, w.WorkPositionLVL " +
                            "from Employee e join Account a on e.EmployeeID = a.EmployeeID " +
                            "join WorkPosition w on w.WorkPositionID = e.WorkPositionID");
            int flag = -1;
            try {
                while (rs.next()){
                    if(rs.getString(1).equals(user)){
                        if(rs.getString(2).equals(pass)){
                            new AdminEmployeeController().getDataEmployee();
                            for (Employee tmp : AdminEmployeeController.EmployeeTempList){
                                if(tmp.getEmployeeID().equals(rs.getString(4))){
                                    MainApp.staff = tmp;
                                }
                            }
                            flag = rs.getInt(5);
                        }else{
                            flag = 0;
                        }
                    }
                }
            }catch (SQLException e){
                System.out.println("DatabaseError");
            }

            switch (flag){
                case -1:
                    a.setContentText("Account not exist");
                    a.show();
                    break;
                case 0:
                    a.setContentText("Wrong Password");
                    a.show();
                    break;
                default:
                    MasterController.start();
                    MainApp.controlBar.initGUI();
                    MainApp.controlBar.getDataControl().setTxtUserName(MainApp.staff.getEmployeeName());
                    MainApp.controlBar.showFunction(flag);
            }

        }
    }
}
