package Main.Admin.DataManager.Controller;

import Main.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable{
    @FXML
    private Label txtUserName;

    public void setTxtUserName(String name){
        txtUserName.setText(name);
    }

    public void changeSceneEmployee(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.centerOnScreen();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("../CNPMCoffee/Admin/IngredientsManager/View/Admin.Employee.fxml"));
        Parent EmployeeViewParent = FXMLLoader.load(getClass().getResource("../View/Admin.Employee.fxml"));
        Scene scene = new Scene(EmployeeViewParent);
        stage.setScene(scene);
    }
    public void changeSceneProduct(ActionEvent e) throws  IOException{
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Admin.Product.fxml"));
        Parent ProductViewParent = loader.load();
        Scene scene = new Scene(ProductViewParent);
        stage.setScene(scene);
    }
    public void changeSceneCategory(ActionEvent e) throws  IOException{
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Admin.Category.fxml"));
        Parent CategoryViewParent = loader.load();
        Scene scene = new Scene(CategoryViewParent);
        stage.setScene(scene);
    }
    public void changeSceneAccount(ActionEvent e) throws  IOException{
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Admin.Account.fxml"));
        Parent AccountViewParent = loader.load();
        Scene scene = new Scene(AccountViewParent);
        stage.setScene(scene);
    }

    public void screenRevenueStatistic(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File("Sales/ReportStatistic/View/RevenueStatistic.fxml").toURI().toURL());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.getScene().getStylesheets().add(new File("Sales/ReportStatistic/View/CSS/RevenueStatistic.css")
                .toURI().toURL().toExternalForm());
        stage.show();
    }

    public void changeSceneSalary(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File("Admin/SalaryCalculate/View/Timekeeping.fxml").toURI().toURL());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.getScene().getStylesheets().add(new File("Admin/SalaryCalculate/View/CSS/Salary.css")
                .toURI().toURL().toExternalForm());
        stage.show();
    }

    public void changeSceneWareHouse(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File("Admin/IngredientsManager/View/IngredientOrder.fxml").toURI().toURL());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTxtUserName(MainApp.staff.getEmployeeName());
    }
}
