
package chatstack;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Hesham-Desktop
 */
public class LoginController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private ImageView iv_stack;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iv_stack.setImage(new Image("StackLogo.png"));
    }    
    
}
