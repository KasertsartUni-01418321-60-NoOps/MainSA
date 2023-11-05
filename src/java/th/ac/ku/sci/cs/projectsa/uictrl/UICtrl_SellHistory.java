package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

import javafx.fxml.*;
import th.ac.ku.sci.cs.projectsa.MyExceptionHandling;
import javafx.scene.control.*;

public class UICtrl_SellHistory {
    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onCreatQuotation_Button() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("quotation");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}
