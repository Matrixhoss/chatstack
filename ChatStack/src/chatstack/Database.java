package chatstack;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class Database {
    protected Connection con;

    public Database() {
    }


    public Connection connectDatabase() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/chatstack", "root", "12345");
            System.out.println("asdasd");

            Statement stmt = con.createStatement();
            System.out.println("Connected");

            return con;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }


        return null;
    }

    public void createTable() throws Exception {
        try {
            this.con = connectDatabase();
            //////Token for all documents
            {  
                PreparedStatement create = this.con.prepareStatement("Create Table Users (" +
                        "id int primary key," +
                        "name varchar(255))");
                create.executeUpdate();
            }
            ///////////Inverted index database

            
        } catch (Exception e) {
            System.out.println(e);
            
            
        } finally {
            System.out.println("Table Created");
        }
    }
}
