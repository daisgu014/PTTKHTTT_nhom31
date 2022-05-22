package Main.Sales.Sales.Control;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.OrderDetail;
import Main.Entity.Element.Product;
import Main.Sales.Sales.Model.SalesApplicationModel;
import Main.Sales.Sales.View.OrderFactory;
import Main.Sales.Sales.View.SalesApplicationView;
import javafx.scene.Scene;

public class SalesApplicationControl {

    private SalesApplicationView view;
    private SalesApplicationModel model;

    DAO dao;
    public SalesApplicationControl(){
        model = new SalesApplicationModel();
        view = new SalesApplicationView(this);
        dao = new DAO();
    }

    public Scene getView(){
        return this.view.getScreen();
    }

    public void cash(){
        this.model.payCurrentOrder();
        this.view.updateOrder();
    }

    public void addNewItem(Product p){
        OrderDetail choice = OrderFactory.choiceItem(p, this.getView().getWindow());
        if(choice.getQuantity()>0){
            this.model.addItem(choice);
            this.view.updateOrder();
        }
    }

    public void updateItem(int i){
        this.model.updateChoice(i,
                OrderFactory.updateChoiceItem(
                        this.model.getCurrentChoices().get(i),
                        this.getView().getWindow()));
        this.view.updateOrder();
    }

    public void setView(SalesApplicationView view) {
        this.view = view;
    }

    public SalesApplicationModel getModel() {
        return model;
    }

    public void setModel(SalesApplicationModel model) {
        this.model = model;
    }

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }
}
