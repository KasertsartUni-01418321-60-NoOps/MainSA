package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataValidation;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import java.time.LocalDate;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.input.KeyEvent;

public class UICtrl_Quotation {
    @FXML private ComboBox<ListViewRowDataWrapper<String>> comboBox_custName;
    @FXML private TextArea textArea_Loc;
    @FXML private Spinner<Double> spinner_Price;
    @FXML private Button button_defaultLoc;
    private String custLoc=null,pdID=null;

    @FXML private void initialize() throws java.sql.SQLException{
        try{
            pdID=(String) ((Object[])FXRouter.getData())[1];
            comboBox_custName_Helper1();
            comboBox_custName.setOnAction(event -> {
                try{
                    DatabaseMnm.Table tmpc_SQLTable = null;
                    try {
                        tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                                null,
                                "SELECT Customer_Address FROM Customer WHERE Customer_Full_Name=?",
                                false,
                                true,
                                null,
                                new Object[] {
                                    comboBox_custName.getValue().ref
                                }
                        )[1]);
                    } catch (java.sql.SQLException e) {
                        MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                                "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                        throw e;
                    }
                    custLoc=(String)(tmpc_SQLTable.cols[0].vals.get(0));
                    if  (custLoc==null) {
                        button_defaultLoc.setDisable(true);
                    } else {
                        button_defaultLoc.setDisable(false);
                    }
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            // SEP
            spinner_Price.setEditable(true);
            spinner_Price.setValueFactory(new DoubleSpinnerValueFactory(DatabaseMnm.DataSpec.RANGE_MIN__Buy_Request_Transportation_Price, DatabaseMnm.DataSpec.RANGE_MAX__Buy_Request_Transportation_Price, Misc.choosenDefaultValueFor_TPrice_AtQuotationPAge, Misc.choosenStepValueFor_TPrice_AtQuotationPAge));
            spinner_Price.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (!newValue) { // If the spinner editor loses focus
                        helper_refreshSpinnerDouble_1();
                    }
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalException(e);
                }
            });
            spinner_Price.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                try{
                    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                        helper_refreshSpinnerDouble_1();
                        event.consume();
                    } else if (event.getCode() == javafx.scene.input.KeyCode.UP){
                        helper_refreshSpinnerDouble_1(); // incase mal-value
                        spinner_Price.increment();
                        event.consume();

                    } else if (event.getCode() == javafx.scene.input.KeyCode.DOWN){
                        helper_refreshSpinnerDouble_1(); // incase mal-value
                        spinner_Price.decrement();
                        event.consume();
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
            Main.switchToSpecificPagename("product_detail",new Object[] {this.getClass(),pdID});
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    
     @FXML private void onpressed_DefaultLocButton() {
        textArea_Loc.setText(custLoc);
    }

    @FXML private void onpressed_SaveButton() throws java.io.IOException,java.sql.SQLException {
        try {
            // Note that this always got from DB, so no validation
            // REMARK: for pdID, it is no need of FK/PK validation because the flow of UI/data operation is designed
            String formval_custName=null;
            try {formval_custName = comboBox_custName.getSelectionModel().getSelectedItem().ref;}
            catch (NullPointerException e) {
                helper2();
                return;
            }
            Long formval_Date= LocalDate.now().toEpochDay();
            String formval_Loc = textArea_Loc.getText();
            helper_refreshSpinnerDouble_1();
            Double formval_tprice = spinner_Price.getValue();
            // [VALIDZONE]
            DatabaseMnm.DataValidation.DATAVALID_DECLINED_REASON tmpReason;
            tmpReason = DataValidation.PerAttributeValidation.check__BUY_REQUEST__Buy_Request_Location(formval_Loc);
            if (tmpReason != null) {
                helper1();
                return;
            }
            tmpReason = DataValidation.PerAttributeValidation.check__BUY_REQUEST__Buy_Request_Transportation_Price(formval_tprice);
            if (tmpReason != null) {
                helper1();
                return;
            }
            // [END VALID ZONE]
            try{
                DatabaseMnm.runSQLcmd(
                    null,
                    "INSERT INTO Buy_Request VALUES (?, ?, ?, ?, ?);",
                    true,
                    false,
                    null,
                    new Object[] {
                        formval_custName,
                        pdID,
                        formval_Date,
                        formval_tprice,
                        formval_Loc
                    }
                );
                DatabaseMnm.runSQLcmd(
                    null,
                    "UPDATE Product SET Product_Status=2 WHERE Product_ID=?;",
                    true,
                    false,
                    null,
                    new Object[] {
                        pdID
                    }
                );
                DatabaseMnm.mainDbConn.commit();
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            };
            onBack_Button();
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    private void helper1() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
						"ไม่สามารถเพ่ิมข้อมูลสัญญาขายได้", "เนื่องจากกรอกข้อมูลผิดรูปแบบ", false);
    }
    // REMARK: เผื่อกรณี NULL ของ ComboBox
    private void helper2() {
        Main.showAlertBox(Main.getPrimaryStage(), AlertType.ERROR, "การเพิ่มข้อมูลผิดพลาด",
						"ไม่สามารถเพ่ิมข้อมูลสัญญาขายได้", "เนื่องจากชื่อลูกค้ายังไม่ได้ถูกเลือก", false);
    }

    private void helper_refreshSpinnerDouble_1() {
        Double tmpk_data = null;
        try {
            tmpk_data = Double.parseDouble(spinner_Price.getEditor().getText());
        } catch (NumberFormatException e) {
            spinner_Price.getEditor().setText(spinner_Price.getValue().toString());
            return;
        }
        // Optionally, round the entered value
        Integer[] lenSpec = DataSpec.MINMAX_LENGTH_OF_ATTRIBS.get("Buy_Request_Transportation_Price");
        tmpk_data = DatabaseMnm.DataTransformation.doubleLengthCropping(tmpk_data, lenSpec[0], lenSpec[1], false);
        // Set the rounded value back to the spinner
        spinner_Price.getEditor().setText(tmpk_data.toString());
        spinner_Price.getValueFactory().setValue(tmpk_data);

    }

    private void comboBox_custName_Helper1() throws java.sql.SQLException {
        DatabaseMnm.Table tmpc_SQLTable = null;
        try {
            tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                    null,
                    "SELECT Customer_Full_Name FROM Customer ORDER BY Customer_Full_Name ASC",
                    false,
                    true,
                    null,
                    null
            )[1]);
        } catch (java.sql.SQLException e) {
            MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                    "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
            throw e;
        }
        int tmpl_0=tmpc_SQLTable.cols[0].vals.size();
        java.util.List<ListViewRowDataWrapper<String>> tmpc_SQLTable__listViewRowDataWrapper = new java.util.ArrayList<ListViewRowDataWrapper<String>>(tmpl_0);
        for (int tmpc_int =0; tmpc_int<tmpl_0; tmpc_int++) {
            String tmpt_str=(String)(tmpc_SQLTable.cols[0].vals.get(tmpc_int));
            tmpc_SQLTable__listViewRowDataWrapper.add(
                new ListViewRowDataWrapper<String>(tmpt_str, tmpt_str)
            );
        }
        comboBox_custName.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
    }
}

