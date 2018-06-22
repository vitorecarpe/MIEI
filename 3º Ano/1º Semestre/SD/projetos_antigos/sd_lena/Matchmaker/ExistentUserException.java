/*
 * Exceção para quando se tenta registar um utilizador já existente.
 */

public class ExistentUserException extends Exception {
    
    public ExistentUserException() {
        super();
    }
    
    public ExistentUserException(String msg) {
        super(msg);
    }
    
    public ExistentUserException(Throwable cause) {
    	super(cause);
    }

    public ExistentUserException(String msg, Throwable cause) {
    	super(msg, cause);
    }

}
