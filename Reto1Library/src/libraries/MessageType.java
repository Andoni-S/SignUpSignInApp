package libraries;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andoni
 */
public enum MessageType implements Serializable{
    Accept,
    LogIn,
    SignUp,
    Ex_ClassNotFound,
    Ex_Credentials,
    Ex_EmailAlreadyExists,
    Ex_ServerError
}
