package chatstack;

import com.jfoenix.controls.JFXButton;
import java.awt.Color;
import java.io.IOException;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.root;
import static chatstack.ChatStack.sc;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.sql.SQLException;



/**
 *
 * @author Hesham-Desktop
 */
public class LoginController implements Initializable {


    @FXML
    private JFXButton singup_btn;

    @FXML
    private ImageView iv_stack;

    @FXML
    private JFXButton login_btn;

    @FXML
    private Label LoginError;

    @FXML
    private JFXTextField userText;

    @FXML
    private JFXPasswordField passText;

    @FXML
    void handeLogin(ActionEvent event) {
        String IP = "0";
        if (!userText.getText().equals("") && !passText.getText().equals("")) {
            try {
                boolean check = false;
                boolean databaseCheck = ChatStack.db.checkLogin(userText.getText(), passText.getText());
                if(databaseCheck){
                    IP = ChatStack.db.CheckServerIP();
                    if(IP.equals("0"))
                        check = false;
                    else check = true;
                }
                if (check == true) {
                    Client client= new Client(userText.getText(),ChatStack.db.getID(userText.getText()));
                    root = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
                    sc = new Scene(root);
                    StageOpened.setScene(sc);
                } else if(databaseCheck){
                    LoginError.setText("server offline");
                }
                else {
                    LoginError.setText("Invalid Username or password");
                }

            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException e) {
                SQLException f = e;
                System.err.println(f);
                
            }
        } else {
            LoginError.setText("Enter Username and password ");
        }
    }

    @FXML
    void handleSignup(ActionEvent event) {
        //sign up code hoes here var: signup_btn 
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            root = loader.load();
            sc = new Scene(root);
            StageOpened.setScene(sc);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
