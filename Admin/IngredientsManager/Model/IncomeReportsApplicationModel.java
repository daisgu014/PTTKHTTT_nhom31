package Main.Admin.IngredientsManager.Model;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.IncomeDetail;
import Main.Entity.Element.IncomeReport;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class IncomeReportsApplicationModel {
    private DAO dao;
    private ObservableList<IncomeReport> incomeReports;
    private ObservableList<IncomeReport> waitingInReport;
    private ObservableList<IncomeDetail> incomeDetails;
    private IncomeReport currentIncomeRe;
    public IncomeReportsApplicationModel() {
        dao = new DAO();
        try {
            incomeReports = dao.getAllIncomeReport("Select * from IncomeReports");
            waitingInReport = dao.getAllIncomeReport("Select * from IncomeReports where StateReport = 'Waiting'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<IncomeDetail> getIncomeDetails() {
        return incomeDetails;
    }

    public void setIncomeDetails(ObservableList<IncomeDetail> incomeDetails) {
        this.incomeDetails = incomeDetails;
    }

    public ObservableList<IncomeReport> getIncomeReports() {
        return incomeReports;
    }

    public void setIncomeReports(ObservableList<IncomeReport> incomeReports) {
        this.incomeReports = incomeReports;
    }

    public ObservableList<IncomeReport> getWaitingInReport() {
        return waitingInReport;
    }

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }

    public void setWaitingInReport(ObservableList<IncomeReport> waitingInReport) {
        this.waitingInReport = waitingInReport;
    }

    public IncomeReport getCurrentIncomeRe() {
        return currentIncomeRe;
    }

    public void setCurrentIncomeRe(IncomeReport currentIncomeRe) {
        this.currentIncomeRe = currentIncomeRe;
    }

    public void getIncomeDetails(IncomeReport choice) {
        this.currentIncomeRe = choice;
        try {
            incomeDetails = dao.getIncomeDetailsByIncomeReport(choice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateRecQtyOfOrder() {
        for(IncomeDetail i : this.getIncomeDetails()) {
            if(i.getReceiveQty() < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid receive quantity");
                return false;
            }
        }
        this.currentIncomeRe.setStatus("Confirmed");
        //update receive quantity to DB
        //Sql update receive quantity for each ingredient of order
        incomeDetails.forEach(data -> {
            dao.insert(String.format("Update IncomeDetails set receiveQty = %d where reportID='%s' and " +
                    "ingredientID='%s'", data.getReceiveQty(), currentIncomeRe.getReportId(),
                    data.getIngredientChoice().getIngredientId()));
        });
        dao.insert(String.format("Update IncomeReports set StateReport='%s' where reportID='%s'",
                currentIncomeRe.getStatus(), currentIncomeRe.getReportId()));


        this.getWaitingInReport().remove(this.getCurrentIncomeRe());
        this.incomeReports.clear();
        try {
            this.incomeReports.addAll(this.dao.getAllIncomeReport("Select * from IncomeReports"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        Sql update warehouse
        incomeDetails.forEach(data -> {
            dao.insert(String.format("Update Ingredients set storage = (storage + '%d') where ingredientID = '%s'",
                   data.getReceiveQty(), data.getIngredientChoice().getIngredientId()));
            try {
                IngredientApplicationModel.ingredientList.clear();
                IngredientApplicationModel.ingredientList.addAll(dao.getAllIngredient());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

}
