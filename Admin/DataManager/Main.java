package Main.Admin.DataManager;

import Main.Entity.DataAccess.DAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("../DataManager/View/Admin.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("../DataManager/View/Css/AdminApplication.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
