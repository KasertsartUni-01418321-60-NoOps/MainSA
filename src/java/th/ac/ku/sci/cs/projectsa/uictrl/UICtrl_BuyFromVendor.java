package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;

public class UICtrl_BuyFromVendor {
    @FXML private TextField textField_custName;
    @FXML private TextField textField_PdId;
    @FXML private TextArea textArea_MeetLoc;
    @FXML private TextArea textArea_PdLooks;
    @FXML private Spinner<Double> spinnerDouble_PaidPrice;
    @FXML private DatePicker datePicker_MeetDate;

    @FXML private void initialize() {
        try{
            spinnerDouble_PaidPrice.setValueFactory(new DoubleSpinnerValueFactory(0.01, 99999999.99, 25000.0, 100.0));
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        // มันไม่ update ให้ update เองแม่งๆ
        spinnerDouble_PaidPrice.increment(0);
        System.out.println(
            datePicker_MeetDate.getValue().toEpochDay()*86400+43200-1
        );
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onpressed_Button_Save() {}
}

