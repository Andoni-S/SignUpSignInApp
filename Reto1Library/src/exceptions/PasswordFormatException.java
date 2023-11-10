package exceptions;

/**
 * Exception that triggers when the password introduced does not comply with the
 * restrictions
 *
 * @author Jagoba Bartolomé
 */
public class PasswordFormatException extends Exception {

    public PasswordFormatException() {
        super();
    }

    public PasswordFormatException(String msg) {
        super(msg);
    }
}
