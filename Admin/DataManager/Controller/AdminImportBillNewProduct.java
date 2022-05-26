package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
import Main.Entity.Element.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminImportBillNewProduct implements Initializable {
    @FXML
    ComboBox<String> CategoryList;
    @FXML
    private TextField textName;
    @FXML
    private TextField Price;
    @FXML
    private TextField storage;
    public List<String> CategoryNameArray = new ArrayList<>();
    private ObservableList<String> CategoryNameList;
    AdminCategoryController adminCategoryController = new AdminCategoryController();
    AdminImportBillController adminImportBillController = new AdminImportBillController();
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
        adminImportBillController.GetDataProduct();
        for(Product product : adminImportBillController.productInTableList){
            if(product.getProductName().equalsIgnoreCase(Name)) return false;
        }
        return true;
    }
    public void AddProduct() throws SQLException {
        String productName = textName.getText();
        String Category = CategoryList.getValue();
        int  price = Integer.parseInt(Price.getText());
        String CategoryId=null;
        adminCategoryController.getData();
        for(Main.Entity.Element.Category category: adminCategoryController.list){
            if(category.getCategoryName().equalsIgnoreCase(Category)){
                CategoryId=category.getCategoryId();
            }
        }
        DAO dao = new DAO();
        dao.execute("INSERT INTO Product (ProductName, ProductPrice, CategoryId,Storage) VALUES (N'"+productName+"',"+price+",'"+CategoryId+"',0)");
    }
    public void Save () throws SQLException {
        AdminProductController adminProductController = new AdminProductController();
        adminImportBillController.GetDataProduct();
        String productId = null;
        for (Product p: adminImportBillController.productInTableList){
            if(p.getProductName().equalsIgnoreCase(textName.getText())){
                productId=p.getProductId();
            }
        }
        DAO dao =new DAO();
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
            pstm.setString(2,productId);
            pstm.setInt(3,Integer.parseInt(storage.getText()));
            pstm.execute();
            dao.execute("UPDATE Product SET Storage="+Integer.parseInt(storage.getText())+" Where ProductID LIKE '"+productId+"'");
            adminImportBillController.GetDataProduct();
            ErrorController errorController = new ErrorController();
            errorController.displayError("save");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminImportBillController.GetDataProduct();
        adminProductController.GetDataProduct();
    }
    public void execute() throws SQLException, IOException {
        if(checkNameProduct(textName.getText())){
            AddProduct();
            Save();
        }else{
            ErrorController errorController = new ErrorController();
            errorController.displayError("name");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.getCategoryName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CategoryNameList= FXCollections.observableList(CategoryNameArray);
        CategoryList.setItems(CategoryNameList);
    }
}
