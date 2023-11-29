package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.time.LocalDate;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UICtrl_Quotation {
    @FXML
    private ComboBox<ListViewRowDataWrapper<String>> comboBox_custName;
    @FXML
    private TextArea textArea_Loc;
    @FXML
    private TextField spinner_Price;
    @FXML
    private Button button_defaultLoc;
    private String custLoc = null, pdID = null;

    @FXML
    private void initialize() throws java.sql.SQLException {
        try {
            pdID = (String) ((Object[]) FXRouter.getData())[1];
            comboBox_custName_Helper1();
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
            Main.switchToSpecificPagename("product_detail", new Object[] { this.getClass(), pdID });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onpressed_DefaultLocButton() {
        textArea_Loc.setText(custLoc);
    }

    @FXML
    private void onpressed_SaveButton() throws java.io.IOException, java.sql.SQLException {
        try {
            // Note that this always got from DB, so no validation
            // REMARK: for pdID, it is no need of FK/PK validation because the flow of
            // UI/data operation is designed
            String formval_custName = null;
            try {
                formval_custName = comboBox_custName.getSelectionModel().getSelectedItem().ref;
            } catch (NullPointerException e) {
                helper2();
                return;
            }
            Long formval_Date = LocalDate.now().toEpochDay();
            String formval_Loc = textArea_Loc.getText();
            // [VALIDZONE]
            DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation.check__BUY_REQUEST__Buy_Request_Location(formval_Loc);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__BUY_REQUEST__Buy_Request_Transportation_Price(spinner_Price.getText());
            if (tmpReason != null) {
                helper1();
                return;
            }
            // [END VALID ZONE]
            Long formval_tprice = Long.parseLong(spinner_Price.getText());
            try {
                DatabaseMnm.runSQLcmd(
                        null,
                        "INSERT INTO Buy_Request VALUES (?, ?, ?, ?, ?);",
                        true,
                        false,
                        null,
                        new Object[] {
                                formval_custName,
                                pdID,
                                formval_Date,
                                formval_tprice,
                                formval_Loc
                        });
                DatabaseMnm.runSQLcmd(
                        null,
                        "UPDATE Product SET Product_Status=2 WHERE Product_ID=?;",
                        true,
                        false,
                        null,
                        new Object[] {
                                pdID
                        });
                DatabaseMnm.mainDbConn.commit();
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            ;
            onBack_Button();
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    private void helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสัญญาขายได้", "เนื่องจากกรอกข้อมูลผิดรูปแบบ", false);
    }

    // REMARK: เผื่อกรณี NULL ของ ComboBox
    private void helper2() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสัญญาขายได้", "เนื่องจากชื่อลูกค้ายังไม่ได้ถูกเลือก", false);
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
}
