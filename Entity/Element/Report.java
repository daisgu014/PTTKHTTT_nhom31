package Main.Entity.Element;

import java.sql.Date;

public class Report {

    private String reportId;
    private Date orderDate;
    private String employeeIdCreate;
    private String employeeIdConfirm;

    public Report(String reportId, Date orderDate, String employeeIdCreate, String employeeIdConfirm) {
        this.reportId = reportId;
        this.orderDate = orderDate;
        this.employeeIdCreate = employeeIdCreate;
        this.employeeIdConfirm = employeeIdConfirm;
    }

    public Report(String reportId, Date orderDate, String employeeIdCreate) {
        this.reportId = reportId;
        this.orderDate = orderDate;
        this.employeeIdCreate = employeeIdCreate;
    }

    public Report() {
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmployeeIdCreate() {
        return employeeIdCreate;
    }

    public void setEmployeeIdCreate(String employeeIdCreate) {
        this.employeeIdCreate = employeeIdCreate;
    }

    public String getEmployeeIdConfirm() {
        return employeeIdConfirm;
    }

    public void setEmployeeIdConfirm(String employeeIdConfirm) {
        this.employeeIdConfirm = employeeIdConfirm;
    }
}
