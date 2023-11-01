package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;

import java.io.NotActiveException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import th.ac.ku.sci.cs.projectsa.*;

public class DatabaseMnm {
	public static java.sql.Connection mainDbConn = null;
	public final static String mainDbPath = "./data/main.db";


	// [Zone:Init]
	// TODO: from above เราจะออกแบบใหม่ คือ
	// >  ให้ runSQLcmd/runSQLcmds สามารถป้อน parameter ของ preparedStatement ได้
	// entire exception handling info: mode=no
	// REMARK: normally, this sql statements execution, only create table, so no begin/rollback required.
	public static void mainDbInit() throws java.sql.SQLException, java.io.IOException {
		java.nio.file.Path tmp_Path = java.nio.file.Paths.get("./data");
		if (!java.nio.file.Files.exists(tmp_Path)) {
			try {
				java.nio.file.Files.createDirectory(tmp_Path);
			} catch (java.io.IOException e) {
				throw e;
			}
		}
		// REMARK: for my group, only use {TEXT,BLOB,REAL,INTEGER} maybe we not using
		// "NUMERIC"
		// TODO: เขียนใหม่ LAMO (ทำหลังจากพวก valid/transform lamo)
		String[] sqlStms = new String[] {
			"CREATE TABLE IF NOT EXISTS CUSTOMER (Customer_Full_Name TEXT PRIMARY KEY, Customer_Shipping_Address TEXT, Customer_Telephone_Number TEXT, Customer_Credit_Amount INTEGER) STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS SELLING_REQUEST (Selling_Request_ID INTEGER PRIMARY KEY, Customer_Full_Name TEXT, Selling_Request_Product_Looks TEXT, Selling_Request_Meet_Date INTEGER, Selling_Request_Paid_Amount REAL, Selling_Request_Meet_Location TEXT, Selling_Request_Status TEXT, Selling_Request_Model TEXT, Selling_Request_Brand TEXT, FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name))STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS PRODUCT (Product_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_ID INTEGER, Product_Price REAL, Product_Arrive_Date INTEGER, Product_Status TEXT, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID), FOREIGN KEY (Repairment_ID) REFERENCES REPAIRMENT(Repairment_ID))STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS USER (Username TEXT PRIMARY KEY, Password TEXT, Role INTEGER)STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS REPAIRMENT (Repairment_ID INTEGER PRIMARY KEY, Selling_Request_ID INTEGER, Repairment_Description TEXT, Repairment_Date INTEGER, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID))STRICT,WITHOUT ROWID;",
			"CREATE TABLE IF NOT EXISTS BUY_REQUEST (Customer_Full_Name TEXT, Product_ID INTEGER, Buy_Request_Created_Date INTEGER, Buy_Request_Transportation_Price REAL, PRIMARY KEY (Customer_Full_Name, Product_ID), FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name), FOREIGN KEY (Product_ID) REFERENCES PRODUCT(Product_ID))STRICT,WITHOUT ROWID;",
			"INSERT INTO CUSTOMER SELECT 'John Doe', '123 Main St', '555-123-4567', 1000 " +
			"WHERE NOT EXISTS (SELECT 1 FROM CUSTOMER WHERE Customer_Full_Name = 'John Doe');",
			"INSERT INTO CUSTOMER SELECT 'Jane Smith', '456 Elm St', '555-987-6543', 1500 " +
			"WHERE NOT EXISTS (SELECT 1 FROM CUSTOMER WHERE Customer_Full_Name = 'Jane Smith');",
			"INSERT INTO SELLING_REQUEST SELECT 1, 'John Doe', 'Sample Product Looks', 1634196000, 500.00, 'Sample Location', 'Pending', 'Sample Model', 'Sample Brand' " +
			"WHERE NOT EXISTS (SELECT 1 FROM SELLING_REQUEST WHERE Selling_Request_ID = 1);",
			"INSERT INTO SELLING_REQUEST SELECT 2, 'Jane Smith', 'Another Product', 1634197000, 600.00, 'Another Location', 'In Progress', 'Another Model', 'Another Brand' " +
			"WHERE NOT EXISTS (SELECT 1 FROM SELLING_REQUEST WHERE Selling_Request_ID = 2);",
			"INSERT INTO PRODUCT SELECT 1, 1, NULL, 250.00, 1634196000, 'Available' " +
			"WHERE NOT EXISTS (SELECT 1 FROM PRODUCT WHERE Product_ID = 1);",
			"INSERT INTO PRODUCT SELECT 2, 2, NULL, 300.00, 1634197000, 'In Stock' " +
			"WHERE NOT EXISTS (SELECT 1 FROM PRODUCT WHERE Product_ID = 2);",
			"INSERT INTO USER SELECT 'user1', 'password1', 1 " +
			"WHERE NOT EXISTS (SELECT 1 FROM USER WHERE Username = 'user1');",
			"INSERT INTO USER SELECT 'user2', 'password2', 2 " +
			"WHERE NOT EXISTS (SELECT 1 FROM USER WHERE Username = 'user2');",
			"INSERT INTO REPAIRMENT SELECT 1, 1, 'Sample Repairment Description', 1634196000 " +
			"WHERE NOT EXISTS (SELECT 1 FROM REPAIRMENT WHERE Repairment_ID = 1);",
			"INSERT INTO REPAIRMENT SELECT 2, 2, 'Another Repairment', 1634197000 " +
			"WHERE NOT EXISTS (SELECT 1 FROM REPAIRMENT WHERE Repairment_ID = 2);",
			"INSERT INTO BUY_REQUEST SELECT 'John Doe', 1, 1634196000, 50.00 " +
			"WHERE NOT EXISTS (SELECT 1 FROM BUY_REQUEST WHERE Customer_Full_Name = 'John Doe' AND Product_ID = 1);",
			"INSERT INTO BUY_REQUEST SELECT 'Jane Smith', 2, NULL, 60.00 " +
			"WHERE NOT EXISTS (SELECT 1 FROM BUY_REQUEST WHERE Customer_Full_Name = 'Jane Smith' AND Product_ID = 2);"
		};
		try {
			DatabaseMnm.mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:"+mainDbPath);
			DatabaseMnm.runSQLcmds(null, sqlStms, true,null,null);
		} catch (java.sql.SQLException e) {
			throw e;
		}
	}

	// [Zone:GeneralUsage]


	// entire exception handling info: mode=no
	// WARNING: this code only get 1st result of whole given statement, for select-query many table, using multiple runSQLcmd or runSQLcmds (which do multiple runSQLcmd, lamo) instead.
	// > so to conclude, this function suit for write operation, and runSQLcmds suit for read operation, for both operation, it depends on you lamo but generally runSQLcmd.
	// RETURN: Object[3] where:
	// > (1st) is type of 1st result returned (null:noReturn; false:UpdateCount; true:QueryTable)
	// > (2nd) is value of result (updateCount or Table)
	// > (3rd) is return of statement
	// REMARK: if entry of params is Class<?> then consider as it is null value of that class lamo
	// REMARK: if keepStatementOpen==null then close statement and do not return, if ==false then same as ==null but also return, otherwise it don't be closed.
	// TODO: try-catch some .close() ?
	public static Object[] runSQLcmd(java.sql.Connection dbConn, String sqlStm, boolean skipGetResultSet, Boolean keepStatementOpen, Object[] params) throws java.sql.SQLException {
		if (dbConn == null) {
			dbConn = DatabaseMnm.mainDbConn;
		}
		java.sql.PreparedStatement tmp_stm = dbConn.prepareStatement(sqlStm);
		try {
			if (params!=null) {
				if (params.length>0) {
					int tmp_c=0;
					for (Object tmp_obj : params) {
						tmp_c++;
						Class<?> javaType = tmp_obj.getClass();
						if (tmp_obj instanceof Class<?>) {
							javaType=(Class<?>) tmp_obj;
							// using java.sql.Types according to getColumnDataTypeFromResultSet
							if (javaType == Integer.class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.INTEGER);
							} else if (javaType == Long.class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.BIGINT);
							} else if (javaType == Float.class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.REAL);
							} else if (javaType == Double.class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.FLOAT);
							} else if (javaType == String.class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.VARCHAR);
							} else if (javaType == byte[].class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.BLOB);
							} else if (javaType == java.math.BigDecimal.class) {
								tmp_stm.setNull(tmp_c, java.sql.Types.NUMERIC);
							} else {
								// it must not reached by putting wrong type lamo, so give runtimeexception
								throw new MyExceptionHandling.UserRuntimeException("Given javaType of parameter is not supported");
							}
						} else {
							if (javaType == Integer.class) {
								tmp_stm.setInt(tmp_c,(Integer)tmp_obj);
							} else if (javaType == Long.class) {
								tmp_stm.setLong(tmp_c,(Long)tmp_obj);
							} else if (javaType == Float.class) {
								tmp_stm.setFloat(tmp_c,(Float)tmp_obj);
							} else if (javaType == Double.class) {
								tmp_stm.setDouble(tmp_c,(Double)tmp_obj);
							} else if (javaType == String.class) {
								tmp_stm.setString(tmp_c,(String)tmp_obj);
							} else if (javaType == byte[].class) {
								tmp_stm.setBytes(tmp_c,(byte[])tmp_obj);
							} else if (javaType == java.math.BigDecimal.class) {
								tmp_stm.setBigDecimal(tmp_c,(java.math.BigDecimal)tmp_obj);
							} else {
								// it must not reached by putting wrong type lamo, so give runtimeexception
								throw new MyExceptionHandling.UserRuntimeException("Given javaType of parameter is not supported");
							}
						}
					}
				}
			}
			boolean tmp1 = false;
			try {
				tmp1 = tmp_stm.execute();
			} catch (java.sql.SQLException e1) {
				throw e1;
			}
			// put resultset
			if (tmp1) {
				if (skipGetResultSet) {
					if (keepStatementOpen==null) { tmp_stm.close();return new Object[] { true, null ,null }; }
					else {
						if (keepStatementOpen==false) { tmp_stm.close(); }
						return new Object[] { true, null , tmp_stm };
					}
				} else {
					// in this case cannot close Statement lamo, so...
					if (keepStatementOpen==null) { return new Object[] { true, tmp_stm.getResultSet() ,null}; }
					else { return new Object[] { true, tmp_stm.getResultSet() ,tmp_stm}; }
				}
			}
			// else then check if it is update count
			else {
				int tmp2 = tmp_stm.getUpdateCount();
				if (tmp2 == -1) {
					if (keepStatementOpen==null) { tmp_stm.close();return new Object[] { null, null ,null }; }
					else {
						if (keepStatementOpen==false) { tmp_stm.close(); }
						return new Object[] { null, null , tmp_stm };
					}
				} else {
					if (keepStatementOpen==null) { tmp_stm.close();return new Object[] { false,tmp2 ,null }; }
					else {
						if (keepStatementOpen==false) { tmp_stm.close(); }
						return new Object[] { false,tmp2 , tmp_stm };
					}
				}
			}
		} catch (Throwable e) {
			try{ tmp_stm.close(); } finally {}
			throw e;
		}
	}

	// entire exception handling info: mode=no
	public static Object[][] runSQLcmds(java.sql.Connection dbConn, String[] sqlStms, boolean skipGetResultSet,Boolean  keepStatementOpen, Object[][] paramsOfEachStms) throws java.sql.SQLException{
		if (dbConn == null) {
			dbConn = DatabaseMnm.mainDbConn;
		}
		Object[][] retVal = new Object[sqlStms.length][];
		if (paramsOfEachStms==null) {paramsOfEachStms=new Object[sqlStms.length][]; }
		for (int i = 0; i < sqlStms.length; i++) {
			retVal[i] = runSQLcmd(dbConn, sqlStms[i], skipGetResultSet,keepStatementOpen,paramsOfEachStms[i]);
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
	// RETURN: see in code, it is not more difficult to interpret
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
			tmpResultSet = (java.sql.ResultSet) (DatabaseMnm.runSQLcmd(null, "SELECT * FROM " + tableName, false,null,null)[1]);
			tmpTable = convertResultSetToTable(tmpResultSet);
			demo_printTableLAMO(tmpTable);
			tmpResultSet.close();
		}
	}

	// [Zone:SubClassAsPackage lamo]

	public static class DataSpec {
		// [Zone:Constants]
		// for attribute that have range (that "required" range checking ถ้าไม่ required ก็เช่น ใช้ >0 หรือ >=0 ได้)
		// > วิธีนี้เพื่อลดการ HARDCODE LAMO 
		public final static long RANGE_MIN__Customer_Credit_Amount = -500;
		public final static long RANGE_MAX__Customer_Credit_Amount = 500;
		// REMARK: สำหรับ REAL attribute, ค่าMin คือ digitCount หน้าทศนิยม และค่าMax คือ digitCount หลังทศนิยม 
		// REMARK: apply ทุก attribte!!
		// REMARK: ชื่อ attribute ซ้ำกันเพราะ FK ซึ่ง FK คุณสมบัติของ length เหมือนกันอยู๋แล้วๆๆ
		public final static Map<String,Integer[]> MINMAX_LENGTH_OF_ATTRIBS = new HashMap<>();
		static {
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Full_Name", new Integer[] {1,192});
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Address", new Integer[] {1,512});
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Telephone_Number", new Integer[] {1,32});
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Credit_Amount", new Integer[] {1,3});
			MINMAX_LENGTH_OF_ATTRIBS.put("User_Name", new Integer[] {1,32});
			MINMAX_LENGTH_OF_ATTRIBS.put("User_Password", new Integer[] {1,32});
			MINMAX_LENGTH_OF_ATTRIBS.put("User_Role", new Integer[] {1,1});
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_Arrive_time", new Integer[] {10,10});
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_Price", new Integer[] {8,2});
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_Status", new Integer[] {1,1});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Full_Name", new Integer[] {1,192});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Brand", new Integer[] {1,64});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Model", new Integer[] {1,128});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Product_Looks", new Integer[] {1,1024});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Meet_Date", new Integer[] {10,10});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Meet_Location", new Integer[] {1,512});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Paid_Amount", new Integer[] {8,2});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Status", new Integer[] {1,1});
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_Description", new Integer[] {1,1024});
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_Date", new Integer[] {10,10});
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Requet_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Full_Name", new Integer[] {1,192});
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_ID", new Integer[] {8,8});
			MINMAX_LENGTH_OF_ATTRIBS.put("Buy_Request_Created_Date", new Integer[] {10,10});
			MINMAX_LENGTH_OF_ATTRIBS.put("Buy_Request_Transportation_Price", new Integer[] {5,2});
		}

		// [Zone:StatusENUM]
		public static enum STATUS_User {
			Employer,
			Employee
		}
		public static enum STATUS_Product {
			NotYetSale,ForSale,SaledAndWaitForSend,Sent
		}
		public static enum STATUS_Selling_Request {
			WaitForCheck,Declined,Acceapted
		}
	}

	// TODO: HighPriority 2 - (valid) และสุดท้าย function รวบยอดไปเลย คือป้อน raw UI value ของแต่ละ attribute แล้ว return ไว้ valid ไหม
	// TODO: HighPriority 3 - (transform) แปลงข้อมูลกลับมาเป็น string แต่สำหรับ รัน SQL
	// TODO: HighPriority 4 - (valid) check FK lamo check PK lamo using isExistedInTable
	// > ส่วนพวก conditional ใน logic ส่วนอื่น ก็เดี่ยวเขียนแยกๆ  
	// WARNING: ให้ถือว่า function ที่ไม่ได้มีหน้าที่เช็ค null/type จะถือว่าข้อมูลที่รับเข้ามามีการเช็ค null/type แล้ว เช่น checkStrIsNotEmpty จะไม่เช็ค String ว่า not-nullไหม หากโยน nullเข้าไปจะ error ทันที
	// REMARK: legnth คือ integer ส่วน range/ตัวค่าคือ long/double (แล้วแต่ dattype ของ attrib)
	public static class DataValidation {

		// [Zone:Annotation lamo]
		@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // You can also use RUNTIME if you want to retain annotations at runtime
		@java.lang.annotation.Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
		public @interface NotNull {
		}
		@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // You can also use RUNTIME if you want to retain annotations at runtime
		@java.lang.annotation.Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
		public @interface Nullable {
		}
		// REMARK: seem that we don't need this yet
		public static class UIDataLevel {}
		public static class JavaTypeLevel {
			// [Zone:SubclassAsPkg]
			public static class PerAttributeValidation {}
			// [Zone:Methods]
			public static boolean checkObjNotNull(@Nullable Object data) {
				return !(data==null);
			}
			// REMARK: ใช้ checkStrLength แทนได้ และในทางกลับกัน (กรณีจะเช็คว่า notEmpty)
			public static boolean  checkStrNotEmpty(@NotNull String data) {
				return data.length()>0;
			}
			// if max==null, then no max limit
			// TODO: need defination
			public static native boolean checkLongDigitLength(long data, int min, @Nullable Integer max);
			public static boolean checkStrLength(@NotNull String data, int min, @Nullable Integer max) {
				if (max==null) {
					return data.length()>=min;
				} else {
					return data.length()>=min && data.length()<=max;
				}
			}
			// TODO:need defination
			public static native boolean checkDoubleDigitLength(double data, int maxFront, int maxRear);
			// TODO:how about double?
			public static boolean checkLongNotNegative(long data) {
				return data>=0;
			}
			// TODO:how about double?
			public static boolean checkLongIsPositive(long data) {
				return data>0;
			}
			// TODO:how about double?
			// if {min/max}==null, then no {coressponding: min/max} limit
			public static boolean checkLongIsInRange(long data, @Nullable Integer min, @Nullable Integer max) {
				boolean tmp1=true;
				boolean tmp2=true;
				if (min==null) {} else {tmp1=data>=min;}
				if (max==null) {} else {tmp2=data<=max;}
				return tmp1&&tmp2;
			}
			public static <T extends Enum<T>> boolean checkLongIsMatchesEnum(long value, @NotNull Class<T> enumClass) {
				for (T enumConstant : enumClass.getEnumConstants()) {
					if (enumConstant.ordinal() == value) {
						return true;
					}
				}
				return false;
			}
			// TODO:need defination
			public static native boolean checkStrIsGeneralValid(@NotNull String data);
			// TODO:need defination
			public static native boolean checkStrIsValidUserName(@NotNull String data);
			// TODO:need defination
			public static native boolean checkStrIsValidPassword(@NotNull String data);
			// TODO:need defination
			public static native boolean checkStrIsValidID(@NotNull String data);
			// TODO:need defination
			public static native boolean checkStrIsValidCustomerName(@NotNull String data);
			// TODO:need defination
			public static native boolean checkStrIsValidTelNum(@NotNull String data);
		}
		public static class SQLQueryLevel {}
	}

	public static class DataTransform {
		// REMARK: seem that we don't need this yet
		public static class UIDataToJavaType {}
		public static class JavaTypeToSQLQuery {}
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
