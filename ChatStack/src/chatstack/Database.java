package chatstack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    protected Connection con;
    protected ResultSet s = null;
    protected Statement stmt;
    public String IP = "0";

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("\u001B[34m" + "initialize connection to database" + "\u001B[0m");
            con = DriverManager.getConnection("jdbc:mysql://db4free.net/stackusers", "stackchat", "12345678");
            stmt = con.createStatement();
            System.out.println("\u001B[32m" + "Connected to database successfully" + "\u001B[0m");

        } catch (Exception e) {
            System.err.println("Error in database Connection");
        }

    }

    public void addUser(String Username, String Password, String Email) throws SQLException {

        if (checkUsername(Username) && checkEmail(Email)) {
            con.prepareStatement("INSERT INTO `Users` (`id`, `username`, `password`, `email`) VALUES (NULL, '" + Username + "', '" + Password + "', '" + Email + "'); ").executeUpdate();
            System.out.println("user added");
        } else {
            System.out.println("Username or email is arraly used");
        }
    }

    public boolean checkUsername(String Username) throws SQLException {
        String name = "";
        s = stmt.executeQuery("SELECT `username` FROM `Users` WHERE `username` LIKE '" + Username + "'");

        while (s.next()) {
            name = s.getString("username");

        }
        if (name.equals(Username)) {
            return false;
        }

        return true;

    }

    public int getID(String Username) throws SQLException {
        String id = "";
        s = stmt.executeQuery("SELECT `id` FROM `Users` WHERE `username` LIKE '" + Username + "'");
        while (s.next()) {
            id = s.getString("id");
        }
        return Integer.parseInt(id);
    }

    public boolean checkEmail(String Email) throws SQLException {
        String name = "";
        s = stmt.executeQuery("SELECT `email` FROM `Users` WHERE `email` LIKE '" + Email + "'");

        while (s.next()) {
            name = s.getString("email");
        }
        if (name.equals(Email)) {
            return false;
        }

        return true;

    }

    public boolean checkLogin(String Username, String Password) throws SQLException {

        String pass = "";
        s = stmt.executeQuery("SELECT `password` FROM `Users` WHERE `username` LIKE '" + Username + "'");

        while (s.next()) {
            pass = s.getString("password");
        }
        if (pass.equals(Password)) {
            return true;
        }

        return false;

    }

    public void setMemeberOnline(String Username) throws SQLException {
        stmt.executeUpdate("UPDATE `Users` SET `online` = 1 WHERE `username` LIKE '" + Username + "'");
        System.out.println("SET to 1");
    }

    public void setMemeberOffline(String Username) throws SQLException {

       stmt.executeUpdate("UPDATE `Users` SET `online` = 0 WHERE `username` LIKE '" + Username + "'");
        System.out.println("SET to 0");
 
    }

    public ArrayList<String> getOnlineMemebers() throws SQLException {

        ArrayList<String> OM = new ArrayList<String>();
        s = stmt.executeQuery("SELECT `username` FROM `Users` WHERE `online` = 1");
        while (s.next()) {
            OM.add(s.getString("username"));
        }
        return OM;

    }
    
     public ArrayList<String> getGroups() throws SQLException {

        ArrayList<String> OM = new ArrayList<String>();
        s = stmt.executeQuery("SELECT `name` FROM `Groups` ");
        while (s.next()) {
            OM.add("Group: " + (s.getString("name")));
        }
        return OM;

    }

    public void CloseDatabaseConnection() throws SQLException {
        this.con.close();
    }

    public void OpenDatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("\u001B[34m" + "initialize connection to database" + "\u001B[0m");
            con = DriverManager.getConnection("jdbc:mysql://db4free.net/stackusers", "stackchat", "12345678");
            stmt = con.createStatement();
            System.out.println("\u001B[32m" + "Connected to database successfully" + "\u001B[0m");

        } catch (Exception e) {
            System.err.println("Error in database Connection");
        }
    }

//<editor-fold defaultstate="collapsed" desc="Groups">
    public boolean createGroup(String name, String pass, String owner, int nOfUsers) throws SQLException {
        if (pass.length() <= 8) {
            con.prepareStatement("INSERT INTO `Groups` (`name`, `owner`, `password`,`numOfUsers`) VALUES ('" + name + "', '" + owner + "', '" + pass + "', '" + nOfUsers + "'); ").executeUpdate();
            return true;
        }
        return false;
    }

    public String getUserGroup(String Username) throws SQLException {
        s = stmt.executeQuery("SELECT `Group` FROM `Users` WHERE `username` LIKE '" + Username + "'");
        String Group = "";
        while (s.next()) {
            Group = s.getString("Group");
        }
        return Group;
    }
    
    public boolean checkGroupname(String group) throws SQLException{
        String g="";
        s = stmt.executeQuery("SELECT `name` FROM `Groups` WHERE `name` LIKE '" + group + "'");
        while(s.next())
            g=s.getString("name");
        if(g.equals("group"))
            return true;
        return false;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Server">
    public String CheckServerIP() throws SQLException {

        s = stmt.executeQuery("SELECT ip FROM `Server` WHERE online=1");

        while (s.next()) {
            IP = s.getString("IP");
        }

        System.out.println(IP);
        boolean online = false;
        try {
            online = CheckIfOnline(IP);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        if (IP.equals("") || !online) {
            return "0";
        }
        return IP;
    }

    public boolean CheckIfOnline(String IP) throws IOException {

        Socket s = new Socket(IP, 55555);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.writeUTF("online ?");
        String response = new String(in.readUTF());
        in.close();
        out.close();
        s.close();
        if (response.equals("yes online")) {
            return true;
        } else {
            return false;
        }
    }
//</editor-fold>

}
