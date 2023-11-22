package th.ac.ku.sci.cs.projectsa.fun;

import th.ac.ku.sci.cs.projectsa.Main;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import sun.misc.Unsafe;

// ใส่เพื่อความสิริมงคล ใครอยากจะแทรกลงใน UI button triggering on press ก็เชิญ แต่บอกก่อนว่า unsafe แบบนี้ exception handling ใน java ใช้ไม่ได้แล้วนะ แปลว่าโค้ด cleanup ก็จะ555 หาก งง ให้ไปอ่านที่ผมพิมใน discrod
public class UnsafeStuff {
	public static void crashJVMLamo() {
		try {
			real_crashJVMLamo();
		} catch (Exception e) {
			System.err.println(Main.clReportHeader("MainApp|FunStuff|UnsafeStuff", "DEVFUNERR")
					+ "Caught Throwable typed 'Exception', StackTrace is displayed below (no GUI reporting):");
			e.printStackTrace();
		}
	}
    private static void real_crashJVMLamo() throws NoSuchFieldException,SecurityException,IllegalArgumentException,IllegalAccessException, java.net.URISyntaxException,java.io.IOException {
		java.lang.reflect.Field f=null;
		try {
			f = Unsafe.class.getDeclaredField("theUnsafe");
		} catch (NoSuchFieldException | SecurityException e) {
			throw e;
		}
		f.setAccessible(true);
		Unsafe unsafe=null;
		try {
			unsafe = (Unsafe) f.get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw e;
		}
		try {
			java.awt.Desktop.getDesktop().browse(
				new URI(
					(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParentFile().getAbsolutePath().replace('\\', '/')
					)
					);
			java.awt.Desktop.getDesktop().browse(new URI("http://bugreport.java.com/bugreport/crash.jsp"));
		} catch (IOException | URISyntaxException e) {
			throw e;
		}
		while (true) {
			Long[] tmpt_arrlong=new Long[] {new java.util.Random().nextLong(),new java.util.Random().nextLong()};
			if (tmpt_arrlong[0]<0) {
				continue;
			}
			unsafe.getAddress(tmpt_arrlong[0]);
		}
	}
}
