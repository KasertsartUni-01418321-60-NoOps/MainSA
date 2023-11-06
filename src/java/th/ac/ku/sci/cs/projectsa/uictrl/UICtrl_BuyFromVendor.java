package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataTransformation;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UICtrl_BuyFromVendor {
    @FXML private TextField textField_custName;
    @FXML private TextField textField_brand;
    @FXML private TextField textField_model;
    @FXML private TextArea textArea_MeetLoc;
    @FXML private TextArea textArea_PdLooks;
    @FXML private DatePicker datePicker_MeetDate;

    @FXML private void initialize() {
        try{
            datePicker_MeetDate.setValue(java.time.LocalDate.now().plusDays(1));
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onpressed_Button_Save() throws java.io.IOException,java.sql.SQLException {
        try {
            String formval_custName = textField_custName.getText();
            String formval_brand = textField_brand.getText();
            String formval_model = textField_model.getText();
            Long formval_meetDate= datePicker_MeetDate.getValue().toEpochDay()*86400+43200-1;
            String formval_custLoc = textArea_MeetLoc.getText();
            String formval_productLooks = textArea_PdLooks.getText();
            // [VALIDZONE]
            DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Customer_Full_Name(formval_custName);
            if (tmpReason ==DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL) {
                helper2();
                return;
            } else if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Brand(formval_brand);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Model(formval_model);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Meet_Date(formval_meetDate);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Meet_Location(formval_custLoc);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Product_Looks(formval_productLooks);
            if (tmpReason != null) {
                helper1();
                return;
            }
            // [END VALID ZONE]
            String tmpt_str = null;
            while (true) {
                tmpt_str= Misc.generateRandomID();
                tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_ID(tmpt_str);
                if (tmpReason==DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK) {
                    continue;
                } else if (tmpReason!=null) {
                    throw new MyExceptionHandling.UserRuntimeException("Code should not be unreachable here.");
                } else { break;}
            }
            try{
                DatabaseMnm.runSQLcmd(
                    null,
                    "INSERT INTO Selling_Request VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    true,
                    false,
                    null,
                    new Object[] {
                        tmpt_str,
                        formval_custName,
                        formval_brand,
                        formval_model,
                        formval_productLooks,
                        formval_meetDate,
                        formval_custLoc,
                        Double.class,
                        (long)0
                    }
                );
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            };
            Main.switchToSpecificPagename("buy_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    private void helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
						"ไม่สามารถเพ่ิมข้อมูลลูกค้าได้ เนื่องจากกรอกข้อมูลผิดรูปแบบ", null, false);
    }
    private void helper2() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
						"ไม่สามารถเพ่ิมข้อมูลลูกค้าได้ เนื่องจากชื่อลูกค้าที่กรอกไม่มีอยู่ โปรดตรวจสอบชื่อฯให้ถูกต้องทุกตัวอักษร", null, false);
    }
}

