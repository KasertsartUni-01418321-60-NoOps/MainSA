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
            ListViewRowDataWrapper<Integer> tmpu_default_lvrdw_int;
            tmpu_default_lvrdw_int=new ListViewRowDataWrapper<Integer>(1,"รับซื้อ/ซ่อมด้วย");
            comboBox_Action.getItems().add(tmpu_default_lvrdw_int);
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(-1,"รับซื้อ/ไม่ซ่อม"));
            comboBox_Action.getItems().add(new ListViewRowDataWrapper<Integer>(0,"ปฏิเสธการรับซื้อ"));
            comboBox_Action.setValue(tmpu_default_lvrdw_int);
            tmpu_default_lvrdw_int=new ListViewRowDataWrapper<Integer>(1,"+1");
            comboBox_creditAction.getItems().add(tmpu_default_lvrdw_int);
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(0,"ไม่แก้ไข"));
            comboBox_creditAction.getItems().add(new ListViewRowDataWrapper<Integer>(-1,"-1"));
            comboBox_creditAction.setValue(tmpu_default_lvrdw_int);
            Object[] tmpt_arr_obj = (Object[])((Object[])FXRouter.getData())[2];
            textField_Brand.setText(tmpt_arr_obj[0].toString());
            textField_Model.setText(tmpt_arr_obj[1].toString());
            comboBox_Action.setOnAction(event -> {
                try {
                    Integer selectedOption = comboBox_Action.getValue().ref;
                    if (selectedOption==1) {
                        textArea_rpmDesc.setDisable(false);
                        textField_Price.setDisable(false);
                    } else if (selectedOption==-1) {
                        textArea_rpmDesc.setDisable(true);
                        textField_Price.setDisable(false);
                    } else {
                        textArea_rpmDesc.setDisable(true);
                        textField_Price.setDisable(true);
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
}
