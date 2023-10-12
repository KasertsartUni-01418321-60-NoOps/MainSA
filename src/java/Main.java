import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a JFrame (window)
            final javax.swing.JFrame mainWindow = new javax.swing.JFrame("Hello World GUI");
            mainWindow.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            // Create a JLabel with the text "Hello, World!"
            final javax.swing.JLabel label = new javax.swing.JLabel("Hello, World!");
            // Add the label to the content pane of the frame
            mainWindow.getContentPane().add(label);
            // Set the size and make the frame visible
            mainWindow.setSize(200, 100);
            mainWindow.setVisible(true);

            // Connect to the SQLite database or create it if it doesn't exist
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:main.db");
            // Create a table and insert sample data
            Statement mainDbConnStm1 = mainDbConn.createStatement();
            mainDbConnStm1.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)");
            mainDbConnStm1.execute("INSERT INTO users (name) VALUES ('John')");
            mainDbConnStm1.execute("INSERT INTO users (name) VALUES ('Alice')");
            // Query the data and display it
            java.sql.ResultSet mainDbConnStm1_resultSet = mainDbConnStm1.executeQuery("SELECT * FROM users");
            while (mainDbConnStm1_resultSet.next()) {
                int tmp_id = mainDbConnStm1_resultSet.getInt("id");
                String tmp_name = mainDbConnStm1_resultSet.getString("name");
                System.out.println("ID: " + tmp_id + ", Name: " + tmp_name);
            }
            // Close the resources
            mainDbConnStm1_resultSet.close();
            mainDbConnStm1.close();
            mainDbConn.close();
        } catch (Exception e) {
            // TODO: Exception System
            e.printStackTrace();
        }
    }
}
