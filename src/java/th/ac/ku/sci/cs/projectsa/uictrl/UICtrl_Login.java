package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_Login {
	// entire exception handling info: mode=fatal
	@FXML
	private TextField textField_userName;
	@FXML
	private PasswordField passwordField_passWord;
	@FXML
	private void onPressed_Button_Login() throws Throwable {
		try {
			try {
				Main.switchToSpecificPagename("homepage");
			} catch (java.io.IOException e1) {
				throw e1;
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
}