public class UICtrl_Test {
	@javafx.fxml.FXML
	private void onMainButtonPressed() throws Throwable {
		Main.tempChangeCSS("Test_1");
	}

	@javafx.fxml.FXML
	private void onExitButtonPressed() throws Throwable {
		// Add code here to open a web browser or a media player to play the Rickroll
		// video
		// You can use Java's Desktop class or external library to open a URL.
		// For simplicity, you can use the default web browser to open the Rickroll URL.
		try {
			java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=oHg5SJYRHA0"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
