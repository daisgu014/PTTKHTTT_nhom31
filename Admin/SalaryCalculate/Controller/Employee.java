package Main.Admin.SalaryCalculate.Controller;

public class Employee {
    private String employeeID;
    private String employeeName;
    private  String employeePhone;
    private  String employeeType;
    private  String employeePosition;
    private  int employeeSalary;
    private int workingDate;
    private int workingHour;
    private int totalSalary;

    public Employee(String employeeID, String employeeName, String employeePhone, String employeeType, String employeePosition, int employeeSalary) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeType = employeeType;
        this.employeePosition = employeePosition;
        this.employeeSalary = employeeSalary;
    }

    public Employee(String employeeID, String employeeName, String employeeType, String employeePosition,int employeeSalary, int workingDate, int workingHour, int totalSalary) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeType = employeeType;
        this.employeePosition = employeePosition;
        this.employeeSalary = employeeSalary;
        this.workingDate = workingDate;
        this.workingHour = workingHour;
        this.totalSalary = totalSalary;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(int workingDate) {
        this.workingDate = workingDate;
    }

    public int getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(int workingHour) {
        this.workingHour = workingHour;
    }

    public int getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(int totalSalary) {
        this.totalSalary = totalSalary;
    }
}
