package Main.Entity.Element;

public class Employee {
    private String employeeID;
    private String employeeName;
    private String employeePhone;
    private String position;
    private String type;

    public Employee() {
    }

    public Employee(String employeeName, String employeePhone, String position, String type) {
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.position = position;
        this.type = type;
    }

    public Employee(String employeeID, String employeeName, String employeePhone, String position, String type) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.position = position;
        this.type = type;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
