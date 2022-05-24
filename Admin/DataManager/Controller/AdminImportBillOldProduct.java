package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.ImportBill;
import Main.Entity.Element.OrderDetail;
import Main.Entity.Element.Product;
import Main.MainApp;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminImportBillOldProduct implements Initializable {
    @FXML
    ComboBox<String> listProductID;
    @FXML
    private TextField txtProductName;
    @FXML
    private Label lblCategory;
    @FXML
    private TextField txtProductPrice;
    @FXML
    private TextField txtStorage;
    DAO dao = new DAO();
    public static List<String> listID = new ArrayList<>();
    public ObservableList<String> listIdProduct;
    public static List<ImportBill> importBillList = new ArrayList<>();
   AdminImportBillController adminImportBillController = new AdminImportBillController();
    public void getImPortBillData() throws SQLException {
        ResultSet resultSet = dao.executeQuery("SELECT * FROM ImportBill");
        while (resultSet.next()){
            String id = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            importBillList.add(new ImportBill(id,date));
        }

    }
    public void getDataProductIdlist() throws SQLException {
        DAO dao = new DAO();
        ResultSet rs = dao.executeQuery("SELECT ProductID FROM Product");
        while (rs.next()){
            String id = rs.getString(1);
            listID.add(id);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.getDataProductIdlist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listIdProduct= FXCollections.observableArrayList(listID);
        listProductID.setItems(listIdProduct);
    }
    public void handleEvent(){
        listProductID.valueProperty().addListener((observableValue, s, t1) -> {
            try {
                System.out.println(t1);
                PrintEmployName(t1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void PrintEmployName(String id) throws SQLException {
        adminImportBillController.GetDataProduct();
        for (Product o : adminImportBillController.productInTableList){
            if(o.getProductId().equalsIgnoreCase(id)){
                System.out.println(id);
                txtProductName.setText(o.getProductName());
                lblCategory.setText(o.getCategoryName());
                txtProductPrice.setText(String.valueOf(o.getProductPrice()));
            }else{
                System.out.println("iD ???");
            }
        }

    }
    public void Save () throws SQLException {
        AdminProductController adminProductController = new AdminProductController();
        PreparedStatement pstm;
        try {
            pstm = dao.getPrepareStatement(
                    "insert into ImportBill (CreateDate) " +
                            "output inserted.ImportBillID " +
                            "values(" +
                            "getDate()" +
                            ")");
            pstm.execute();
            ResultSet rs = pstm.getResultSet();
            rs.next();
            String orderId = rs.getString(1);
                pstm = dao.getPrepareStatement("insert into ImportBillDetails values (?,?,?)");
                pstm.setString(1,orderId);
                pstm.setString(2,listProductID.getValue());
                pstm.setInt(3,Integer.parseInt(txtStorage.getText()));
                pstm.execute();

                adminProductController.GetDataProduct();
            int OldStorage=0;
            for (Product product : adminProductController.productInTableList){
                    if(product.getProductId().equalsIgnoreCase(listProductID.getValue())){
                        OldStorage = product.getStorage();
                    }
                }
            int newStorage=OldStorage+Integer.parseInt(txtStorage.getText());
            dao.execute("UPDATE Product Set Storage="+newStorage+" Where ProductID ='"+listProductID.getValue()+"'");
            ErrorController errorController = new ErrorController();
            errorController.displayError("save");
            }
         catch (SQLException e) {
             throw new RuntimeException(e);
         } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminProductController.GetDataProduct();

    }

}
