/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
    public static Database db;
    public static Parent root;
    
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//        db = new Database();
//        launch(args);
        try {
            Socket s=new Socket("127.0.0.1",4520);
            String name="hassan";
            DataInputStream in=new DataInputStream(s.getInputStream());
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            out.writeUTF(name);
            String st=new String(in.readUTF());
            System.out.print(st);
            in.close();
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    // Hiiii 
}
