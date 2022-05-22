package Main.Entity.Element;

import java.sql.Date;

public class IncomeReport extends Report{

    private String supplier;
    private String Status;

    public IncomeReport(String reportId, Date orderDate, String employeeIdCreate,
                        String employeeIdConfirm, String supplier, String status) {
        super(reportId, orderDate, employeeIdCreate, employeeIdConfirm);
        this.supplier = supplier;
        Status = status;
    }

    public IncomeReport(String reportId, Date orderDate, String employeeIdCreate, String supplier, String status) {
        super(reportId, orderDate, employeeIdCreate);
        this.supplier = supplier;
        Status = status;
    }

    public IncomeReport() {
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
