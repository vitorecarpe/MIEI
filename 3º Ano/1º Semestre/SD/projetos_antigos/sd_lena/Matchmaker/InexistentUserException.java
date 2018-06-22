/*
 * Exceção para quando o utilizador não existe.
 */

public class InexistentUserException extends Exception {
    
    public InexistentUserException() {
        super();
    }
    
    public InexistentUserException(String msg) {
        super(msg);
    }
    
    public InexistentUserException(Throwable cause) {
    	super(cause);
    }

    public InexistentUserException(String msg, Throwable cause) {
    	super(msg, cause);
    }
}