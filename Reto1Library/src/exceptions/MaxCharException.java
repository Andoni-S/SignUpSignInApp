/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class MaxCharException extends Exception{
        public MaxCharException() {
		super();
	}
	public MaxCharException(String msg)
	{
		super(msg);
	}
}
