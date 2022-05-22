package Main.Entity.Element;

public class PositionType {
    private String positionId;
    private String typeId;
    private int salaryPerHour;

    public PositionType() {
    }

    public PositionType(String positionId, String typeId, int salaryPerHour) {
        this.positionId = positionId;
        this.typeId = typeId;
        this.salaryPerHour = salaryPerHour;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }



}
