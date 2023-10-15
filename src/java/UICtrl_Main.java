public class UICtrl_Main {
	@javafx.fxml.FXML
	private void onButton1Pressed() throws Throwable {
		try {
			Main.tempSwitchToTestPage();
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}
}