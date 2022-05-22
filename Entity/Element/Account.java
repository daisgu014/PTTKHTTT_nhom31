package Main.Entity.Element;

public class Account {
    private String username;
    protected String password;
    private String ownerId;

    public Account() {
    }

    public Account(String username, String password, String ownerId) {
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUsername() {
        return username;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
