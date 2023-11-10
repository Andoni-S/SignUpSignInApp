package exceptions;

/**
 * Exception that triggers when an error relative to the connection with server
 * is presented.
 *
 * @author Jagoba Bartolomé
 */
public class ServerErrorException extends Exception {

    public ServerErrorException() {
        super();
    }

    public ServerErrorException(String msg) {
        super(msg);
    }
}
