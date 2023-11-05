package th.ac.ku.sci.cs.projectsa.uictrl;

import javafx.fxml.FXML;
import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_BuyHistory {
     @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onCreatePurchase_Button() throws java.io.IOException, Throwable {
        try {
            Main.switchToSpecificPagename("buy_from_vender");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
