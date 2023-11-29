package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Product;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Selling_Request;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.time.LocalDate;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_BuyData {
    @FXML
    private TextField textField_ID, textField_IDofPd;
    @FXML
    private TextField textField_CustName;
    // เราไม่ได้แก้ไขค่า date อยู่แล้ว เลยตั้งเป็น non-editable textfield
    @FXML
    private TextField textField_MeetDate;
    @FXML
    private TextField textField_Brand;
    @FXML
    private TextField textField_Model;
    @FXML
    private TextArea textArea_PLooks;
    @FXML
    private TextArea textArea_MeetLoc;
    // เราไม่ได้แก้ไขค่า double เลยตั้งเป็น non-editable textfield
    @FXML
    private TextField textField_PaidAmount;
    @FXML
    private TextField textField_Status;
    @FXML
    private TextArea textArea_RpmDesc;
    @FXML
    private Button button_Check;

    @FXML
    private void initialize() throws Throwable {
        try {
            String srID = ((ListViewRowDataWrapper<String>) ((Object[]) com.github.saacsos.FXRouter.getData())[1]).ref;
            DatabaseMnm.Table tmpc_SQLTable = null;
            try {
                tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                        null,
                        "SELECT SR.Selling_Request_ID, SR.Customer_Full_Name, SR.Selling_Request_Brand, SR.Selling_Request_Model, SR.Selling_Request_Product_Looks, SR.Selling_Request_Meet_Date, SR.Selling_Request_Meet_Location, SR.Selling_Request_Paid_Amount, SR.Selling_Request_Status, SR.Selling_Request_Repairment_Description, PD.Product_ID, PD.Product_Status FROM Selling_Request AS SR LEFT JOIN Product AS PD ON SR.Selling_Request_ID = PD.Selling_Request_ID WHERE SR.Selling_Request_ID=?",
                        false,
                        true,
                        null,
                        new Object[] { srID })[1]);
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            String tmpt_str;
            textField_ID.setText(srID);
            String tmpk_PdId = (String) (tmpc_SQLTable.cols[10].vals.get(0));
            if (tmpk_PdId == null) {
                textField_IDofPd.setText("< สินค้ายังไม่ได้เพิ่มลงในคลัง >");
                textField_IDofPd.setDisable(true);
            } else {
                textField_IDofPd.setText(tmpk_PdId);
            }
            textField_CustName.setText((String) (tmpc_SQLTable.cols[1].vals.get(0)));
            textField_Brand.setText((String) (tmpc_SQLTable.cols[2].vals.get(0)));
            textField_Model.setText((String) (tmpc_SQLTable.cols[3].vals.get(0)));
            textArea_PLooks.setText((String) (tmpc_SQLTable.cols[4].vals.get(0)));
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[5].vals.get(0), tmpc_SQLTable.cols[5].javaType);
            tmpt_str = LocalDate.ofEpochDay(tmpu_epochTimeData).toString();
            textField_MeetDate.setText(tmpt_str);
            textArea_MeetLoc.setText((String) (tmpc_SQLTable.cols[6].vals.get(0)));
            Long tmpu_paidAmount = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[7].vals.get(0), tmpc_SQLTable.cols[7].javaType);
            if (tmpu_paidAmount != null) {
                tmpt_str = tmpu_paidAmount.toString();
                textField_PaidAmount.setText(tmpt_str);
            } else {
                textField_PaidAmount.setText("<ไม่มีค่า>");
                textField_PaidAmount.setDisable(true);
            }
            DatabaseMnm.DataSpec.STATUS_Selling_Request tmpk_Selling_Request_Status = DatabaseMnm.DataSpec.STATUS_Selling_Request
                    .values()[DatabaseMnm.convertIntegerAlikeSQLColToLong(
                            tmpc_SQLTable.cols[8].vals.get(0),
                            tmpc_SQLTable.cols[8].javaType).intValue()];
            DatabaseMnm.DataSpec.STATUS_Product tmpk_Product_Status = null;
            Long tmpt_long = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[11].vals.get(0),
                    tmpc_SQLTable.cols[11].javaType);
            if (tmpt_long != null) {
                tmpk_Product_Status = DatabaseMnm.DataSpec.STATUS_Product.values()[tmpt_long.intValue()];
            }
            String tmpk_Status = null;
            if (tmpk_Selling_Request_Status == STATUS_Selling_Request.WaitForCheck) {
                tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[0];
            } else if (tmpk_Selling_Request_Status == STATUS_Selling_Request.Acceapted) {
                if (tmpk_Product_Status == null) {
                    tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[2] + " (รอนำสินค้าเข้าคลัง)";
                } else if (tmpk_Product_Status == STATUS_Product.NotYetSale) {
                    tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[2] + " (สถานะสินค้า: "
                            + Misc.ThaiStr_DataSpec_Status_pd[0] + ")";
                } else if (tmpk_Product_Status == STATUS_Product.ForSale) {
                    tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[2] + " (สถานะสินค้า: "
                            + Misc.ThaiStr_DataSpec_Status_pd[1] + ")";
                } else if (tmpk_Product_Status == STATUS_Product.SaledAndWaitForSend) {
                    tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[2] + " (สถานะสินค้า: "
                            + Misc.ThaiStr_DataSpec_Status_pd[2] + ")";
                } else {
                    tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[2] + " (สถานะสินค้า: "
                            + Misc.ThaiStr_DataSpec_Status_pd[3] + ")";
                }
            } else {
                tmpk_Status = Misc.ThaiStr_DataSpec_Status_SR[1];
            }
            textField_Status.setText(tmpk_Status);
            String tmpk_rpmDesc = (String) (tmpc_SQLTable.cols[9].vals.get(0));
            button_Check.setDisable(true);
            if (tmpk_Selling_Request_Status == DatabaseMnm.DataSpec.STATUS_Selling_Request.Acceapted) {
                if (tmpk_rpmDesc == null) {
                    textArea_RpmDesc.setText("<สินค้าไม่มีการซ่อม>");
                    textArea_RpmDesc.setDisable(true);
                } else {
                    textArea_RpmDesc.setText(tmpk_rpmDesc);
                }
            } else if (tmpk_Selling_Request_Status == DatabaseMnm.DataSpec.STATUS_Selling_Request.Declined) {
                textArea_RpmDesc.setText("<คำร้องฯถูกปฏิเสธ>");
                textArea_RpmDesc.setDisable(true);
            } else {
                button_Check.setDisable(false);
                textArea_RpmDesc.setText("<รอการตรวจสอบสภาพ>");
                textArea_RpmDesc.setDisable(true);
            }
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
    private void onCheck_Button() throws java.io.IOException {
        try {
            // 0: this class
            // 1: (Object[])com.github.saacsos.FXRouter.getData())[1]
            // (ListViewRowDataWrapper<String> ของ SR_ID)
            // 2: Array ของข้อมูล ดังนี้ Brand/Model/CustName
            Main.switchToSpecificPagename(
                    "check_items",
                    new Object[] {
                            this.getClass(),
                            ((Object[]) com.github.saacsos.FXRouter.getData())[1],
                            new Object[] { textField_Brand.getText(), textField_Model.getText(),
                                    textField_CustName.getText() }
                    });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
