package th.ac.ku.sci.cs.projectsa.fun;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

// ใส่เพื่อความสิริมงคล ใครอยากจะแทรกลงใน UI button triggering on press ก็เชิญ แต่บอกก่อนว่า unsafe แบบนี้ exception handling ใน java ใช้ไม่ได้แล้วนะ แปลว่าโค้ด cleanup ก็จะ555 หาก งง ให้ไปอ่านที่ผมพิมใน discrod
public class UnsafeStuff {
    public static void crashJVMLamo() {
		Field f=null;
		try {
			f = Unsafe.class.getDeclaredField("theUnsafe");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setAccessible(true);
		Unsafe unsafe=null;
		try {
			unsafe = (Unsafe) f.get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unsafe.putAddress(0, 0);
	}
}
