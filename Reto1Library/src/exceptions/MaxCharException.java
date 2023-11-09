package exceptions;

/**
 * Exceptions that triggers when the limit of characters is reached.
 *
 * @author Jagoba Bartolomé
 */
public class MaxCharException extends Exception {

    public MaxCharException() {
        super();
    }

    public MaxCharException(String msg) {
        super(msg);
    }
}
