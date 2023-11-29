package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UICtrl_AddItem {
    @FXML
    private ComboBox<ListViewRowDataWrapper<String>> selectedSRID;
    @FXML
    private TextArea rpDetail;
    @FXML
    private TextField spinnerDouble_Price;
    private long buyPrice=0;

    @FXML
    private void initialize() throws java.sql.SQLException {
        try {
            rpDetail.setDisable(true);
            comboBox_srID_Helper1();
            selectedSRID.setOnAction(event -> {
                try {
                    String srID = selectedSRID.getValue().ref;
                    DatabaseMnm.Table tmpc_SQLTable = null;
                    try {
                        tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                                null,
                                "SELECT SR.Selling_Request_Repairment_Description, SR.Selling_Request_Paid_Amount FROM Selling_Request AS SR WHERE SR.Selling_Request_ID=?",
                                false,
                                true,
                                null,
                                new Object[] { srID })[1]);
                    } catch (java.sql.SQLException e) {
                        MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                                "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                        throw e;
                    }
                    String tmpk_rpmDesc = (String) (tmpc_SQLTable.cols[0].vals.get(0));
                    if (tmpk_rpmDesc == null) {
                        rpDetail.setDisable(true);
                        rpDetail.setText("<สินค้าของสัญญาซื้อนี้ ไม่มีการซ่อม>");
                    } else {
                        rpDetail.setDisable(false);
                        rpDetail.setText(tmpk_rpmDesc);
                    }
                    buyPrice=(Long) DatabaseMnm.convertIntegerAlikeSQLColToLong(
                        tmpc_SQLTable.cols[1].vals.get(0),
                        tmpc_SQLTable.cols[1].javaType
                    );
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
            Main.switchToSpecificPagename("warehouse");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onpressed_Button_Save() throws java.io.IOException, java.sql.SQLException {
        try {
            // Note that this always got from DB, so no validation
            // REMARK: for FK check, the comboBox_srID_Helper1 done for us
            String formval_srID = null;
            try {
                formval_srID = selectedSRID.getSelectionModel().getSelectedItem().ref;
            } catch (NullPointerException e) {
                helper2();
                return;
            }
            String formval_rpDetail = rpDetail.getText();
            if (rpDetail.isDisable()) {
                formval_rpDetail = null;
            }
            // [VALIDZONE]
            DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Repairment_Description(formval_rpDetail);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__PRODUCT__Product_Price(spinnerDouble_Price.getText());
            if (tmpReason != null) {
                helper1();
                return;
            }
            Long formval_PdPRice = Long.parseLong(spinnerDouble_Price.getText());
            if (formval_PdPRice<=buyPrice) {
                helper3();
                return;
            }
            // [END VALID ZONE]
            String tmpt_str = null;
            while (true) {
                tmpt_str = Misc.generateRandomID();
                tmpReason = DataValidation.PerAttributeValidation.check__PRODUCT__Product_ID(tmpt_str);
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
                        "INSERT INTO Product VALUES (?, ?, ?, ?, ?);",
                        true,
                        false,
                        null,
                        new Object[] {
                                tmpt_str,
                                java.time.LocalDate.now().toEpochDay(),
                                formval_PdPRice,
                                (long) 0,
                                formval_srID
                        });
                if (formval_rpDetail != null) {
                    DatabaseMnm.runSQLcmd(
                            null,
                            "UPDATE Selling_Request SET Selling_Request_Repairment_Description=? WHERE Selling_Request_ID=?;",
                            true,
                            false,
                            null,
                            new Object[] {
                                    formval_rpDetail,
                                    formval_srID
                            });
                }
                DatabaseMnm.mainDbConn.commit();
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            ;
            Main.switchToSpecificPagename("warehouse");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    private void helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสินค้าเข้าคลังได้", "เนื่องจากกรอกข้อมูลผิดรูปแบบ", false);
    }

    // REMARK: เผื่อกรณี NULL ของ ComboBox
    private void helper2() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสินค้าเข้าคลังได้", "เนื่องจาก ID สัญญาซื้อ ยังไม่ได้ถูกเลือก", false);
    }

    private void helper3() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
                "ไม่สามารถเพ่ิมข้อมูลสินค้าเข้าคลังได้", "เนื่องจากราคาขายไม่เกินราคาซื้อ", false);
    }

    private void comboBox_srID_Helper1() throws java.sql.SQLException {
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT SR.Selling_Request_ID FROM Selling_Request AS SR LEFT JOIN Product AS PD ON SR.Selling_Request_ID=PD.Selling_Request_ID WHERE PD.Selling_Request_ID IS NULL AND SR.Selling_Request_Status=2 ORDER BY SR.Selling_Request_ID ASC",
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
        selectedSRID.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }
}
