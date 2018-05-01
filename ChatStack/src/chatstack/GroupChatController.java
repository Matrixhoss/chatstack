/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class GroupChatController implements Initializable {


    @FXML
    private GridPane test;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void  hand(ActionEvent e){
        Label x = new Label("Test");
        x.setTextFill(Color.web("#FFFFFF"));
        test.getChildren().add(x);
    
    } 
    
}
