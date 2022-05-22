package Main.Sales.Sales;

import Main.Sales.Sales.Control.SalesApplicationControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainSale extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        primaryStage.setScene(new SalesApplicationControl().getView());
        primaryStage.show();

    }
}

