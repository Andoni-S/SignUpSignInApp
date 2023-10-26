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
public class ClassNotFoundException extends Exception{
        
        public ClassNotFoundException() {
		super();
	}
	public ClassNotFoundException(String msg)
	{
		super(msg);
	}
}
