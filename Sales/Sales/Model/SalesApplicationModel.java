package Main.Sales.Sales.Model;

import Main.Admin.DataManager.Controller.AdminProductController;
import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Order;
import Main.Entity.Element.OrderDetail;
import Main.Entity.Element.Product;
import Main.Entity.Element.ProductPrice;
import Main.MainApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesApplicationModel {
    List<Product> productList;
    Order currentOrders;
    ArrayList<OrderDetail> currentChoices;
    DAO dao;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Order getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(Order currentOrders) {
        this.currentOrders = currentOrders;
    }

    public ArrayList<OrderDetail> getCurrentChoices() {
        return currentChoices;
    }

    public void setCurrentChoices(ArrayList<OrderDetail> currentChoices) {
        this.currentChoices = currentChoices;
    }

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }

    public SalesApplicationModel(){
        dao = new DAO();
        productList = dao.getAllProduct();
        createNewOrder();
    }

    private void prepareProductList(){
        productList = new ArrayList<>();
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
            productList.add(tmp);
        }
    }

    public void createNewOrder(){
        currentOrders = new Order();
        currentChoices = new ArrayList<>();

    }

    public void updateChoice(int i,OrderDetail ord){
        if(ord.getQuantity()==0){
            currentChoices.remove(i);
        }else{
            currentChoices.set(i, ord);
        }

    }

    public void addItem(OrderDetail od){
        boolean flag = false;
        for(OrderDetail orderDetail : currentChoices){
            if(orderDetail.getProductChoice().getProductId().equals(od.getProductChoice().getProductId())
                    && orderDetail.getSize().equals(od.getSize())){
                orderDetail.setQuantity(orderDetail.getQuantity()+od.getQuantity());
                orderDetail.setPrice(orderDetail.getPrice()+od.getPrice());
                flag=true;
            }
        }
        if(!flag) {
            this.currentChoices.add(od);
        }
    }

    public void updatePrice(){
        calculatePrice();
    }

    public void payCurrentOrder(){
        calculatePrice();
        save();
        createNewOrder();
    }

    private void save(){
        PreparedStatement pstm;
        try {
           pstm = dao.getPrepareStatement(
                    "insert into Orders (TotalPrice, OrderDate, OrderTime, Cashier) " +
                    "output inserted.OrderID " +
                    "values(" +
                    "?, getDate(), getDate(), ? " +
                    ")");
           pstm.setInt(1,currentOrders.getTotalPrice());
           pstm.setString(2, MainApp.staff.getEmployeeID());
           pstm.execute();
           ResultSet rs = pstm.getResultSet();
           rs.next();
           String orderId = rs.getString(1);

           for (OrderDetail od : currentChoices){
               pstm = dao.getPrepareStatement("insert into OrderDetails values (?,?,?,?)");
               pstm.setString(1,orderId);
               pstm.setString(2,od.getProductChoice().getProductId());
               pstm.setInt(3,od.getQuantity());
               pstm.setString(4, od.getSize());
               pstm.execute();
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void calculatePrice(){
        int totalPrice = 0;
        for(OrderDetail od : currentChoices){
            totalPrice += od.getPrice();
        }
        currentOrders.setTotalPrice(totalPrice);
    }

}
