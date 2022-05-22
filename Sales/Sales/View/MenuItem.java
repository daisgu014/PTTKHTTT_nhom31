package Main.Sales.Sales.View;

import Main.Entity.Element.Product;
import Main.Entity.Element.ProductPrice;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MenuItem extends VBox {

    private Product product;

    private String nameLabelCSS =
            "   -fx-background-color: transparent;" +
                    "   -fx-alignment: center;" +
                    "   -fx-text-fill: #fff ;"  +
                    "   -fx-effect: dropshadow(three-pass-box, #5F50F1,3,1,1,0); "+
                    "    -fx-font-size: 16px; ";
    private String categoryLabelCSS =
            "   -fx-background-color: transparent;" +
                    "   -fx-alignment: center;" +
                    "   -fx-text-fill: #fff ;"  +
                    "   -fx-effect: dropshadow(three-pass-box, #5F50F1,3,1,1,0); "+
                    "    -fx-font-size: 14px; ";
    private String pricelabelCSS =
                    "   -fx-background-color: transparent;" +
                    "   -fx-alignment: center;" +
                    "   -fx-text-fill: #fff ;"  +
                    "   -fx-effect: dropshadow(three-pass-box, #646060,2,1,1,0); "+
                    "   -fx-font-size: 14px; ";
    private String boxCSS =
                    "   -fx-alignment: center;" +
                    "   -fx-background-radius: 5;" +
                    "   -fx-background-color: rgba(244,245,245,0.2) ;" +
                    "   -fx-font-size: 16;" +
                    "   -fx-font-weight: 700;" +
                    "   -fx-effect: dropshadow(three-pass-box, #48cae4,5,0,4,2);";

    public MenuItem (Product product){
        this.product = product;
        this.setStyle(boxCSS);
        initGUI();
    }

    private void initGUI(){
        this.setSpacing(5);
        Label nameLbl = new Label(product.getProductName());
        Label categoryLbl = new Label(product.getCategoryName());
        this.getChildren().add(nameLbl);
        this.getChildren().add(categoryLbl);
        this.setMinHeight(200);
        this.setPrefSize(200,200);
        ImageView image = new ImageView();
        try {
            image.setImage(new Image(new FileInputStream("Icon/nguyenlieu.png")));
            image.setX(0);
            image.setY(0);
            image.setFitHeight(100);
            image.setFitWidth(100);
            //this.getChildren().add(image);
        } catch (FileNotFoundException e) {
            System.out.println("Not found image for "+this.product.getProductName());
        }

        String price="";
        for(ProductPrice pp : product.getPriceList()){
            price += String.format("%2s %6s\n",pp.getSize(),pp.getPrice());
        }
        Label priceLbl = new Label(price);
        HBox content = new HBox(image,priceLbl);
        content.setSpacing(5);
        priceLbl.setStyle(pricelabelCSS);
        nameLbl.setStyle(nameLabelCSS);
        categoryLbl.setStyle(categoryLabelCSS);
        this.getChildren().add(content);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
