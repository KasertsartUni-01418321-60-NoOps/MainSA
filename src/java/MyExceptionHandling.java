
public class MyExceptionHandling {
	public static final String appFatalHeader = "Application has fatal exception below:";
	public static boolean isFatal = false;

	public static void handleFatalException(Throwable e) throws Throwable {
		try {
			MyExceptionHandling.isFatal = true;
			MyExceptionHandling.reportFatalExceptionInCUI(e);
			MyExceptionHandling.reportFatalExceptionInGUI(e);
		} catch (Throwable e1) {
			throw e1;
		} finally {
			try {
				throw e;
			} catch (Throwable e0) {
				throw e0;
			} finally {
				try {
					try {
						javafx.application.Platform.exit();
					} catch (Throwable e2) {
						System.exit(255);
					} finally {
						// in case it don't shutdown lamo
						try {
							Thread.sleep(1000 * 30);
						} catch (InterruptedException e3) {
							// then force shutdown,so do nothing here
						}
						System.exit(255);
					}
				} catch (Throwable e1) {
					throw e1;
				} finally {
					throw e;
				}
			}
		}
	}

	private static String getStackTraceAsString(Throwable e) {
		java.io.StringWriter sw = new java.io.StringWriter();
		e.printStackTrace(new java.io.PrintWriter(sw));
		return sw.toString().replace("\t", "    ");
	}

	private static void reportFatalExceptionInGUI(Throwable e) {
		try {
			// Create a frame for the GUI
			javax.swing.JFrame frame = new javax.swing.JFrame("Application Fatal (at " + getISODateTimeString() + ")");
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
			javax.swing.JLabel introLabel = new javax.swing.JLabel(appFatalHeader);
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
			System.out.println("[" + getISODateTimeString() + "|App|Err] GUI exception reporting has exception below:");
			e1.printStackTrace();
		}
	}

	private static void reportFatalExceptionInCUI(Throwable e) {
		System.out.println("[" + getISODateTimeString() + "|App|FATAL] " + appFatalHeader);
		e.printStackTrace();
	}

	private static String getISODateTimeString() {
		return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
