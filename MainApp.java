package Main;

import Main.Admin.IngredientsManager.Controller.MasterController;
import Main.Entity.Element.Employee;
import Main.Helpers.MainControl.ControlBar;
import Main.Sales.Sales.Control.SalesApplicationControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;


public class MainApp extends Application {

    Dimension screenSize;

    Stage toolPanel;

    public static Employee staff;

    public static ControlBar controlBar;


    private void createControlBar(Stage stage){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        toolPanel = new Stage(StageStyle.TRANSPARENT);
        toolPanel.initOwner(stage);
        controlBar = new ControlBar(stage);
        toolPanel.setScene(new Scene(controlBar));
        controlBar.prepareCSS();
        controlBar.showFunction(0);
    }


    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        createControlBar(stage);
        toolPanel.show();
    }
}