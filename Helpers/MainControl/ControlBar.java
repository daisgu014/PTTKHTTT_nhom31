package Main.Helpers.MainControl;

import Main.Admin.DataManager.Controller.AdminController;
import Main.Sales.ReportEndDay.Control.ReportCancelDay;
import Main.Sales.Sales.Control.SalesApplicationControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class ControlBar extends VBox {

    ToggleButton saleBtn, EoDsBtn, dataBtn, salaryBtn, ingredientBtn;
    Scene salesScreen;
    Scene eodScreen;
    Scene dataManagerScreen;
    Scene ingredientManagerScreen;
    Scene salaryManagerScreen;
    Scene loginScreen;
    Stage owner;
    Button logoutBtn,exitBtn;

    AdminController dataControl;

    public ControlBar(Stage stage){
        this.owner = stage;
        start();
    }

    public AdminController getDataControl() {
        return dataControl;
    }

    public void showFunction(int lvl){
        this.getChildren().clear();
        switch (lvl) {
            case 0 -> {
                owner.setScene(loginScreen);
                this.getChildren().add(exitBtn);
            }
            case 1 -> {
                this.getChildren().add(saleBtn);
                this.getChildren().add(EoDsBtn);
                saleBtn.setSelected(true);
                owner.setScene(salesScreen);
                this.getChildren().add(logoutBtn);
            }
            case 2 -> {
                this.getChildren().add(saleBtn);
                this.getChildren().add(EoDsBtn);
                this.getChildren().add(dataBtn);
                this.getChildren().add(ingredientBtn);
                this.getChildren().add(salaryBtn);
                dataBtn.setSelected(true);
                owner.setScene(dataManagerScreen);
                this.getChildren().add(logoutBtn);
            }
        }
        this.getScene().getWindow().sizeToScene();
    }

    public void initGUI(){
        saleBtn = new ToggleButton("Sale");
        EoDsBtn = new ToggleButton("CloseStore");
        dataBtn = new ToggleButton("Data");
        salaryBtn = new ToggleButton("Salary");
        ingredientBtn = new ToggleButton("Ingredients");
        salesScreen = new SalesApplicationControl().getView();
        try {
            FXMLLoader fx = new FXMLLoader();
            fx.setLocation(getClass().getResource("../../Admin/DataManager/View/Admin.fxml"));
            dataManagerScreen = new Scene(fx.load());
            dataControl = fx.getController();
            ingredientManagerScreen = new Scene(FXMLLoader.load(getClass().getResource("../../Admin/IngredientsManager/View/IngredientOrder.fxml")));
            eodScreen = new Scene(FXMLLoader.load(new File("Sales/ReportEndDay/View/ReportEndDay.fxml").toURI().toURL()));
            eodScreen.getStylesheets().add(new File("Sales/ReportEndDay/View/CSS/ReportEndDay.css").toURI().toURL().toExternalForm());
            loginScreen = new Scene(FXMLLoader.load(new File("Helpers/SignIn/Signinv2.fxml").toURI().toURL()));

            salaryManagerScreen = new Scene(FXMLLoader.load(getClass().getResource("../../Admin/SalaryCalculate/View/Timekeeping.fxml")));
            salaryManagerScreen.getStylesheets().add(new File("Admin/SalaryCalculate/View/CSS/Salary.css").toURI().toURL().toExternalForm());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        ToggleGroup gr = new ToggleGroup();
        saleBtn.setToggleGroup(gr);
        EoDsBtn.setToggleGroup(gr);
        dataBtn.setToggleGroup(gr);
        ingredientBtn.setToggleGroup(gr);
        salaryBtn.setToggleGroup(gr);

        logoutBtn = new Button("Logout");

        setBtnImgandTooltip();


        createHandleEvent();




        //this.setRotate(90);

    }

    public void start(){
        try {
            loginScreen = new Scene(FXMLLoader.load(new File("Helpers/SignIn/Signinv2.fxml").toURI().toURL()));
            } catch (IOException e) {
            throw new RuntimeException(e);
        }

        exitBtn = new Button("Exit");
        exitBtn.setOnAction(e->owner.close());
        owner.sceneProperty().addListener((w,o,n)->{
            w.getValue().getWindow().centerOnScreen();
            changeLocation();
        });
    }

    private void setBtnImgandTooltip(){
        try {
            ImageView img = new ImageView(new Image(new FileInputStream("Icon/coffee-cup.png")));
            img.setFitHeight(25);
            img.setFitWidth(25);
            saleBtn.setGraphic(img);
            saleBtn.setTooltip(new Tooltip("Go to Sales Screen"));
            saleBtn.setText("");
            img = new ImageView(new Image(new FileInputStream("Icon/seo-report.png")));
            img.setFitHeight(25);
            img.setFitWidth(25);
            EoDsBtn.setGraphic(img);
            EoDsBtn.setTooltip(new Tooltip("Close store session and start report Daily Sales"));
            EoDsBtn.setText("");
            img = new ImageView(new Image(new FileInputStream("Icon/manager.png")));
            img.setFitHeight(25);
            img.setFitWidth(25);
            dataBtn.setGraphic(img);
            dataBtn.setTooltip(new Tooltip("Go to Manager Screen to manage store data"));
            dataBtn.setText("");
            img = new ImageView(new Image(new FileInputStream("Icon/ingredients.png")));
            img.setFitHeight(25);
            img.setFitWidth(25);
            ingredientBtn.setGraphic(img);
            ingredientBtn.setTooltip(new Tooltip("Go to Workshop Management screen"));
            ingredientBtn.setText("");
            img = new ImageView(new Image(new FileInputStream("Icon/salary2.png")));
            img.setFitHeight(25);
            img.setFitWidth(25);
            salaryBtn.setGraphic(img);
            salaryBtn.setTooltip(new Tooltip("Go to Salary Management Screen"));
            salaryBtn.setText("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void prepareCSS(){
        try {
            this.getScene().getStylesheets().add(new File("Helpers/MainControl/Controlbar.css").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeLocation(){

        if(owner.getX()<100){
            owner.setX(100);
            owner.setY(0);
        }

        this.getScene().getWindow().setX(owner.getX()-100);
        this.getScene().getWindow().setY(owner.getY());


    }

    private void createHandleEvent(){

        saleBtn.setOnAction(e->{
            owner.setScene(salesScreen);
            //changeLocation();
        });
        EoDsBtn.setOnAction(e->{
            try {
                eodScreen = new Scene(FXMLLoader.load(new File("Sales/ReportEndDay/View/ReportEndDay.fxml").toURI().toURL()));
                eodScreen.getStylesheets().add(new File("Sales/ReportEndDay/View/CSS/ReportEndDay.css").toURI().toURL().toExternalForm());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            owner.setScene(eodScreen);
            //changeLocation();
        });
        dataBtn.setOnAction(e->{
            owner.setScene(dataManagerScreen);
            //changeLocation();

        });
        salaryBtn.setOnAction(e->{
            owner.setScene(salaryManagerScreen);
        });
        ingredientBtn.setOnAction(e->{
            owner.setScene(ingredientManagerScreen);
            //changeLocation();
        });
        logoutBtn.setOnAction(e->showFunction(0));

    }

}
