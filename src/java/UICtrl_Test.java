
public class UICtrl_Test {
	private boolean isDarkMode = false;

	@javafx.fxml.FXML
	private void onMainButtonPressed() throws Throwable {
		try {
			if (isDarkMode) {
				Main.tempChangeCSS("Test");
				isDarkMode = false;
			} else {
				Main.tempChangeCSS("Test_1");
				isDarkMode = true;
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}

	@javafx.fxml.FXML
	private void onExitButtonPressed() throws Throwable {
		try {// Add code here to open a web browser or a media player to play the
				// Rickroll
				// video
				// You can use Java's Desktop class or external library to open a URL.
				// For simplicity, you can use the default web browser to open the Rickroll URL.

			// [disabled]
			// try {
			// java.awt.Desktop.getDesktop().browse(new
			// java.net.URI("https://www.youtube.com/watch?v=oHg5SJYRHA0"));
			// } catch (java.io.IOException | java.net.URISyntaxException e) {
			// throw e;
			// }
			// just test the fatal exception
			throw new Exception("RICKROLL");
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}
}
