package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.Table;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;
import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_ProductDetail {
    private int stateTransitionMode=0;
    @FXML private TextField textField_PdID,textField_SrID;
    @FXML private TextField textField_Brand,textField_Model;
    @FXML private TextField textField_PdStatus,textField_PdPrice,textField_PdArriveDate;
    @FXML private Button button_MainAction;

    @FXML private void initialize() throws Throwable {
        try {
            // TODO: debug
            String pdID="PD0099AZ";
            // String pdID =((ListViewRowDataWrapper<String>) ((Object[])com.github.saacsos.FXRouter.getData())[1]).ref;
            DatabaseMnm.Table tmpc_SQLTable = null;
            try {
                tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                        null,
                        "SELECT PD.Product_Arrive_Time, PD.Product_Price, PD.Product_Status, PD.Selling_Request_ID, SR.Selling_Request_Brand,SR.Selling_Request_Model FROM Product AS PD LEFT JOIN Selling_Request AS SR ON PD.Selling_Request_ID = SR.Selling_Request_ID WHERE PD.Product_ID=?;",
                        false,
                        true,
                        null,
                        new Object[] {pdID}
                )[1]);
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            // TODO:
            textField_PdID.setText(pdID);
            textField_SrID.setText((String)(tmpc_SQLTable.cols[3].vals.get(0)));
            textField_Brand.setText((String)(tmpc_SQLTable.cols[4].vals.get(0)));
            textField_Model.setText((String)(tmpc_SQLTable.cols[5].vals.get(0)));
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                tmpc_SQLTable.cols[0].vals.get(0),tmpc_SQLTable.cols[0].javaType
                );
            String tmpt_str;
            tmpt_str = (java.time.Instant.ofEpochSecond(tmpu_epochTimeData)).atZone(java.time.ZoneOffset.UTC).toLocalDate().toString();
            textField_PdArriveDate.setText(tmpt_str);
            Double tmpu_moneyAmount = DatabaseMnm.convertRealAlikeSQLColToDouble(
                tmpc_SQLTable.cols[1].vals.get(0),tmpc_SQLTable.cols[1].javaType
            );
            tmpt_str=(new java.text.DecimalFormat("0.00")).format(tmpu_moneyAmount);
            textField_PdPrice.setText(tmpt_str);
            int tmpt_int = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                tmpc_SQLTable.cols[2].vals.get(0),tmpc_SQLTable.cols[2].javaType
            ).intValue();
            DatabaseMnm.DataSpec.STATUS_Product tmpt_statusSR= DatabaseMnm.DataSpec.STATUS_Product.values()[tmpt_int];
            if (tmpt_statusSR==DatabaseMnm.DataSpec.STATUS_Product.Sent) {
                textField_PdStatus.setText(Misc.ThaiStr_DataSpec_Status_pd[3]);
                button_MainAction.setVisible(false);
            } else if (tmpt_statusSR==DatabaseMnm.DataSpec.STATUS_Product.NotYetSale) {
                textField_PdStatus.setText(Misc.ThaiStr_DataSpec_Status_pd[0]);
                stateTransitionMode=-1;
                button_MainAction.setText("เปลี่ยนสถานะให้เป็น \"พร้อมขาย\"");
            } else if (tmpt_statusSR==DatabaseMnm.DataSpec.STATUS_Product.SaledAndWaitForSend) {
                textField_PdStatus.setText(Misc.ThaiStr_DataSpec_Status_pd[2]);
                stateTransitionMode=1;
                button_MainAction.setText("เปลี่ยนสถานะให้เป็น \"ส่งแล้ว\"");
            } else {
                textField_PdStatus.setText(Misc.ThaiStr_DataSpec_Status_pd[1]);
            }
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    } 
    // TODO: depend on where we come from..., default to quotation
    @FXML private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("buy_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onMainAction_Button() throws java.io.IOException {
        try {
            // 0: this class
            // 1: (Object[])com.github.saacsos.FXRouter.getData())[1] (ListViewRowDataWrapper<String> ของ SR_ID)
            // 2: Array ของข้อมูล ดังนี้ Brand/Model/CustName
            Main.switchToSpecificPagename(
                "check_items",
                new Object[] {
                    this.getClass(),
                    ((Object[])com.github.saacsos.FXRouter.getData())[1],
                    new Object[] {textField_Brand.getText(),textField_Model.getText(),textField_Brand.getText()}
                }
            );
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
