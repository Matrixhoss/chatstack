package chatstack;

import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.root;
import static chatstack.ChatStack.sc;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateGroupController implements Initializable {

    @FXML
    JFXPasswordField passText;
    
    @FXML
    private JFXTextField maxNumber;
    
    @FXML
    private JFXTextField grpName;
    
    @FXML
    private JFXRadioButton passRadio;
    
    @FXML
    private Label createError;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void handleCreate(ActionEvent event) {
        try {
            if (!grpName.getText().equals("") && !maxNumber.getText().equals("")) {
                if ((passRadio.isSelected() && !passText.getText().equals(""))) {
                    createError.setText("Please Enter a Password");
                } else if (!grpName.getText().matches("\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b")) {
                    createError.setText("The group name is not Valid");
                } else if (!maxNumber.getText().matches("-?([1-9][0-9]*)?")) {
                    if(Integer.parseInt(maxNumber.getText())>20){
                    createError.setText("Number must be smaller than 20");
                    }
                    createError.setText("Number of user must be a number.");
                } else if (ChatStack.db.checkGroupname(grpName.getText()) == false) {
                    createError.setText("This Username already exists");
                } else {
                    ChatStack.db.createGroup(grpName.getText(), passText.getText(), ChatStack.client.getName(),Integer.parseInt(maxNumber.getText()));
                    createError.setTextFill(Color.web("#00C853"));
                    createError.setText("Sign Up Success");
                }

            } else {
                createError.setText("Please fill all fields");
            }
        } catch (Exception e) {
            System.err.println("Error while create a group");
        }
    }
    

    @FXML
    void enablePassword(ActionEvent event) {
        if (passText.isDisabled()) {

            passText.setDisable(false);
        } else {
            passText.setDisable(true);
        }
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
