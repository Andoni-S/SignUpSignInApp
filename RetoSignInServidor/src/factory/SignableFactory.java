package factory;

import libraries.Signable;
import model.SignableImplementation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2dam
 */
public class SignableFactory {
    
    public static synchronized Signable getSignable(){
        //return the Implementation of Signable interface
        return new SignableImplementation();
    }
}
