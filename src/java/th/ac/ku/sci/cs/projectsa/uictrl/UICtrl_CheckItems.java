package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_CheckItems {

    // TODO:
    @FXML private void initialize() throws Throwable {
    }

    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            String tmpt_str=(String)(((Object[])FXRouter.getData())[1]);
            // TODO: โยน
            // 1: this class
            // 2: Param [1] ที่โยนจาก BuyData
            Main.switchToSpecificPagename("buy_history");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}
