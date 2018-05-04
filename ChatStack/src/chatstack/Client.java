package chatstack;

import java.net.Socket;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import protocol.chatStackProtocol;

/**
 *
 * @author Hesham-Desktop
 */
public class Client extends Thread {

    private String Name;
    private int ID;
    Socket client;
    private boolean ThreadOpen = true;
    private chatStackProtocol p;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    @Override
    public void run() {
        try {
            while (ThreadOpen) {
                p = (chatStackProtocol) in.readObject();
                System.out.println("ID : " + p.getId() + "From User : " + p.getUser());
                if (p.getId() == 1) {
                    ChatStack.users = ChatStack.db.getOnlineMemebers();
                    MainPanelController.showmembers();
                }
                if (p.getId() == 2) {
                    MainPanelController.showGroups();
                }
                for (int i = 0; i < ChatStack.users.size(); i++) {
                    System.out.println(ChatStack.users.get(i));
                }
            }
            this.out.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getUserName() {
        return Name;
    }

    public void setUserName(String Name) {
        this.Name = Name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Client(String Name, int ID) throws IOException {
        this.Name = Name;
        this.ID = ID;
        try {
            client = new Socket("127.0.0.1", 4520);
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        in = new ObjectInputStream(client.getInputStream());
        out = new ObjectOutputStream(client.getOutputStream());

    }

    public void closeConnection() throws IOException {
        chatStackProtocol sp = new chatStackProtocol(0, Name, "");
        out.writeObject(sp);
        client.close();
        this.stop();

    }

    public void sendPacket(int id) throws IOException {
        chatStackProtocol sp = new chatStackProtocol(id, Name, "");
        out.writeObject(sp);
    }

    public void sendMsgPacket(int id, String msg) throws IOException {
        chatStackProtocol sp = new chatStackProtocol(id, Name, msg);
        out.writeObject(sp);
    }

}
