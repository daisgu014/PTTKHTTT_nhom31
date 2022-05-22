package Main.Admin.DataManager.Controller;

import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class AdminCategoryAddController  {
    @FXML
    private TextField textNameCategory;
    public void AddCategory() throws SQLException {
        String CategoryName = textNameCategory.getText();
            System.out.println(CategoryName);
                Category category = new Category(CategoryName);
                AdminCategoryController categoryController = new AdminCategoryController();
                DAO dao = new DAO();
                dao.execute("INSERT INTO Category (CategoryName) VALUES('"+category.getCategoryName()+"')");
                categoryController.getData();

    }
    public boolean checkName(String Name) throws SQLException {
        AdminCategoryController adminCategoryController = new AdminCategoryController();
        adminCategoryController.getData();
        for(Category category : adminCategoryController.list){
            if(category.getCategoryName().equalsIgnoreCase(Name)) return false;
        }
        return true;
    }
    public void excuteCheck() throws SQLException {
        if(!checkName(textNameCategory.getText())){
            ErrorController ErrorController = new ErrorController();
            try {
                ErrorController.displayError("name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            AddCategory();
        }
    }

}
