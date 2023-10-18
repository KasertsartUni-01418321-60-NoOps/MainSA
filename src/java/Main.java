
public class Main extends javafx.application.Application {
    public static void funcTestOFCaughtException(javafx.stage.Stage primary) {

    }

    public static void main(String[] args) throws Throwable {
        try {
            // TODO: MAnage caught exception
            if (true) {
                javax.sound.midi.Sequence rickrollMIDISeq = javax.sound.midi.MidiSystem
                        .getSequence(Main.class.getClassLoader().getResourceAsStream("res/rickroll.mid"));
                javax.sound.midi.Sequencer midiSequencer = javax.sound.midi.MidiSystem.getSequencer();
                midiSequencer.open();
                midiSequencer.setLoopCount(javax.sound.midi.Sequencer.LOOP_CONTINUOUSLY);
                midiSequencer.setSequence(rickrollMIDISeq);
                midiSequencer.start();
            }
            tempDatabase();
            launch(args);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    // Function don't want me to throw lamo
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            MainAlt1.primaryStage = primaryStage;
            primaryStage.setOnCloseRequest(event -> {
                try {

                    primaryStage.hide();
                    javafx.application.Platform.exit();
                    // in case it don't shutdown lamo
                    try {
                        Thread.sleep(1000 * 30);
                    } catch (InterruptedException e3) {
                        // then force shutdown,so do nothing here
                    }
                    System.exit(0);
                } catch (Throwable e) {
                    try {
                        MyExceptionHandling.handleFatalException(e);
                    } catch (Throwable e0) {
                    } // we cannot throw out of lambda function
                }
            });
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            com.github.saacsos.FXRouter.bind(this, primaryStage,
                    "<APPNAME>", 800, 600);
            com.github.saacsos.FXRouter.when("main", "res/Main.fxml");
            com.github.saacsos.FXRouter.when("test", "res/Test.fxml");
            try {
                com.github.saacsos.FXRouter.goTo("main");
            } catch (java.io.IOException e) {
                throw e;
            }
            java.awt.Toolkit.getDefaultToolkit().beep(); // เสียงเพื่อสิริมงคล55
        } catch (Throwable e) {
            try {
                MyExceptionHandling.handleFatalException(e);
            } catch (Throwable e0) {
            } // we cannot throw out of this function due to override
        }
    }

    // TODO: rearrange of control flow of handle excpeiton
    private static void tempDatabase() throws Throwable {
        try {
            String[] sqlStms = new String[] {
                    "CREATE TABLE IF NOT EXISTS CUSTOMER (Customer_Full_Name TEXT PRIMARY KEY, Customer_Shipping_Address TEXT, Customer_Telephone_Number TEXT, Customer_Credit_Amount INTEGER);",
                    "CREATE TABLE IF NOT EXISTS SELLING_REQUEST (Selling_Request_ID INTEGER PRIMARY KEY, Customer_Full_Name TEXT, Selling_Request_Product_Looks TEXT, Selling_Request_Meet_Date INTEGER, Selling_Request_Paid_Amount REAL, Selling_Request_Meet_Location TEXT, Selling_Request_Status TEXT, Selling_Request_Model TEXT, Selling_Request_Brand TEXT, FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name));",
                    "CREATE TABLE IF NOT EXISTS PRODUCT (Product_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_ID INTEGER, Product_Price REAL, Product_Arrive_Date INTEGER, Product_Status TEXT, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID), FOREIGN KEY (Repairment_ID) REFERENCES REPAIRMENT(Repairment_ID));",
                    "CREATE TABLE IF NOT EXISTS USER (Username TEXT PRIMARY KEY, Password TEXT, Role INTEGER);",
                    "CREATE TABLE IF NOT EXISTS REPAIRMENT (Repairment_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_Description TEXT, Repairment_Date INTEGER, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID));",
                    "CREATE TABLE IF NOT EXISTS BUY_REQUEST (Customer_Full_Name TEXT, Product_ID INTEGER, Buy_Request_Transportation_Start_Date INTEGER, Buy_Request_Transportation_Finished_Date INTEGER, Buy_Request_Transportation_Price REAL, Buy_Request_Product_Look_After_Cleaning TEXT, PRIMARY KEY (Customer_Full_Name, Product_ID), FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name), FOREIGN KEY (Product_ID) REFERENCES PRODUCT(Product_ID));",
                    "INSERT INTO CUSTOMER SELECT 'John Doe', '123 Main St', '555-123-4567', 1000 " +
                            "WHERE NOT EXISTS (SELECT 1 FROM CUSTOMER WHERE Customer_Full_Name = 'John Doe');",
                    "INSERT INTO SELLING_REQUEST SELECT 1, 'John Doe', 'Sample Product Looks', 1634196000, 500.00, 'Sample Location', 'Pending', 'Sample Model', 'Sample Brand' "
                            +
                            "WHERE NOT EXISTS (SELECT 1 FROM SELLING_REQUEST WHERE Selling_Request_ID = 1);",
                    "INSERT INTO PRODUCT SELECT 1, 1, NULL, 250.00, 1634196000, 'Available' " +
                            "WHERE NOT EXISTS (SELECT 1 FROM PRODUCT WHERE Product_ID = 1);",
                    "INSERT INTO USER SELECT 'user1', 'password1', 1 " +
                            "WHERE NOT EXISTS (SELECT 1 FROM USER WHERE Username = 'user1');",
                    "INSERT INTO REPAIRMENT SELECT 1, 1, 'Sample Repairment Description', 1634196000 " +
                            "WHERE NOT EXISTS (SELECT 1 FROM REPAIRMENT WHERE Repairment_ID = 1);",
                    "INSERT INTO BUY_REQUEST SELECT 'John Doe', 1, 1634196000, 1634200000, 50.00, 'Product looks good after cleaning' "
                            +
                            "WHERE NOT EXISTS (SELECT 1 FROM BUY_REQUEST WHERE Customer_Full_Name = 'John Doe' AND Product_ID = 1);"
            };
            java.sql.Connection mainDbConn = null;
            java.sql.Statement mainDbConnStm1 = null;
            java.sql.ResultSet tablesRS = null, tableRS = null;
            try {
                mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:./data/main.db");
                // SQL ERR
                mainDbConnStm1 = mainDbConn.createStatement();
                for (String sqlStm : sqlStms) {
                    // SQL ERR
                    mainDbConnStm1.execute(sqlStm);
                }
                // SQL ERR
                tablesRS = mainDbConn.getMetaData()
                        .getTables(null, null, null, new String[] { "TABLE" });
                int colCount = 0;
                // SQL ERR
                while (tablesRS.next()) {
                    // SQL ERR
                    String tableName = tablesRS.getString("TABLE_NAME");
                    System.out.println("[TABLE: " + tableName + "]");
                    // SQL ERR
                    tableRS = mainDbConnStm1.executeQuery("SELECT * FROM " + tableName);
                    // SQL ERR
                    java.sql.ResultSetMetaData metaDataTableRS = tableRS.getMetaData();
                    // SQL ERR
                    while (tableRS.next()) {
                        System.out.println("> [ROW OF TABLE]");
                        // SQL ERR
                        colCount = metaDataTableRS.getColumnCount();
                        for (int i = 1; i <= colCount; i++) {
                            String columnName;
                            // SQL ERR
                            columnName = metaDataTableRS.getColumnName(i);
                            String columnValue;
                            // SQL ERR
                            columnValue = tableRS.getString(i);
                            System.out.println(columnName + ": " + columnValue);
                        }
                        System.out.println(); // Separate rows
                    }
                    // SQL ERR
                    tableRS.close();
                }
                // SQL ERR
                tablesRS.close();
                // Close the resources
                // SQL ERR
                mainDbConnStm1.close();
                // SQL ERR
                mainDbConn.close();
            } catch (java.sql.SQLException e) {
                throw e;
            } finally {
                try {
                    tableRS.close();
                } catch (NullPointerException e) {
                }
                try {
                    tablesRS.close();
                } catch (NullPointerException e) {
                }
                try {
                    mainDbConnStm1.close();
                } catch (NullPointerException e) {
                }
                try {
                    mainDbConn.close();
                } catch (NullPointerException e) {
                }

            }
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    @javafx.fxml.FXML
    public static void tempSwitchToTestPage() throws Throwable {
        try {
            try {
                com.github.saacsos.FXRouter.goTo("test");
            } catch (java.io.IOException e) {
                throw e;
            }
            tempChangeCSS("Test");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    @javafx.fxml.FXML
    public static void tempChangeCSS(String cssName) throws Throwable {
        try {
            javafx.scene.Scene temp = MainAlt1.primaryStage.getScene();
            temp.getStylesheets().clear();
            temp.getStylesheets().add(Main.class.getResource("res/" + cssName + ".css").toExternalForm());
            temp.getRoot().applyCss();
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    public static javafx.stage.Stage tempGetPrimaryStage() throws Throwable {
        javafx.stage.Stage retVal = null;
        try {
            retVal = MainAlt1.primaryStage;
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
        return retVal;
    }

}

class MainAlt1 {
    public static javafx.stage.Stage primaryStage = null;
}