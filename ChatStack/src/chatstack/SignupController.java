package chatstack;

import static chatstack.ChatStack.StageOpened;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Hesham-Desktop
 */
public class SignupController implements Initializable {

    @FXML
    private Label SignupError;
    @FXML
    private JFXPasswordField passText;

    @FXML
    private JFXTextField emailText;

    @FXML
    private JFXTextField userText;

    @FXML
    private JFXPasswordField rpassText;

    @FXML
    void handleCancel(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene sc1 = new Scene(root);
            StageOpened.setScene(sc1);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleCreate(ActionEvent event) {
        SignupError.setTextFill(Color.web("#f53030"));
        
        String UserName = userText.getText();
        String PassWord = passText.getText();
        String RPassword = rpassText.getText();
        String Email = emailText.getText();

        try {
            if (!UserName.equals("") && !PassWord.equals("") && !RPassword.equals("") && !Email.equals("")) {
                if (!PassWord.equals(RPassword)) {
                    SignupError.setText("The Password not match");
                } else if (!UserName.matches("\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b")) {
                    SignupError.setText("The Username is not Valid");
                } else if (!Email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                    SignupError.setText("The Email is not Valid");
                } else if (ChatStack.db.checkUsername(UserName) == false) {
                    SignupError.setText("This Username already exists");
                } else if (ChatStack.db.checkEmail(Email) == false) {
                    SignupError.setText("This Email already exists");
                } else {
                    ChatStack.db.addUser(UserName, PassWord, Email);
                    SignupError.setTextFill(Color.web("#00C853"));
                    SignupError.setText("Sign Up Success");
                }

            } else {
                SignupError.setText("Please fill all fields");
            }
        } catch (Exception e) {
            System.err.println("Error in signup");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
