package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UICtrl_BuyFromVendor {
    @FXML
    private ComboBox<ListViewRowDataWrapper<String>> comboBox_custName;
    @FXML
    private TextField textField_brand;
    @FXML
    private TextField textField_model;
    @FXML
    private TextArea textArea_MeetLoc;
    @FXML
    private TextArea textArea_PdLooks;
    @FXML
    private DatePicker datePicker_MeetDate;
    @FXML
    private Button button_defaultLoc;
    private String custLoc = null;

    @FXML
    private void initialize() throws java.sql.SQLException {
        try {
            comboBox_custName_Helper1();
            datePicker_MeetDate.setValue(java.time.LocalDate.now().plusDays(1));
            comboBox_custName.setOnAction(event -> {
                try {
                    DatabaseMnm.Table tmpc_SQLTable = null;
                    try {
                        tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                                null,
                                "SELECT Customer_Address FROM Customer WHERE Customer_Full_Name=?",
                                false,
                                true,
                                null,
                                new Object[] {
                                        comboBox_custName.getValue().ref
                                })[1]);
                    } catch (java.sql.SQLException e) {
                        MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                                "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                        throw e;
                    }
                    custLoc = (String) (tmpc_SQLTable.cols[0].vals.get(0));
                    if (custLoc == null) {
                        button_defaultLoc.setDisable(true);
                    } else {
                        button_defaultLoc.setDisable(false);
                    }
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("buy_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onpressed_Button_Save() throws java.io.IOException, java.sql.SQLException {
        try {
            // Note that this always got from DB, so no validation
            String formval_custName = null;
            try {
                formval_custName = comboBox_custName.getSelectionModel().getSelectedItem().ref;
            } catch (NullPointerException e) {
                helper2();
                return;
            }
            String formval_brand = textField_brand.getText();
            String formval_model = textField_model.getText();
            Long formval_meetDate = datePicker_MeetDate.getValue().toEpochDay();
            String formval_custLoc = textArea_MeetLoc.getText();
            String formval_productLooks = textArea_PdLooks.getText();
            // [VALIDZONE]
            DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            // DISABLED: due to above reason
            // DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            // tmpReason =
            // DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Customer_Full_Name(formval_custName);
            // if (tmpReason
            // ==DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON.VALUE_NOT_EXISTED_AT_REFERENCED_COL)
            // {
            // helper2();
            // return;
            // } else if (tmpReason != null) {
            // helper1();
            // return;
            // }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Brand(formval_brand);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Model(formval_model);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Meet_Date(formval_meetDate);
            if (tmpReason != null) {
                helper1();
                return;
            }
            if (!DatabaseMnm.DataValidation.ForMoreBussinessConstraint
                    .checkDateAsEpochTimeIsNotPast(formval_meetDate)) {
                helper3();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Meet_Location(formval_custLoc);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Product_Looks(formval_productLooks);
            if (tmpReason != null) {
                helper1();
                return;
            }
            // [END VALID ZONE]
            String tmpt_str = null;
            while (true) {
                tmpt_str = Misc.generateRandomID();
                tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_ID(tmpt_str);
                if (tmpReason == DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON.REPEATED_VAL_OF_COL_PK) {
                    continue;
                } else if (tmpReason != null) {
                    throw new MyExceptionHandling.UserRuntimeException("Code should not be unreachable here.");
                } else {
                    break;
                }
            }
            try {
                DatabaseMnm.runSQLcmd(
                        null,
                        "INSERT INTO Selling_Request VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?);",
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
                                (long) 0,
                                String.class
                        });
                DatabaseMnm.mainDbConn.commit();
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            ;
            Main.switchToSpecificPagename("buy_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    private void helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสัญญาชื้อได้", "เนื่องจากกรอกข้อมูลผิดรูปแบบ", false);
    }

    // REMARK: เผื่อกรณี NULL ของ ComboBox
    private void helper2() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสัญญาชื้อได้", "เนื่องจากชื่อลูกค้ายังไม่ได้ถูกเลือก", false);
    }

    private void helper3() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสัญญาชื้อได้", "เนื่องจากวันที่ไม่ใช่วันนี้หรือเวลาในอนาคต", false);
    }

    private void comboBox_custName_Helper1() throws java.sql.SQLException {
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT Customer_Full_Name FROM Customer ORDER BY Customer_Full_Name ASC",
                    false,
                    true,
                    null,
                    null)[1]);
        } catch (java.sql.SQLException e) {
            MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                    "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
            throw e;
        }
        int tmpl_0 = tmpc_SQLTable.cols[0].vals.size();
        java.util.List<ListViewRowDataWrapper<String>> tmpc_SQLTable__listViewRowDataWrapper = new java.util.ArrayList<ListViewRowDataWrapper<String>>(
                tmpl_0);
        for (int tmpc_int = 0; tmpc_int < tmpl_0; tmpc_int++) {
            String tmpt_str = (String) (tmpc_SQLTable.cols[0].vals.get(tmpc_int));
            tmpc_SQLTable__listViewRowDataWrapper.add(
                    new ListViewRowDataWrapper<String>(tmpt_str, tmpt_str));
        }
        comboBox_custName.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }

    @FXML
    private void onpressed_DefaultLocButton() {
        textArea_MeetLoc.setText(custLoc);
    }
}
