package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.ImportBill;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ImportBillDetailsController {
    @FXML
    private Label txtImportBillId;
    @FXML
    private Label txtProductName;
    @FXML
    private Label txtCategory;
    @FXML
    private Label txtQuantity;
    DAO dao;
    public void displayImportBillDetails(ImportBill importBill) throws SQLException {
        txtImportBillId.setText(importBill.getImportBillID());
        dao = new DAO();
        ResultSet resultSet =dao.executeQuery("SELECT Product.ProductName, Category.CategoryName, ImportBillDetails.Quantity\n" +
                "FROM ImportBill, ImportBillDetails, Product, Category\n" +
                "Where ImportBill.ImportBillID=ImportBillDetails.ImportBillID\n" +
                "AND ImportBill.ImportBillID='"+importBill.getImportBillID()+"'\n" +
                "AND ImportBillDetails.ProductID=Product.ProductID\n" +
                "AND Product.CategoryId=Category.CategoryID");
        while (resultSet.next()){
            txtProductName.setText(resultSet.getString(1));
            txtCategory.setText(resultSet.getString(2));
            txtQuantity.setText(String.valueOf(resultSet.getInt(3)));
        }
    }
}
