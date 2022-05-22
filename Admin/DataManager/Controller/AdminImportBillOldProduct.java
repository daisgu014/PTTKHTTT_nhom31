package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Product;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public static List<String> listID = new ArrayList<>();
    public ObservableList<String> listIdProduct;
   AdminImportBillController adminImportBillController = new AdminImportBillController();

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

}
