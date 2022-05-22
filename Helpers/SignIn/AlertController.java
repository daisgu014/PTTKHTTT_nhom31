package Main.Helpers.SignIn;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AlertController {

    @FXML
    private Label warningLbl;

    @FXML
    private Label messLbl;

    public void setMessage(String mess){
        messLbl.setText(mess);
    }

    public void setWarningType(String warningType) {
        this.warningLbl.setText(warningType);
    }
}
