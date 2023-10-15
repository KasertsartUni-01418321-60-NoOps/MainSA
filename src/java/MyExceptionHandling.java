import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyExceptionHandling {
	public static final String appFatalHeader = "Application has fatal exception below:";
	public static boolean isFatal = false;

	public static void handleFatalException(Throwable e) throws Throwable {
		try {
			MyExceptionHandling.isFatal = true;
			MyExceptionHandling.reportFatalExceptionInGUI(e);
			MyExceptionHandling.reportFatalExceptionInCUI(e);
		} catch (Throwable e1) {
			throw e1;
		} finally {
			try {
				throw e;
			} catch (Throwable e0) {
				throw e0;
			} finally {
				try {
					System.exit(255);
				} catch (Throwable e1) {
					throw e1;
				} finally {
					throw e;
				}
			}
		}
	}

	private static String getStackTraceAsString(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	private static void reportFatalExceptionInGUI(Throwable e) {
		try {
			javax.swing.JOptionPane.showMessageDialog(null,
					appFatalHeader + "\n\n" + getStackTraceAsString(e),
					"Application Fatal (at " + getISODateTimeString() + ")",
					javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (Exception e1) {
			System.out.println("[" + getISODateTimeString() + "|App|Err] GUI exception reporting has exception below:");
			e1.printStackTrace();
		}
	}

	private static void reportFatalExceptionInCUI(Throwable e) {
		System.out.println("[" + getISODateTimeString() + "|App|FATAL] " + appFatalHeader);
		e.printStackTrace();
	}

	private static String getISODateTimeString() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
