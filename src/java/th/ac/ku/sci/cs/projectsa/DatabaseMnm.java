package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;

import java.io.IOException;

import th.ac.ku.sci.cs.projectsa.*;

public class DatabaseMnm {
	public static java.sql.Connection mainDbConn = null;
	public static java.sql.Statement mainDbConnStm1 = null;
	public final static String mainDbPath = "./data/main.db";


	// [Zone:Init]

	// entire exception handling info: mode=no
	public static void mainDbInit() throws java.sql.SQLException, IOException {
		java.nio.file.Path tmp_Path = java.nio.file.Paths.get("./data");
		if (!java.nio.file.Files.exists(tmp_Path)) {
			try {
				java.nio.file.Files.createDirectory(tmp_Path);
			} catch (java.io.IOException e) {
				throw e;
			}
		}
		// TODO: (OUTSIDE OF CODING) describe Data Spec
		// REMARK: for my group, only use {TEXT,BLOB,REAL,INTEGER} maybe we not using
		// "NUMERIC"
		String[] sqlStms = new String[] {
			"CREATE TABLE IF NOT EXISTS CUSTOMER (Customer_Full_Name TEXT PRIMARY KEY, Customer_Shipping_Address TEXT, Customer_Telephone_Number TEXT, Customer_Credit_Amount INTEGER) STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS SELLING_REQUEST (Selling_Request_ID INTEGER PRIMARY KEY, Customer_Full_Name TEXT, Selling_Request_Product_Looks TEXT, Selling_Request_Meet_Date INTEGER, Selling_Request_Paid_Amount REAL, Selling_Request_Meet_Location TEXT, Selling_Request_Status TEXT, Selling_Request_Model TEXT, Selling_Request_Brand TEXT, FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name))STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS PRODUCT (Product_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_ID INTEGER, Product_Price REAL, Product_Arrive_Date INTEGER, Product_Status TEXT, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID), FOREIGN KEY (Repairment_ID) REFERENCES REPAIRMENT(Repairment_ID))STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS USER (Username TEXT PRIMARY KEY, Password TEXT, Role INTEGER)STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS REPAIRMENT (Repairment_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_Description TEXT, Repairment_Date INTEGER, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID))STRICT,WITHOUT ROWID;",
			// TODO: BUY_REQUEST แก้ด้วยๆ
			"CREATE TABLE IF NOT EXISTS BUY_REQUEST (Customer_Full_Name TEXT, Product_ID INTEGER, Buy_Request_Transportation_Start_Date INTEGER, Buy_Request_Transportation_Finished_Date INTEGER, Buy_Request_Transportation_Price REAL, Buy_Request_Product_Look_After_Cleaning TEXT, PRIMARY KEY (Customer_Full_Name, Product_ID), FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name), FOREIGN KEY (Product_ID) REFERENCES PRODUCT(Product_ID))STRICT,WITHOUT ROWID;",
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
						// TODO: BUY_REQUEST แก้ด้วยๆ
						"INSERT INTO BUY_REQUEST SELECT 'John Doe', 1, 1634196000, 1634200000, 50.00, 'Product looks good after cleaning' "
						+
						"WHERE NOT EXISTS (SELECT 1 FROM BUY_REQUEST WHERE Customer_Full_Name = 'John Doe' AND Product_ID = 1);"
		};
		try {
			DatabaseMnm.mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:./data/main.db");
			DatabaseMnm.mainDbConnStm1 = DatabaseMnm.mainDbConn.createStatement();
			DatabaseMnm.runSQLcmds(null, sqlStms, true);
		} catch (java.sql.SQLException e) {
			throw e;
		}
	}

	// [Zone:GeneralUsage]


	// entire exception handling info: mode=no
	public static Object[] runSQLcmd(java.sql.Statement dbStm, String sqlStm, boolean skipGetTable)
			throws java.sql.SQLException {
		if (dbStm == null) {
			dbStm = DatabaseMnm.mainDbConnStm1;
		}
		boolean tmp1 = false;
		try {
			tmp1 = DatabaseMnm.mainDbConnStm1.execute(sqlStm);
		} catch (java.sql.SQLException e1) {
			throw e1;
		}
		// put resultset
		if (tmp1) {
			if (skipGetTable) {
				return new Object[] { true, null };
			} else {
				return new Object[] { true, DatabaseMnm.mainDbConnStm1.getResultSet() };
			}
		}
		// else then check if it is update count
		else {
			int tmp2 = DatabaseMnm.mainDbConnStm1.getUpdateCount();
			if (tmp2 == -1) {
				return new Object[] { null, null };
			} else {
				return new Object[] { false, tmp2 };
			}
		}

	}

	// entire exception handling info: mode=no
	public static Object[][] runSQLcmds(java.sql.Statement dbStm, String[] sqlStms, boolean skipGetTable)
			throws java.sql.SQLException {
		if (dbStm == null) {
			dbStm = DatabaseMnm.mainDbConnStm1;
		}
		Object[][] retVal = new Object[sqlStms.length][];
		for (int i = 0; i < sqlStms.length; i++) {
			retVal[i] = runSQLcmd(dbStm, sqlStms[i], skipGetTable);
		}
		return retVal;
	}

	// [Zone:PublicHelper]

	// entire exception handling info: mode=no
	public static String getTableNameFromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		return metaData.getTableName(1); // Assuming the first column in the result set corresponds to a table.
	}

	// entire exception handling info: mode=no
	public static int getColsCountFromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		return metaData.getColumnCount();
	}

	// entire exception handling info: mode=no
	public static String getColumnNameFromResultSet(java.sql.ResultSet resultSet, int columnIndex)
			throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		return metaData.getColumnName(columnIndex);
	}

	// entire exception handling info: mode=no
	// REMARK: only determine by using of native datatype in SQL query only, do not
	// using another datatype else from {INTEGER,REAL,BLOB,TEXT}
	public static Object[] getColumnDataTypeFromResultSet(java.sql.ResultSet resultSet, int columnIndex)
			throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		int sqlType = metaData.getColumnType(columnIndex);
		String dbType = metaData.getColumnTypeName(columnIndex);
		Class<?> javaType;
		switch (sqlType) {
			// การแปลงเป็น javaType ผมอิงตาม ChatGPT lamo โดยถามมันว่า อิงตาม general
			// situation
			case java.sql.Types.INTEGER:
				javaType = Integer.class;
				break;
			case java.sql.Types.BIGINT:
				javaType = Long.class;
				break;
			case java.sql.Types.REAL:
				javaType = Float.class;
				break;
			case java.sql.Types.FLOAT:
				javaType = Double.class;
				break;
			case java.sql.Types.VARCHAR:
				javaType = String.class;
				break;
			case java.sql.Types.BLOB:
				javaType = byte[].class;
				break;
			case java.sql.Types.NUMERIC:
				javaType = java.math.BigDecimal.class;
				break;
			default:
				// it must not reached, so give runtimeexception
				throw new MyExceptionHandling.UserRuntimeException("Given javaType is not supported");

		}
		return new Object[] { dbType, sqlType, javaType };
	}

	// entire exception handling info: mode=no
	// REMARK: only determine by using of available javatype in
	// getColumnDataTypeFromResultSet() only, do not
	// using another datatype else from {INTEGER,REAL,BLOB,TEXT} to control
	// possible output of getColumnDataTypeFromResultSet()
	public static <T> T getDataWithJavaTypeBasedOnJavaType(Class<T> javaType, java.sql.ResultSet resultSet,
			int columnIndex)
			throws java.sql.SQLException {
		T retVal =null;
		if (javaType == Integer.class) {
			retVal =(T) (Integer) resultSet.getInt(columnIndex);
		} else if (javaType == Long.class) {
			retVal= (T) (Long) resultSet.getLong(columnIndex);
		} else if (javaType == Float.class) {
			retVal =(T) (Float) resultSet.getFloat(columnIndex);
		} else if (javaType == Double.class) {
			retVal =(T) (Double) resultSet.getDouble(columnIndex);
		} else if (javaType == String.class) {
			retVal =(T) resultSet.getString(columnIndex);
		} else if (javaType == byte[].class) {
			retVal =(T) resultSet.getBytes(columnIndex);
		} else if (javaType == java.math.BigDecimal.class) {
			retVal =(T) resultSet.getBigDecimal(columnIndex);
		} else {// it must not reached, so give runtimeexception
			throw new MyExceptionHandling.UserRuntimeException("Given javaType is not supported");
		}
		if (resultSet.wasNull()) {return null;}
		return retVal;

	}

	// entire exception handling info: mode=no
	// WARNING: this would executed `resultSet.next()`
	// REMARK: only determine by using of available javatype in
	// getColumnDataTypeFromResultSet() only, do not
	// using another datatype else from {INTEGER,REAL,BLOB,TEXT} to control
	// possible output of getColumnDataTypeFromResultSet()
	public static <T> java.util.List<T> getColumnValuesFromResultSet(java.sql.ResultSet resultSet, int columnIndex,
			Class<T> javaType,
			Integer initRowCountForArrayList)
			throws java.sql.SQLException {
		java.util.List<T> values = null;
		if (initRowCountForArrayList == null) {
			values = new java.util.LinkedList<>();
		} else {
			values = new java.util.ArrayList<>(initRowCountForArrayList);
		}
		while (resultSet.next()) {
			values.add(getDataWithJavaTypeBasedOnJavaType(javaType, resultSet, columnIndex));
		}

		return values;
	}

	// entire exception handling info: mode=no
	// WARNING: this would executed `resultSet.next()`
	public static Table convertResultSetToTable(java.sql.ResultSet resultSet) throws java.sql.SQLException {
		Table table = new Table();
		table.name = getTableNameFromResultSet(resultSet);
		table.cols = new Column<?>[getColsCountFromResultSet(resultSet)];
		Object[] tmp_colDataInfo = null;
		for (int i = 0; i < table.cols.length; i++) {
			int colIndex = i + 1;
			tmp_colDataInfo = getColumnDataTypeFromResultSet(resultSet, colIndex);
			table.cols[i] = convertResultSetToTable_Helper1_createColumnWithSpecificJavaType(
					(Class<?>) tmp_colDataInfo[2]);
			table.cols[i].name = getColumnNameFromResultSet(resultSet, colIndex);
			table.cols[i].dbType = (String) tmp_colDataInfo[0];
			table.cols[i].sqlType = (Integer) tmp_colDataInfo[1];
			// for .javaType/.vals setting, it already done in
			// createColumnWithSpecificJavaType, due to limitation and my knowledge about
			// Java dynamic type lamo
		}
		while (resultSet.next()) {
			for (int i = 0; i < table.cols.length; i++) {
				int colIndex = i + 1;
				convertResultSetToTable_Helper2_addValueToColumnWithSpecificJavaType(table.cols[i],
						(Class<?>) table.cols[i].javaType, resultSet, colIndex);
			}
		}
		return table;
	}

	// [Zone:ProtectedHelper]

	// entire exception handling info: mode=no
	protected static <T> Column<T> convertResultSetToTable_Helper1_createColumnWithSpecificJavaType(Class<T> javaType) {
		Column<T> col = new Column<T>();
		col.javaType = javaType;
		col.vals = new java.util.LinkedList<T>();
		return col;
	}

	// entire exception handling info: mode=no
	protected static <T> void convertResultSetToTable_Helper2_addValueToColumnWithSpecificJavaType(Column<?> col,
			Class<T> javaType, java.sql.ResultSet resultSet, int colIndex) throws java.sql.SQLException {
		Column<T> tmp_newcol = (Column<T>) col;
		tmp_newcol.vals.add(
				getDataWithJavaTypeBasedOnJavaType(javaType, resultSet, colIndex));

	}

	// [Zone:DemoMethod]

	// entire exception handling info: mode=no
	public static void demo_printTableLAMO(Table table) {
		System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "[START OF SQL Table Printing]");
		System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "Name:" + table.name);
		System.out
				.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "Column Length:" + table.cols.length);
		System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO")); // new line sep
		for (DatabaseMnm.Column<?> col : table.cols) {
			System.out.println(
					Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "> [START OF SQL Column Printing]");
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "Name:" + col.name);
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO")
					+ "sqlType (you compare to java.sql.Types manually, because it is not enum lamo):" + col.sqlType);
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "dbType:" + col.dbType);
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "JavaType:" + col.javaType);
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO")
					+ "> > [START OF Each rows' value in column Printing]");
			for (Object obj : col.vals) {
				System.out.println(
						Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "> Val:" + col.javaType.cast(obj));
			}
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO")
					+ "> > [END OF Each rows' value in column Printing]");
			System.out
					.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "> [END OF SQL Column Printing]");
			System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO")); // new line sep
		}
		System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO") + "[END OF SQL Table Printing]");
		System.out.println(Main.clReportHeader("MainApp/DatabaseMnm", "DEVDEMO")); // new line sep
	}

	// entire exception handling info: mode=no
	public static void demo_printOurInitTableLAMO() throws java.sql.SQLException {
		java.sql.ResultSet tmpResultSet = null;
		Table tmpTable = null;
		String[] tableNames = new String[] { "USER", "CUSTOMER", "PRODUCT", "BUY_REQUEST", "SELLING_REQUEST",
				"REPAIRMENT" };
		for (String tableName : tableNames) {
			tmpResultSet = (java.sql.ResultSet) (DatabaseMnm.runSQLcmd(null, "SELECT * FROM " + tableName, false)[1]);
			tmpTable = convertResultSetToTable(tmpResultSet);
			demo_printTableLAMO(tmpTable);
			tmpResultSet.close();
		}
	}
	// [Zone:SubClass]

	public static class Table {
		public String name = null;
		public Column<?>[] cols = null;
	}

	public static class Column<T> {
		public String name = null;
		public Integer sqlType = null;
		public String dbType = null;
		public Class<T> javaType = null;
		public java.util.List<T> vals = null;
	}

}
