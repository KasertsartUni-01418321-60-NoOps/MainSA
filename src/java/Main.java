public class Main {
    private static boolean isFatal = false;
    public static void main(String[] args) throws Throwable {
        try {
            // TODO: Exception System
            try {
                Main.temp();
            } catch (Error e) {
                Main.isFatal=true;
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                Main.isFatal=true;
                e.printStackTrace();
                throw e;
            } catch (Throwable e) {
                Main.isFatal=true;
                e.printStackTrace();
                throw e;
            }
        } finally {
            try {
                if (Main.isFatal) {
                    System.exit(255);
                } else {
                    // due to we wait until window is closed // System.exit(0);
                }
            } catch (Throwable e) {
                try { System.exit(255); }
                finally { throw e; }
            }
        }
    }
    public static void temp() throws Throwable {
        // Create a JFrame (window)
        final javax.swing.JFrame mainWindow = new javax.swing.JFrame("Hello World GUI");
        mainWindow.setSize(400, 150);
        mainWindow.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        final javax.swing.JPanel mainWindow_MainPanel = new javax.swing.JPanel();
        mainWindow_MainPanel.setLayout(new java.awt.FlowLayout());
        final javax.swing.JLabel mainWindow_Label1 = new javax.swing.JLabel("Hello, World!");
        final javax.swing.JButton mainWindow_Button1= new javax.swing.JButton("Test Me!");
        mainWindow_Button1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)  {
                try {
                java.awt.Desktop.getDesktop().browse( new java.net.URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ") );
                } catch (Throwable e1) {}
            }
        });
        mainWindow_MainPanel.add(mainWindow_Label1);
        mainWindow_MainPanel.add(mainWindow_Button1);
        mainWindow.add(mainWindow_MainPanel);
        mainWindow.setVisible(true);

        // Connect to the SQLite database or create it if it doesn't exist
        Class.forName("org.sqlite.JDBC");
        java.sql.Connection mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:./data/main.db");
        // Create a table and insert sample data
        java.sql.Statement mainDbConnStm1 = mainDbConn.createStatement();
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
    }
}
