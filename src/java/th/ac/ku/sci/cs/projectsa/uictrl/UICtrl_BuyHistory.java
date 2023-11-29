package th.ac.ku.sci.cs.projectsa.uictrl;

import javafx.fxml.FXML;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Product;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Selling_Request;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.time.LocalDate;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class UICtrl_BuyHistory {
    @FXML
    private ListView<ListViewRowDataWrapper<String>> srListView;
    @FXML
    private Button button_sortByDate;
    @FXML
    private Button button_sortByPdStatus;
    @FXML
    private Button button_CreatePurchase;

    @FXML
    private void initialize() throws java.sql.SQLException {
        try {
            if (((DatabaseMnm.DataSpec.STATUS_User) Main.globalVar
                    .get("loggedinUser_Role")) == DatabaseMnm.DataSpec.STATUS_User.Employee) {
                button_CreatePurchase.setDisable(true);
            }
            button_sortByDate.setDisable(true);
            srListView.setCellFactory(param -> new ListCell<ListViewRowDataWrapper<String>>() {
                @Override
                protected void updateItem(ListViewRowDataWrapper<String> item, boolean empty) {
                    super.updateItem(item, empty);
    
                    if (empty || item == null) {
                        setText(null);
                        setBackground(null);
                    } else {
                        setText(item.toString());
                        int tmpt_int = (int)item.params[0];
                        int[] tmpt_arr_int = Misc.javafxListViewCellRowBgColorBasedOnPdType[tmpt_int];
                        setBackground(new Background(new BackgroundFill(
                            Color.rgb(tmpt_arr_int[0],tmpt_arr_int[1],tmpt_arr_int[2]),
                            null, null)));
                        tmpt_arr_int = Misc.getHighestContrastFGColor(tmpt_arr_int);
                        setTextFill(Color.rgb(tmpt_arr_int[0],tmpt_arr_int[1],tmpt_arr_int[2]));
                        // copy จากข้างบนมาเลยๆ
                        selectedProperty().addListener((observable, oldValue, newValue) -> {
                            int tmpt_int_1=0;
                            int[] tmpt_arr_int_1=null;
                            if (isSelected()) {
                                tmpt_int_1 = (int)item.params[0];
                                tmpt_arr_int_1 = Misc.javafxListViewCellRowBgColorBasedOnPdType[tmpt_int_1];
                                setBackground(new Background(new BackgroundFill(
                                    Color.rgb(255-tmpt_arr_int_1[0],255-tmpt_arr_int_1[1],255-tmpt_arr_int_1[2]),
                                    null, null)));
                                tmpt_arr_int_1 = Misc.getHighestContrastFGColor(tmpt_arr_int_1);
                                setTextFill(Color.rgb(255-tmpt_arr_int_1[0],255-tmpt_arr_int_1[1],255-tmpt_arr_int_1[2]));
                            } else {
                                tmpt_int_1 = (int)item.params[0];
                                tmpt_arr_int_1 = Misc.javafxListViewCellRowBgColorBasedOnPdType[tmpt_int_1];
                                setBackground(new Background(new BackgroundFill(
                                    Color.rgb(tmpt_arr_int_1[0],tmpt_arr_int_1[1],tmpt_arr_int_1[2]),
                                    null, null)));
                                tmpt_arr_int_1 = Misc.getHighestContrastFGColor(tmpt_arr_int_1);
                                setTextFill(Color.rgb(tmpt_arr_int_1[0],tmpt_arr_int_1[1],tmpt_arr_int_1[2]));
                            }
                        });
                    }
                }
            });
            helper_listViewUpdate(-1);
            srListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
            srListView.setOnKeyPressed(event -> {
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
    private void onCreatePurchase_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("buy_from_vender");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onpressed_Button_sortByPdStatus() throws java.sql.SQLException {
        try {
            helper_listViewUpdate(1);
            button_sortByDate.setDisable(false);
            button_sortByPdStatus.setDisable(true);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onpressed_Button_sortByDate() throws java.sql.SQLException {
        try {
            helper_listViewUpdate(-1);
            button_sortByDate.setDisable(true);
            button_sortByPdStatus.setDisable(false);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    private void helper_listViewUpdate(int mode) throws java.sql.SQLException {
        srListView.getItems().clear();
        String orderQuery = null;
        if (mode <= 0) {
            orderQuery = "ORDER BY SR.Selling_Request_Meet_Date DESC, SR.rowid DESC";
        } else {
            orderQuery = "ORDER BY SR.Selling_Request_Status, COALESCE(PD.Product_Status, -1), SR.Selling_Request_Meet_Date DESC, SR.rowid DESC";
        }
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT SR.Selling_Request_ID,SR.Customer_Full_Name,SR.Selling_Request_Brand,SR.Selling_Request_Model,SR.Selling_Request_Status,PD.Product_Status,SR.Selling_Request_Meet_Date,PD.Product_ID FROM Selling_Request AS SR LEFT JOIN Product AS PD ON SR.Selling_Request_ID = PD.Selling_Request_ID "
                            + orderQuery,
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
            String tmpk_Selling_Request_ID = (String) (tmpc_SQLTable.cols[0].vals.get(tmpc_int));
            String tmpk_Customer_Full_Name = (String) (tmpc_SQLTable.cols[1].vals.get(tmpc_int));
            String tmpk_Selling_Request_Brand = (String) (tmpc_SQLTable.cols[2].vals.get(tmpc_int));
            String tmpk_Selling_Request_Model = (String) (tmpc_SQLTable.cols[3].vals.get(tmpc_int));
            DatabaseMnm.DataSpec.STATUS_Selling_Request tmpk_Selling_Request_Status = DatabaseMnm.DataSpec.STATUS_Selling_Request
                    .values()[DatabaseMnm.convertIntegerAlikeSQLColToLong(
                            tmpc_SQLTable.cols[4].vals.get(tmpc_int),
                            tmpc_SQLTable.cols[4].javaType).intValue()];
            DatabaseMnm.DataSpec.STATUS_Product tmpk_Product_Status = null;
            Long tmpt_long = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[5].vals.get(tmpc_int),
                    tmpc_SQLTable.cols[5].javaType);
            if (tmpt_long != null) {
                tmpk_Product_Status = DatabaseMnm.DataSpec.STATUS_Product.values()[tmpt_long.intValue()];
            }
            String tmpt_str_1 = null;
            int tmpt_int=0;
            if (tmpk_Selling_Request_Status == STATUS_Selling_Request.WaitForCheck) {
                tmpt_int=0;
                tmpt_str_1 = Misc.ThaiStr_DataSpec_Status_SR[0];
            } else if (tmpk_Selling_Request_Status == STATUS_Selling_Request.Acceapted) {
                if (tmpk_Product_Status == null) {
                    tmpt_int=2;
                    tmpt_str_1 = "รอนำสินค้าเข้าคลัง";
                } else if (tmpk_Product_Status == STATUS_Product.NotYetSale) {
                    tmpt_int=3;
                    tmpt_str_1 = Misc.ThaiStr_DataSpec_Status_pd[0];
                } else if (tmpk_Product_Status == STATUS_Product.ForSale) {
                    tmpt_int=4;
                    tmpt_str_1 = Misc.ThaiStr_DataSpec_Status_pd[1];
                } else if (tmpk_Product_Status == STATUS_Product.SaledAndWaitForSend) {
                    tmpt_int=5;
                    tmpt_str_1 = Misc.ThaiStr_DataSpec_Status_pd[2];
                } else {
                    tmpt_int=6;
                    tmpt_str_1 = Misc.ThaiStr_DataSpec_Status_pd[3];
                }
            } else {
                tmpt_int=1;
                tmpt_str_1 = Misc.ThaiStr_DataSpec_Status_SR[1];
            }
            Long tmpu_epochTimeData = DatabaseMnm.convertIntegerAlikeSQLColToLong(
                    tmpc_SQLTable.cols[6].vals.get(tmpc_int), tmpc_SQLTable.cols[6].javaType);
            String tmpk_dateStr = LocalDate.ofEpochDay(tmpu_epochTimeData).toString();
            String tmpk_pdID = (String) (tmpc_SQLTable.cols[7].vals.get(tmpc_int));
            String tmpt_str_2 = "";
            if (tmpk_pdID != null) {
                tmpt_str_2 = " " + "(ID สินค้า: " + tmpk_pdID + ")" + " ";
            }
            String tmpk_repr = "(นัดตรวจสอบสภาพเมื่อ " + tmpk_dateStr + ") [" + tmpk_Selling_Request_ID + ": "
                    + tmpt_str_1 + tmpt_str_2 + "] โดยคุณ \"" + tmpk_Customer_Full_Name + "\" (ยี่ห้อ/รุ่น: \""
                    + tmpk_Selling_Request_Brand + "\"/\"" + tmpk_Selling_Request_Model + "\")";
            tmpc_SQLTable__listViewRowDataWrapper.add(
                    new ListViewRowDataWrapper<String>(tmpk_Selling_Request_ID, tmpk_repr,new Object[] {tmpt_int}));
        }
        srListView.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }

    private void helper_changePageForViewDataOfRow() throws java.io.IOException {
        ListViewRowDataWrapper<String> tmpt_lvrdw = srListView.getSelectionModel().getSelectedItem();
        if (tmpt_lvrdw != null) {
            Main.switchToSpecificPagename("buy_data", new Object[] { this.getClass(), tmpt_lvrdw });
        }
    }
}
