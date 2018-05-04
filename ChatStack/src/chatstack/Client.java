package chatstack;

import java.net.Socket;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Hesham-Desktop
 */
public class Client {

    private String Name;
    private int ID;
    Socket client;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Client(String Name, int ID) {
        this.Name = Name;
        this.ID = ID;
        try {
            client = new Socket("127.0.0.1", 4520);
        } catch (Exception ex) {
            ex.getStackTrace();
        }

    }

    public void closeConnection() throws IOException {
        client.close();

    }

}
