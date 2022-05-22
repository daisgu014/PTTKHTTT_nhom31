package Main.Entity.DataAccess;

import Main.Admin.DataManager.Controller.AdminProductController;
import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.Element.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
//    private String connectURL = "jdbc:sqlserver://;" +
//                                "serverName=localhost;" +
//                                "databaseName=CNPM;" +
//                                "encrypt=true;trustServerCertificate=true";
    private String connectURL = "jdbc:sqlserver://;" +
                                "serverName=" +
                                "databaseName=CNPM;" +
                                "encrypt=true;trustServerCertificate=true";

//    private String DBuser = "sa";
//    private String DBpass = "123456";
    private String DBuser = "admin";
    private String DBpass = "1248163264128";


    private Connection connect;
    private Statement stmt;

    public ResultSet executeQuery(String sqlQuery){
        ResultSet result = null;
        try {
            connect = DriverManager.getConnection(connectURL,DBuser,DBpass);
            stmt= connect.createStatement();
            result = stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public void execute(String sqlQuery) throws SQLException {
        connect = DriverManager.getConnection(connectURL,DBuser,DBpass);
        stmt= connect.createStatement();
        stmt.execute(sqlQuery);
    }
    public boolean insert(String sqlQuery){
        boolean flag = false;
        try {
            connect = DriverManager.getConnection(connectURL,DBuser,DBpass);
            stmt= connect.createStatement();
            flag = stmt.execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public List<Product> getAllProduct() {
        ArrayList<Product> list =  new ArrayList<>();
        try {
            new AdminProductController().GetDataProduct();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(ProductInTable p : AdminProductController.productInTableList){
            Product tmp = new Product(p.getProductID(), p.getProductName(), p.getCategoryName());
            if(p.getPriceByS()!=0){
                tmp.getPriceList().add(new ProductPrice(tmp.getProductId(), "S",p.getPriceByS()));
            }
            if(p.getPriceByM()!=0){
                tmp.getPriceList().add(new ProductPrice(tmp.getProductId(), "M",p.getPriceByM()));
            }
            if(p.getPriceByL()!=0){
                tmp.getPriceList().add(new ProductPrice(tmp.getProductId(), "L",p.getPriceByL()));
            }
            list.add(tmp);
        }

        return list;
    }


    public String findFinalIncomeReport() {
        ResultSet rs;
        try {
            connect = DriverManager.getConnection(connectURL,DBuser,DBpass);
            stmt= connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("Select reportID from IncomeReports");
            rs.last();
            return rs.getString("reportID");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Ingredient> getAllIngredient() throws SQLException {
        ObservableList<Ingredient> list = FXCollections.observableArrayList();
        Ingredient i = null;
        ResultSet rs = this.executeQuery("Select * from Ingredients");
        while(rs.next()) {
            i = new Ingredient();
            i.setIngredientId(rs.getString("ingredientID"));
            i.setIngredientName(rs.getString("ingredientName"));
            i.setIngredientType(rs.getString("ingredientType"));
            i.setStorage(rs.getInt("storage"));
            i.setProducer(rs.getString("Producer"));
            i.setIncomePrice(rs.getInt("price"));
            list.add(i);
        }
        return list;
    }

    public ObservableList<IncomeReport> getAllIncomeReport(String sql) throws SQLException {
        ObservableList<IncomeReport> list = FXCollections.observableArrayList();
        ResultSet rs = this.executeQuery(sql);
        IncomeReport ir;
        while (rs.next()) {
            ir = new IncomeReport();
            ir.setReportId(rs.getString("reportID"));
            ir.setEmployeeIdCreate(rs.getString("created"));
            ir.setOrderDate(rs.getDate("reportDate"));
            ir.setStatus(rs.getString("StateReport"));
            ir.setSupplier(rs.getString("supplier"));
            list.add(ir);
        }
        return list;
    }

//    public ObservableList<ProductRecipe> getAllProductRecipe() throws SQLException {
//        ObservableList<ProductRecipe> list = FXCollections.observableArrayList();
//        ResultSet rs = this.executeQuery("Select * from ProductRecipes");
//        ProductRecipe pr;
//        while(rs.next()) {
//            pr = new ProductRecipe();
//            pr.setProductId(rs.getString("productID"));
//            pr.setIngredientId(rs.getString("ingredientID"));
//            pr.setProductQty(rs.getInt("productQty"));
//            pr.setIngredientQty(rs.getInt("ingredientQty"));
//            list.add(pr);
//        }
//        return list;
//    }

    public Ingredient findIngredientById(String id) throws SQLException {
        Ingredient i = null;
        ResultSet rs = this.executeQuery("Select * from Ingredients where ingredientID = '"+id+"'");
        while(rs.next()) {
            i = new Ingredient();
            i.setIngredientId(rs.getString("ingredientID"));
            i.setIngredientName(rs.getString("ingredientName"));
            i.setIngredientType(rs.getString("ingredientType"));
            i.setStorage(rs.getInt("storage"));
            i.setProducer(rs.getString("Producer"));
            i.setIncomePrice(rs.getInt("price"));
        }
        return i;
    }

    public String findEmployeeNameById(String id) throws SQLException {
        ResultSet rs = this.executeQuery("Select * from Employee where EmployeeID = '"+id+"'");
        if(rs.next()) {
            return  rs.getString("EmployeeName");
        }
        return null;
    }

    public ObservableList<IncomeDetail> getIncomeDetailsByIncomeReport(IncomeReport ir) throws SQLException {
        IncomeDetail ide;
        ObservableList<IncomeDetail> list = FXCollections.observableArrayList();
        ResultSet rs = this.executeQuery("Select * from IncomeDetails where reportID = '"+ir.getReportId()+"'");
        while (rs.next()) {
            ide = new IncomeDetail();
            ide.setIngredientChoice(findIngredientById(rs.getString("ingredientID")));
            ide.setOrderQty(rs.getInt("qty"));
            ide.setReceiveQty(rs.getInt("receiveQty"));
            list.add(ide);
        }
        return list;
    }

    public PreparedStatement getPrepareStatement(String sqlQuery) throws SQLException {
        connect = DriverManager.getConnection(connectURL,DBuser,DBpass);
        return connect.prepareStatement(sqlQuery);
    }

}
