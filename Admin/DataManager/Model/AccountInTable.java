package Main.Admin.DataManager.Model;

import Main.Entity.Element.Account;

public class AccountInTable extends Account {
    private String ownerName;

    public AccountInTable(String ownerName) {
        this.ownerName = ownerName;
    }

    public AccountInTable(String username, String password, String ownerId, String ownerName) {
        super(username, password, ownerId);
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getPassword(){
        return super.password;
    }
}
