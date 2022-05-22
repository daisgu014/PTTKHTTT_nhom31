package Main.Admin.DataManager;

import Main.Admin.DataManager.Controller.AdminCategoryController;
import Main.Entity.Element.Category;

import java.util.Iterator;

public class CeateId {
    public static String createNewCategoryID() {
        String id = "CA";
        int ID = 1;
        String newCAID;
        boolean flag=true;
        for (Iterator<Category> it = AdminCategoryController.list.iterator(); it.hasNext();) {
            Category x = it.next();
            newCAID = id + String.format("%03d", ID);
            if(x.getCategoryId().equalsIgnoreCase(newCAID) && flag){
                ID++;
            }
            else{
                flag = false;
            }
        }
        newCAID = id + String.format("%03d",ID);
        return newCAID;
    }
}
