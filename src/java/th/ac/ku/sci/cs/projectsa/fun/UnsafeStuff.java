package th.ac.ku.sci.cs.projectsa.fun;

import th.ac.ku.sci.cs.projectsa.Main;
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
    private static void real_crashJVMLamo() throws NoSuchFieldException,SecurityException,IllegalArgumentException,IllegalAccessException {
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
		unsafe.putAddress(0, 0);
	}
}
