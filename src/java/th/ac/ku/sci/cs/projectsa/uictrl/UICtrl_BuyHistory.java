package th.ac.ku.sci.cs.projectsa.uictrl;

import javafx.fxml.FXML;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Selling_Request;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class UICtrl_BuyHistory {
    @FXML private ListView<ListViewRowDataWrapper> srListView;
    @FXML private Button button_sortByDate;
    @FXML private Button button_sortByPdStatus;
    @FXML
    private void initialize() throws java.sql.SQLException{
        try {
            button_sortByDate.setDisable(true);
			helper_listViewUpdate(-1);
            srListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if (event.getClickCount() >= 2) {
                            ListViewRowDataWrapper tmpt_lvrdw = srListView.getSelectionModel().getSelectedItem();
                            if (tmpt_lvrdw!=null) {
                            Main.switchToSpecificPagename("buy_data",new Object[] {this.getClass(),tmpt_lvrdw});
                            }
                        }
                    } catch (Throwable e) {
                        MyExceptionHandling.handleFatalException(e);
                    }
                }
            });
            // SAME AS ABOVE LAMO
            srListView.setOnKeyPressed(event -> {
                try {
                    if (event.getCode() == KeyCode.ENTER) {
                        ListViewRowDataWrapper tmpt_lvrdw = srListView.getSelectionModel().getSelectedItem();
                            if (tmpt_lvrdw!=null) {
                            Main.switchToSpecificPagename("customer_data",new Object[] {this.getClass(),tmpt_lvrdw});
                            }
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
    @FXML private void onBack_Button() throws java.io.IOException  {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onCreatePurchase_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("buy_from_vender");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    private void helper_listViewUpdate(int mode) throws java.sql.SQLException {
        srListView.getItems().clear();
        String orderQuery=null;
        if (mode<=0) {
            orderQuery="ORDER BY SR.Selling_Request_Meet_Date DESC, COALESCE(PD.Product_Status, -1)";
        } else {
            orderQuery="ORDER BY COALESCE(PD.Product_Status, -1), SR.Selling_Request_Meet_Date DESC";
        }
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT SR.Selling_Request_ID,SR.Customer_Full_Name,SR.Selling_Request_Brand,SR.Selling_Request_Model,SR.Selling_Request_Status,PD.Product_Status FROM Selling_Request AS SR LEFT JOIN Product AS PD ON SR.Selling_Request_ID = PD.Selling_Request_ID "+orderQuery,
                    false,
                    true,
                    null,
                    null
            )[1]);
        } catch (java.sql.SQLException e) {
            MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                    "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
            throw e;
        }
        int tmpl_0=tmpc_SQLTable.cols[0].vals.size();
        ListViewRowDataWrapper[] tmpc_SQLTable__listViewRowDataWrapper = new ListViewRowDataWrapper[tmpl_0];
        for (int tmpc_int =0; tmpc_int<tmpl_0; tmpc_int++) {
            String tmpk_Selling_Request_ID=(String)(tmpc_SQLTable.cols[0].vals.get(tmpc_int));
            String tmpk_Customer_Full_Name=(String)(tmpc_SQLTable.cols[1].vals.get(tmpc_int));
            String tmpk_Selling_Request_Brand=(String)(tmpc_SQLTable.cols[2].vals.get(tmpc_int));
            String tmpk_Selling_Request_Model=(String)(tmpc_SQLTable.cols[3].vals.get(tmpc_int));
            DatabaseMnm.DataSpec.STATUS_Selling_Request tmpk_Selling_Request_Status=DatabaseMnm.DataSpec.STATUS_Selling_Request.values()[
                DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[4].vals.get(tmpc_int),
                    tmpc_SQLTable.cols[4].javaType
                ).intValue()
            ];
            String tmpt_str_1=null;
            if (tmpk_Selling_Request_Status==STATUS_Selling_Request.WaitForCheck) {tmpt_str_1="รอการนัดเช็คสภาพฯ";}
            else if (tmpk_Selling_Request_Status==STATUS_Selling_Request.Acceapted) {tmpt_str_1="รับซื้อแล้ว";}
            else {tmpt_str_1="ปฏิเสธการรับซื้อแล้ว";}
            String tmpk_repr = "["+tmpk_Selling_Request_ID+": "+tmpt_str_1+"] เสนอขาย (ยี่ห้อ/รุ่น: "+tmpk_Selling_Request_Brand+"/"+tmpk_Selling_Request_Model+") โดยคุณ \""+tmpk_Customer_Full_Name+"\"";
            tmpc_SQLTable__listViewRowDataWrapper[tmpc_int]=new ListViewRowDataWrapper(tmpk_Selling_Request_ID, tmpk_repr);
        }
        srListView.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }

    @FXML private void onpressed_Button_sortByPdStatus() throws java.sql.SQLException {
        try {
            helper_listViewUpdate(1);
            button_sortByDate.setDisable(false);
            button_sortByPdStatus.setDisable(true);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onpressed_Button_sortByDate() throws java.sql.SQLException {
        try {
            helper_listViewUpdate(-1);
            button_sortByDate.setDisable(true);
            button_sortByPdStatus.setDisable(false);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}
