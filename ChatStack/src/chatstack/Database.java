package chatstack;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database {
    protected Connection con;
    ResultSet s  = null;
     Statement stmt;

    public Database() {
    }


    public Connection connectDatabase() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection( "jdbc:mysql://db4free.net/stackusers", "stackchat", "12345678");
            System.out.println("asdasd");

             stmt = con.createStatement();
            System.out.println("Connected");

            return con;
        } catch (Exception e) {
            System.out.println("Fuck");
            e.printStackTrace();
        }


        return null;
    }

    public void createTable() throws Exception {
        try {
            this.con = connectDatabase();
            //////Token for all documents
            {  

            }
            
            //con.prepareStatement("INSERT INTO `Users` (`id`, `username`, `password`, `email`) VALUES (NULL, 'vvv', 'vvv', 'vvv'); ").executeUpdate();
            addUser("1","2","3");
            s = stmt.executeQuery("select * from Users");
            
            while (s.next()){
                System.out.println(s.getString("username"));
            }
            ///////////Inverted index database

            
        } catch (Exception e) {
         //   System.out.println(e);
          //  System.out.println("Table Created");
          //  e.printStackTrace();
            
        } finally {
           
        }
    }
    
    public void addUser (String Username , String Password , String Email ){
        
        try {
            con.prepareStatement("INSERT INTO `Users` (`id`, `username`, `password`, `email`) VALUES (NULL, '"+Username+"', '"+Password+"', '"+Email+"'); ").executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erron in database ");
        }
    
    }
    
    
}
