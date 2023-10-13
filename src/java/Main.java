public class Main extends javafx.application.Application {
    private static boolean isFatal = false;

    public static void main(String[] args) throws Throwable {
        try {
            // TODO: Exception System
            try {
                tempDatabase();
                launch(args);
            } catch (Error e) {
                Main.isFatal = true;
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                Main.isFatal = true;
                e.printStackTrace();
                throw e;
            } catch (Throwable e) {
                Main.isFatal = true;
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
                try {
                    System.exit(255);
                } finally {
                    throw e;
                }
            }
        }
    }

    // Function don't want me to throw lamo
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            // TODO: Exception System
            try {
                com.github.saacsos.FXRouter.bind(this, primaryStage, "<AppName>", 800, 600);
                com.github.saacsos.FXRouter.when("main", "res/Main.fxml");
                com.github.saacsos.FXRouter.when("test", "res/Test.fxml");
                com.github.saacsos.FXRouter.goTo("main");
            } catch (Error e) {
                Main.isFatal = true;
                e.printStackTrace();
                // Function don't want me to throw lamo // throw e;
            } catch (Exception e) {
                Main.isFatal = true;
                e.printStackTrace();
                // Function don't want me to throw lamo // throw e;
            } catch (Throwable e) {
                Main.isFatal = true;
                e.printStackTrace();
                // Function don't want me to throw lamo // throw e;
            }
        } finally {
            try {
                if (Main.isFatal) {
                    System.exit(255);
                } else {
                    // due to we wait until window is closed // System.exit(0);
                }
            } catch (Throwable e0) {
                try {
                    System.exit(255);
                } finally {
                    // Function don't want me to throw lamo // throw e0;
                }
            }
        }
    }

    private static void tempDatabase() throws Throwable {
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

    @javafx.fxml.FXML
    private void tempRickroll() throws Throwable {
        // Add code here to open a web browser or a media player to play the Rickroll
        // video
        // You can use Java's Desktop class or external library to open a URL.
        // For simplicity, you can use the default web browser to open the Rickroll URL.
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=oHg5SJYRHA0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    private void tempSwitchToTestPage() throws Throwable {
        com.github.saacsos.FXRouter.goTo("test");
    }
}
