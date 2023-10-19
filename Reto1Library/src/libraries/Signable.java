package libraries;


import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2dam
 */
public interface Signable {
    public User signUp(User user) throws ServerErrorException, EmailAlreadyExistsException;
    public User logIn(User user) throws ServerErrorException, CredentialsException;
}
