/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocol.chatStackProtocol;

/**
 *
 * @author AbdElRahmanOssamAhme
 */
public class PeerChatThread extends Thread {

    private String userName;
    private boolean ThreadOpen = true;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket s;
    boolean Sending;
    private String messageOUT;
    private String messageIN;
    private PrivateChatController pcc;

    public PeerChatThread(Socket s,PrivateChatController pcc) {
        this.pcc = pcc;
        this.s = s;
        try {
            System.out.println("before this.s");
            this.s = s;
            System.out.println("before this.out");
            this.out = new DataOutputStream(s.getOutputStream());
            System.out.println("before this.in");
            this.in = new DataInputStream(s.getInputStream());

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    

    @Override
    public void run() {
        try {
            //recive any protcol
            while (ThreadOpen) {
                System.out.println("Peer Chat: Waiting for Message ...");
                String message = in.readUTF();
                System.out.println("Peer Chat: Recieved Message ...");
                RecieveMessage(message);
            }
            this.out.close();
        } catch (IOException ex) {
            System.out.println(ex);
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
        try {
            this.in.close();
            this.out.close();
            this.s.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
