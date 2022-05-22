package Main.Sales.ReportStatistic;

import java.io.File;

import Main.Sales.ReportStatistic.Control.RevenueStatistic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage){
        try {
            RevenueStatistic revenue = new RevenueStatistic();
            FXMLLoader fxmlLoader = new FXMLLoader();
            // fxmlLoader.setLocation(new File("src/Main/Sales/Discard/ReportStatistic/View/RevenueStatistic.fxml").toURI().toURL());
            fxmlLoader.setLocation(new File("Sales/ReportStatistic/View/Test.fxml").toURI().toURL());
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(new File("src/Main/Sales/Discard/ReportStatistic/View/CSS/RevenueStatistic.css").toURI().toURL().toExternalForm());
            primaryStage.setTitle("ReportStatistic");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
