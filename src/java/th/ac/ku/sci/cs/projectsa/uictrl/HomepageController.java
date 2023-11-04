package th.ac.ku.sci.cs.projectsa.uictrl;

import javafx.fxml.FXML;
import th.ac.ku.sci.cs.projectsa.Main;
import th.ac.ku.sci.cs.projectsa.MyExceptionHandling;

public class HomepageController {
    @FXML
    private void warehouseButton()
        throws java.security.NoSuchAlgorithmException, Throwable {
        try {
            Main.switchToSpecificPagename("warehouse");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}
