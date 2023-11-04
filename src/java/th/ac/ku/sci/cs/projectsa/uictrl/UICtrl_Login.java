package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UICtrl_Login {
	// entire exception handling info: mode=fatal
	@FXML
	private TextField textField_userName;
	@FXML
	private PasswordField passwordField_passWord;

	public void funcTestOFCaughtException(String[] args) {
	}

	@FXML
	private void onPressed_Button_Login()
			throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
		try {
			String formval_userName = textField_userName.getText();
			String formval_passWord = passwordField_passWord.getText();
			DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason = DatabaseMnm.DataValidation.PerAttributeValidation
					.check__USER__User_Name(formval_userName);
			if (tmpReason == DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK) {
			} else if (tmpReason == null) {
				Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเข้าสู่ระบบผิดพลาด",
						"ไม่สามารถเข้าสู่ระบบได้ เนื่องจากกรอกข้อมูลผิด", "", false);
				return;
			} else {
				Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเข้าสู่ระบบผิดพลาด",
						"ไม่สามารถเข้าสู่ระบบได้ เนื่องจากกรอกข้อมูลชื่อผู้ใช้ผิดรูปแบบ", "", false);
				return;
			}
			tmpReason = DatabaseMnm.DataValidation.PerAttributeValidation.check__USER__User_Password(formval_passWord);
			if (tmpReason == null) {
			} else {
				Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเข้าสู่ระบบผิดพลาด",
						"ไม่สามารถเข้าสู่ระบบได้ เนื่องจากกรอกรหัสผ่านผิดรูปแบบ", "", false);
				return;
			}
			DatabaseMnm.Table tmpc_SQLTable = null;
			try {
				tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
						null,
						"SELECT User_Name,User_Role FROM User WHERE User_Name=? AND User_Password=?",
						false,
						true,
						null,
						new Object[] {
								formval_userName,
								Misc.passwordHash(formval_passWord)
						})[1]);
			} catch (java.security.NoSuchAlgorithmException e) {
				throw e;
			} catch (java.sql.SQLException e) {
				MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
						"<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
				throw e;
			}
			int tmpt_int = tmpc_SQLTable.cols[0].vals.size();
			if (tmpt_int > 0) {
				try {
					Main.switchToSpecificPagename("homepage");
				} catch (java.io.IOException e1) {
					throw e1;
				}
			} else {
				Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเข้าสู่ระบบผิดพลาด",
						"ไม่สามารถเข้าสู่ระบบได้ เนื่องจากกรอกข้อมูลผิด", "", false);
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
}