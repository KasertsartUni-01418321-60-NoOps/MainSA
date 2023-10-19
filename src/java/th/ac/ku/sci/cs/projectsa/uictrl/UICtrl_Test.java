package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class UICtrl_Test implements javafx.fxml.Initializable {
	private boolean isDarkMode = false;
	@javafx.fxml.FXML
	private javafx.scene.control.Label MIDIIndicatorLabel;
	private static UICtrl_Test currentInstance;

	@Override
	// TODO: exception handling
	public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
		UICtrl_Test.currentInstance = this;
		UICtrl_Test.MIDIPlayerCallback();
	}

	// TODO: excepiton handling
	public static void MIDIPlayerCallback() {
		if (currentInstance != null) {
			try {
				currentInstance.MIDIIndicatorLabel.setText("Current MIDI song: " + MIDIPlayer.getCurrentSongName());
			} catch (RuntimeException e) {
			} // ignore error
		}
	}

	// entire exception handling info: mode=fatal
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

	// entire exception handling info: mode=fatal
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
