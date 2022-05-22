package Main.Sales.ReportEndDay.Control;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneController {
    public Stage stage;
    public Scene scene;
    public Parent root;

    public void screenReportEndDay(ActionEvent event) throws IOException {
        root = FXMLLoader.load(new File("Sales/ReportEndDay/View/ReportEndDay.fxml").toURI().toURL());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(new File("Sales/ReportEndDay/View/CSS/ReportEndDay.css").toURI().toURL().toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void screenReportCancelEndDay(ActionEvent event) throws IOException {
        root = FXMLLoader.load(new File("Sales/ReportEndDay/View/ReportCancelEndDay.fxml").toURI().toURL());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(new File("Sales/ReportEndDay/View/CSS/ReportCancelEndDay.css").toURI().toURL().toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
