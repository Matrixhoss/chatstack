
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
    private Label LoginError ; 
    
 
    
    
    
    @FXML
    void handeLogin(ActionEvent event) {
        LoginError.setText("Comming Soon");
    }


    
    @FXML
    void handleSignup(ActionEvent event) {
        //sign up code hoes here var: signup_btn 
         try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Parent root = loader.load();
            Scene sc1 = new Scene(root);
            StageOpened.setScene(sc1);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    
    
    
    
    
    
    //Start of title bar code
    double x,y;
    @FXML
    void dragToolBar(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
    }
    @FXML
    void maximize(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint(" ");
        stage.setFullScreen(true);
    }
    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //end of title bar code

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
    
    
    
    
}
