package Main.Admin.DataManager.Controller;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Category;
import Main.Entity.Element.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminCategoryController implements Initializable {
    @FXML
    TableView<Category> table;
    @FXML
    private TableColumn<Category, String> CategoryIdColumn;
    @FXML
    private TableColumn<Category, String> CategoryNameColumn;
    @FXML
    private TextField textNameCategory;

    @FXML
    private TextField txtSearchName;
    ObservableList<Category> CategoryList;
    public static List<Category> list = new ArrayList<>();
    public void getData() throws SQLException {
        list.clear();
        DAO dao = new DAO();
        ResultSet resultSet = dao.executeQuery("SELECT * FROM Category");
        while (resultSet.next()) {
            Category category = new Category(resultSet.getString(2), resultSet.getString(3));
            list.add(category);
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.getData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CategoryList = FXCollections.observableArrayList(list);
        CategoryIdColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryId"));
        CategoryNameColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        table.setItems(CategoryList);
        SearchNameAutoFill();
    }

    public void changeSceneAddEvent(ActionEvent e) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/Admin.Category.Add.fxml"));
        Pane CategoryAddParentView = loader.load();
        Dialog<ButtonType> dialogCategoryAdd = new Dialog<>();;
        dialogCategoryAdd.initStyle(StageStyle.TRANSPARENT);
        dialogCategoryAdd.setDialogPane((DialogPane) CategoryAddParentView);
        Optional<ButtonType> ClickedButton = dialogCategoryAdd.showAndWait();
        AdminCategoryAddController AddController = loader.getController();
        if(ClickedButton.get()==ButtonType.APPLY){
            AddController.excuteCheck();
            table.setItems(FXCollections.observableArrayList(list));
            table.refresh();
        }
    }

    public void changeSceneEditEvent(ActionEvent e) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Category selected = table.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        if (selected == null) {
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane CategoryEditParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) CategoryEditParentView);
            dialog.show();
        } else {
            loader.setLocation(this.getClass().getResource("../View/Admin.Category.Edit.fxml"));
            Pane CategoryEditParentView = loader.load();
            AdminCategoryEditController controller = loader.getController();
            controller.handleEvent(selected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setDialogPane((DialogPane) CategoryEditParentView);
            Optional<ButtonType> clickButton = dialog.showAndWait();
            if(clickButton.get() ==  ButtonType.APPLY){
                controller.EidtCategory(selected);
                table.setItems(FXCollections.observableArrayList(list));
                table.refresh();
            }

        }

    }

    public void changeSceneDeleteEvent(ActionEvent e) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Category selected = table.getSelectionModel().getSelectedItem();
        if(selected ==null){
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane CategoryEditParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) CategoryEditParentView);
            dialog.showAndWait();
        }else{
            loader.setLocation(this.getClass().getResource("../View/Admin.Delete.fxml"));
            Pane CategoryDeleteParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) CategoryDeleteParentView);
            Optional<ButtonType> ClickedButton = dialog.showAndWait();
            AdminDeleteController adminDeleteController = loader.getController();
            if(ClickedButton.get()==ButtonType.YES){
                adminDeleteController.DeleteCategory(selected);
                list.remove(selected);
                table.setItems(FXCollections.observableArrayList(list));
                table.refresh();
            }

        }

    }

    public void GoBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("../CNPMCoffee/Admin/IngredientsManager/View/Admin.Employee.fxml"));
        Parent AdminViewParent = FXMLLoader.load(getClass().getResource("../View/Admin.fxml"));
        Scene scene = new Scene(AdminViewParent);
        stage.setScene(scene);
    }
    public void SearchName(ActionEvent e) throws SQLException {
        this.getData();
        List<Category> Array = new ArrayList<>();
        String pattern = ".*" + txtSearchName.getText() + ".*";
        for(Category o : list){
            if(o.getCategoryName().toLowerCase().matches(pattern.toLowerCase())){
                Array.add(o);
            }
        }
        table.setItems(FXCollections.observableArrayList(Array));
        table.refresh();
    }
    public void SearchNameAutoFill(){
        this.txtSearchName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                List<Category>Array = new ArrayList<>();
                String pattern = ".*" + t1 + ".*";
                for(Category o : list){
                    if(o.getCategoryName().toLowerCase().matches(pattern.toLowerCase())){
                        Array.add(o);
                    }
                }
                table.setItems(FXCollections.observableArrayList(Array));
                table.refresh();
            }
        });
    }




}
