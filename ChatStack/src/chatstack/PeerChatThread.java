/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    boolean Sending;
    private String messageOUT;
    private String messageIN;
    private PeerChatController pcc;

    public PeerChatThread(Socket s, PeerChatController pcc) {
        this.pcc = pcc;
        try {
            this.s = s;
            this.out = new ObjectOutputStream(s.getOutputStream());
            this.in = new ObjectInputStream(s.getInputStream());

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    

    @Override
    public void run() {
        try {
            //recive any protcol
            while (ThreadOpen) {
                String message = new String(in.readUTF());
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
