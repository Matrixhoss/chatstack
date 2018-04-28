
package chatstack;

import static chatstack.ChatStack.StageOpened;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author Hesham-Desktop
 */
public class SignupController implements Initializable {

    @FXML
    private Label SignupError ;
    
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
     SignupError.setText("Comming Soon");
    }
    

    //<editor-fold defaultstate="collapsed" desc="TitleBar code DO NOT EDIT">
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
//</editor-fold>
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
