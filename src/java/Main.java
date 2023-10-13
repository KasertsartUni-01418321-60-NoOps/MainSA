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

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            // TODO: Exception System
            try {
                primaryStage.setTitle("<App Name>");

                javafx.scene.control.Button Button1 = new javafx.scene.control.Button("Click me!");
                Button1.setOnAction(e -> tempOpenWebPage("https://www.youtube.com/watch?v=oHg5SJYRHA0"));

                javafx.scene.layout.StackPane MainScene_MainRoot = new javafx.scene.layout.StackPane();
                MainScene_MainRoot.getChildren().add(Button1);

                javafx.scene.Scene MainScene = new javafx.scene.Scene(MainScene_MainRoot, 400, 200);
                primaryStage.setScene(MainScene);

                primaryStage.show();
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

    private static void tempOpenWebPage(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (java.io.IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
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
}
