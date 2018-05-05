package chatstack;

import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.clip1;
import static chatstack.ChatStack.root;
import static chatstack.ChatStack.sc;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.sound.sampled.AudioSystem;
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
    private boolean inGroup = false;
    private String curruntGroup = "";

    public Client(String Name, int ID) throws IOException {

        this.Name = Name;
        this.ID = ID;
        try {
            //"127.0.0.1"
            //"51.255.35.210"
            String ip=ChatStack.db.CheckServerIP();
            System.out.println("Server ip : "+ip);
            client = new Socket(ip, 4520);
            
            ChatStack.db.updatetIP(getPublicIp(), Name);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        in = new ObjectInputStream(client.getInputStream());
        out = new ObjectOutputStream(client.getOutputStream());

    }

    @Override
    public void run() {
        try {
            while (ThreadOpen) {
                p = (chatStackProtocol) in.readObject();
                System.out.println("ID : " + p.getId() + " From User : " + p.getUser() + " Group: " + p.getGroup() + "Message: " + p.getMessage());
                if (p.getId() == 1) {
                    MainPanelController.showmembers();
                }
                if (p.getId() == 2) {
                    MainPanelController.showGroups();
                }
                if (p.getId() == 3) {
                    GroupChatController.showGroupMemebers();
                }
                if (p.getId() == 4 && getGroup().equals(p.getGroup())) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                clip1 = AudioSystem.getClip();
                                InputStream audioSrc = getClass().getResourceAsStream("recieve.wav");
                                clip1.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc)));
                                clip1.start();
                            } catch (Exception e) {}
                                v.getChildren().add(new SpeechBox(p.getMessage(), SpeechDirection.LEFT, p.getUser()));

                            }
                        }
                    );

                    }
                if (p.getId() == 5) {
                        if (getUserName().equals(p.getMessage())) {
                            leaveGroup();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        root = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
                                    } catch (IOException ex) {
                                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    sc = new Scene(root);
                                    StageOpened.setScene(sc);
                                }
                            });

                        } else {
                            GroupChatController.showGroupMemebers();
                        }
                    }

                }
                this.out.close();
            }catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
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

    public void closeConnection() throws IOException {
        chatStackProtocol sp = new chatStackProtocol(0, Name, "");
        out.writeObject(sp);
        MainPanelController.pcts.closeConnection();
        client.close();
        this.stop();
        ThreadOpen = false;

    }

    public String getGroup() {
        return this.curruntGroup;
    }

    public void sendPacket(int id) throws IOException {
        chatStackProtocol sp = new chatStackProtocol(id, Name, "");
        out.writeObject(sp);
    }

    public void sendPacket(int id, String Username) throws IOException {
        chatStackProtocol sp = new chatStackProtocol(id, Name, Username);
        out.writeObject(sp);
    }

    public void sendGroupMsgPacket(int id, String msg, String Groupname) throws IOException, SQLException {
        chatStackProtocol sp = new chatStackProtocol(id, Name, msg, Groupname);
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

    public void setVbox(VBox v) {
        this.v = v;
    }

    public void joinGroup(String Groupname) throws SQLException {
        this.curruntGroup = Groupname;
        ChatStack.db.setUserGroup(ChatStack.client.getUserName(), Groupname);
        this.inGroup = true;

    }

    public void leaveGroup() throws SQLException {
        this.curruntGroup = "";
        ChatStack.db.setUserGroup(getUserName(), "");
        this.inGroup = false;

    }

}
