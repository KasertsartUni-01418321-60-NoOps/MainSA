package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.function.DoubleBinaryOperator;

import javafx.scene.chart.PieChart.Data;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation.NotNull;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation.Nullable;


// TODO: DataTransform Double to limit digit
// - min,max to possible range
// - and then convert to string and crop and toDouble
public class DatabaseMnm {
	public static java.sql.Connection mainDbConn = null;
	public final static String mainDbPath = "./data/main.db";
	public final static String setFKCheckQuery ="PRAGMA foreign_keys = ON;";

	// [Zone:Init]
	// entire exception handling info: mode=no
	// REMARK: normally, this sql statements execution, only create table, so no
	// begin/rollback required.
	public static void mainDbInit() throws java.sql.SQLException, java.io.IOException, NoSuchAlgorithmException {
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
		// TODO: + REMARK: [EASYJUSTWAITTIMELAMO] These example are not compatitble yet to data spec/valid but just pretesting lamo 
		String[] sqlStms_0 = new String[] {
				setFKCheckQuery,
				"CREATE TABLE IF NOT EXISTS Customer (Customer_Full_Name TEXT PRIMARY KEY, Customer_Address TEXT, Customer_Telephone_Number TEXT NOT NULL, Customer_Credit_Amount INTEGER NOT NULL) STRICT,WITHOUT ROWID;",
				"CREATE TABLE IF NOT EXISTS Selling_Request (Selling_Request_ID TEXT PRIMARY KEY, Customer_Full_Name TEXT NOT NULL, Selling_Request_Brand TEXT NOT NULL, Selling_Request_Model TEXT NOT NULL, Selling_Request_Product_Looks TEXT NOT NULL, Selling_Request_Meet_Date INTEGER NOT NULL, Selling_Request_Meet_Location TEXT NOT NULL, Selling_Request_Paid_Amount REAL, Selling_Request_Status INTEGER NOT NULL,  FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name))STRICT,WITHOUT ROWID;",
				"CREATE TABLE IF NOT EXISTS Product (Product_ID TEXT PRIMARY KEY, Product_Arrive_Time INTEGER NOT NULL, Product_Price REAL NOT NULL, Product_Status INTEGER NOT NULL, Selling_Request_ID TEXT NOT NULL UNIQUE, Repairment_ID TEXT  UNIQUE, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID), FOREIGN KEY (Repairment_ID) REFERENCES REPAIRMENT(Repairment_ID))STRICT,WITHOUT ROWID;",
				"CREATE TABLE IF NOT EXISTS User (User_Name TEXT PRIMARY KEY, User_Password TEXT NOT NULL, User_Role INTEGER NOT NULL)STRICT,WITHOUT ROWID;",
				"CREATE TABLE IF NOT EXISTS Repairment (Repairment_ID TEXT PRIMARY KEY, Repairment_Description TEXT NOT NULL, Repairment_Date INTEGER NOT NULL, Selling_Request_ID TEXT NOT NULL UNIQUE, FOREIGN KEY (Selling_Request_ID) REFERENCES SELLING_REQUEST(Selling_Request_ID))STRICT,WITHOUT ROWID;",
				"CREATE TABLE IF NOT EXISTS Buy_Request	 (Customer_Full_Name TEXT, Product_ID TEXT UNIQUE, Buy_Request_Created_Date INTEGER NOT NULL, Buy_Request_Transportation_Price REAL, PRIMARY KEY (Customer_Full_Name, Product_ID), FOREIGN KEY (Customer_Full_Name) REFERENCES CUSTOMER(Customer_Full_Name), FOREIGN KEY (Product_ID) REFERENCES PRODUCT(Product_ID))STRICT,WITHOUT ROWID;"
		};
		String[] sqlStms_1 = new String[] {
			"INSERT OR IGNORE INTO User (User_Name, User_Password, User_Role)"
			+"VALUES (?, ?, ?);"
		};
		String[] sqlStms_2 = new String[] {
			"INSERT OR IGNORE INTO Customer (Customer_Full_Name, Customer_Address, Customer_Telephone_Number, Customer_Credit_Amount)"
			+"VALUES (?, ?, ?, ?);"
		};
		String[] sqlStms_3 = new String[] {
			"INSERT OR IGNORE INTO Selling_Request (Selling_Request_ID, Customer_Full_Name, Selling_Request_Brand, Selling_Request_Model, Selling_Request_Product_Looks, Selling_Request_Meet_Date, Selling_Request_Meet_Location, Selling_Request_Paid_Amount, Selling_Request_Status)"
			+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
		};
		String[] sqlStms_4 = new String[] {
			"INSERT OR IGNORE INTO Repairment (Repairment_ID, Repairment_Description, Repairment_Date, Selling_Request_ID)"
			+"VALUES (?, ?, ?, ?);"
		};
		String[] sqlStms_5 = new String[] {
			"INSERT OR IGNORE INTO Product (Product_ID, Product_Arrive_Time, Product_Price, Product_Status, Selling_Request_ID, Repairment_ID)"
			+"VALUES (?, ?, ?, ?, ?, ?);"
		};
		String[] sqlStms_6 = new String[] {
			"INSERT OR IGNORE INTO Buy_Request (Customer_Full_Name, Product_ID, Buy_Request_Created_Date, Buy_Request_Transportation_Price)"
			+"VALUES (?,?,?,?);"
		};

		try {
			DatabaseMnm.mainDbConn = java.sql.DriverManager.getConnection("jdbc:sqlite:" + mainDbPath);
			DatabaseMnm.runSQLcmds(null, sqlStms_0, true, null, null);
			// PART 1:
				String SD_User_Name = "npchaonay";
				String SD_User_Password ="HFE#FJOS@J@(O@SOJS@OJ@SJL)";
				Long SD_User_Role=(long)0;
				String SD_Customer_Full_Name= "ณัฐพงศ์ พันพิพัฒน์";
				String SD_Customer_Address = "แขวงรามอินทรา เขตคันนายาว กรุงเทพมหานคร";
				String SD_Customer_Telephone_Number="+660123456789";
				Long SD_Customer_Credit_Amount =(long) 100;
				String SD_Selling_Request_ID="SR0099AZ";
				String SD_Selling_Request_Brand="สยาม";
				String SD_Selling_Request_Model="อภิมหาจักรวาลนฤมิต";
				String SD_Selling_Request_Product_Looks="สภาพเหมือนใหม่";
				Long SD_Selling_Request_Meet_Date = (long) 1635830400;
				String SD_Selling_Request_Meet_Location = "กรมทหารราบที่ 11";
				Double SD_Selling_Request_Paid_Amount=1000000.25;
				Long SD_Selling_Request_Status=(long)2;
				String SD_Repairment_ID="RP0099AZ";
				String SD_Repairment_Description="เปลี่ยนจอเว้ยยยย";
				Long SD_Repairment_Date= (long)1635830400;
				String SD_Product_ID="PD0099AZ";
				Long SD_Product_Arrive_Time = (long)1635830400;
				Double SD_Product_Price=1234567.55;
				Long SD_Product_Status = (long)1;
				Long SD_Buy_Request_Created_Date=(long)1635830400;
				Double SD_Buy_Request_Transportation_Price = null;
				DataValidation.DATAVALID_DECLINED_REASON tmpReason=null;
			// PART 2A:
				tmpReason=DataValidation.PerAttributeValidation.check__USER__User_Name(SD_User_Name);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__USER__User_Password(SD_User_Password);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__USER__User_Role(SD_User_Role);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				else {DatabaseMnm.runSQLcmds(null, sqlStms_1, true, null, new Object[][] {{SD_User_Name,Misc.passwordHash(SD_User_Password),SD_User_Role}});}
			// PART 2B:
				tmpReason=DataValidation.PerAttributeValidation.check__CUSTOMER__Customer_Full_Name(SD_Customer_Full_Name);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__CUSTOMER__Customer_Address(SD_Customer_Address);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__CUSTOMER__Customer_Credit_Amount(SD_Customer_Credit_Amount);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__CUSTOMER__Customer_Telephone_Number(SD_Customer_Telephone_Number);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				else {
					DatabaseMnm.runSQLcmds(null, sqlStms_2, true, null, new Object[][] {{SD_Customer_Full_Name,DataTransformation.NullableTransform(SD_Customer_Address,String.class),SD_Customer_Telephone_Number,SD_Customer_Credit_Amount}});
				}
			// PART 2C:
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_ID(SD_Selling_Request_ID);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Customer_Full_Name(SD_Customer_Full_Name);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Brand(SD_Selling_Request_Brand);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Model(SD_Selling_Request_Model);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Product_Looks(SD_Selling_Request_Product_Looks);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Meet_Date(SD_Selling_Request_Meet_Date);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Meet_Location(SD_Selling_Request_Meet_Location);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Paid_Amount(SD_Selling_Request_Paid_Amount);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Status(SD_Selling_Request_Status);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				else {DatabaseMnm.runSQLcmds(null, sqlStms_3, true, null, new Object[][] {{SD_Selling_Request_ID,SD_Customer_Full_Name,SD_Selling_Request_Brand,SD_Selling_Request_Model,SD_Selling_Request_Product_Looks,SD_Selling_Request_Meet_Date,SD_Selling_Request_Meet_Location,DataTransformation.doubleLengthCroppingAndNullableTransform(SD_Selling_Request_Paid_Amount,DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Paid_Amount")[0],DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Paid_Amount")[1]),SD_Selling_Request_Status}});}
			// PART 2D:
				tmpReason=DataValidation.PerAttributeValidation.check__REPAIRMENT__Repairment_ID(SD_Repairment_ID);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__REPAIRMENT__Repairment_Description(SD_Repairment_Description);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__REPAIRMENT__Repairment_Date(SD_Repairment_Date);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__REPAIRMENT__Selling_Request_ID(SD_Selling_Request_ID);
				// WTH
				if (tmpReason==null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				else {DatabaseMnm.runSQLcmds(null, sqlStms_4, true, null, new Object[][] {{SD_Repairment_ID,SD_Repairment_Description,SD_Repairment_Date,SD_Selling_Request_ID}});}
			// PART 2E:
				tmpReason=DataValidation.PerAttributeValidation.check__PRODUCT__Product_ID(SD_Product_ID);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				// WTH
				// tmpReason=DataValidation.PerAttributeValidation.check__PRODUCT__Product_Arrive_Time(SD_Product_Arrive_Time);
				// if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__PRODUCT__Product_Price(SD_Product_Price);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__PRODUCT__Product_Status(SD_Product_Status);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				// WTH
				// tmpReason=DataValidation.PerAttributeValidation.check__PRODUCT__Selling_Request_ID(SD_Selling_Request_ID);
				// if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				// tmpReason=DataValidation.PerAttributeValidation.check__PRODUCT__Repairment_ID(SD_Repairment_ID);
				// if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				if (false) {} else {DatabaseMnm.runSQLcmds(null, sqlStms_5, true, null, new Object[][] {{SD_Product_ID,SD_Product_Arrive_Time,DataTransformation.doubleLengthCropping(SD_Product_Price,DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_Price")[0],DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_Price")[1]),SD_Product_Status,SD_Selling_Request_ID,DataTransformation.NullableTransform(SD_Repairment_ID,String.class)}});}
			// PART 2F:
				tmpReason=DataValidation.PerAttributeValidation.check__BUY_REQUEST__Customer_Full_Name(SD_Customer_Full_Name);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				// WTH
				// tmpReason=DataValidation.PerAttributeValidation.check__BUY_REQUEST__Product_ID(SD_Product_ID);
				// if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__BUY_REQUEST__Buy_Request_Created_Date(SD_Buy_Request_Created_Date);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				tmpReason=DataValidation.PerAttributeValidation.check__BUY_REQUEST__Buy_Request_Transportation_Price(SD_Buy_Request_Transportation_Price);
				if (tmpReason!=null) {throw new MyExceptionHandling.UserRuntimeException("Reason:"+tmpReason.toString());}
				else {DatabaseMnm.runSQLcmds(null, sqlStms_6, true, null, new Object[][] {{SD_Customer_Full_Name,SD_Product_ID,SD_Buy_Request_Created_Date,DataTransformation.doubleLengthCroppingAndNullableTransform(SD_Buy_Request_Transportation_Price,DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Buy_Request_Transportation_Price")[0],DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Buy_Request_Transportation_Price")[1])}});}
			// [ZONE END]
		} catch (java.sql.SQLException e) {
			throw e;
		}
	}

	// [Zone:GeneralUsage]

	// entire exception handling info: mode=no
	// WARNING: this code only get 1st result of whole given statement, for
	// select-query many table, using multiple runSQLcmd or runSQLcmds (which do
	// multiple runSQLcmd, lamo) instead.
	// > so to conclude, this function suit for write operation, and runSQLcmds suit
	// for read operation, for both operation, it depends on you lamo but generally
	// runSQLcmd.
	// RETURN: Object[3] where:
	// > (1st) is type of 1st result returned (null:noReturn; false:UpdateCount;
	// true:QueryTable)
	// > (2nd) is value of result (updateCount or Table)
	// > (3rd) is return of statement
	// REMARK: if entry of params is Class<?> then consider as it is null value of
	// that class lamo
	// REMARK: if keepStatementOpen==null then close statement and do not return, if
	// ==false then same as ==null but also return, otherwise it don't be closed.
	// TODO: [EASY+LESSI+NOTREQUIREDACTUALLY] try-catch all .close() in try clause?
	public static Object[] runSQLcmd(java.sql.Connection dbConn, String sqlStm, boolean skipGetResultSet,
			Boolean keepStatementOpen, Object[] params) throws java.sql.SQLException {
		if (dbConn == null) {
			dbConn = DatabaseMnm.mainDbConn;
		}
		java.sql.PreparedStatement tmp_stm = dbConn.prepareStatement(sqlStm);
		try {
			if (params != null) {
				if (params.length > 0) {
					int tmp_c = 0;
					for (Object tmp_obj : params) {
						tmp_c++;
						Class<?> javaType = tmp_obj.getClass();
						if (tmp_obj instanceof Class<?>) {
							javaType = (Class<?>) tmp_obj;
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
								throw new MyExceptionHandling.UserRuntimeException(
										"Given javaType of parameter is not supported");
							}
						} else {
							if (javaType == Integer.class) {
								tmp_stm.setInt(tmp_c, (Integer) tmp_obj);
							} else if (javaType == Long.class) {
								tmp_stm.setLong(tmp_c, (Long) tmp_obj);
							} else if (javaType == Float.class) {
								tmp_stm.setFloat(tmp_c, (Float) tmp_obj);
							} else if (javaType == Double.class) {
								tmp_stm.setDouble(tmp_c, (Double) tmp_obj);
							} else if (javaType == String.class) {
								tmp_stm.setString(tmp_c, (String) tmp_obj);
							} else if (javaType == byte[].class) {
								tmp_stm.setBytes(tmp_c, (byte[]) tmp_obj);
							} else if (javaType == java.math.BigDecimal.class) {
								tmp_stm.setBigDecimal(tmp_c, (java.math.BigDecimal) tmp_obj);
							} else {
								// it must not reached by putting wrong type lamo, so give runtimeexception
								throw new MyExceptionHandling.UserRuntimeException(
										"Given javaType of parameter is not supported");
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
					if (keepStatementOpen == null) {
						tmp_stm.close();
						return new Object[] { true, null, null };
					} else {
						if (keepStatementOpen == false) {
							tmp_stm.close();
						}
						return new Object[] { true, null, tmp_stm };
					}
				} else {
					// in this case cannot close Statement lamo, so...
					if (keepStatementOpen == null) {
						return new Object[] { true, tmp_stm.getResultSet(), null };
					} else {
						return new Object[] { true, tmp_stm.getResultSet(), tmp_stm };
					}
				}
			}
			// else then check if it is update count
			else {
				int tmp2 = tmp_stm.getUpdateCount();
				if (tmp2 == -1) {
					if (keepStatementOpen == null) {
						tmp_stm.close();
						return new Object[] { null, null, null };
					} else {
						if (keepStatementOpen == false) {
							tmp_stm.close();
						}
						return new Object[] { null, null, tmp_stm };
					}
				} else {
					if (keepStatementOpen == null) {
						tmp_stm.close();
						return new Object[] { false, tmp2, null };
					} else {
						if (keepStatementOpen == false) {
							tmp_stm.close();
						}
						return new Object[] { false, tmp2, tmp_stm };
					}
				}
			}
		} catch (Throwable e) {
			try {
				tmp_stm.close();
			} finally {
			}
			throw e;
		}
	}

	// entire exception handling info: mode=no
	public static Object[][] runSQLcmds(java.sql.Connection dbConn, String[] sqlStms, boolean skipGetResultSet,
			Boolean keepStatementOpen, Object[][] paramsOfEachStms) throws java.sql.SQLException {
		if (dbConn == null) {
			dbConn = DatabaseMnm.mainDbConn;
		}
		Object[][] retVal = new Object[sqlStms.length][];
		if (paramsOfEachStms == null) {
			paramsOfEachStms = new Object[sqlStms.length][];
		}
		for (int i = 0; i < sqlStms.length; i++) {
			retVal[i] = runSQLcmd(dbConn, sqlStms[i], skipGetResultSet, keepStatementOpen, paramsOfEachStms[i]);
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
		T retVal = null;
		if (javaType == Integer.class) {
			retVal = (T) (Integer) resultSet.getInt(columnIndex);
		} else if (javaType == Long.class) {
			retVal = (T) (Long) resultSet.getLong(columnIndex);
		} else if (javaType == Float.class) {
			retVal = (T) (Float) resultSet.getFloat(columnIndex);
		} else if (javaType == Double.class) {
			retVal = (T) (Double) resultSet.getDouble(columnIndex);
		} else if (javaType == String.class) {
			retVal = (T) resultSet.getString(columnIndex);
		} else if (javaType == byte[].class) {
			retVal = (T) resultSet.getBytes(columnIndex);
		} else if (javaType == java.math.BigDecimal.class) {
			retVal = (T) resultSet.getBigDecimal(columnIndex);
		} else {// it must not reached, so give runtimeexception
			throw new MyExceptionHandling.UserRuntimeException("Given javaType is not supported");
		}
		if (resultSet.wasNull()) {
			return null;
		}
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
			tmpResultSet = (java.sql.ResultSet) (DatabaseMnm.runSQLcmd(null, "SELECT * FROM " + tableName, false, null,
					null)[1]);
			tmpTable = convertResultSetToTable(tmpResultSet);
			demo_printTableLAMO(tmpTable);
			tmpResultSet.close();
		}
	}

	// [Zone:SubClassAsPackage lamo]
	// REMARK APPLY THIS TO INSIDE OF THIS ZONE UNLESSS EXPLICIT OVERRIDE: entire exception handling info: mode=no

	// REMARK: only considered type of LONG/DOUBLE/STRING
	// REMARK: currently, only RANGE/LENGTH/ENUM is specified here, for another such as connectivty,nullablity,etc., implement manually in PerAttributeValidation lamo
	public static class DataSpec {
		// [Zone:Constants]
		// for attribute that have range (that "required" range checking ถ้าไม่ required
		// ก็เช่น ใช้ >0 หรือ >=0 ได้)
		// > วิธีนี้เพื่อลดการ HARDCODE LAMO
		public final static long RANGE_MIN__Customer_Credit_Amount = -500;
		public final static long RANGE_MAX__Customer_Credit_Amount = 500;
		// REMARK: สำหรับ REAL attribute, ค่าMin คือ digitCount หน้าทศนิยม และค่าMax คือ
		// digitCount หลังทศนิยม
		// REMARK: apply ทุก attribte!!
		// REMARK: ชื่อ attribute ซ้ำกันเพราะ FK ซึ่ง FK คุณสมบัติของ length
		// เหมือนกันอยู๋แล้วๆๆ
		public final static java.util.Map<String, Integer[]> MINMAX_LENGTH_OF_ATTRIBS = new java.util.HashMap<>();
		static {
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Full_Name", new Integer[] { 1, 192 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Address", new Integer[] { 1, 512 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Telephone_Number", new Integer[] { 1, 32 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Credit_Amount", new Integer[] { 1, 3 });
			MINMAX_LENGTH_OF_ATTRIBS.put("User_Name", new Integer[] { 1, 32 });
			MINMAX_LENGTH_OF_ATTRIBS.put("User_Password", new Integer[] { 1, 32 });
			MINMAX_LENGTH_OF_ATTRIBS.put("User_Role", new Integer[] { 1, 1 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_Arrive_time", new Integer[] { 10, 10 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_Price", new Integer[] { 8, 2 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_Status", new Integer[] { 1, 1 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Full_Name", new Integer[] { 1, 192 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Brand", new Integer[] { 1, 64 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Model", new Integer[] { 1, 128 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Product_Looks", new Integer[] { 1, 1024 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Meet_Date", new Integer[] { 10, 10 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Meet_Location", new Integer[] { 1, 512 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Paid_Amount", new Integer[] { 8, 2 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Request_Status", new Integer[] { 1, 1 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_Description", new Integer[] { 1, 1024 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Repairment_Date", new Integer[] { 10, 10 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Selling_Requet_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Customer_Full_Name", new Integer[] { 1, 192 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Product_ID", new Integer[] { 8, 8 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Buy_Request_Created_Date", new Integer[] { 10, 10 });
			MINMAX_LENGTH_OF_ATTRIBS.put("Buy_Request_Transportation_Price", new Integer[] { 5, 2 });
		}

		// [Zone:StatusENUM]
		public static enum STATUS_User {
			Employer,
			Employee
		}

		public static enum STATUS_Product {
			NotYetSale, ForSale, SaledAndWaitForSend, Sent
		}

		public static enum STATUS_Selling_Request {
			WaitForCheck, Declined, Acceapted
		}
	}

	// WARNING: ให้ถือว่า function ที่ไม่ได้มีหน้าที่เช็ค null/type
	// จะถือว่าข้อมูลที่รับเข้ามามีการเช็ค null/type แล้ว เช่น checkStrIsNotEmpty
	// จะไม่เช็ค String ว่า not-nullไหม หากโยน nullเข้าไปจะ error ทันที
	// REMARK: legnth คือ integer ส่วน range/ตัวค่าคือ long/double (แล้วแต่ dattype
	// ของ attrib)
	// REMARK: only considered type of LONG/DOUBLE/STRING
	// TODO: [MED] clear native function lamo
	public static class DataValidation {

		// [Zone:Annotation lamo]
		@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // You can also use RUNTIME if you
																						// want to retain annotations at
																						// runtime
		@java.lang.annotation.Target({ java.lang.annotation.ElementType.PARAMETER,
				java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD })
		public @interface NotNull {
		}

		@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // You can also use RUNTIME if you
																						// want to retain annotations at
																						// runtime
		@java.lang.annotation.Target({ java.lang.annotation.ElementType.PARAMETER,
				java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD })
		public @interface Nullable {
		}

		public static enum DATAVALID_DECLINED_REASON {
			ISNULL,
			INVALID_LENGTH,
			INVALID_RANGE,
			INVALID_FORMAT,
			REPEATED_VAL_OF_COL,
			REPEATED_VAL_OF_COL_PK,
			REPEATED_VAL_OF_COL_FK,
			VALUE_NOT_EXISTED_AT_REFERENCED_COL,
		}

		public static class JavaTypeLevel {
			// [Zone:Methods]
			public static boolean checkObjNotNull(@Nullable Object data) {
				return !(data == null);
			}

			// REMARK: ใช้ checkStrLength แทนได้ และในทางกลับกัน (กรณีจะเช็คว่า notEmpty)
			public static boolean checkStrNotEmpty(@NotNull String data) {
				return data.length() > 0;
			}

			// if max==null, then no max limit

			public static boolean checkLongDigitLength(long data, int min, @Nullable Integer max) {
				String tmp = Long.toString(data);
				return checkStrLength(tmp, min, max);
			}

			public static boolean checkStrLength(@NotNull String data, int min, @Nullable Integer max) {
				if (max == null) {
					return data.length() >= min;
				} else {
					return data.length() >= min && data.length() <= max;
				}
			}

			// REMARK: ส่วนพวก maxRear เดี่ยวค่อย rounding เอา LAMO ด้วยเหตุผลกลัวว่าความ inaccrate ของ Double จะทำให้ข้อมูลเกิด ValidError lamo
			public static boolean checkDoubleDigitLength(double data, int maxFront, int maxRear) {
				StringBuilder tmp1 = new StringBuilder();
				for (int i = 0; i < maxFront; i++) {
					tmp1.append("9");
				}
				tmp1.append(".");
				for (int i = 0; i < maxRear; i++) {
					tmp1.append("9");
				}
				String tmp_posdoublestr = tmp1.toString();
				String tmp_negdoublestr = "-"+tmp_posdoublestr;
				Double[] tmp2 = new Double[2];
				try {
					tmp2[0]=Double.parseDouble(tmp_negdoublestr);
					tmp2[1]=Double.parseDouble(tmp_posdoublestr);
					if (tmp2[0]<=data && data<=tmp2[1]) {return true;}
				} catch (RuntimeException e) {
					// in case it exceeded Double, then we sure that condition passed
					return true;
				}
				// if code reached here, mean false
				return false;
			}
			public static boolean checkLongNotNegative(long data) {return data >= 0;}
			public static boolean checkDoubleNotNegative(double data) {return data >= 0;}
			public static boolean checkLongIsPositive(long data) {return data > 0;}
			public static boolean checkDoubleIsPositive(double data) {return data > 0;}

			// if {min/max}==null, then no {coressponding: min/max} limit
			public static boolean checkLongIsInRange(long data, @Nullable Long min, @Nullable Long max) {
				boolean tmp1 = true;
				boolean tmp2 = true;
				if (min == null) {
				} else {
					tmp1 = data >= min;
				}
				if (max == null) {
				} else {
					tmp2 = data <= max;
				}
				return tmp1 && tmp2;
			}
			// if {min/max}==null, then no {coressponding: min/max} limit
			public static boolean checkDoubleIsInRange(double data, @Nullable Double min, @Nullable Double max) {
				boolean tmp1 = true;
				boolean tmp2 = true;
				if (min == null) {
				} else {
					tmp1 = data >= min;
				}
				if (max == null) {
				} else {
					tmp2 = data <= max;
				}
				return tmp1 && tmp2;
			}

			public static <T extends Enum<T>> boolean checkLongIsMatchesEnum(long value, @NotNull Class<T> enumClass) {
				for (T enumConstant : enumClass.getEnumConstants()) {
					if (enumConstant.ordinal() == value) {
						return true;
					}
				}
				return false;
			}

			

			public static boolean checkStrIsGeneralValid(@NotNull String data) {
				String regexPattern = "^[ -~\\u0E01-\\u0E39\\u0E3F-\\u0E4D\\u0E50-\\u0E59]*$";
				return java.util.regex.Pattern.compile(regexPattern).matcher(data).matches();
			}

			public static boolean checkStrIsValidUserName(@NotNull String data) {
				String regexPattern = "^[A-Za-z0-9]{1,32}$";
				return java.util.regex.Pattern.compile(regexPattern).matcher(data).matches();
			}

			public static boolean checkStrIsValidPassword(@NotNull String data) {
				String regexPattern = "^[ -~]{1,32}$";
				return java.util.regex.Pattern.compile(regexPattern).matcher(data).matches();
			}

			public static  boolean checkStrIsValidID(@NotNull String data) {
				String regexPattern = "^[A-Z0-9]{8}$";
				return java.util.regex.Pattern.compile(regexPattern).matcher(data).matches();
			}

			public static  boolean checkStrIsValidCustomerName(@NotNull String data) {
				if (!checkStrIsGeneralValid(data)) {return false;}
				return true;
			}


			public static boolean checkStrIsValidTelNum(@NotNull String data) {
				String regexPattern = "^[0-9+*#,;]+$";
				return java.util.regex.Pattern.compile(regexPattern).matcher(data).matches();
			}
		}

		public static class SQLLevel {
			// SECURITY WARNING: this function using SQL string injection, do not putting public string unless strict check
			public static boolean isThisValExisted(@NotNull Object val,@NotNull String tableName,@NotNull String colName) throws java.sql.SQLException {
				java.sql.ResultSet tmp_rs= (java.sql.ResultSet) runSQLcmd(null,
					"SELECT count("+colName+") FROM "+tableName+" WHERE "+colName+"=?",
					false,null,new Object[] {val}
				)[1];
				Table tmp_table =convertResultSetToTable(tmp_rs);
				if (tmp_table.cols[0].vals.size()>=1) {return true;}
				else {return false;}
			}
		}

		// REMARK: only Password that required DataTransform, see Misc.passwordHash() lamo, อ่อลืมบอก ฐานข้อมูลจริงๆเก็บเป็น hash แต่โค้ดของเราที่จะ validate password คือ validate ว่าเป็นรหัสยอมรับได้ก่อนนำไป hashing
		// REMARK: function here must handled NULL too
		// REMARK: function here if return as null >> valid passed
		public static class PerAttributeValidation {
			@Nullable
			public static DataValidation.DATAVALID_DECLINED_REASON check__CUSTOMER__Customer_Full_Name(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Customer_Full_Name");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidCustomerName(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Customer", "Customer_Full_Name")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable
			public static DataValidation.DATAVALID_DECLINED_REASON check__CUSTOMER__Customer_Address(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					// we accept null lamo, and no further checked
					return null;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Customer_Full_Name");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsGeneralValid(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable
			public static DataValidation.DATAVALID_DECLINED_REASON check__CUSTOMER__Customer_Telephone_Number(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Customer_Telephone_Number");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidTelNum(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__CUSTOMER__Customer_Credit_Amount(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Customer_Credit_Amount");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check if in range
					if (DataValidation.JavaTypeLevel.checkLongIsInRange(data,DataSpec.RANGE_MIN__Customer_Credit_Amount,DataSpec.RANGE_MAX__Customer_Credit_Amount)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__USER__User_Name(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("User_Name");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidUserName(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "User", "User_Name")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static  DataValidation.DATAVALID_DECLINED_REASON check__USER__User_Password(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("User_Password");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidPassword(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__USER__User_Role(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("User_Role");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsMatchesEnum(data,DataSpec.STATUS_User.class)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static  DataValidation.DATAVALID_DECLINED_REASON check__PRODUCT__Product_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Product", "Product_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__PRODUCT__Product_Arrive_Time(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_Arrive_Time");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsPositive(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__PRODUCT__Product_Price(@Nullable Double data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_Price");
					if (DataValidation.JavaTypeLevel.checkDoubleDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkDoubleIsPositive(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__PRODUCT__Product_Status(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_Status");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsMatchesEnum(data,DataSpec.STATUS_Product.class)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__PRODUCT__Selling_Request_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is FK-insertable (in aspect of existed value at referenced column)
					if (DataValidation.SQLLevel.isThisValExisted(data, "Selling_Request", "Selling_Request_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL;}
					// (PART 3) Check is FK-insertable (in aspect of UNIQUE)
					if (!DataValidation.SQLLevel.isThisValExisted(data, "Product", "Selling_Request_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_FK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__PRODUCT__Repairment_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return null;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Repairment_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is FK-insertable (in aspect of existed value at referenced column)
					if (DataValidation.SQLLevel.isThisValExisted(data, "Repairment", "Repairment_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL;}
					// (PART 3) Check is FK-insertable (in aspect of UNIQUE)
					if (!DataValidation.SQLLevel.isThisValExisted(data, "Product", "Repairment_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_FK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Selling_Request", "Selling_Request_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Customer_Full_Name(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Customer_Full_Name");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidCustomerName(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is FK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Customer", "Customer_Full_Name")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL;}
					// the code should  reached here means it (data) is passed
					return null;
				}	
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Brand(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Brand");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsGeneralValid(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Model(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Model");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsGeneralValid(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Product_Looks(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Product_Looks");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsGeneralValid(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static  DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Meet_Date(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Meet_Date");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsPositive(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Meet_Location(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Meet_Location");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsGeneralValid(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Paid_Amount(@Nullable Double data) {
				// (PART 0): check if it is null
				if (data==null) {
					return null;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Paid_Amount");
					if (DataValidation.JavaTypeLevel.checkDoubleDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkDoubleIsPositive(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__SELLING_REQUEST__Selling_Request_Status(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Status");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsMatchesEnum(data,DataSpec.STATUS_Selling_Request.class)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__REPAIRMENT__Repairment_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Repairment_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Repairment", "Repairment_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static  DataValidation.DATAVALID_DECLINED_REASON check__REPAIRMENT__Repairment_Description(@Nullable String data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Repairment_Description");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsGeneralValid(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static  DataValidation.DATAVALID_DECLINED_REASON check__REPAIRMENT__Repairment_Date(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Repairment_Date");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsPositive(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__REPAIRMENT__Selling_Request_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is FK-insertable (in aspect of existed value at referenced column)
					if (DataValidation.SQLLevel.isThisValExisted(data, "Selling_Request", "Selling_Request_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL;}
					// (PART 3) Check is FK-insertable (in aspect of UNIQUE)
					if (!DataValidation.SQLLevel.isThisValExisted(data, "Repairment", "Selling_Request_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_FK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			// REMARK: ดูด้านล่างๆ
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__BUY_REQUEST__Customer_Full_Name(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Customer_Full_Name");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidCustomerName(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Customer", "Customer_Full_Name")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			// REMARK: ผมวิเคราะห์มาแล้ว การเช็คนี้จะเช็ค Unique of PKแบบควบ ด้วย เพราะเรื่อง UNIQUE มันโดนเช็คจาก  UNIQUE of attribute ของ Buy_Request.Product_ID แล้วๆ
			// > ฉะนั้นหากเช็คอันนี้ผ่าน คือ PK unique ผ่านๆ 
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__BUY_REQUEST__Product_ID(@Nullable String data) throws SQLException {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Product_ID");
					if (DataValidation.JavaTypeLevel.checkStrLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check string conditions
					if (DataValidation.JavaTypeLevel.checkStrIsValidID(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_FORMAT;}
					// (PART 3) Check is PK-insertable
					if (DataValidation.SQLLevel.isThisValExisted(data, "Product", "Product_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL;}
					// (PART 4) Check is FK-insertable
					if (!DataValidation.SQLLevel.isThisValExisted(data, "Buy_Request", "Product_ID")) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_FK;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__BUY_REQUEST__Buy_Request_Created_Date(@Nullable Long data) {
				// (PART 0): check if it is null
				if (data==null) {
					return DataValidation.DATAVALID_DECLINED_REASON.ISNULL;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Buy_Request_Created_Date");
					if (DataValidation.JavaTypeLevel.checkLongDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkLongIsPositive(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			@Nullable public static DataValidation.DATAVALID_DECLINED_REASON check__BUY_REQUEST__Buy_Request_Transportation_Price(@Nullable Double data) {
				// (PART 0): check if it is null
				if (data==null) {
					return null;
				} else {
					// (PART 1): Check length
					Integer[] lenSpec=DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Buy_Request_Transportation_Price");
					if (DataValidation.JavaTypeLevel.checkDoubleDigitLength(data, lenSpec[0],lenSpec[1])) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_LENGTH;}
					// (PART 2): Check long conditions
					if (DataValidation.JavaTypeLevel.checkDoubleNotNegative(data)) {}
					else {return DataValidation.DATAVALID_DECLINED_REASON.INVALID_RANGE;}
					// the code should  reached here means it (data) is passed
					return null;
				}
			}
			
		}
	}

	public static class DataTransformation {
		@NotNull
		public static <T> Object NullableTransform(@Nullable T data, @NotNull Class<T> dataClass) {
			if (data!=null) {return data;}
			else {return dataClass;}
		}
		public static double doubleLengthCropping(double data, int maxFront, int maxRear) {
			// TODO:
			return data;
		}
		@NotNull
		public static Object doubleLengthCroppingAndNullableTransform(@Nullable Double data, int maxFront, int maxRear) {
			if (data!=null) {return doubleLengthCropping(data,maxFront,maxRear);}
			else {return Double.class;}
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
