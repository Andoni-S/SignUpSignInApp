package exceptions;
/**
 * 
 * @author Andoni Sanz
 */
public class ServerErrorException extends Exception {
	public ServerErrorException() {
		super();
	}
	public ServerErrorException(String msg)
	{
		super(msg);
	}
}
