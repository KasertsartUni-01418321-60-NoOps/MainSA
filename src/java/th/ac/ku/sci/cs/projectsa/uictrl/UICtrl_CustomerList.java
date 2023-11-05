package th.ac.ku.sci.cs.projectsa.uictrl;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class UICtrl_CustomerList {
    @FXML private ListView<ListViewRowDataWrapper> custListView;
    
    @FXML
    private void initialize() throws java.sql.SQLException{
        try {
			DatabaseMnm.Table tmpc_SQLTable = null;
			try {
				tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
						null,
						"SELECT Customer_Full_Name FROM Customer",
						false,
						true,
						null,
						null)[1]);
			} catch (java.sql.SQLException e) {
				MyExceptionHandling.handleFatalException_simplev1(e, true, "MainApp|DatabaseMnm", null, null,
						"<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>");
				throw e;
			}
            int tmpl_0=tmpc_SQLTable.cols[0].vals.size();
            ListViewRowDataWrapper[] tmpc_SQLTable__listViewRowDataWrapper = new ListViewRowDataWrapper[tmpl_0];
            for (int tmpc_int =0; tmpc_int<tmpl_0; tmpc_int++) {
                String tmpt_str=(String)(tmpc_SQLTable.cols[0].vals.get(tmpc_int));
                tmpc_SQLTable__listViewRowDataWrapper[tmpc_int]=new ListViewRowDataWrapper(tmpt_str, tmpt_str);
            }
            custListView.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
            custListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if (event.getClickCount() >= 2) {
                            ListViewRowDataWrapper tmpt_lvrdw = custListView.getSelectionModel().getSelectedItem();
                            Main.switchToSpecificPagename("customer_data",tmpt_lvrdw);
                        }
                    } catch (Throwable e) {
                        MyExceptionHandling.handleFatalException(e);
                    }
                }
            });
        }
        catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    // REMARK: เราจะcopy function นี้ไปใช้ได้เลย เวลาต้องการ backToHomePage
    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void creatNewCustomer_Button() throws java.io.IOException, Throwable {
        try {
            Main.switchToSpecificPagename("customer_data");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}

