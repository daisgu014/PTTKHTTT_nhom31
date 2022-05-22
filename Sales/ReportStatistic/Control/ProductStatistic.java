package Main.Sales.ReportStatistic.Control;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import Main.Sales.ReportStatistic.Model.Prod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductStatistic extends ScreenManager implements Initializable {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnOk;

    @FXML
    private TableColumn<Prod, String> idProduct;

    @FXML
    private TableColumn<Prod, String> nameProduct;

    @FXML
    private TableColumn<Prod, String> catProduct;

    @FXML
    private TableColumn<Prod, String> priceProduct;

    @FXML
    private TableColumn<Prod, String> qtyProduct;

    @FXML
    private TableView<Prod> tableProduct;

    public static String startTime = "";
    public static String endTime = "";
    public static LocalDate beginTime = null;
    public static LocalDate lastTime = null;

    ObservableList<Prod> listProd = FXCollections.observableArrayList();
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
                    listProd.add(new Prod(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5)));
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
                    RevenueStatistic.beginTime = startDate.getValue();
                    RevenueStatistic.lastTime = endDate.getValue();
                    CategoryStatistic.beginTime = startDate.getValue();
                    CategoryStatistic.lastTime = endDate.getValue();
                    startTime = dateFormat.format(beginDate);
                    endTime = dateFormat.format(finishDate);
                    listProd.clear();
                    getData(
                            "select pd.ProductID, pd.ProductName, pd.CategoryID, sum(pr.ProductPrice*odt.Quantity), sum(odt.Quantity), od.OrderDate " +
                                    "from Orders od join OrderDetails odt on odt.OrderID = od.OrderID " +
                                    "join Product pd on pd.ProductID = odt.ProductID join Category ct on ct.CategoryID = pd.CategoryID " +
                                    "join ProductPrice pr on pr.ProductID = odt.ProductID " +
                                    "where od.OrderDate >=('%s') and od.OrderDate <= ('%s') and pr.ProductSize = odt.productSize " +
                                    "group by od.OrderDate, pd.ProductID, pd.ProductName, pd.CategoryID", startTime, endTime);
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
        RevenueStatistic.beginTime = null;
        RevenueStatistic.endTime = null;
        CategoryStatistic.beginTime = null;
        CategoryStatistic.endTime = null;
        listProd.clear();
        tableProduct.refresh();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        idProduct.setCellValueFactory(new PropertyValueFactory<Prod, String>("productId"));
        nameProduct.setCellValueFactory(new PropertyValueFactory<Prod, String>("productName"));
        catProduct.setCellValueFactory(new PropertyValueFactory<Prod, String>("productCategory"));
        priceProduct.setCellValueFactory(new PropertyValueFactory<Prod, String>("productPrice"));
        qtyProduct.setCellValueFactory(new PropertyValueFactory<Prod, String>("productQty"));
        if (beginTime != null && lastTime != null) {
            try {
                startDate.setValue(beginTime);
                endDate.setValue(lastTime);
                Date beginDate = dateFormat.parse(beginTime.toString());
                Date finishDate = dateFormat.parse(lastTime.toString());
                if (beginDate.compareTo(finishDate) <= 0) {
                    startTime = dateFormat.format(beginDate);
                    endTime = dateFormat.format(finishDate);
                    listProd.clear();
                    getData(
                            "select pd.ProductID, pd.ProductName, pd.CategoryID, sum(pr.ProductPrice*odt.Quantity), sum(odt.Quantity), od.OrderDate " +
                            "from Orders od join OrderDetails odt on odt.OrderID = od.OrderID " +
                            "join Product pd on pd.ProductID = odt.ProductID join Category ct on ct.CategoryID = pd.CategoryID " +
                            "join ProductPrice pr on pr.ProductID = odt.ProductID " +
                            "where od.OrderDate >=('%s') and od.OrderDate <= ('%s') and pr.ProductSize = odt.productSize " +
                            "group by od.OrderDate, pd.ProductID, pd.ProductName, pd.CategoryID", startTime, endTime);
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
            listProd.clear();
        }
        tableProduct.setItems(listProd);
    }

}
