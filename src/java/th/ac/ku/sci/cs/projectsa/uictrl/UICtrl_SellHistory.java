package th.ac.ku.sci.cs.projectsa.uictrl;

import javafx.fxml.FXML;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Product;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Selling_Request;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.time.LocalDate;

import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class UICtrl_SellHistory {
    @FXML
    private ListView<ListViewRowDataWrapper<String>> brListView;
    @FXML
    private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_StatusFilter;
    @FXML
    private DatePicker datePicker_DateFilter;

    @FXML

    private void initialize() throws java.sql.SQLException {
        try {
            ListViewRowDataWrapper<Integer> tmpt_lvrdwInt = new ListViewRowDataWrapper<Integer>(0, "ทุกสถานะ");
            comboBox_StatusFilter.getItems().add(tmpt_lvrdwInt);
            comboBox_StatusFilter.getItems().add(new ListViewRowDataWrapper<Integer>(-1, "เฉพาะสถานะ \"รอการจัดส่ง\""));
            comboBox_StatusFilter.getItems().add(new ListViewRowDataWrapper<Integer>(1, "เฉพาะสถานะ \"ส่งแล้ว\""));
            comboBox_StatusFilter.setValue(tmpt_lvrdwInt);
            brListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if (event.getClickCount() >= 2) {
                            helper_changePageForViewDataOfRow();
                        }
                    } catch (Throwable e) {
                        MyExceptionHandling.handleFatalException(e);
                    }
                }
            });
            // SAME AS ABOVE LAMO
            brListView.setOnKeyPressed(event -> {
                try {
                    if (event.getCode() == KeyCode.ENTER) {
                        helper_changePageForViewDataOfRow();
                    }
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            comboBox_StatusFilter.setOnAction(event -> {
                try {
                    helper_listViewUpdate();
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            datePicker_DateFilter.setOnAction(event -> {
                try {
                    helper_listViewUpdate();
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            // this done helper_listViewUpdate for us
            onpressed_EveryDay_Button();
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onpressed_EveryDay_Button() throws java.sql.SQLException {
        datePicker_DateFilter.setValue(null);
        datePicker_DateFilter.getEditor().setText("<ไม่กรองวัน>");
        helper_listViewUpdate();
    }

    // mode: {-1:OnlyWaitSending, 0:Both, 1:OnlySent}
    private void helper_listViewUpdate() throws java.sql.SQLException {
        int mode = comboBox_StatusFilter.getValue().ref;
        LocalDate date = datePicker_DateFilter.getValue();
        Long dateRawVal;
        if (date == null) {
            dateRawVal = null;
        } else {
            dateRawVal = date.toEpochDay();
        }
        brListView.getItems().clear();
        String whereQuery = "WHERE (" +
                "(Product_Status = 2 AND ?) OR " +
                "(Product_Status = 3 AND ?) )" +
                ((dateRawVal != null) ? " AND  (BR.Buy_Request_Created_Date=?)" : "");
        Object[] SQLParams;
        if (dateRawVal == null) {
            SQLParams = new Object[] { null, null };
        } else {
            SQLParams = new Object[] { null, null, dateRawVal };
        }
        if (mode == -1) {
            SQLParams[0] = new Long(1);
            SQLParams[1] = new Long(0);
        } else if (mode == 1) {
            SQLParams[0] = new Long(0);
            SQLParams[1] = new Long(1);
        } else {
            SQLParams[0] = new Long(1);
            SQLParams[1] = new Long(1);
        }
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT BR.Customer_Full_Name, BR.Product_ID, BR.Buy_Request_Created_Date, SR.Selling_Request_Brand, SR.Selling_Request_Model, PD.Product_Status FROM Buy_Request AS BR LEFT JOIN Product AS PD ON BR.Product_ID = PD.Product_ID LEFT JOIN  Selling_Request AS SR ON PD.Selling_Request_ID = SR.Selling_Request_ID "
                            + whereQuery
                            + " ORDER BY CASE WHEN PD.Product_Status = 2 THEN 0 ELSE 1 END, BR.Buy_Request_Created_Date DESC, BR.rowid DESC;",
                    false,
                    true,
                    null,
                    SQLParams)[1]);
        } catch (java.sql.SQLException e) {
            MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                    "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
            throw e;
        }
        int tmpl_0 = tmpc_SQLTable.cols[0].vals.size();
        java.util.List<ListViewRowDataWrapper<String>> tmpc_SQLTable__listViewRowDataWrapper = new java.util.ArrayList<ListViewRowDataWrapper<String>>(
                tmpl_0);
        for (int tmpc_int = 0; tmpc_int < tmpl_0; tmpc_int++) {
            // 0: BR.Customer_Full_Name
            String tmpk_CustName = (String) (tmpc_SQLTable.cols[0].vals.get(tmpc_int));
            // 1: BR.Product_ID
            String tmpk_PDID = (String) (tmpc_SQLTable.cols[1].vals.get(tmpc_int));
            // 2: BR.Buy_Request_Created_Date
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[2].vals.get(tmpc_int), tmpc_SQLTable.cols[2].javaType);
            String tmpk_BRDate = LocalDate.ofEpochDay(tmpu_epochTimeData).toString();
            // 3: SR.Selling_Request_Brand
            String tmpk_Brand = (String) (tmpc_SQLTable.cols[3].vals.get(tmpc_int));
            // 4: SR.Selling_Request_Model
            String tmpk_Model = (String) (tmpc_SQLTable.cols[4].vals.get(tmpc_int));
            // 5: PD.Product_Status
            DatabaseMnm.DataSpec.STATUS_Product tmpk_PDStatus_javaType = DatabaseMnm.DataSpec.STATUS_Product
                    .values()[DatabaseMnm.convertIntegerAlikeSQLColToLong(
                            tmpc_SQLTable.cols[5].vals.get(tmpc_int),
                            tmpc_SQLTable.cols[5].javaType).intValue()];
            String tmpk_PDStatus = null;
            if (tmpk_PDStatus_javaType == STATUS_Product.NotYetSale) {
                tmpk_PDStatus = Misc.ThaiStr_DataSpec_Status_pd[0];
            } else if (tmpk_PDStatus_javaType == STATUS_Product.ForSale) {
                tmpk_PDStatus = Misc.ThaiStr_DataSpec_Status_pd[1];
            } else if (tmpk_PDStatus_javaType == STATUS_Product.SaledAndWaitForSend) {
                tmpk_PDStatus = Misc.ThaiStr_DataSpec_Status_pd[2];
            } else {
                tmpk_PDStatus = Misc.ThaiStr_DataSpec_Status_pd[3];
            }
            String tmpk_repr = "(จองขายไว้เมื่อ " + tmpk_BRDate + ") [สินค้ารหัส:" + tmpk_PDID + ": " + tmpk_PDStatus
                    + "] จองโดยคุณ \"" + tmpk_CustName + "\" (ยี่ห้อ/รุ่น: \"" + tmpk_Brand + "\"/\"" + tmpk_Model
                    + "\")";
            tmpc_SQLTable__listViewRowDataWrapper.add(
                    new ListViewRowDataWrapper<String>(tmpk_PDID, tmpk_repr));
        }
        brListView.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }

    private void helper_changePageForViewDataOfRow() throws java.io.IOException {
        ListViewRowDataWrapper<String> tmpt_lvrdw = brListView.getSelectionModel().getSelectedItem();
        if (tmpt_lvrdw != null) {
            Main.switchToSpecificPagename("sell_data", new Object[] { this.getClass(), tmpt_lvrdw });
        }
    }
}
