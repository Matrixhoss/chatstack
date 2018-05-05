/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    public static Client client;
    public static ArrayList<String> users = new ArrayList<String>();
    public static ArrayList<String> groups = new ArrayList<String>();
    public static VBox Vbox =new VBox();

    @Override
    public void start(Stage stage) throws Exception {
        //this.playback();
        StageOpened = stage;
        StageOpened.initStyle(StageStyle.UNDECORATED);
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        StageOpened.setResizable(false);
        sc = new Scene(root);
        StageOpened.setScene(sc);
        StageOpened.show();

    }


    public void playback() {
        try {
            clip1 = AudioSystem.getClip();
            InputStream audioSrc = getClass().getResourceAsStream("bac.wav");
            clip1.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc)));
            clip1.loop(Clip.LOOP_CONTINUOUSLY);
            clip1.start();
        } catch (Exception e) {
            System.err.println(e);
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
