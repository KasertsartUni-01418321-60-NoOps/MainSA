package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class UICtrl_Warehouse {

    @FXML private ComboBox<String> choice;
    // LESSI TODO: ยี่ห้อสินค้า
    private String[] filter = {"ยี่ห้อ", "รุ่นสินค้า", "สถานะของสินค้า"};

    @FXML private ListView<ListViewRowDataWrapper<String>> productListView;
    

    @FXML
    private void initialize() throws java.sql.SQLException {
        choice.getItems().addAll(filter);
        choice.setValue(filter[0]);
        // TODO: คัดกรอง query จาก filter
        // ทดสอบ filter
        choice.setOnAction(event -> {
            String selected = choice.getValue();
            System.out.println("Selected: " + selected);
        });
        
        try {
			DatabaseMnm.Table tmpc_SQLTable = null;
			try {
                // TODO: แสดงสินค้าให้คนเห็น
				tmpc_SQLTable = (DatabaseMnm.Table) (DatabaseMnm.runSQLcmd(
						null,
						"SELECT Product_ID FROM Product",
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
            java.util.List<ListViewRowDataWrapper<String>> tmpc_SQLTable__listViewRowDataWrapper = new java.util.ArrayList<ListViewRowDataWrapper<String>>(tmpl_0);
            for (int tmpc_int =0; tmpc_int<tmpl_0; tmpc_int++) {
                String tmpt_str=(String)(tmpc_SQLTable.cols[0].vals.get(tmpc_int));
                tmpc_SQLTable__listViewRowDataWrapper.add(
                new ListViewRowDataWrapper<String>(tmpt_str, tmpt_str)
                );
            }
            productListView.getItems().addAll(tmpc_SQLTable__listViewRowDataWrapper);
            productListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if (event.getClickCount() >= 2) {
                            ListViewRowDataWrapper<String> tmpt_lvrdw = productListView.getSelectionModel().getSelectedItem();
                            if (tmpt_lvrdw!=null ) {
                                // TODO: แสดงรายละเอียดของสินค้า
                                Main.switchToSpecificPagename("product_detail",new Object[] {this.getClass(),tmpt_lvrdw});
                            }
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

    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException,java.io.IOException {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onPressed_Button_addProduct() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("add_item");
        } catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
   

}
