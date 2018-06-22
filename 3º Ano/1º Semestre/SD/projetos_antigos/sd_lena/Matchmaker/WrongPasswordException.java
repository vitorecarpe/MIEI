/*
 * Exceção para quando o utilizador insere a password errada.
 */

public class WrongPasswordException extends Exception {
	
	public WrongPasswordException() {
		super();
	}
	
	public WrongPasswordException(String msg) {
		super(msg);
	}
	
	public WrongPasswordException(Throwable cause) {
		super(cause);
	}

	public WrongPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
