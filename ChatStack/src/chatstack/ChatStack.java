/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Hesham-Desktop
 */
public class ChatStack extends Application {
    
    public static Stage StageOpened;
    public static Scene sc;
    public static Database db;
    public static Parent root;
    public static Clip clip1;
    @Override
    public void start(Stage stage) throws Exception {
        
        StageOpened=stage;
        StageOpened.initStyle(StageStyle.UNDECORATED);
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        StageOpened.setResizable(false);
        sc = new Scene(root);
        StageOpened.setScene(sc);
        StageOpened.show();

        
       
    }
 public void playback(){
        try{
           
                File file = new File("Menu_Theme.wav");
                clip1 = AudioSystem.getClip();
                clip1.open(AudioSystem.getAudioInputStream(file));
                clip1.loop(Clip.LOOP_CONTINUOUSLY);
                clip1.start();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        db = new Database();
        launch(args);
    }
    // Hiiii 
}
