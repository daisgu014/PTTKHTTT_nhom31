package Main.Sales.Sales.View;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.OrderDetail;
import Main.Entity.Element.Product;
import Main.Sales.Sales.Control.SalesApplicationControl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SalesApplicationView{

    private BorderPane root;
    private GridPane menu;
    private FlowPane controlPnl;
    private BorderPane orderPnl;
    TableView<OrderDetail> orderBody;
    FlowPane orderTittle;
    VBox orderFootter;

    TextField search;
    private Scene mainScene;
    private ArrayList<Button> buttons;
    ScrollPane srcViewMenu;

    Label priceLbl;
    Button cashBtn;

    private String[] cates = {"ALL","Trà Xanh","Trà sữa", "Cà phê"};

    private String filerCondition ="ALL";

    private ArrayList<MenuItem> itemList;

    private SalesApplicationControl controller;

    public SalesApplicationView(SalesApplicationControl controller){
        this.controller = controller;
        initGUI();

    }

    private void initGUI(){
        root = new BorderPane();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        mainScene = new Scene(root,d.getWidth()-100,d.getHeight()-50);
        mainScene.getStylesheets().add(getClass().getResource("SalesStyle.css").toExternalForm());

        createControlPnl();
        createSearchBar();
        prepareMenuGUI();
        prepareMenuItem();
        createFilterButtons();
        createOrderPnl();
    }

    private void createSearchBar(){
        search = new TextField();
        search.setPromptText("Search Drink!");
        search.textProperty().addListener(observable -> menuFilter());
        controlPnl.getChildren().add(search);
        try {
            ImageView searchIcon = new ImageView(new Image(new FileInputStream("Icon/search.png")));
            searchIcon.setFitWidth(20);
            searchIcon.setFitHeight(20);
            searchIcon.setId("searchIcon");
            controlPnl.getChildren().add(searchIcon);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createControlPnl(){
        controlPnl = new FlowPane();
        controlPnl.setPrefSize(100,50);
        controlPnl.setHgap(10);
        controlPnl.setAlignment(Pos.CENTER_LEFT);
        root.setTop(controlPnl);
    }

    private void prepareMenuItem(){
        itemList = new ArrayList<>();
        for (Product p : this.controller.getModel().getProductList()) {
            MenuItem tmp = new MenuItem(p);
            tmp.setOnMouseClicked(mouseEvent -> {
                controller.addNewItem(p);
            });
            itemList.add(tmp);
        }
        menuFilter();
    }

    private void prepareMenuGUI(){
        srcViewMenu= new ScrollPane();
        srcViewMenu.setFitToHeight(true);
        srcViewMenu.setFitToWidth(true);
        srcViewMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        srcViewMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setCenter(srcViewMenu);
    }

    private void createMenuView(){
        menu = new GridPane();
        menu.setHgap(10);
        menu.setVgap(10);
        for (int i=0;i<5;i++){
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            column.setPrefWidth(400);
            column.setMinWidth(150);
            column.setHgrow(Priority.ALWAYS);
            menu.getColumnConstraints().add(column);
            //menu.setBackground(null);
        }
        try {
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            Image img = new Image(new FileInputStream("Icon/background.jpg"));
            menu.setBackground(new Background(new BackgroundImage(
                    img,null,null,null,
                    new BackgroundSize(d.getWidth()-350,d.getHeight(),false,false,false,false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        srcViewMenu.setContent(menu);
    }

    private void createOrderPnl(){
        orderPnl = new BorderPane();
        orderPnl.setPrefSize(350,300);
        createOrderTittle();
        createOrderBody();
        createOrderFooter();

        orderPnl.setTop(orderTittle);
        orderPnl.setBottom(orderFootter);
        orderPnl.setCenter(orderBody);
        root.setRight(orderPnl);
    }

    private void createOrderFooter(){
        orderFootter =  new VBox();
        orderFootter.setFillWidth(true);
        orderFootter.setSpacing(5);
        orderFootter.setPrefSize(100,100);
        priceLbl = new Label("0");
        priceLbl.setId("pricelbl");
        cashBtn = new Button("Cash!!");
        cashBtn.setId("cashbtn");
        cashBtn.setOnAction(actionEvent -> controller.cash());
        orderFootter.getChildren().add(priceLbl);
        orderFootter.getChildren().add(cashBtn);
    }

    private void createOrderBody(){
        orderBody = new TableView<>();
        TableColumn<OrderDetail,String> indexColumn = new TableColumn<>("STT");
        TableColumn<OrderDetail,String> nameColumn = new TableColumn<>("Tên");
        TableColumn<OrderDetail,String> sizeColumn = new TableColumn<>("Size");
        TableColumn<OrderDetail,Integer> qtyColumn = new TableColumn<>("Số lượng");
        TableColumn<OrderDetail,Integer> priceColumn = new TableColumn<>("Thành tiền");

        indexColumn.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(controller.getModel().getCurrentChoices().indexOf(data.getValue())+1)));

        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                                                    .getProductChoice().getProductName()));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        indexColumn.setPrefWidth(30);
        nameColumn.setPrefWidth(110);
        sizeColumn.setPrefWidth(40);
        qtyColumn.setPrefWidth(80);
        priceColumn.setPrefWidth(100);

        orderBody.setOnMouseClicked(mouseEvent -> {
            if(orderBody.getSelectionModel().getSelectedIndex()>=0){
                this.controller.updateItem(orderBody.getSelectionModel().getSelectedIndex());
            }
        });


        orderBody.getColumns().addAll(indexColumn,nameColumn,sizeColumn,qtyColumn,priceColumn);
        initOrder();

    }

    private void initOrder(){
        orderBody.setItems(FXCollections.observableList(this.controller.getModel().getCurrentChoices()));
        orderBody.refresh();
    }

    public void updateOrder(){
        orderBody.setItems(FXCollections.observableList(this.controller.getModel().getCurrentChoices()));
        orderBody.refresh();
        this.controller.getModel().updatePrice();
        priceLbl.setText(String.valueOf(this.controller.getModel().getCurrentOrders().getTotalPrice()));
    }


    private void createOrderTittle(){
        orderTittle = new FlowPane();
        orderTittle.setId("pane");
        orderTittle.setPrefSize(200,50);
        orderTittle.setAlignment(Pos.TOP_RIGHT);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Label timeLbl = new Label(dateFormat.format((System.currentTimeMillis())));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e-> {
            timeLbl.setText(dateFormat.format(System.currentTimeMillis()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        orderTittle.setStyle(
                        "-fx-background-color: #5F50F1;" +
                        "    -fx-text-fill: #fff;" +
                        "    -fx-background-radius: 5;" +
                        "    -fx-font-size: 14;" +
                        "    -fx-font-weight: 700;" +
                        "    -fx-effect: dropshadow(three-pass-box, #48cae4,5,0,1,0);");
        timeLbl.setStyle(
                "    -fx-background-color: #5F50F1;" +
                "    -fx-text-fill: #fff;" +
                "    -fx-font-size: 14;" +
                "    -fx-font-weight: 700;");
        orderTittle.getChildren().add(timeLbl);
    }

    private void createFilterButtons(){
        ResultSet rs = new DAO().executeQuery("select CategoryName from Category");
        ToggleGroup gr = new ToggleGroup();
        try {
            while (rs.next()){
                ToggleButton btn = new ToggleButton(rs.getString(1));
                btn.setToggleGroup(gr);

                controlPnl.getChildren().add(btn);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        ToggleButton btn = new ToggleButton("ALL");
        controlPnl.getChildren().add(btn);
        btn.setToggleGroup(gr);

        controlPnl.getChildren().forEach(node ->{
            if(node instanceof ToggleButton){
                ((ToggleButton)node).setOnAction(actionEvent -> {
                    filerCondition = ((ToggleButton)actionEvent.getSource()).getText();
                    menuFilter();
                });
            }
        });


    }
    public Scene getScreen(){
        return this.mainScene;
    }

    private void menuFilter(){
        createMenuView();
        String searchString = search.getText().toLowerCase(Locale.ROOT);
        List<MenuItem> currentList;
        if(filerCondition.equals("ALL")){
            currentList = itemList.stream().filter(menuItem ->
                            menuItem.getProduct().getProductName().toLowerCase(Locale.ROOT).contains(searchString))
                            .collect(Collectors.toList());
        }
        else {
            currentList = itemList.stream().filter(menuItem ->
                            filerCondition.equals(menuItem.getProduct().getCategoryName())
                                    && menuItem.getProduct().getProductName().toLowerCase(Locale.ROOT).contains(searchString))
                            .collect(Collectors.toList());
        }
        for (int i = 0; i < currentList.size(); i++) {
            menu.add(currentList.get(i), (int) i % 5, (int) i / 5);
        }
    }

}
