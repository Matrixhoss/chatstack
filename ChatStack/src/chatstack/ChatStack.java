/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Hesham-Desktop
 */
public class ChatStack extends Application {
    
    public static Stage StageOpened;
    public static Scene sc;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        StageOpened=stage;
        StageOpened.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        StageOpened.setResizable(false);
        sc = new Scene(root);
        StageOpened.setScene(sc);
        StageOpened.show();
        
        
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);
        Database db = new Database();
        db.createTable();
    }
    // Hiiii 
}
