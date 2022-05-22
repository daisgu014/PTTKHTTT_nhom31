package Main.Admin.SalaryCalculate;

import Main.Entity.DataAccess.DAO;
import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainSalary extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../SalaryCalculate/View/Timekeeping.fxml"));
        Scene scene = new Scene(root);
        EmbeddedWindow stage;
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {

        DAO sql = new DAO();
//        sql.executeQuery("SELECT * FROM category");
        ResultSet rs = sql.executeQuery("SELECT * FROM category");
        while(true){
            try {
                if (!rs.next()) break;
                else {
                    String s = rs.getString(2);
                    System.out.println(s);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        launch(args);

    }
}
