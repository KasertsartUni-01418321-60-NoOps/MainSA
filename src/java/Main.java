import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
                MainAlt1.primaryStage = primaryStage;
                primaryStage.setResizable(false);
                primaryStage.setMaximized(false);
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
        int colCount = 0;
        String[] sqlStms = null;
        sqlStms = new String[] {
                "CREATE TABLE CUSTOMER (Customer_Full_Name TEXT PRIMARY KEY, Customer_Shipping_Address TEXT, Customer_Telephone_Number TEXT, Customer_Credit_Amount INTEGER);",
                "CREATE TABLE SELLING_REQUEST (Selling_Request_ID INTEGER PRIMARY KEY, Customer_Full_Name TEXT, Selling_Request_Product_Looks TEXT, Selling_Request_Meet_Date INTEGER, Selling_Request_Paid_Amount REAL, Selling_Request_Meet_Location TEXT, Selling_Request_Status TEXT, Selling_Request_Model TEXT, Selling_Request_Brand TEXT, FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name));",
                "CREATE TABLE PRODUCT (Product_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_ID INTEGER, Product_Price REAL, Product_Arrive_Date INTEGER, Product_Status TEXT, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID), FOREIGN KEY (Repairment_ID) REFERENCES REPAIRMENT(Repairment_ID));",
                "CREATE TABLE USER (Username TEXT PRIMARY KEY, Password TEXT, Role INTEGER);",
                "CREATE TABLE REPAIRMENT (Repairment_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_Description TEXT, Repairment_Date INTEGER, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID));",
                "CREATE TABLE BUY_REQUEST (Customer_Full_Name TEXT, Product_ID INTEGER, Buy_Request_Transportation_Start_Date INTEGER, Buy_Request_Transportation_Finished_Date INTEGER, Buy_Request_Transportation_Price REAL, Buy_Request_Product_Look_After_Cleaning TEXT, PRIMARY KEY (Customer_Full_Name, Product_ID), FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name), FOREIGN KEY (Product_ID) REFERENCES PRODUCT(Product_ID));"
        };
        for (String sqlStm : sqlStms) {
            mainDbConnStm1.execute(sqlStm);
        }
        sqlStms = new String[] {
                "INSERT INTO CUSTOMER VALUES ('John Doe', '123 Main St', '555-123-4567', 1000);",
                "INSERT INTO SELLING_REQUEST VALUES (1, 'John Doe', 'Sample Product Looks', 1634196000, 500.00, 'Sample Location', 'Pending', 'Sample Model', 'Sample Brand');",
                "INSERT INTO PRODUCT VALUES (1, 1, NULL, 250.00, 1634196000, 'Available');",
                "INSERT INTO USER VALUES ('user1', 'password1', 1);",
                "INSERT INTO REPAIRMENT VALUES (1, 1, 'Sample Repairment Description', 1634196000);",
                "INSERT INTO BUY_REQUEST VALUES ('John Doe', 1, 1634196000, 1634200000, 50.00, 'Product looks good after cleaning');"
        };
        for (String sqlStm : sqlStms) {
            mainDbConnStm1.execute(sqlStm);
        }
        // Query the data and display it
        if (true) {
            java.sql.ResultSet tablesRS = mainDbConn.getMetaData().getTables(null, null, null,
                    new String[] { "TABLE" });
            while (tablesRS.next()) {
                String tableName = tablesRS.getString("TABLE_NAME");
                System.out.println("[TABLE: " + tableName + "]");
                java.sql.ResultSet tableRS = mainDbConnStm1.executeQuery("SELECT * FROM " + tableName);
                java.sql.ResultSetMetaData metaDataTableRS = tableRS.getMetaData();
                while (tableRS.next()) {
                    System.out.println("> [ROW OF TABLE]");
                    colCount = metaDataTableRS.getColumnCount();
                    for (int i = 1; i <= colCount; i++) {
                        String columnName = metaDataTableRS.getColumnName(i);
                        String columnValue = tableRS.getString(i);
                        System.out.println(columnName + ": " + columnValue);
                    }
                    System.out.println(); // Separate rows
                }
                tableRS.close();
            }
            tablesRS.close();
        }
        // Close the resources
        mainDbConnStm1.close();
        mainDbConn.close();
    }

    @javafx.fxml.FXML
    public static void tempSwitchToTestPage() throws Throwable {
        com.github.saacsos.FXRouter.goTo("test");
        tempChangeCSS("Test");
    }

    @javafx.fxml.FXML
    public static void tempChangeCSS(String cssName) throws Throwable {
        javafx.scene.Scene temp = MainAlt1.primaryStage.getScene();
        temp.getStylesheets().clear();
        temp.getStylesheets().add(Main.class.getResource("res/" + cssName + ".css").toExternalForm());
        temp.getRoot().applyCss();
    }

    public static javafx.stage.Stage tempGetPrimaryStage() throws Throwable {
        return MainAlt1.primaryStage;
    }
}

class MainAlt1 {
    public static javafx.stage.Stage primaryStage = null;
}