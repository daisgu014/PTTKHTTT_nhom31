package Main.Admin.DataManager.Controller;
import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Product;
import Main.Entity.Element.ProductPrice;
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
import java.util.*;

public class AdminProductController implements Initializable {
    @FXML
    private TableView<ProductInTable> productTableView;
    @FXML
    private TextField txtSearchName;
    @FXML
    private TableColumn<ProductInTable, String> productIDTableColumn;
    @FXML
    private TableColumn<ProductInTable, String> productNameTableColumn;
    @FXML
    private TableColumn<ProductInTable, String> CategoryNameTableColumn;
    @FXML
    private TableColumn<ProductInTable, Integer> productPriceBySTableColumn;
    @FXML
    private TableColumn<ProductInTable, Integer> productPriceByMTableColumn;
    @FXML
    private TableColumn<ProductInTable, Integer> productPriceByLTableColumn;
     ObservableList<ProductInTable> productInTables;
    public static List<ProductInTable> productInTableList = new ArrayList<>();

    ArrayList<ProductPrice> productPriceArrayList = new ArrayList<>();
    public void GetDataProduct() throws SQLException {
        productInTableList.clear();
        DAO dao = new DAO();
        ResultSet rs = dao.executeQuery("SELECT ProductID, ProductName, CategoryName FROM Product, Category Where Product.CategoryID = Category.CategoryID");
        while (rs.next()) {
            String productId = rs.getString(1);
            String productName = rs.getString(2);
            String productCategory = rs.getString(3);
            ResultSet resultSet = dao.executeQuery("SELECT * FROM ProductPrice");
            while (resultSet.next()) {
                String productPriceId = resultSet.getString(1);
                if(productId.equalsIgnoreCase(productPriceId)){
                    String productSize = resultSet.getString(2);
                    int productPrice = resultSet.getInt(3);
                    ProductPrice newProductPrice = new ProductPrice(productId, productSize, productPrice);
                    productPriceArrayList.add(newProductPrice);
                }
            }
            Product newProduct = new Product(productId, productName, productCategory,productPriceArrayList);
            ProductInTable productInTable = new ProductInTable(newProduct);
            productInTableList.add(productInTable);
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
                productIDTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, String>("productID"));
                productNameTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, String>("productName"));
                CategoryNameTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, String>("categoryName"));
                productPriceBySTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, Integer>("priceByS"));
                productPriceByMTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, Integer>("priceByM"));
                productPriceByLTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, Integer>("priceByL"));
        productTableView.setItems(productInTables);
    }
    public void changeSceneAddEvent(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/Admin.Product.Add.fxml"));
        Pane ProductAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane((DialogPane) ProductAddViewParent);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        AdminProductAddController AddController = loader.getController();
        if(ClickedButton.get()==ButtonType.APPLY){
            AddController.excuteCheck();
            productTableView.setItems(FXCollections.observableArrayList(productInTableList));
            productTableView.refresh();
        }else if(ClickedButton.get()==ButtonType.CLOSE){
            dialog.close();
        }
        }
    public void changeSceneEditEvent(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        ProductInTable selected = productTableView.getSelectionModel().getSelectedItem();
        if (selected==null){
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane EditParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) EditParentView);
            dialog.showAndWait();
        }else{
            loader.setLocation(this.getClass().getResource("../View/Admin.Product.Edit.fxml"));
            Pane ProductEditViewParent = loader.load();
            AdminProductEditController adminProductEditController = loader.getController();
            adminProductEditController.handleEvent(selected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) ProductEditViewParent);
            dialog.initStyle(StageStyle.TRANSPARENT);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if(clickedButton.get() == ButtonType.APPLY){
                adminProductEditController.EditProduct(selected);
                productTableView.setItems(FXCollections.observableArrayList(productInTableList));
                productTableView.refresh();
            }else if(clickedButton.get()==ButtonType.CLOSE){
                dialog.close();
            }
        }

    }
    public void changeSceneDeleteEvent(ActionEvent e) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        ProductInTable selected = productTableView.getSelectionModel().getSelectedItem();
        if(selected ==null){
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane CategoryEditParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) CategoryEditParentView);
            dialog.showAndWait();
        }else{
            loader.setLocation(this.getClass().getResource("../View/Admin.Delete.fxml"));
            Pane CategoryDeleteParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) CategoryDeleteParentView);
            Optional<ButtonType> ClickedButton = dialog.showAndWait();
            AdminDeleteController adminDeleteController = loader.getController();
            if(ClickedButton.get()==ButtonType.YES){
                adminDeleteController.DeleteProduct(selected);;
                productTableView.setItems(FXCollections.observableArrayList(productInTableList));
                productTableView.refresh();
            }

        }

    }
    public void SearchName(ActionEvent e) throws SQLException {
        this.GetDataProduct();
        List<ProductInTable> productArray = new ArrayList<>();
        String pattern = ".*" + txtSearchName.getText() + ".*";
       for(ProductInTable o : productInTableList){
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
                List<ProductInTable> productArray = new ArrayList<>();
                String pattern = ".*" + t1 + ".*";
                for(ProductInTable o : productInTableList){
                    if(o.getProductName().toLowerCase().matches(pattern.toLowerCase())){
                        productArray.add(o);
                    }
                }
                productTableView.setItems(FXCollections.observableArrayList(productArray));
                productTableView.refresh();
            }
    });
    }
    public void GoBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("../CNPMCoffee/Admin/IngredientsManager/View/Admin.Employee.fxml"));
        Parent AdminViewParent = FXMLLoader.load(getClass().getResource("../View/Admin.fxml"));
        Scene scene = new Scene(AdminViewParent);
        stage.setScene(scene);
    }


}
