
public class DatabaseMnm {
	public static java.sql.Connection mainDbConn = null;
	public static java.sql.Statement mainDbConnStm1 = null;

	// entire exception handling info: mode=no
	public static void init() throws java.sql.SQLException, UserException {

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
			DatabaseMnm.mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:./data/main.db");
			DatabaseMnm.mainDbConnStm1 = DatabaseMnm.mainDbConn.createStatement();
			try {
				DatabaseMnm.runSQLcmd(sqlStms);
			} catch (UserException e1) {
				throw e1;
			}
		} catch (java.sql.SQLException e) {
			throw e;
		}
	}

	// entire exception handling info: mode=no
	public static void runSQLcmd(String[] sqlStms) throws UserException {
		String recentSqlStm = null;
		try {
			for (String sqlStm : sqlStms) {
				recentSqlStm = sqlStm;
				DatabaseMnm.mainDbConnStm1.execute(sqlStm);
			}
		} catch (java.sql.SQLException e1) {
			throw new UserException("SQL Error on query: " + recentSqlStm, e1);
		}

	}

	// entire exception handling info: mode=no
	public static void tempSeeDatabaseLamo() throws java.sql.SQLException {
		java.sql.ResultSet tablesRS = null, tableRS = null;
		try {
			tablesRS = DatabaseMnm.mainDbConn.getMetaData().getTables(null, null, null,
					new String[] { "TABLE" });
			int colCount = 0;
			while (tablesRS.next()) {
				String tableName = tablesRS.getString("TABLE_NAME");
				System.out.println("[TABLE: " + tableName + "]");
				tableRS = mainDbConnStm1.executeQuery("SELECT * FROM " + tableName);
				java.sql.ResultSetMetaData metaDataTableRS = tableRS.getMetaData();
				while (tableRS.next()) {
					System.out.println("> [ROW OF TABLE]");
					colCount = metaDataTableRS.getColumnCount();
					for (int i = 1; i <= colCount; i++) {
						String columnName;
						columnName = metaDataTableRS.getColumnName(i);
						String columnValue;
						columnValue = tableRS.getString(i);
						System.out.println(columnName + ": " + columnValue);
					}
					System.out.println(); // Separate rows
				}
				tableRS.close();
				tableRS = null;
			}
			tablesRS.close();
			tablesRS = null;
		} catch (java.sql.SQLException e1) {
			throw e1;
		} finally {
			try {
				tableRS.close();
			} catch (NullPointerException | java.sql.SQLException e1) {
			}
			try {
				tablesRS.close();
			} catch (NullPointerException | java.sql.SQLException e1) {
			}
		}
	}

}
