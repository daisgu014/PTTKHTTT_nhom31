package Main.Sales.Sales.View;

import Main.Entity.Element.OrderDetail;
import Main.Entity.Element.Product;
import Main.Entity.Element.ProductPrice;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class OrderFactory {

    private OrderDetail item;

    private static ComboBox<String> sizeChoice;
    private static Label nameLbl, priceLbl, sizeLbl, qtyLbl;
    private static TextField qty;
    private static Button addBtn,minusBtn,checkBtn,cancelBtn,delBtn;
    private static Stage stage;

    private static void prepareGUI(){
        stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        nameLbl = new Label();
        sizeChoice = new ComboBox<>();
        qty = new TextField();
        qty.setEditable(false);
        qty.setPrefSize(35,20);
        priceLbl = new Label();
        addBtn = new Button("+");
        minusBtn = new Button("-");
        delBtn = new Button("Delete");
        try {
            ImageView img = new ImageView(new Image(new FileInputStream("Icon/delete.png")));
            img.setFitHeight(20);
            img.setFitWidth(20);
            delBtn.setGraphic(img);
            delBtn.setText("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ImageView img = new ImageView(new Image(new FileInputStream("Icon/up.png")));
            img.setFitHeight(20);
            img.setFitWidth(20);
            addBtn.setGraphic(img);
            addBtn.setText("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ImageView img = new ImageView(new Image(new FileInputStream("Icon/down.png")));
            img.setFitHeight(20);
            img.setFitWidth(20);
            minusBtn.setGraphic(img);
            minusBtn.setText("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        checkBtn = new Button("OK");
        cancelBtn = new Button("Cancel");
        try {
            ImageView img = new ImageView(new Image(new FileInputStream("Icon/cancel2.png")));
            img.setFitHeight(30);
            img.setFitWidth(30);
            cancelBtn.setGraphic(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ImageView img = new ImageView(new Image(new FileInputStream("Icon/ok.png")));
            img.setFitHeight(30);
            img.setFitWidth(30);
            checkBtn.setGraphic(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        FlowPane qtyControl = new FlowPane();
        qtyControl.setHgap(2);
        qtyControl.getChildren().add(minusBtn);
        qtyControl.getChildren().add(qty);
        qtyControl.getChildren().add(addBtn);
        qtyControl.getChildren().add(delBtn);

        BorderPane root = new BorderPane();
        HBox cont = new HBox();
        VBox cont1 = new VBox();
        VBox cont2 = new VBox();
        cont.setPrefSize(300,200);
        cont1.setPrefSize(100,200);
        cont1.setSpacing(5);
        cont2.setPrefSize(100,200);
        cont2.setSpacing(5);
        Label nameInfoLbl = new Label("Tên Sản phẩm: ");
        Label priceInfoLbl = new Label("Thành tiền: ");
        sizeLbl = new Label("Size đang chọn: ");
        qtyLbl = new Label("Số lượng: ");
        cont1.getChildren().add(nameInfoLbl);
        cont1.getChildren().add(priceInfoLbl);
        cont1.getChildren().add(sizeLbl);
        cont1.getChildren().add(qtyLbl);
        cont2.getChildren().add(nameLbl);
        cont2.getChildren().add(priceLbl);
        cont2.getChildren().add(sizeChoice);
        cont2.getChildren().add(qtyControl);
        cont.setId("maincont");
        cont1.setId("cont");
        cont2.setId("cont");


        cont.getChildren().addAll(cont1,cont2);

        root.setCenter(cont);
        FlowPane ctl = new FlowPane(cancelBtn,checkBtn);
        ctl.setId("control");
        ctl.setHgap(10);
        root.setBottom(ctl);

        Scene sc = new Scene(root);
        try {
            sc.getStylesheets().add(new File("Sales/Sales/View/OrderFactory.css").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(sc);
    }

    public static OrderDetail choiceItem(Product p, Window window){
        prepareGUI();
        OrderDetail od = new OrderDetail();
        od.setQuantity(0);
        nameLbl.setText(p.getProductName());
        for(ProductPrice pp : p.getPriceList()){
            sizeChoice.getItems().add(pp.getSize());
        }


        sizeChoice.getSelectionModel().select(0);
        qty.setText("1");
        priceLbl.setText(String.valueOf(p.getPriceList().get(0).getPrice()));
        addBtn.setOnAction(actionEvent -> {
            qty.setText(String.valueOf(Integer.parseInt(qty.getText())+1));
            priceLbl.setText(String.valueOf(Integer.parseInt(qty.getText())
                    * p.getPriceList().get(sizeChoice.getSelectionModel().getSelectedIndex()).getPrice()));
        });

        minusBtn.setOnAction(actionEvent -> {
            qty.setText(String.valueOf(Math.max(1,Integer.parseInt(qty.getText())-1)));
            priceLbl.setText(String.valueOf(Integer.parseInt(qty.getText())
                    * p.getPriceList().get(sizeChoice.getSelectionModel().getSelectedIndex()).getPrice()));
        });
        sizeChoice.setOnAction(actionEvent -> {
            priceLbl.setText(String.valueOf(Integer.parseInt(qty.getText())
                    * p.getPriceList().get(sizeChoice.getSelectionModel().getSelectedIndex()).getPrice()));
        });

        checkBtn.setOnAction(actionEvent -> {
            od.setQuantity(Integer.parseInt(qty.getText()));
            od.setProductChoice(p);
            od.setSize(sizeChoice.getSelectionModel().getSelectedItem());
            od.setPrice(Integer.parseInt(priceLbl.getText()));

            stage.close();
        });
        cancelBtn.setOnAction(actionEvent -> {
            stage.close();
        });
        delBtn.setOnAction(actionEvent -> {
            stage.close();
        });


        stage.centerOnScreen();
        stage.initOwner(window);
        stage.showAndWait();

        return od;
    }

    public static OrderDetail updateChoiceItem(OrderDetail orderDetail, Window window){
        prepareGUI();
        Product p = orderDetail.getProductChoice();
        OrderDetail od = new OrderDetail();
        od.setQuantity(orderDetail.getQuantity());
        od.setProductChoice(orderDetail.getProductChoice());
        od.setPrice(orderDetail.getPrice());
        od.setSize(orderDetail.getSize());

        nameLbl.setText(p.getProductName());
        qty.setText(String.valueOf(od.getQuantity()));

        for(ProductPrice pp : p.getPriceList()){
            sizeChoice.getItems().add(pp.getSize());
        }
        sizeChoice.getSelectionModel().select(od.getSize());
        priceLbl.setText(String.valueOf(od.getPrice()));
        addBtn.setOnAction(actionEvent -> {
            qty.setText(String.valueOf(Integer.parseInt(qty.getText())+1));
            priceLbl.setText(String.valueOf(Integer.parseInt(qty.getText())
                    * p.getPriceList().get(sizeChoice.getSelectionModel().getSelectedIndex()).getPrice()));
        });
        minusBtn.setOnAction(actionEvent -> {
            qty.setText(String.valueOf(Math.max(1,Integer.parseInt(qty.getText())-1)));
            priceLbl.setText(String.valueOf(Integer.parseInt(qty.getText())
                    * p.getPriceList().get(sizeChoice.getSelectionModel().getSelectedIndex()).getPrice()));
        });
        sizeChoice.setOnAction(actionEvent -> {
            priceLbl.setText(String.valueOf(Integer.parseInt(qty.getText())
                    * p.getPriceList().get(sizeChoice.getSelectionModel().getSelectedIndex()).getPrice()));
        });

        checkBtn.setOnAction(actionEvent -> {
            od.setQuantity(Integer.parseInt(qty.getText()));
            od.setProductChoice(p);
            od.setSize(sizeChoice.getSelectionModel().getSelectedItem());
            od.setPrice(Integer.parseInt(priceLbl.getText()));

            stage.close();
        });
        cancelBtn.setOnAction(actionEvent -> {
            od.setQuantity(orderDetail.getQuantity());
            od.setProductChoice(orderDetail.getProductChoice());
            od.setPrice(orderDetail.getPrice());
            od.setSize(orderDetail.getSize());

            stage.close();
        });
        delBtn.setOnAction(actionEvent -> {
            od.setQuantity(0);
            stage.close();
        });

        stage.centerOnScreen();
        stage.initOwner(window);
        stage.showAndWait();

        return od;
    }

}
