package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class MyExceptionHandling {
	public static final String appFatalHeader = "Application has fatal exception below:";
	public static boolean isFatal = false;
	public static boolean isTextReportingFatal = false;
	public static boolean haveDoneJavaFXExitingExceptionReport = false;

	// entire exception handling info: mode=no (because it would be recurisve
	// lamolamolamo)
	public static void handleFatalException(Throwable e) throws Throwable {
		boolean oldIsFatal=MyExceptionHandling.isFatal;
		try {
			MyExceptionHandling.isFatal = true;
			MyExceptionHandling.reportFatalExceptionInCUI(e, null, null);
			MyExceptionHandling.reportFatalExceptionInGUI(e, null, null);
		} catch (Throwable e1) {
			throw e1;
		} finally {
			// To prevent repeating of this process, or even inifinite-recursive
			if (oldIsFatal==false) {
				try {
					throw e;
				} catch (Throwable e0) {
					isTextReportingFatal = true;
					throw e0;
				} finally {
					try {
						try {
							MainAlt1.primaryApplication.stop();
						} catch (Throwable e2) {
							haveDoneJavaFXExitingExceptionReport = true;
							// changed my mind, no further exception report
							System.exit(255);
						}
						try {
							javafx.application.Platform.exit();
						} catch (Throwable e2) {

							if (haveDoneJavaFXExitingExceptionReport) {
							} else {
								// changed my mind, no further exception report
							}
							System.exit(255);
						}
						System.exit(255);
					} catch (Throwable e1) {
						throw e1;
					} finally {
						throw e;
					}
				}
			}
		}

	}

	// entire exception handling info: mode=no
	private static String getStackTraceAsString(Throwable e) {
		java.io.StringWriter sw = new java.io.StringWriter();
		java.io.PrintWriter pw = new java.io.PrintWriter(sw);
		e.printStackTrace(pw);
		String ret = sw.toString().replace("\t", "    ");
		pw.close();
		try {
			sw.close();
		} catch (java.io.IOException e1) {
		}
		return ret;
	}

	// entire exception handling info: mode=no
	private static void reportFatalExceptionInGUI(Throwable e, String title, String mainText) {
		try {
			if (title == null) {
				title = "Application Fatal (at " + Main.getISODateTimeString() + ")";
			}
			if (mainText == null) {
				mainText = appFatalHeader;
			}
			// Create a frame for the GUI
			javax.swing.JFrame frame = new javax.swing.JFrame(title);
			frame.setSize(800, 600);
			// Create a panel for the components
			javax.swing.JPanel panel = new javax.swing.JPanel();
			panel.setLayout(new java.awt.BorderLayout());
			// Create a custom panel for icon and message
			javax.swing.JPanel messagePanel = new javax.swing.JPanel();
			messagePanel.setLayout(new java.awt.BorderLayout());
			// Load an error logo (replace "path/to/error-logo.png" with the actual path)
			javax.swing.Icon errorIcon = javax.swing.UIManager.getIcon("OptionPane.errorIcon");
			javax.swing.JLabel errorIconLabel = new javax.swing.JLabel(errorIcon);
			messagePanel.add(errorIconLabel, java.awt.BorderLayout.WEST);
			// Create a label for the introduction text
			javax.swing.JLabel introLabel = new javax.swing.JLabel(mainText);
			messagePanel.add(introLabel, java.awt.BorderLayout.CENTER);
			// Add the custom message panel to the main panel
			panel.add(messagePanel, java.awt.BorderLayout.NORTH);
			// Create a non-editable text area for the stack trace
			javax.swing.JTextArea stackTraceTextArea = new javax.swing.JTextArea();
			stackTraceTextArea.setText(getStackTraceAsString(e));
			stackTraceTextArea.setEditable(false);
			panel.add(new javax.swing.JScrollPane(stackTraceTextArea), java.awt.BorderLayout.CENTER);
			// Add the panel to the frame and make it visible
			frame.add(panel);
			frame.setVisible(true);
			// Wait for the frame to be closed
			while (frame.isVisible()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e2) {
					// then maybe we can break from this
					break;
				}
			}
		} catch (Throwable e1) {
			System.out.println(Main.clReportHeader(null, "ERR")+"GUI exception reporting has exception below:");
			e1.printStackTrace();
		}
	}

	// entire exception handling info: mode=no
	private static void reportFatalExceptionInCUI(Throwable e, String title, String mainText) {
		if (title == null) {
			title = Main.clReportHeader(null, "FATAL");
		}
		if (mainText == null) {
			mainText = appFatalHeader;
		}
		System.out.println(title + mainText);
		e.printStackTrace();
	}

	// follow default constructors
	public static class UserException extends Exception {

		// entire exception handling info: mode=no
		public UserException() {
			super();
		}

		// entire exception handling info: mode=no
		public UserException(String var1) {
			super(var1);
		}

		// entire exception handling info: mode=no
		public UserException(String var1, Throwable var2) {
			super(var1, var2);
		}

		// entire exception handling info: mode=no
		public UserException(Throwable var1) {
			super(var1);
		}

		// entire exception handling info: mode=no
		protected UserException(String var1, Throwable var2, boolean var3, boolean var4) {
			super(var1, var2, var3, var4);
		}
	}

	// follow default constructors
	public static class UserRuntimeException extends RuntimeException {
		// entire exception handling info: mode=no
		public UserRuntimeException() {
			super();
		}

		// entire exception handling info: mode=no
		public UserRuntimeException(String var1) {
			super(var1);
		}

		// entire exception handling info: mode=no
		public UserRuntimeException(String var1, Throwable var2) {
			super(var1, var2);
		}

		// entire exception handling info: mode=no
		public UserRuntimeException(Throwable var1) {
			super(var1);
		}

		// entire exception handling info: mode=no
		protected UserRuntimeException(String var1, Throwable var2, boolean var3, boolean var4) {
			super(var1, var2, var3, var4);
		}
	}

}
