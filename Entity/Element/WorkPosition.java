package Main.Entity.Element;

public class WorkPosition {
    private String workPositionId;
    private String workPositionName;

    public WorkPosition() {
    }

    public WorkPosition(String workPositionId, String workPositionName) {
        this.workPositionId = workPositionId;
        this.workPositionName = workPositionName;
    }

    public String getWorkPositionId() {
        return workPositionId;
    }

    public void setWorkPositionId(String workPositionId) {
        this.workPositionId = workPositionId;
    }

    public String getWorkPositionName() {
        return workPositionName;
    }

    public void setWorkPositionName(String workPositionName) {
        this.workPositionName = workPositionName;
    }
}
