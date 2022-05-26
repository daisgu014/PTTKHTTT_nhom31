package Main.Admin.DataManager.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ErrorController {
    @FXML
     Label massage;
    @FXML
    ImageView imageView;
    @FXML
    public void setMassage(String massage1){
        massage.setText(massage1);
    }
    public void setImageView(String path) throws FileNotFoundException {
        imageView.setImage(new Image(new FileInputStream(path)));
    }


    public void displayError(String errorName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../View/ErrorPassword.fxml"));
        DialogPane error = loader.load();
        ErrorController errorController =loader.getController();
        switch (errorName){
            case "password":
                errorController.setMassage("The password is incorrect");
                break;
            case "name":
                errorController.setMassage("The name already exists");
                break;
            case "position":
                errorController.setMassage("Must be a full-time employee");
            case "save":
                errorController.setImageView("Icon/check.png");
                errorController.setMassage("Complete");
                break;
            case "quantity":
                errorController.setImageView("Icon/warning-sign.png");
                errorController.setMassage("Product is not enough");
                break;
        }
        Dialog<ButtonType> dialogError = new Dialog<>();
        dialogError.setTitle("Error");
        dialogError.setDialogPane(error);
        dialogError.showAndWait();


    }


}
