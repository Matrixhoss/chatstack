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
import javafx.scene.layout.VBox;
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
    private VBox v;
        
    @Override
    public void run() {
        try {
            while (ThreadOpen) {
                p = (chatStackProtocol) in.readObject();
                System.out.println("ID : " + p.getId() + "From User : " + p.getUser());
                if (p.getId() == 1) {
                    MainPanelController.showmembers();
                }
                if (p.getId() == 2) {
                    MainPanelController.showGroups();
                }
                if (p.getId() == 4) {
                    v.getChildren().add(new SpeechBox(p.getMessage(), SpeechDirection.LEFT));
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
            ChatStack.db.updatetIP(getPublicIp(), Name);
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

    public void sendGroupMsgPacket(int id, String msg) throws IOException, SQLException {
        chatStackProtocol sp = new chatStackProtocol(id, Name, msg,ChatStack.db.getUserGroup(Name));
        out.writeObject(sp);
    }
    private String getPublicIp() {
        String ip = "";
        try {
            URL connection = new URL("http://checkip.amazonaws.com/");
            URLConnection con = connection.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            ip = reader.readLine();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip;
    }
    
    public void setVbox(VBox v){
        this.v=v;
    }
    
    

}
