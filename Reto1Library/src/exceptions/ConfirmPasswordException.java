package exceptions;

/**
 * Exception that triggers when the confirmation Password does not coincide with
 * the Password
 *
 * @author Jagoba Bartolomé
 */
public class ConfirmPasswordException extends Exception {

    public ConfirmPasswordException() {
        super();
    }

    public ConfirmPasswordException(String msg) {
        super(msg);
    }
}
