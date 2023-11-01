package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class UICtrl_Example {
	// entire exception handling info: mode=fatal
	@javafx.fxml.FXML
	private void onButton1Pressed() throws Throwable {
		try {
			try {
				Main.switchToSpecificPagename("");
			} catch (java.io.IOException e1) {
				throw e1;
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
}