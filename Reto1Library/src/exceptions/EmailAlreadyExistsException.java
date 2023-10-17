/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author 2dam
 */
public class EmailAlreadyExistsException extends Exception {

	public EmailAlreadyExistsException() {
		super();
	}
	public EmailAlreadyExistsException(String msg)
	{
		super(msg);
	}
}

