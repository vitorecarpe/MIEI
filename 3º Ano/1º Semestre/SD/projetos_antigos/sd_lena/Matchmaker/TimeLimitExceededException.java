/*
 * Exceção para quando excedeu o tempo de escolha das personagens.
 */

public class TimeLimitExceededException extends Exception {
	
	public TimeLimitExceededException() {
		super();
	}
	
	public TimeLimitExceededException(String msg) {
		super(msg);
	}
	
	public TimeLimitExceededException(Throwable cause) {
		super(cause);
	}

	public TimeLimitExceededException(String msg, Throwable cause) {
		super(msg, cause);
	}
}