package Main.Admin.DataManager.Controller;

import Main.Admin.DataManager.Model.AccountInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
import Main.Entity.Element.Employee;
import Main.Entity.Element.Product;

import java.sql.SQLException;

public class AdminDeleteController {
    DAO dao;
    public void DeleteCategory (Category category) throws SQLException {
         dao = new DAO();
        dao.execute("DELETE Category WHERE CategoryName LIKE'"+category.getCategoryName()+"'");
    }
    public void DeleteProduct (Product product) throws SQLException {
        dao = new DAO();
        dao.execute("DELETE Product Where ProductID LIKE '"+product.getProductId()+"'");
        AdminProductController adminProductController = new AdminProductController();
        adminProductController.GetDataProduct();
    }
    public void DeleteEmployee(Employee employee) throws SQLException {
        dao = new DAO();
        dao.execute("DELETE Employee WHERE EmployeeID like '"+employee.getEmployeeID()+"'");
        AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
        adminEmployeeController.getDataEmployee();

    }
    public void DeleteAccount(AccountInTable account) throws SQLException {
        dao = new DAO();
        dao.execute("DELETE Account Where EmployeeID like '"+account.getOwnerId()+"'");
        AdminAccountController adminAccountController = new AdminAccountController();
        adminAccountController.getDataAccount();
    }


}
