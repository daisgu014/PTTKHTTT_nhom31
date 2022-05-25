package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminImportBillController implements Initializable {
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField txtSearchName;
    @FXML
    private TableColumn<Product, String> productIDTableColumn;
    @FXML
    private TableColumn<Product, String> productNameTableColumn;
    @FXML
    private TableColumn<Product, String> CategoryNameTableColumn;
    @FXML
    private TableColumn<Product, Integer> productPriceTableColumn;
    @FXML
    private TableColumn<Product, Integer> storageTableColumn;
    ObservableList<Product> productInTables;
    public static List<Product> productInTableList = new ArrayList<>();


    public void GetDataProduct() throws SQLException {
        productInTableList.clear();
        DAO dao = new DAO();
        ResultSet rs = dao.executeQuery("SELECT ProductID,ProductName,CategoryName,ProductPrice,Storage\n" +
                "FROM Category, Product\n" +
                "Where Category.CategoryID=Product.CategoryId");
        while (rs.next()) {
            String productId = rs.getString(1);
            String productName = rs.getString(2);
            String productCategory = rs.getString(3);
            int productPrice =rs.getInt(4);
            int storage = rs.getInt(5);
            Product product = new Product(productId,productName,productCategory,productPrice,storage);
            productInTableList.add(product);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.GetDataProduct();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SearchNameAutoFill();
        productInTables= FXCollections.observableArrayList(productInTableList);
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productId"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        CategoryNameTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("categoryName"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productPrice"));
        storageTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("storage"));

        productTableView.setItems(productInTables);
    }
    public void SearchName(ActionEvent e) throws SQLException {
        this.GetDataProduct();
        List<Product> productArray = new ArrayList<>();
        String pattern = ".*" + txtSearchName.getText() + ".*";
        for(Product o : productInTableList){
            if(o.getProductName().toLowerCase().matches(pattern.toLowerCase())){
                productArray.add(o);
            }
        }
        productTableView.setItems(FXCollections.observableArrayList(productArray));
        productTableView.refresh();
    }
    public void SearchNameAutoFill(){
        this.txtSearchName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                List<Product> productArray = new ArrayList<>();
                String pattern = ".*" + t1 + ".*";
                for(Product o : productInTableList){
                    if(o.getProductName().toLowerCase().matches(pattern.toLowerCase())){
                        productArray.add(o);
                    }
                }
                productTableView.setItems(FXCollections.observableArrayList(productArray));
                productTableView.refresh();
            }
        });
    }
    public void changeSceneImportBillOldProduct(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/Admin.ImportBill.OldProduct.fxml"));
        Pane ImportBillOldProductView = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane((DialogPane) ImportBillOldProductView);
        AdminImportBillOldProduct adminImportBillOldProduct=loader.getController();
        adminImportBillOldProduct.handleEvent();
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        if (ClickedButton.get() == ButtonType.APPLY) {
            adminImportBillOldProduct.Save();
            productTableView.setItems(productInTables);
            productTableView.refresh();
        } else if (ClickedButton.get() == ButtonType.CLOSE) {
            dialog.close();
        }
    }
    public void changeSceneImportBillNewProduct(ActionEvent e) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/Admin.ImportBill.NewProduct.fxml"));
        Pane ImportBillNewProductView = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane((DialogPane) ImportBillNewProductView);
        dialog.showAndWait();
//        Optional<ButtonType> ClickedButton = dialog.showAndWait();
//        AdminProductAddController AddController = loader.getController();
//        if (ClickedButton.get() == ButtonType.APPLY) {
//            AddController.excuteCheck();
//
//        } else if (ClickedButton.get() == ButtonType.CLOSE) {
//            dialog.close();
//        }
    }
    public void changeScenceImportBill(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/AdminImportBill.fxml"));
        Parent ProductViewParent = loader.load();
        Scene scene = new Scene(ProductViewParent);
        stage.setScene(scene);
    }
    public void GoBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("../CNPMCoffee/Admin/IngredientsManager/View/Admin.Employee.fxml"));
        Parent AdminViewParent = FXMLLoader.load(getClass().getResource("../View/Admin.Product.fxml"));
        Scene scene = new Scene(AdminViewParent);
        stage.setScene(scene);
    }
}
