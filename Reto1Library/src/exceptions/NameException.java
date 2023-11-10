package exceptions;

/**
 * Exception that triggers when the written full name does not comply with the
 * restrictions.
 *
 * @author Jagoba Bartolomé
 */
public class NameException extends Exception {

    public NameException() {
        super();
    }

    public NameException(String msg) {
        super(msg);
    }
}
