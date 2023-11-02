package exceptions;

public class ServerErrorException extends Exception {

	public ServerErrorException() {
		super();
	}
	public ServerErrorException(String msg)
	{
		super(msg);
	}
}
