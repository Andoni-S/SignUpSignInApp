package exceptions;

/**
 * Exception that triggers when the Email provided already exist.
 *
 * @author Jagoba Bartolomé
 */
public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException() {
        super();
    }

    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
