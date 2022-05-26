package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AdminProductEditController{
    @FXML
    private TextField textNameProductEdit;
    @FXML
    private Label lbCategory;
    @FXML
    private TextField textPrice;
    @FXML
    private Label lbStorage;

    AdminProductController adminProductController = new AdminProductController();
    public void handleEvent(Product product){
        textNameProductEdit.setText(product.getProductName());
        lbCategory.setText(product.getCategoryName());
        textPrice.setText(String.valueOf(product.getProductPrice()));
        lbStorage.setText(String.valueOf(product.getStorage()));
    }
    public boolean checkNameProduct(String Name) throws SQLException {
        AdminProductController adminProductController = new AdminProductController();
        adminProductController.GetDataProduct();
        for(Product product : adminProductController.productInTableList){
            if(product.getProductName().equalsIgnoreCase(Name)) return false;
        }
        return true;
    }
    public void excuteCheck(Product product) throws SQLException {
        if(!checkNameProduct(textNameProductEdit.getText())){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            EditProduct(product);
        }
    }
    public void EditProduct(Product product) throws SQLException {
        AdminCategoryController adminCategoryController = new AdminCategoryController();
        DAO dao = new DAO();
        String ProductID = product.getProductId();
        String ProductNameEdit = textNameProductEdit.getText();
        int price = Integer.parseInt(textPrice.getText());
        dao.execute("UPDATE Product Set ProductName=N'"+ProductNameEdit+"', ProductPrice="+price+" Where ProductID LIKE '"+ProductID+"'");
        adminProductController.GetDataProduct();
        ErrorController errorController = new ErrorController();
        try {
            errorController.displayError("save");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
