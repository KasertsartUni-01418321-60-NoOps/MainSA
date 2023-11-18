package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataTransformation;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UICtrl_CheckItems {
    // set to default of comboBox default seleection
    private int selectedOption=1;
    @FXML private TextField textField_Brand;
    @FXML private TextField textField_Model;
    @FXML private TextArea textArea_rpmDesc;
    @FXML private Spinner<Double> spinnerDouble_Price;
    @FXML private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_Action;
    @FXML private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_creditAction;
    @FXML private void initialize() {
        try{
            spinnerDouble_Price.setEditable(true);
            spinnerDouble_Price.setValueFactory(new DoubleSpinnerValueFactory(DatabaseMnm.DataSpec.RANGE_MIN__Selling_Request_Paid_Amount, DatabaseMnm.DataSpec.RANGE_MAX__Selling_Request_Paid_Amount, Misc.choosenDefaultValueFor_PaidAmount_AtCheckItemPAge, Misc.choosenStepValueFor_PaidAmount_AtCheckItemPAge));
            spinnerDouble_Price.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (!newValue) { // If the spinner editor loses focus
                        helper_refreshSpinnerDouble_1();
                    }
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            spinnerDouble_Price.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                try{
                    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                        helper_refreshSpinnerDouble_1();
                        event.consume();
                    } else if (event.getCode() == javafx.scene.input.KeyCode.UP){
                        helper_refreshSpinnerDouble_1(); // incase mal-value
                        spinnerDouble_Price.increment();
                        event.consume();

                    } else if (event.getCode() == javafx.scene.input.KeyCode.DOWN){
                        helper_refreshSpinnerDouble_1(); // incase mal-value
                        spinnerDouble_Price.decrement();
                        event.consume();
                    }
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            // SEP
            ListViewRowDataWrapper<Integer> tmpu_default_lvrdw_int;
            tmpu_default_lvrdw_int=new ListViewRowDataWrapper<Integer>(1,"รับซื้อ/ซ่อมด้วย");
            comboBox_Action.getItems().add(tmpu_default_lvrdw_int);
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(-1,"รับซื้อ/ไม่ซ่อม"));
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(0,"ปฏิเสธการรับซื้อ"));
            comboBox_Action.setValue(tmpu_default_lvrdw_int);
            tmpu_default_lvrdw_int=new ListViewRowDataWrapper<Integer>(1,"+1");
            comboBox_creditAction.getItems().add(tmpu_default_lvrdw_int);
            // comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(0,"ไม่แก้ไข"));
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(-1,"-1"));
            comboBox_creditAction.setValue(tmpu_default_lvrdw_int);
            Object[] tmpt_arr_obj = (Object[])((Object[])FXRouter.getData())[2];
            textField_Brand.setText(tmpt_arr_obj[0].toString());
            textField_Model.setText(tmpt_arr_obj[1].toString());
            comboBox_Action.setOnAction(event -> {
                try {
                    selectedOption = comboBox_Action.getValue().ref;
                    if (selectedOption==1) {
                        textArea_rpmDesc.setDisable(false);
                        spinnerDouble_Price.setDisable(false);
                    } else if (selectedOption==-1) {
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

    @FXML private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("buy_data", new Object[] {
                this.getClass(),
                (((Object[])FXRouter.getData())[1])
            });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onSave_Button() throws java.sql.SQLException, java.io.IOException {
        try {
            // [PART: Valid]
            DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Brand(textField_Brand.getText());
            if (tmpReason != null) {
                onSave_Button__Helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Model(textField_Model.getText());
            if (tmpReason != null) {
                onSave_Button__Helper1();
                return;
            }
            if (selectedOption!=0) {
                tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Paid_Amount(spinnerDouble_Price.getValue());
                if (tmpReason != null) {
                    onSave_Button__Helper1();
                    return;
                }
            }
            if (selectedOption==1) {
                tmpReason = DataValidation.PerAttributeValidation.check__SELLING_REQUEST__Selling_Request_Repairment_Description(textArea_rpmDesc.getText());
                if (tmpReason != null) {
                    onSave_Button__Helper1();
                    return;
                }
            } 
            // [PART: Update SR]
            String SR_ID= ((ListViewRowDataWrapper<String>)((Object[])FXRouter.getData())[1]).ref;
            int tmpt_int = 1;
            if (comboBox_Action.getValue().ref!=0) {
                tmpt_int=2;
            }
            try{
                DatabaseMnm.runSQLcmd(
                    null,
                    "UPDATE Selling_Request SET Selling_Request_Status=?, Selling_Request_Brand=?,Selling_Request_Model=?  WHERE Selling_Request_ID=? ;",
                    true,
                    false,
                    null,
                    new Object[] {
                        tmpt_int,
                        textField_Brand.getText(),
                        textField_Model.getText(),
                        SR_ID
                    }
                );
                    DatabaseMnm.mainDbConn.commit();
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            };
            // [PART: Create Rpm]
            // [PART: Update Ctm]
            Main.switchToSpecificPagename("buy_data", new Object[] {
                this.getClass(),
                (((Object[])FXRouter.getData())[1])
            });
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    private void helper_refreshSpinnerDouble_1() {
        Double tmpk_data=null;
        try {
            tmpk_data = Double.parseDouble(spinnerDouble_Price.getEditor().getText());
        } catch (NumberFormatException e) {
            spinnerDouble_Price.getEditor().setText(spinnerDouble_Price.getValue().toString());
            // TODO: delete this dEbug later
            System.out.println("FAIL");
            return;
        }
        // Optionally, round the entered value
        Integer[] lenSpec = DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Selling_Request_Paid_Amount");
        tmpk_data=DatabaseMnm.DataTransformation.doubleLengthCropping(tmpk_data, lenSpec[0], lenSpec[1],false);
        // Set the rounded value back to the spinner
        spinnerDouble_Price.getEditor().setText(tmpk_data.toString());
        spinnerDouble_Price.getValueFactory().setValue(tmpk_data);
        // TODO: delete this dEbug later
        System.out.println("GOOD:"+tmpk_data.toString());
    }

    private void onSave_Button__Helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การแก้ไขข้อมูลผิดพลาด",
						"ไม่สามารถบันทึกข้อมูลการตรวจสอบสภาพสินค้าได้ เนื่องจากกรอกข้อมูลผิดรูปแบบ", null, false);
    }
}
