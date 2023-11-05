package th.ac.ku.sci.cs.projectsa.uictrl;
import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_CustomerList {
    // REMARK: เราจะcopy function นี้ไปใช้ได้เลย เวลาต้องการ backToHomePage
    @FXML private void backHomepage() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}
