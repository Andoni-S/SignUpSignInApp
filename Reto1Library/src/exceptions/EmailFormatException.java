package exceptions;

/**
 * Exception that triggers when the email format does not comply with the
 * specified format.
 *
 * @author Jagoba Bartolomé
 */
public class EmailFormatException extends Exception {

    public EmailFormatException() {
        super();
    }

    public EmailFormatException(String msg) {
        super(msg);
    }
}
