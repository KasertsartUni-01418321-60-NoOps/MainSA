package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class MyExceptionHandling {
	public static final String appFatalHeader = "Application has fatal exception below:";
	public static boolean isFatal = false;

	// entire exception handling info: mode=no (because it would be recurisve
	// lamolamolamo)
	public static void handleFatalException(Throwable e, boolean doGUI, String[] titleAndMainTextOfCUIAndGUI) {
		try {
			boolean oldIsFatal = MyExceptionHandling.isFatal;
			try {
				MyExceptionHandling.isFatal = true;
				MyExceptionHandling.reportFatalExceptionInCUI(e, titleAndMainTextOfCUIAndGUI[0],
						titleAndMainTextOfCUIAndGUI[1]);
				if (doGUI) {
					MyExceptionHandling.reportFatalExceptionInGUI(e, titleAndMainTextOfCUIAndGUI[2],
							titleAndMainTextOfCUIAndGUI[3]);
				}
			} catch (Throwable e1) {
				// do not throw, so caller could able to continue throwing main exception //
				// throw e1;
			} finally {
				// To prevent repeating of this process, or even inifinite-recursive
				if (oldIsFatal == false) {
					try {
						System.exit(255);
					} catch (Throwable e1) {
						try {
							MyExceptionHandling.handleFatalExitException(e1, null);
						} catch (Throwable e2) {
							// throw System.exit Throwable instead...
						}
						// do not throw, so caller could able to continue throwing main exception
						// //throw e1;
					}
				}
			}
		} catch (Throwable e0) {
			// do nothing so that main exception could be throw lamo
		}
	}

	// entire exception handling info: mode=no (because it would be recurisve
	// lamolamolamo)
	// REMARK: ที่สร้างอันนี้มาเพราะ ขก. แก้ lamo
	public static void handleFatalException(Throwable e) {
		try {
			MyExceptionHandling.handleFatalException(e, true, new String[] { null, null, null, null });
		} catch (Throwable e0) {
			// do nothing so that main exception could be throw lamo
		}
	}

	// TODO: entire exception handling info: mode=no
	public static void handleFatalExitException(Throwable e, String scope) {
		try {
			if (scope == null) {
				scope = "MainApp|ShutdownSystem";
			}
			String tmp1 = "Application has below fatal exception on app-shutdown system:";
			String tmp2 = "Application Exiting Fatal (at " + Main.getISODateTimeString() + " | at scope \""+scope+"\")";
			String[] tmp0 = new String[] { Main.clReportHeader(scope, "FATAL"), tmp1, tmp2, tmp1 };
			if (MyExceptionHandling.isFatal) {
				MyExceptionHandling.handleFatalException(e, false, tmp0);
			} else {
				MyExceptionHandling.handleFatalException(e, true, tmp0);
			}
		} catch (Throwable e0) {
			// do nothing so that main exception could be throw lamo
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
				title = "Application Fatal (at " + Main.getISODateTimeString() + " | at scope \""+"MainApp"+"\")";
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
			System.out.println(Main.clReportHeader(null, "ERR") + "GUI exception reporting has exception below:");
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
