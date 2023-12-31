package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UICtrl_CheckItems {
    // set to default of comboBox default seleection
    private int selectedOption = 1;
    @FXML
    private TextField textField_Brand;
    @FXML
    private TextField textField_Model;
    @FXML
    private TextArea textArea_rpmDesc;
    @FXML
    private TextField spinnerDouble_Price;
    @FXML
    private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_Action;
    @FXML
    private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_creditAction;

    @FXML
    private void initialize() {
        try {
            ListViewRowDataWrapper<Integer> tmpu_default_lvrdw_int;
            tmpu_default_lvrdw_int = new ListViewRowDataWrapper<Integer>(1, "รับซื้อ/ซ่อมด้วย");
            comboBox_Action.getItems().add(tmpu_default_lvrdw_int);
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(-1, "รับซื้อ/ไม่ซ่อม"));
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(0, "ปฏิเสธการรับซื้อ"));
            comboBox_Action.setValue(tmpu_default_lvrdw_int);
            tmpu_default_lvrdw_int = new ListViewRowDataWrapper<Integer>(1, "+1");
            comboBox_creditAction.getItems().add(tmpu_default_lvrdw_int);
            // comboBox_creditAction.getItems().add(new
            // ListViewRowDataWrapper<Integer>(0,"ไม่แก้ไข"));
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(-1, "-1"));
            comboBox_creditAction.setValue(tmpu_default_lvrdw_int);
            Object[] tmpt_arr_obj = (Object[]) ((Object[]) FXRouter.getData())[2];
            textField_Brand.setText(tmpt_arr_obj[0].toString());
            textField_Model.setText(tmpt_arr_obj[1].toString());
            comboBox_Action.setOnAction(event -> {
                try {
                    selectedOption = comboBox_Action.getValue().ref;
                    if (selectedOption == 1) {
                        textArea_rpmDesc.setDisable(false);
                        spinnerDouble_Price.setDisable(false);
                    } else if (selectedOption == -1) {
                        textArea_rpmDesc.setDisable(true);
                        spinnerDouble_Price.setDisable(false);
                    } else {
                        textArea_rpmDesc.setDisable(true);
                        spinnerDouble_Price.setDisable(true);
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
            Main.switchToSpecificPagename("buy_data", new Object[] {
                    this.getClass(),
                    (((Object[]) FXRouter.getData())[1])
            });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onSave_Button() throws java.sql.SQLException, java.io.IOException {
        try {
            // [PART: Valid]
            DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Brand(textField_Brand.getText());
            if (tmpReason != null) {
                onSave_Button__Helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation
                    .check__SELLING_REQUEST__Selling_Request_Model(textField_Model.getText());
            if (tmpReason != null) {
                onSave_Button__Helper1();
                return;
            }
            if (selectedOption != 0) {
                tmpReason = DataValidation.PerAttributeValidation
                        .check__SELLING_REQUEST__Selling_Request_Paid_Amount(spinnerDouble_Price.getText());
                if (tmpReason != null) {
                    onSave_Button__Helper1();
                    return;
                }
            }
            if (selectedOption == 1) {
                tmpReason = DataValidation.PerAttributeValidation
                        .check__SELLING_REQUEST__Selling_Request_Repairment_Description(textArea_rpmDesc.getText());
                if (tmpReason != null) {
                    onSave_Button__Helper1();
                    return;
                }
            }
            // [PART: Update SR]
            String SR_ID = ((ListViewRowDataWrapper<String>) ((Object[]) FXRouter.getData())[1]).ref;
            Object SR_Price = Long.class;
            Object SR_rpm = String.class;
            int tmpt_int = 1;
            if (comboBox_Action.getValue().ref != 0) {
                tmpt_int = 2;
                SR_Price = Long.parseLong(spinnerDouble_Price.getText());
            }
            if (comboBox_Action.getValue().ref == 1) {
                SR_rpm = textArea_rpmDesc.getText();
            }
            try {
                DatabaseMnm.runSQLcmd(
                        null,
                        "UPDATE Selling_Request SET Selling_Request_Status=?, Selling_Request_Brand=?,Selling_Request_Model=?,Selling_Request_Paid_Amount=?,Selling_Request_Repairment_Description=?  WHERE Selling_Request_ID=? ;",
                        true,
                        false,
                        null,
                        new Object[] {
                                tmpt_int,
                                textField_Brand.getText(),
                                textField_Model.getText(),
                                SR_Price,
                                SR_rpm,
                                SR_ID
                        });
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            ;
            // [PART: Update Ctm]
            if (comboBox_creditAction.getValue().ref == 1) {
                tmpt_int = 1;
            } else {
                tmpt_int = -1;
            }
            try {
                DatabaseMnm.runSQLcmd(
                        null,
                        "UPDATE Customer SET Customer_Credit_Amount=CASE WHEN (Customer_Credit_Amount + ?) < -500 THEN -500 WHEN (Customer_Credit_Amount + ?) > 500 THEN 500 ELSE (Customer_Credit_Amount + ?) END WHERE Customer_Full_Name=? ;",
                        true,
                        false,
                        null,
                        new Object[] {
                                tmpt_int, tmpt_int, tmpt_int,
                                (String) (((Object[]) (((Object[]) FXRouter.getData())[2]))[2])
                        });
                // ค่อย commit ทีเดียวๆ
                DatabaseMnm.mainDbConn.commit();
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            ;
            Main.switchToSpecificPagename("buy_data", new Object[] {
                    this.getClass(),
                    (((Object[]) FXRouter.getData())[1])
            });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    private void onSave_Button__Helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การแก้ไขข้อมูลผิดพลาด",
                "ไม่สามารถบันทึกข้อมูลการตรวจสอบสภาพสินค้าได้ เนื่องจากกรอกข้อมูลผิดรูปแบบ", null, false);
    }
}
