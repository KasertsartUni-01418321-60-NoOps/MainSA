package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import th.ac.ku.sci.cs.projectsa.*;

public class DatabaseMnm {
	public static java.sql.Connection mainDbConn = null;
	public static java.sql.Statement mainDbConnStm1 = null;
	public final static String mainDbPath = "./data/main.db";

	// [Zone:Init]

	// entire exception handling info: mode=no
	public static void mainDbInit() throws java.sql.SQLException {

		try {
			// TODO: describe Data Spec
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
			DatabaseMnm.runSQLcmds(null,sqlStms, true);
		} catch (java.sql.SQLException e) {
			throw e;
		}
	}

	// [Zone:GeneralUsage]

	// entire exception handling info: mode=no
	public static Object[] runSQLcmd(java.sql.Statement dbStm, String sqlStm, boolean skipGetTable) throws java.sql.SQLException {
		if( dbStm==null) {dbStm=DatabaseMnm.mainDbConnStm1;}
		boolean tmp1 = false;
		try {
			tmp1 = DatabaseMnm.mainDbConnStm1.execute(sqlStm);
		} catch (java.sql.SQLException e1) {
			throw e1;
		}
		// then put resultset as table[]
		if (tmp1) {
			if (skipGetTable) {
				return new Object[] { true, null };
			} else {
				DatabaseMnm.Table table = new DatabaseMnm.Table();
				// don't forget to check exception handling and close()
				return new Object[] { true, table };
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
	public static Object[][] runSQLcmds(java.sql.Statement dbStm, String[] sqlStms, boolean skipGetTable) throws java.sql.SQLException {
		if( dbStm==null) {dbStm=DatabaseMnm.mainDbConnStm1;}
		Object[][] retVal = new Object[sqlStms.length][];
		for (int i = 0; i < sqlStms.length; i++) {
			retVal[i] = runSQLcmd(dbStm,sqlStms[i], skipGetTable);
		}
		return retVal;
	}

	// [Zone:Unmanaged]
	// TODO: for sorting in this zone

	// entire exception handling info: mode=no
	public static <T> T getDataWithJavaTypeBasedOnJavaType(Class<T>  javaType, java.sql.ResultSet resultSet, int columnIndex)
			throws java.sql.SQLException {
		if (javaType==Integer.class)
			{return (T) (Integer) resultSet.getInt(columnIndex);}
		else if (javaType==Long.class)
			{return (T) (Long) resultSet.getLong(columnIndex);}
		else if (javaType==Float.class)
			{return (T) (Float) resultSet.getFloat(columnIndex);}
		else if (javaType==Double.class)
			{return (T) (Double)resultSet.getDouble(columnIndex);}
		else if (javaType==String.class)
			{return (T) resultSet.getString(columnIndex);}
		else if (javaType==byte[].class)
			{return (T) resultSet.getBytes(columnIndex);}
		else if (javaType==java.math.BigDecimal.class)
			{return (T) resultSet.getBigDecimal(columnIndex);}
		else
			{// it must not reached, so give runtimeexception
			throw new MyExceptionHandling.UserRuntimeException("Given javaType is not supported");}
	
	}

	// entire exception handling info: mode=no
	// TODO: separate become function that (get ResultSet from SQL query) + (get
	// Table obj from ResultSet) + (Print Table obj)
	// TODO: maybe we create this from scratch byusing as reference
	// TODO: don't forget to check close()
	public static void tempSeeDatabaseLamo() throws java.sql.SQLException {
		java.sql.ResultSet tablesRS = null, tableRS = null;
		try {
			tablesRS = DatabaseMnm.mainDbConn.getMetaData().getTables(null, null, null,
					new String[] { "TABLE" });
			int colCount = 0;
			while (tablesRS.next()) {
				String tableName = tablesRS.getString("TABLE_NAME");
				System.out.println(Main.clReportHeader(null, "DEVTEST") + "[TABLE: " + tableName + "]");
				tableRS = mainDbConnStm1.executeQuery("SELECT * FROM " + tableName);
				java.sql.ResultSetMetaData metaDataTableRS = tableRS.getMetaData();
				while (tableRS.next()) {
					System.out.println(Main.clReportHeader(null, "DEVTEST") + "> [ROW OF TABLE]");
					colCount = metaDataTableRS.getColumnCount();
					for (int i = 1; i <= colCount; i++) {
						String columnName;
						columnName = metaDataTableRS.getColumnName(i);
						String columnValue;
						columnValue = tableRS.getString(i);
						System.out.println(Main.clReportHeader(null, "DEVTEST") + columnName + ": " + columnValue);
					}
					System.out.println(Main.clReportHeader(null, "DEVTEST")); // Separate rows
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

	// entire exception handling info: mode=no
	// TODO: see above TODO
	// public static void unused_tempCreateAndSeeMySQLTableDataStr() {
	// 	DatabaseMnm.Table testTable = new DatabaseMnm.Table();
	// 	testTable.name = "Rickroll";
	// 	DatabaseMnm.Column<Integer> testTable_Id = new DatabaseMnm.Column<Integer>();
	// 	testTable_Id.name = "Id";
	// 	// TODO:
	// 	testTable_Id.sqlType = java.sql.Types.INTEGER;
	// 	testTable_Id.dbType = "INTEGER";
	// 	testTable_Id.javaType = Integer.class;
	// 	DatabaseMnm.Column<String> testTable_Lyrics = new DatabaseMnm.Column<String>();
	// 	testTable_Lyrics.name = "Lyrics";
	// 	testTable_Lyrics.sqlType = java.sql.Types.VARCHAR;
	// 	testTable_Lyrics.dbType = "TEXT";
	// 	testTable_Lyrics.javaType = String.class;
	// 	testTable_Id.vals = new Integer[] { 1, 2 };
	// 	testTable_Lyrics.vals = new String[] { "NeverGonnaGiveYouUp", "MyHeartWillGoOn" };
	// 	testTable.cols = new DatabaseMnm.Column<?>[] { testTable_Id, testTable_Lyrics };
	// 	//
	// 	for (DatabaseMnm.Column<?> col : testTable.cols) {
	// 		System.out.println(Main.clReportHeader(null, "DEVTEST") + "Col:" + col);
	// 		System.out.println(Main.clReportHeader(null, "DEVTEST") + "Name:" + col.name);
	// 		System.out.println(Main.clReportHeader(null, "DEVTEST")
	// 				+ "sqlType (you compare to java.sql.Types manually, because it is not enum lamo):" + col.sqlType);
	// 		System.out.println(Main.clReportHeader(null, "DEVTEST") + "dbType:" + col.dbType);
	// 		System.out.println(Main.clReportHeader(null, "DEVTEST") + "JavaType:" + col.javaType);
	// 		for (Object obj : col.vals) {
	// 			System.out.println(Main.clReportHeader(null, "DEVTEST") + "> Val:" + col.javaType.cast(obj));
	// 		}
	// 	}
	// }

	// entire exception handling info: mode=no
	private static String getTableNameFromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		return metaData.getTableName(1); // Assuming the first column in the result set corresponds to a table.
	}

	private static int getColsCountFromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		return  metaData.getColumnCount();
	}

	// entire exception handling info: mode=no
	private static String getColumnNameFromResultSet(java.sql.ResultSet resultSet, int columnIndex)
			throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		return metaData.getColumnName(columnIndex);
	}

	// entire exception handling info: mode=no
	// REMARK: only determine by using of native datatype in SQL query only, do not
	// using another datatype else from {INTEGER,REAL,BLOB,TEXT,NUMERIC}
	private static Object[] getColumnDataTypeFromResultSet(java.sql.ResultSet resultSet, int columnIndex)
			throws java.sql.SQLException {
		java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
		int sqlType = metaData.getColumnType(columnIndex);
		String dbType = metaData.getColumnTypeName(columnIndex);
		Class<?> javaType;
		switch (sqlType) {
			// การแปลงเป็น javaType ผมอิงตาม ChatGPT lamo โดยถามมันว่า อิงตาม general
			// situation
			// TODO: บอกด้วยว่า db table declare แบบไหนได้อะไร แล้ว ถ้าไม่มี table declare
			// แล้ว sqltype แบบไหนจะ route ไปอันไหน
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
		return new Object[] { sqlType, dbType, javaType };
	}

	// entire exception handling info: mode=no
	// REMARK: only determine by using of native datatype in SQL query only, do not
	// using another datatype else from {INTEGER,REAL,BLOB,TEXT,NUMERIC}
	public static <T> List<T> getColumnValuesFromResultSet(ResultSet resultSet, int columnIndex, Class<T> javaType,
			Integer initRowCountForArrayList)
			throws java.sql.SQLException {
		List<T> values = null;
		if (initRowCountForArrayList == null) {
			values = new LinkedList<>();
		} else {
			values = new ArrayList<>(initRowCountForArrayList);
		}
		while (resultSet.next()) {
			values.add(getDataWithJavaTypeBasedOnJavaType(javaType,resultSet,columnIndex));
		}

		return values;
	}


	// TODO: adding remark header
	public static Table convertResultSetToTable(java.sql.ResultSet resultSet) throws java.sql.SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		Table table = new Table();
		table.name=getTableNameFromResultSet(resultSet);
		table.cols=new Column[getColsCountFromResultSet(resultSet)];
		Object[] tmp_colDataInfo=null;
		Column tmp_col=null;
		// TODO: อย่าลืมเตือนเพื่อนล่ะว่า index ต้องดูดีๆนะว่าเริ่มจาก 0/1
		for (int i = 1; i <= table.cols.length; i++) {
			tmp_colDataInfo = getColumnDataTypeFromResultSet(resultSet, i);
			table.cols[i] = new Column();
			tmp_col=table.cols[i];
			tmp_col.name= getColumnNameFromResultSet(resultSet, i);
			tmp_col.dbType = (String) tmp_colDataInfo[0];
			tmp_col.sqlType= (Integer) tmp_colDataInfo[1];
			tmp_col.javaType=(Class<?>) tmp_colDataInfo[2];
			tmp_col.vals = getColumnValuesFromResultSet(resultSet, i, tmp_col.javaType, null);
		}
		return table;
	}

	// [Zone:SubClass]

	public static class Table {
		public String name = null;
		public Column[] cols = null;
	}

	public static class Column {
		public String name = null;
		public Integer sqlType = null;
		public String dbType = null;
		public Class<?> javaType = null;
		public List<?> vals = null;
	}

}
