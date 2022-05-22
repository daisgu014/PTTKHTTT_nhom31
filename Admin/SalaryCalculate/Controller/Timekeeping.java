package Main.Admin.SalaryCalculate.Controller;
import Main.Entity.DataAccess.DAO;
import Main.Helpers.SignIn.AlertController;
import com.sun.marlin.Dasher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
public class Timekeeping implements Initializable {
    @FXML
    private Label textError;
    @FXML
    private Label textSuccess;
    @FXML
    private Label textFailed;
    @FXML
    private TextField name;
    @FXML
    private TextField employeeID;
    public void changeSceneSalarytable(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Salarytable.fxml"));
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
    @FXML
    public void checkin(ActionEvent event) throws IOException {
        String ID = employeeID.getText();
        DAO sql = new DAO();
        try {
            sql.execute("INSERT INTO DailyWork(DailyDate,EmployeeID, Checkin,Checkout,WorkingHour) \n" +
                    "VALUES (CURRENT_TIMESTAMP,'"+ID+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0);");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../View/AlertSuccess.fxml"));
            Pane ProductAddViewParent = loader.load();
            javafx.scene.control.Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) ProductAddViewParent);
            Optional<ButtonType> ClickedButton = dialog.showAndWait();
            if(ClickedButton.get()==ButtonType.OK) {
                dialog.close();
            }

        } catch (Exception e ) {
            AlertControllerSalary alert = new AlertControllerSalary();
            alert.AlertSQL("../View/AlertSQL.fxml");
        }
        System.out.println(ID);
    }
    public boolean checkExist(String id) throws SQLException {
        DAO sql = new DAO();
        ResultSet rs = sql.executeQuery("SELECT EmployeeID FROM  DailyWork WHERE EmployeeID = '"+id+"' AND DATEPART(DAYOFYEAR,DailyDate) = DATEPART(DAYOFYEAR,CURRENT_TIMESTAMP);");
        while (rs.next()){
            if (id.equalsIgnoreCase(rs.getString(1))){
                return true;
            }
        }
        return  false;
    }
    public void checkout(ActionEvent event) throws IOException, SQLException {
        DAO sql = new DAO();
        String ID = employeeID.getText();
        System.out.println(ID+"id");
        if (ID=="" || checkExist(ID) == false){
            AlertControllerSalary alert = new AlertControllerSalary();
            alert.AlertFailed();
        }else {
            try {
                sql.execute("UPDATE DailyWork SET \n" +
                        "Checkout=CURRENT_TIMESTAMP,\n" +
                        "WorkingHour=DATEDIFF(MI,Checkin,Checkout)/60\n" +
                        "WHERE EmployeeID = '"+ID+"' AND DATEPART(DAYOFYEAR,DailyDate) = DATEPART(DAYOFYEAR,CURRENT_TIMESTAMP);");
                AlertControllerSalary alert = new AlertControllerSalary();
                alert.AlertSuccess();
            } catch (SQLException e) {
                System.out.println(ID);
            }
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
