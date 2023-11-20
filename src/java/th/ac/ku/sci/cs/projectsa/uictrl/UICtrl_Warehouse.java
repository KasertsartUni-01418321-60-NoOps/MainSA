package th.ac.ku.sci.cs.projectsa.uictrl;

// TODO: แก้เสร็จแล้ว ลบ unused import
import javafx.fxml.FXML;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Product;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Selling_Request;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class UICtrl_Warehouse {
    @FXML private ListView<ListViewRowDataWrapper<String>> pdListView;
    @FXML private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_filterType;
    @FXML private Text text_filterStr,text_filterCheckbox;
    @FXML private TextField textField_filter;
    @FXML private CheckBox checkBoxPdStatus1,checkBoxPdStatus2,checkBoxPdStatus3,checkBoxPdStatus4;

    @FXML
    private void initialize() throws java.sql.SQLException{
        try {
            comboBox_filterType.getItems().add(
                new ListViewRowDataWrapper<Integer>(-1, "ยี่ห้อ")
            );
            comboBox_filterType.getItems().add(
                new ListViewRowDataWrapper<Integer>(1, "รุ่นสินค้า")
            );
            ListViewRowDataWrapper<Integer> tmpt_lvrdwInt = new ListViewRowDataWrapper<Integer>(0, "สถานะของสินค้า");
            comboBox_filterType.getItems().add(
                tmpt_lvrdwInt
            );
            comboBox_filterType.setValue(tmpt_lvrdwInt);
            comboBox_filterType.setOnAction(event -> {
                boolean tmpt_bool;
                if (comboBox_filterType.getValue().ref==0) {
                    tmpt_bool=false;
                } else {
                    tmpt_bool=true;
                }
                text_filterStr.setVisible(tmpt_bool);
                text_filterCheckbox.setVisible(!tmpt_bool);
                textField_filter.setVisible(tmpt_bool);
                checkBoxPdStatus1.setVisible(!tmpt_bool);
                checkBoxPdStatus2.setVisible(!tmpt_bool);
                checkBoxPdStatus3.setVisible(!tmpt_bool);
                checkBoxPdStatus4.setVisible(!tmpt_bool);
            });
			helper_listViewUpdate(0,null);
            pdListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
            pdListView.setOnKeyPressed(event -> {
                try {
                    if (event.getCode() == KeyCode.ENTER) {
                        helper_changePageForViewDataOfRow();
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
    // TODO: data to throw
    @FXML private void onPressed_Button_addProduct() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("add_item");
        } catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    
    @FXML private void onPressed_Button_Search() throws java.sql.SQLException {
        try {
            int tmpt_pint = comboBox_filterType.getValue().ref;
            if (tmpt_pint==0) {
                boolean[] tmpt_pbool_arr= new boolean[] {
                    checkBoxPdStatus1.isSelected(),
                    checkBoxPdStatus2.isSelected(),
                    checkBoxPdStatus3.isSelected(),
                    checkBoxPdStatus4.isSelected()
                };
                helper_listViewUpdate(tmpt_pint,tmpt_pbool_arr);
            }
            else {
                helper_listViewUpdate(tmpt_pint,textField_filter.getText());
            }
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    // mode={0:pdStatus,-1:pdBrand,1:pdModel}
    private void helper_listViewUpdate(int mode, Object fObj) throws java.sql.SQLException {
        String fStr=null;
        boolean[] fBoolArr=null;
        if (mode==0) {
            fBoolArr=(boolean[]) fObj;
        } else {
            fStr=(String) fObj;
        }
        pdListView.getItems().clear();
        String whereQuery="";
        Object[] SQLParams=null;
        if (mode==-1) {
            whereQuery="WHERE SR.Selling_Request_Brand LIKE ?";
            SQLParams=new Object[] {"%"+fStr+"%"};
        } else if (mode==1) {
            whereQuery="WHERE SR.Selling_Request_Model LIKE ?";
            SQLParams=new Object[] {"%"+fStr+"%"};
        } else {
            whereQuery="WHERE Customer_Full_Name LIKE ?";
        }
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT PD.Product_ID, PD.Product_Arrive_Time, PD.Product_Price, PD.Product_Status, PD.Selling_Request_ID, SR.Selling_Request_Brand,SR.Selling_Request_Model FROM Product AS PD LEFT JOIN Selling_Request AS SR ON PD.Selling_Request_ID = SR.Selling_Request_ID "+whereQuery+" ORDER BY CASE WHEN PD.Product_Status = 2 THEN 0 WHEN PD.Product_Status = 0 THEN 1 ELSE 2 END, ROWID DESC;",
                    false,
                    true,
                    null,
                    SQLParams
            )[1]);
        } catch (java.sql.SQLException e) {
            MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                    "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
            throw e;
        }
        // [TODO START ZONE]
        int tmpl_0=tmpc_SQLTable.cols[0].vals.size();
        java.util.List<ListViewRowDataWrapper<String>> tmpc_SQLTable__listViewRowDataWrapper = new java.util.ArrayList<ListViewRowDataWrapper<String>>(tmpl_0);
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
            DatabaseMnm.DataSpec.STATUS_Product tmpk_Product_Status=null;
            Long tmpt_long=DatabaseMnm.convertIntegerAlikeSQLColToLong(
                tmpc_SQLTable.cols[5].vals.get(tmpc_int),
                tmpc_SQLTable.cols[5].javaType
            );
            if (tmpt_long!=null) {
            tmpk_Product_Status=DatabaseMnm.DataSpec.STATUS_Product.values()[
                tmpt_long.intValue()
            ];
            }
            String tmpt_str_1=null;
            if (tmpk_Selling_Request_Status==STATUS_Selling_Request.WaitForCheck) {tmpt_str_1=Misc.ThaiStr_DataSpec_Status_SR[0];}
            else if (tmpk_Selling_Request_Status==STATUS_Selling_Request.Acceapted) {
                if (tmpk_Product_Status==null) {tmpt_str_1="รอนำสินค้าเข้าคลัง";}
                else if (tmpk_Product_Status==STATUS_Product.NotYetSale) {tmpt_str_1="นำสินค้าเข้าคลังแล้ว/"+Misc.ThaiStr_DataSpec_Status_pd[0];}
                else if (tmpk_Product_Status==STATUS_Product.ForSale) {tmpt_str_1=Misc.ThaiStr_DataSpec_Status_pd[1];}
                else if (tmpk_Product_Status==STATUS_Product.SaledAndWaitForSend) {tmpt_str_1=Misc.ThaiStr_DataSpec_Status_pd[2];}
                else {tmpt_str_1=Misc.ThaiStr_DataSpec_Status_pd[3];}
            }
            else {tmpt_str_1=Misc.ThaiStr_DataSpec_Status_SR[1];}
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                tmpc_SQLTable.cols[6].vals.get(tmpc_int),tmpc_SQLTable.cols[6].javaType
            );
            String tmpk_dateStr= (java.time.Instant.ofEpochSecond(tmpu_epochTimeData)).atZone(java.time.ZoneOffset.UTC).toLocalDate().toString();
            String tmpk_repr="(นัดตรวจสอบสภาพเมื่อ "+tmpk_dateStr+") ["+tmpk_Selling_Request_ID+": "+tmpt_str_1+"] โดยคุณ \""+tmpk_Customer_Full_Name+"\" (ยี่ห้อ/รุ่น: "+tmpk_Selling_Request_Brand+"/"+tmpk_Selling_Request_Model+")";
            tmpc_SQLTable__listViewRowDataWrapper.add(
                new ListViewRowDataWrapper<String>(tmpk_Selling_Request_ID, tmpk_repr)
            );
        }
        // [TODO END ZONE]
        pdListView.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }
    private void helper_changePageForViewDataOfRow() throws java.io.IOException {
        ListViewRowDataWrapper<String> tmpt_lvrdw = pdListView.getSelectionModel().getSelectedItem();
        if (tmpt_lvrdw!=null) {
        Main.switchToSpecificPagename("product_detail",new Object[] {this.getClass(),tmpt_lvrdw});
        }
    }
}
