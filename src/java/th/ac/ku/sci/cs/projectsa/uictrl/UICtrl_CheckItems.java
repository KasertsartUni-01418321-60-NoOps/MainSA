package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.Misc.ListViewRowDataWrapper;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_CheckItems {

    @FXML private TextField textField_Brand;
    @FXML private TextField textField_Model;
    @FXML private TextArea textArea_rpmDesc;
    @FXML private TextField textField_Price;
    @FXML private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_Action;
    @FXML private ComboBox<ListViewRowDataWrapper<Integer>> comboBox_creditAction;
    @FXML private void initialize() {
        try{
            Object[] tmpt_arr_obj = (Object[])((Object[])FXRouter.getData())[2];
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(-1,"รับซื้อ/ไม่ซ่อม"));
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(0,"ปฏิเสธการรับซื้อ"));
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(1,"รับซื้อ/ซ่อมด้วย"));
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(-1,"-1"));
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(0,"ไม่แก้ไข"));
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(1,"+1"));
            textField_Brand.setText(tmpt_arr_obj[0].toString());
            textField_Model.setText(tmpt_arr_obj[1].toString());
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
}
