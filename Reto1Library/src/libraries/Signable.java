package libraries;


import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import java.io.IOException;

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
    public User signUp(User u) throws IOException, ClassNotFoundException, EmailAlreadyExistsException, ServerErrorException;
    public User logIn(User u) throws IOException, ClassNotFoundException, CredentialsException, EmailAlreadyExistsException, ServerErrorException;
}
