public class AlreadyLoggedInException extends Exception {
    
    public AlreadyLoggedInException() {
        super();
    }
    
    public AlreadyLoggedInException(String msg) {
        super(msg);
    }
    
    public AlreadyLoggedInException(Throwable cause) {
	super(cause);
    }

    public AlreadyLoggedInException(String msg, Throwable cause) {
	super(msg, cause);
    }
}