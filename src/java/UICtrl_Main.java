public class UICtrl_Main {
	@javafx.fxml.FXML
	private void onButton1Pressed() throws Throwable {
		try {
			try {
				Main.tempSwitchToTestPage();
			} catch (java.io.IOException e1) {
				throw e1;
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}
}