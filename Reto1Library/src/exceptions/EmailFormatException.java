/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author angoi
 */
public class EmailFormatException extends Exception {

    public EmailFormatException() {
        super();
    }

    public EmailFormatException(String msg) {
        super(msg);
    }

}
