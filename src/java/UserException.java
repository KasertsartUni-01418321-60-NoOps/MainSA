// follow default constructors
public class UserException extends Exception {
	public UserException() {
		super();
	}

	public UserException(String var1) {
		super(var1);
	}

	public UserException(String var1, Throwable var2) {
		super(var1, var2);
	}

	public UserException(Throwable var1) {
		super(var1);
	}

	protected UserException(String var1, Throwable var2, boolean var3, boolean var4) {
		super(var1, var2, var3, var4);
	}
}