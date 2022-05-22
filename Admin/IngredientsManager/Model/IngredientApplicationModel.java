package Main.Admin.IngredientsManager.Model;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.IncomeDetail;
import Main.Entity.Element.IncomeReport;
import Main.Entity.Element.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public class IngredientApplicationModel {

    private IncomeReport incomeReport;
    private DAO dao;

    public static ObservableList<Ingredient> ingredientList;

    private ObservableList<IncomeDetail> currentChoices;

    public IngredientApplicationModel() {
        dao = new DAO();
        try {
            ingredientList = dao.getAllIngredient();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }

    public ObservableList<IncomeDetail> getCurrentChoices() {
        return currentChoices;
    }

    public void setCurrentChoices(ObservableList<IncomeDetail> currentChoices) {
        this.currentChoices = currentChoices;
    }


    public IncomeReport getIncomeReport() {
        return incomeReport;
    }

    public void setIncomeReport(IncomeReport incomeReport) {
        this.incomeReport = incomeReport;
    }
    public void addNewItem(Ingredient i) {
        String sql = String.format("Insert into Ingredients(ingredientName, ingredientType, storage, Producer, price) " +
                "values (N'%s', N'%s', '%d', N'%s', '%d')", i.getIngredientName(), i.getIngredientType(), i.getStorage(),
                i.getProducer(), i.getIncomePrice());
        dao.insert(sql);
        try {
            ingredientList.clear();
            ingredientList.addAll(dao.getAllIngredient());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateItem(int index, Ingredient i) {
        ingredientList.set(index, i);
        String sql = String.format("Update Ingredients set ingredientName = N'%s', ingredientType = N'%s', " +
                "storage = '%d', Producer = N'%s', price = '%d' where ingredientID = '%s'", i.getIngredientName(),
                i.getIngredientType(), i.getStorage(), i.getProducer(), i.getIncomePrice(), i.getIngredientId());
        dao.insert(sql);
    }

    public void removeItem(Ingredient i) {
        ingredientList.remove(i);
        String sql = String.format("Delete from Ingredients where ingredientID = '%s'", i.getIngredientId());
        dao.insert(sql);
    }

    public void createNewOrder(){
        this.incomeReport = new IncomeReport();
        this.currentChoices = FXCollections.observableArrayList();
    }

    public boolean addChosenItem(IncomeDetail incomeDetail) {
        int i = 0;
        for (IncomeDetail id : this.getCurrentChoices()) {
            if(id.getIngredientChoice().getIngredientId().equalsIgnoreCase(
                    incomeDetail.getIngredientChoice().getIngredientId())) {
                id.setOrderQty(id.getOrderQty() + 1);
                currentChoices.set(i, id);
                return true;
            }
            i++;
        }
        this.getCurrentChoices().add(incomeDetail);
        return true;
    }

    public void removeChosenItem(IncomeDetail incomeDetail) {
        this.getCurrentChoices().remove(incomeDetail);
    }

    public boolean checkChooseItem() {
        return this.getCurrentChoices().isEmpty();
    }

    public void saveIncomeReport() {
        String sql = String.format("Insert into IncomeReports(created, reportDate, supplier, StateReport) " +
                "values ('%s', '"+incomeReport.getOrderDate()+"', '%s', '%s')", incomeReport.getEmployeeIdCreate(),
                incomeReport.getSupplier(), incomeReport.getStatus());
        dao.insert(sql);
        incomeReport.setReportId(dao.findFinalIncomeReport());
    }


    public void saveIncomeDetails() {
        this.getCurrentChoices().forEach(data -> {
            String sql = String.format("Insert into IncomeDetails(reportID, ingredientID, qty, receiveQty) " +
                    "values " +"('%s', '%s', '%d', '%d')", incomeReport.getReportId(),
                    data.getIngredientChoice().getIngredientId(), data.getOrderQty(), data.getReceiveQty());
            dao.insert(sql);
        });
    }


}
