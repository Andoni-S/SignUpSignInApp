package exceptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class CredentialsException extends Exception {
	public CredentialsException() {
		super();
	}
	public CredentialsException(String msg)
	{
		super(msg);
	}
}