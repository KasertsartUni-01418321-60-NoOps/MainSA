package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_CustomerData {
    @FXML
    private TextField textField_Name;
    @FXML
    private TextArea textArea_Addr;
    @FXML
    private TextField textField_Tel;
    @FXML
    private TextField textField_Credit;
    // <UNUSED>
    // @FXML private ListView<ListViewRowDataWrapper<String>> listView_BSR;

    @FXML
    private void initialize() throws java.sql.SQLException {
        try {
            String custName = ((ListViewRowDataWrapper<String>) ((Object[]) com.github.saacsos.FXRouter
                    .getData())[1]).ref;
            DatabaseMnm.Table tmpc_SQLTable = null;
            try {
                tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
                        null,
                        "SELECT * FROM Customer WHERE Customer_Full_Name=?",
                        false,
                        true,
                        null,
                        new Object[] { custName })[1]);
            } catch (java.sql.SQLException e) {
                MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดตรวจสอบความถูกต้องของไฟล์โปรแกรมและข้อมูลและตรวจสอบว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
                throw e;
            }
            textField_Name.setText(custName);
            String tmpt_str = (String) (tmpc_SQLTable.cols[1].vals.get(0));
            if (tmpt_str != null) {
                textArea_Addr.setText(tmpt_str);
            } else {
                textArea_Addr.setText("<ว่างเปล่า>");
                textArea_Addr.setDisable(true);
            }
            textField_Tel.setText((String) (tmpc_SQLTable.cols[2].vals.get(0)));
            textField_Credit.setText(
                    DatabaseMnm.convertIntegerAlikeSQLColToLong(
                            tmpc_SQLTable.cols[3].vals.get(0), tmpc_SQLTable.cols[3].javaType).toString());
            // <UNUSED>
            // helper_listViewBSR_Update(custName);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML
    private void onBack_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("customer_list");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
