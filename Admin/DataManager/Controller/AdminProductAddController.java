package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminProductAddController implements Initializable {
    @FXML
    ComboBox<String> CategoryList;
    @FXML
    private TextField textName;
    @FXML
    private TextField Price;

    public  List<String> CategoryNameArray = new ArrayList<>();
    private ObservableList<String> CategoryNameList;
    AdminCategoryController adminCategoryController = new AdminCategoryController();
    public void getCategoryName() throws SQLException {
        CategoryNameArray.clear();
        DAO dao = new DAO();
        ResultSet rs = dao.executeQuery("SELECT * FROM Category");
        while (rs.next()){
            String CategoryName = rs.getString(3);
            CategoryNameArray.add(CategoryName);
        }

    }
    public boolean checkNameProduct(String Name) throws SQLException {
        AdminProductController adminProductController = new AdminProductController();
        adminProductController.GetDataProduct();
        for(Product product : adminProductController.productInTableList){
            if(product.getProductName().equalsIgnoreCase(Name)) return false;
        }
    return true;
    }
    public void excuteCheck() throws SQLException {
        if(!checkNameProduct(textName.getText())){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            AddProduct();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCategoryName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CategoryNameList=FXCollections.observableList(CategoryNameArray);
        CategoryNameList.forEach((e)->{
            System.out.println(e);
        });
        CategoryList.setItems(CategoryNameList);

    }
    public void AddProduct() throws SQLException {
        String ProductName = textName.getText();
        String Category = CategoryList.getValue();
    }
}
