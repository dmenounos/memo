package aminet.naming;

/**
 * Represents errors that occur from the naming codebase.
 *
 * @author Dimitris Menounos
 */
public class NamingException extends RuntimeException {
    
    /**
     * Creates instance without an error message.
     */
    public NamingException() {
    }
    
    /**
     * Creates instance with a specified error message.
     *
     * @param message the error message.
     */
    public NamingException(String message) {
        super(message);
    }
    
    /**
     * Creates instance with a specified error cause.
     *
     * @param cause the error cause.
     */
    public NamingException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Creates instance with a specified error message and cause.
     *
     * @param message the error message.
     * @param cause the error cause.
     */
    public NamingException(String message, Throwable cause) {
        super(message, cause);
    }
}
