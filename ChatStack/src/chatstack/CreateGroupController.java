package chatstack;

import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.sc;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreateGroupController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void handleCreate(ActionEvent event) {

    }

    //<editor-fold defaultstate="collapsed" desc="TitleBar code DO NOT EDIT">
    //Start of title bar code
    double x, y;

    @FXML
    void dragToolBar(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void pressToolBar(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void maximize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint(" ");
        stage.setFullScreen(true);
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //end of title bar code
//</editor-fold>

}
