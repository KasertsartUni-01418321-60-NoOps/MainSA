package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Product;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.time.LocalDate;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_SellData {
    @FXML
    private TextField textField_ID;
    @FXML
    private TextField textField_CustName;
    @FXML
    private TextField textField_Date;
    @FXML
    private TextField textField_Brand;
    @FXML
    private TextField textField_Model;
    @FXML
    private TextArea textArea_Loc;
    @FXML
    private TextField textField_PaidAmount;
    @FXML
    private TextField textField_TPrice;
    @FXML
    private TextField textField_Status;

    @FXML
    private void initialize() throws Throwable {
        try {
            String pdID = ((ListViewRowDataWrapper<String>) ((Object[]) com.github.saacsos.FXRouter.getData())[1]).ref;
            DatabaseMnm.Table tmpc_SQLTable = null;
            try {
                tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                        null,
                        "SELECT BR.Customer_Full_Name, BR.Product_ID, BR.Buy_Request_Created_Date, SR.Selling_Request_Brand, SR.Selling_Request_Model, PD.Product_Status, BR.Buy_Request_Transportation_Price, BR.Buy_Request_Location, PD.Product_Price FROM Buy_Request AS BR LEFT JOIN Product AS PD ON BR.Product_ID = PD.Product_ID LEFT JOIN  Selling_Request AS SR ON PD.Selling_Request_ID = SR.Selling_Request_ID WHERE BR.Product_ID=?;",
                        false,
                        true,
                        null,
                        new Object[] { pdID })[1]);
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            String tmpt_str;
            // 0 BR.Customer_Full_Name
            tmpt_str = (String) (tmpc_SQLTable.cols[0].vals.get(0));
            textField_CustName.setText(tmpt_str);
            // 1 BR.Product_ID
            textField_ID.setText(pdID);
            // 2 BR.Buy_Request_Created_Date
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[2].vals.get(0), tmpc_SQLTable.cols[2].javaType);
            tmpt_str = LocalDate.ofEpochDay(tmpu_epochTimeData).toString();
            textField_Date.setText(tmpt_str);
            // 3 SR.Selling_Request_Brand
            tmpt_str = (String) (tmpc_SQLTable.cols[3].vals.get(0));
            textField_Brand.setText(tmpt_str);
            // 4 SR.Selling_Request_Model
            tmpt_str = (String) (tmpc_SQLTable.cols[4].vals.get(0));
            textField_Model.setText(tmpt_str);
            // 5 PD.Product_Status
            DatabaseMnm.DataSpec.STATUS_Product tmpk_Product_Status = DatabaseMnm.DataSpec.STATUS_Product
                    .values()[DatabaseMnm.convertIntegerAlikeSQLColToLong(
                            tmpc_SQLTable.cols[5].vals.get(0),
                            tmpc_SQLTable.cols[5].javaType).intValue()];
            String tmpk_Status = null;
            if (tmpk_Product_Status == STATUS_Product.NotYetSale) {
                tmpk_Status = Misc.ThaiStr_DataSpec_Status_pd[0];
            } else if (tmpk_Product_Status == STATUS_Product.ForSale) {
                tmpk_Status = Misc.ThaiStr_DataSpec_Status_pd[1];
            } else if (tmpk_Product_Status == STATUS_Product.SaledAndWaitForSend) {
                tmpk_Status = Misc.ThaiStr_DataSpec_Status_pd[2];
            } else {
                tmpk_Status = Misc.ThaiStr_DataSpec_Status_pd[3];
            }
            textField_Status.setText(tmpk_Status);
            // 6 BR.Buy_Request_Transportation_Price
            Long tmpu_paidAmount = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[6].vals.get(0), tmpc_SQLTable.cols[6].javaType);
            tmpt_str = tmpu_paidAmount.toString();
            textField_TPrice.setText(tmpt_str);
            // 8 (7 is next) PD.Product_Price
            Long tmpt_long = tmpu_paidAmount;
            tmpu_paidAmount = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[8].vals.get(0), tmpc_SQLTable.cols[8].javaType);
            tmpt_str = ((Long)(tmpu_paidAmount + tmpt_long)).toString();
            textField_PaidAmount.setText(tmpt_str);
            // 7 BR.Buy_Request_Location
            tmpt_str = (String) (tmpc_SQLTable.cols[7].vals.get(0));
            textArea_Loc.setText(tmpt_str);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("sell_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
