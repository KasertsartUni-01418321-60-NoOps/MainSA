package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataTransformation;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UICtrl_CreateCustomer {
    @FXML
	private TextField textField_customerName,textField_customerPhone;
    @FXML
    private TextArea textField_customerLocation;

    @FXML 
    private void onPressed_Button_CreateCustomer() throws java.io.IOException, java.sql.SQLException {
		try {
			String formval_customerName = textField_customerName.getText();
			String formval_customerPhone = textField_customerPhone.getText();
            String formval_customerLocation = textField_customerLocation.getText();
            // [VALIDZONE]
            DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation
                    .check__CUSTOMER__Customer_Full_Name(formval_customerName);
            if  (tmpReason == DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK) {
                onPressed_Button_CreateCustomer__Helper2();
				return;
            }
            else if (tmpReason != null) {
                onPressed_Button_CreateCustomer__Helper1();
				return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__CUSTOMER__Customer_Address(formval_customerLocation);
            if (tmpReason != null) {
                onPressed_Button_CreateCustomer__Helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__CUSTOMER__Customer_Telephone_Number(formval_customerPhone);
            if (tmpReason != null) {
                onPressed_Button_CreateCustomer__Helper1();
                return;
            // [END VALIDZONE]
            } else {
                try{
                    DatabaseMnm.runSQLcmd(
                        null,
                        "INSERT INTO Customer (Customer_Full_Name, Customer_Address, Customer_Telephone_Number, Customer_Credit_Amount) VALUES (?, ?, ?, ?);",
                        true,
                        false,
                        null,
                        new Object[] {
                            formval_customerName,
                            DataTransformation.NullableTransform(formval_customerLocation, String.class),
                            formval_customerPhone,
                            (long)0
                        }
                    );
                } catch (java.sql.SQLException e) {
                    MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                    "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                    throw e;
                };
            }
            Main.switchToSpecificPagename("customer_list");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    private void onPressed_Button_CreateCustomer__Helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
						"ไม่สามารถเพ่ิมข้อมูลลูกค้าได้ เนื่องจากกรอกข้อมูลลูกค้าผิดรูปแบบ", null, false);
    }
// เหมือนแบบบน แต่แจ้งว่า existed Customer LAMO
 private void onPressed_Button_CreateCustomer__Helper2() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
						"ไม่สามารถเพ่ิมข้อมูลลูกค้าได้ เนื่องจากมีชื่อลูกค้านี้อยู่แล้ว", null, false);
    }

	@FXML 
    private void onPress_Back_To_CustomerList() throws java.io.IOException {
		try{
			Main.switchToSpecificPagename("customer_list");
        } catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
}