/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.root;
import static chatstack.ChatStack.sc;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import protocol.chatStackProtocol;

/**
 *
 * @author AbdElRahmanOssamAhme
 */
public class PeerChatThreadServer extends Thread {

    private String userName;
    private boolean ThreadOpen = true;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerSocket ss;
    private Socket s;
    boolean Sending;
    private String messageOUT;
    private String messageIN;
    private PrivateChatController1 pcc;
    private MainPanelController mpc;

    public PeerChatThreadServer(ServerSocket ss, MainPanelController mpc) {
        this.setName("Server Connection Waiting");
        this.mpc = mpc;
        this.ss = ss;
    }

    @Override
    public void run() {
        try {
            //recive any protcol
            System.out.println("waiting for connection");
            s = ss.accept();
            System.out.println("connection successful");
            mpc.chatClicked();
            
            

            while (ThreadOpen) {
                this.in = new DataInputStream(s.getInputStream());
                this.out = new DataOutputStream(s.getOutputStream());
                System.out.println("Peer Chat: Recieved Connection, waiting for message ...");
                String message = in.readUTF();
                System.out.println("Peer Chat: Recieved Message ...");
                System.out.print(message);
                RecieveMessage(message);
            }
            this.out.close();
            this.in.close();
            this.stop();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

    }

    public void SendMessage(String s) {
        try {
            out.writeUTF(s);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void RecieveMessage(String s) {
        pcc.recieveGUI(s);
    }

    public void closeConnection() {
        System.out.println("CLOSING SERVER CONNECTION THREAD");
        this.ThreadOpen = false;
        this.stop();
        try {
            this.in.close();
            }
        catch (Exception ex) {
            System.out.println(ex);
        }
         try {
            this.out.close();
            }
        catch (Exception ex) {
            System.out.println(ex);
        }
         try {
            this.ss.close();
            }
        catch (Exception ex) {
            System.out.println(ex);
        }
         System.out.println("CLOSING SERVER CONNECTION THREAD FINISH");
         this.stop();
         
    }

    public void setGUI(PrivateChatController1 pcc) {
        this.pcc = pcc;

    }

}
