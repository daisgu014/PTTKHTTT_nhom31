package Main.Sales.ReportStatistic.Control;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import Main.Sales.ReportStatistic.Model.Revenue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class RevenueStatistic extends ScreenManager implements Initializable {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableColumn<Revenue, String> colDate;

    @FXML
    private TableColumn<Revenue, String> colProduct;

    @FXML
    private TableColumn<Revenue, String> colOrder;

    @FXML
    private TableColumn<Revenue, String> colPrice;

    @FXML
    private TableView<Revenue> tableProduct;

    public static String startTime = "";
    public static String endTime = "";
    public static LocalDate beginTime = null;
    public static LocalDate lastTime = null;

    ObservableList<Revenue> listRevenue = FXCollections.observableArrayList();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ResultSet loadData(String yourQuery) {
        ResultSet rs = null;
        Connection cnn = null;
        try {
//            String url = "jdbc:sqlserver://;" +
//                    "serverName=localhost;" +
//                    "databaseName=CNPM;" +
//                    "encrypt=true;trustServerCertificate=true";
//            String user = "admin";
//            String pass = "123456";
            String url = "jdbc:sqlserver://;" +
                    "serverName=" +
                    "databaseName=CNPM;" +
                    "encrypt=true;trustServerCertificate=true";
            String user = "admin";
            String pass = "1248163264128";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cnn = DriverManager.getConnection(url, user, pass);
            Statement state = cnn.createStatement();
            rs = state.executeQuery(yourQuery);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thể tải dữ liệu lên !!");
            alert.showAndWait();
        }
        return rs;
    }

    public void getData(String yourQuery, String beginDate, String endDate) {
        ResultSet rs = loadData(String.format(yourQuery, beginDate, endDate));
        try {
            while (rs.next()) {
                try {
                    listRevenue.add(new Revenue(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                } catch (SQLException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Không thể liên kết để lấy dữ liệu !");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thể liên kết để lấy dữ liệu !");
            alert.showAndWait();
        }
    }

    public void checkDate(ActionEvent event) throws Exception {
        if (startDate.getValue() == null || endDate.getValue() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đúng định dạng ngày bắt đầu và ngày kết thúc !");
            alert.showAndWait();
            startDate.getEditor().clear();
            endDate.getEditor().clear();
        } else {
            try {
                Date beginDate = dateFormat.parse(startDate.getValue().toString());
                Date finishDate = dateFormat.parse(endDate.getValue().toString());
                if (beginDate.compareTo(finishDate) <= 0) {
                    beginTime = startDate.getValue();
                    lastTime = endDate.getValue();
                    ProductStatistic.beginTime = startDate.getValue();
                    ProductStatistic.lastTime = endDate.getValue();
                    CategoryStatistic.beginTime = startDate.getValue();
                    CategoryStatistic.lastTime = endDate.getValue();
                    startTime = dateFormat.format(beginDate);
                    endTime = dateFormat.format(finishDate);
                    listRevenue.clear();
                    getData(
                            "select od.OrderDate, count(distinct od.OrderID), sum(odt.Quantity), " +
                                    "sum(distinct od.TotalPrice) " +
                                    "from Orders od join OrderDetails odt on odt.OrderID = od.OrderID " +
                                    "where od.OrderDate >=('%s') and od.OrderDate <= ('%s') " +
                                    "group by  od.OrderDate", startTime, endTime);
                    tableProduct.refresh();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Ngày bắt đầu không được lớn hơn ngày kết thúc !");
                    alert.showAndWait();
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Không thể tải dữ liệu lên !");
                alert.showAndWait();
            }
        }
    }

    public void clearDate(ActionEvent event) throws Exception {
        startDate.getEditor().clear();
        endDate.getEditor().clear();
        beginTime = null;
        endTime = null ;
        startTime = "";
        endTime = "";
        ProductStatistic.beginTime = null;
        ProductStatistic.endTime = null;
        CategoryStatistic.beginTime = null;
        CategoryStatistic.endTime = null;
        listRevenue.clear();
        tableProduct.refresh();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        colDate.setCellValueFactory(new PropertyValueFactory<Revenue, String>("date"));
        colProduct.setCellValueFactory(new PropertyValueFactory<Revenue, String>("numProduct"));
        colOrder.setCellValueFactory(new PropertyValueFactory<Revenue, String>("numOrder"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Revenue, String>("totalPrice"));
        if (beginTime != null && lastTime != null) {
            try {
                startDate.setValue(beginTime);
                endDate.setValue(lastTime);
                Date beginDate = dateFormat.parse(beginTime.toString());
                Date finishDate = dateFormat.parse(lastTime.toString());
                if (beginDate.compareTo(finishDate) <= 0) {
                    startTime = dateFormat.format(beginDate);
                    endTime = dateFormat.format(finishDate);
                    listRevenue.clear();
                    getData(
                            "select od.OrderDate, count(distinct od.OrderID), sum(odt.Quantity), " +
                            "sum(distinct od.TotalPrice) " +
                            "from Orders od join OrderDetails odt on odt.OrderID = od.OrderID " +
                            "where od.OrderDate >=('%s') and od.OrderDate <= ('%s') " +
                            "group by  od.OrderDate", startTime, endTime);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Ngày bắt đầu không được lớn hơn ngày kết thúc !");
                    alert.showAndWait();
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Không thể tải dữ liệu lên !");
                alert.showAndWait();
            }
        }
        else if (beginTime == null || lastTime == null) {
            listRevenue.clear();
        }
        tableProduct.setItems(listRevenue);
    }

}
