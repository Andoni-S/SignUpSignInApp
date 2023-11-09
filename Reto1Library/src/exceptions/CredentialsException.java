package exceptions;

/**
 * Exception that triggers when the credentials provided by the user do not
 * comply with the credentials requirements.
 *
 * @author Jagoba Bartolomé
 */
public class CredentialsException extends Exception {

    public CredentialsException() {
        super();
    }

    public CredentialsException(String msg) {
        super(msg);
    }
}
