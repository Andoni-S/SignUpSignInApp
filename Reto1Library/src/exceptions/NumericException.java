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
public class NumericException extends Exception{
      public NumericException() {
		super();
	}
	public NumericException(String msg)
	{
		super(msg);
	}
}
