package model;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import libraries.Signable;
import libraries.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2dam
 */
public class SignableImplementation implements Signable{

    @Override
    public User signUp(User user) throws ServerErrorException, EmailAlreadyExistsException {
        return new User();
    }

    @Override
    public User logIn(User user) throws ServerErrorException, CredentialsException {
        return new User();
    }
    
}
