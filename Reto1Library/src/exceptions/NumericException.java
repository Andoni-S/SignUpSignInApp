package exceptions;

/**
 * Exception that triggers when a non-numeric character is introduced in a field
 * where only numeric characters are required.
 *
 * @author Jagoba Bartolomé
 */
public class NumericException extends Exception {

    public NumericException() {
        super();
    }

    public NumericException(String msg) {
        super(msg);
    }
}
