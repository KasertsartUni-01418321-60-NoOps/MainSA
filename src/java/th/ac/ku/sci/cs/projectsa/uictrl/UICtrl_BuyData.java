package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.text.DecimalFormat;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_BuyData {
    @FXML private TextField textField_ID;
    @FXML private TextField textField_CustName;
    // เราไม่ได้แก้ไขค่า date อยู่แล้ว เลยตั้งเป็น non-editable textfield
    @FXML private TextField textField_MeetDate;
    @FXML private TextField textField_Brand;
    @FXML private TextField textField_Model;
    @FXML private TextArea textArea_PLooks;
    @FXML private TextArea textArea_MeetLoc;
    // เราไม่ได้แก้ไขค่า double เลยตั้งเป็น non-editable textfield
    @FXML private TextField textField_PaidAmount;
    @FXML private TextField textField_Status;
    @FXML private Button button_Check;

    @FXML private void initialize() throws Throwable {
        try {
            String srID =((ListViewRowDataWrapper<String>) ((Object[])com.github.saacsos.FXRouter.getData())[1]).ref;
            DatabaseMnm.Table tmpc_SQLTable = null;
            try {
                tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                        null,
                        "SELECT * FROM Selling_Request WHERE Selling_Request_ID=?",
                        false,
                        true,
                        null,
                        new Object[] {srID}
                )[1]);
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            String tmpt_str;
            textField_ID.setText(srID);
            textField_CustName.setText((String)(tmpc_SQLTable.cols[1].vals.get(0)));
            textField_Brand.setText((String)(tmpc_SQLTable.cols[2].vals.get(0)));
            textField_Model.setText((String)(tmpc_SQLTable.cols[3].vals.get(0)));
            textArea_PLooks.setText((String)(tmpc_SQLTable.cols[4].vals.get(0)));
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                tmpc_SQLTable.cols[5].vals.get(0),tmpc_SQLTable.cols[5].javaType
            );
            tmpt_str = (java.time.Instant.ofEpochSecond(tmpu_epochTimeData)).atZone(java.time.ZoneOffset.UTC).toLocalDate().toString();
            textField_MeetDate.setText(tmpt_str);
            textArea_MeetLoc.setText((String)(tmpc_SQLTable.cols[6].vals.get(0)));
            Double tmpu_paidAmount = DatabaseMnm.convertRealAlikeSQLColToDouble(
                tmpc_SQLTable.cols[7].vals.get(0),tmpc_SQLTable.cols[7].javaType
            );
            if (tmpu_paidAmount!=null) { 
                tmpt_str=(new java.text.DecimalFormat("0.00")).format(tmpu_paidAmount);
                textField_PaidAmount.setText(tmpt_str);
            } else {
                textField_PaidAmount.setText("<ไม่มีค่า>");
                textField_PaidAmount.setDisable(true);
            }
            int tmpt_int = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                tmpc_SQLTable.cols[8].vals.get(0),tmpc_SQLTable.cols[8].javaType
            ).intValue();
            DatabaseMnm.DataSpec.STATUS_Selling_Request tmpt_statusSR= DatabaseMnm.DataSpec.STATUS_Selling_Request.values()[tmpt_int];
            button_Check.setDisable(true);
            if (tmpt_statusSR==DatabaseMnm.DataSpec.STATUS_Selling_Request.Acceapted) {textField_Status.setText(Misc.ThaiStr_DataSpec_Status_SR[2]);}
            else if (tmpt_statusSR==DatabaseMnm.DataSpec.STATUS_Selling_Request.Declined) {textField_Status.setText(Misc.ThaiStr_DataSpec_Status_SR[1]);}
            else {textField_Status.setText(Misc.ThaiStr_DataSpec_Status_SR[0]); button_Check.setDisable(false);}
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    } 
    @FXML private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("buy_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onCheck_Button() throws java.io.IOException {
        try {
            // TODO: โยน
            // 0: this class
            // 1: (Object[])com.github.saacsos.FXRouter.getData())[1] (ListViewRowDataWrapper<String> ของ SR_ID)
            // 2: Array ของข้อมูล ดังนี้ Brand/Model/CustName
            Main.switchToSpecificPagename(
                "check_items",
                new Object[] {
                    this.getClass(),
                    ((Object[])com.github.saacsos.FXRouter.getData())[1],
                    new Object[] {textField_Brand.getText(),textField_Model.getText(),textField_CustName.getText()}
                }
            );
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
