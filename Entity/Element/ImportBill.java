package Main.Entity.Element;

import java.util.Date;

public class ImportBill {
    private String importBillID;
    private Date createDate;
    private String SupplierID;

    public ImportBill(String importBillID, Date createDate, String supplierID) {
        this.importBillID = importBillID;
        this.createDate = createDate;
        SupplierID = supplierID;
    }

    public ImportBill(String importBillID, Date createDate) {
        this.importBillID = importBillID;
        this.createDate = createDate;
    }

    public ImportBill() {
    }

    public String getImportBillID() {
        return importBillID;
    }

    public void setImportBillID(String importBillID) {
        this.importBillID = importBillID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }
}
