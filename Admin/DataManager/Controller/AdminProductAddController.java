package Main.Admin.DataManager.Controller;

import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
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
    private TextField PriceByS;
    @FXML
    private TextField PriceByM;
    @FXML
    private TextField PriceByL;
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
        for(ProductInTable product : adminProductController.productInTableList){
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
        int PriceS;
        if (PriceByS.getText().equalsIgnoreCase("")){
            PriceS=0;
        }else{
            PriceS= Integer.parseInt(PriceByS.getText());
        };
        int PriceM;
        if(PriceByL.getText().equalsIgnoreCase("")){
            PriceM=0;
        }else{
            PriceM=Integer.parseInt(PriceByM.getText());
        }
        int PriceL;
        if (PriceByL.getText().equalsIgnoreCase("")){
             PriceL = 0;
        }else{
            PriceL=Integer.parseInt(PriceByL.getText());
        }

        String CategoryId = null;
        adminCategoryController.getData();
        for(int i=0;i<adminCategoryController.list.size();i++)
        {
            if(adminCategoryController.list.get(i).getCategoryName().equalsIgnoreCase(Category)){
                CategoryId=adminCategoryController.list.get(i).getCategoryId();
            }
        }
        System.out.println(ProductName+CategoryId);
         DAO dao = new DAO();
        dao.execute("INSERT INTO Product (ProductName, CategoryID) VALUES (N'"+ProductName+"','"+CategoryId+"')");
        AdminProductController adminProductController = new AdminProductController();
        adminProductController.GetDataProduct();
        String ProductId = null;
        for (int i= 0; i<adminProductController.productInTableList.size();i++){
            if(adminProductController.productInTableList.get(i).getProductName().equalsIgnoreCase(ProductName)){
                ProductId=adminProductController.productInTableList.get(i).getProductID();
            }
        }
        dao.execute("INSERT INTO ProductPrice (ProductID, ProductSize, ProductPrice) VALUES ('"+ProductId+"','S','"+PriceS+"')");
        dao.execute("INSERT INTO ProductPrice (ProductID, ProductSize, ProductPrice) VALUES ('"+ProductId+"','M','"+PriceM+"')");
        dao.execute("INSERT INTO ProductPrice (ProductID, ProductSize, ProductPrice) VALUES ('"+ProductId+"','L','"+PriceL+"')");
        adminProductController.GetDataProduct();
    }
}
