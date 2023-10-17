public class UICtrl_Main {
	@javafx.fxml.FXML
	private void onButton1Pressed() throws Throwable {
		try {
			Main.tempSwitchToTestPage();
			java.awt.Toolkit.getDefaultToolkit().beep(); // เสียงเพื่อสิริมงคล55
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}
}