package th.ac.ku.sci.cs.projectsa.uictrl;
import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_CustomerList {
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
