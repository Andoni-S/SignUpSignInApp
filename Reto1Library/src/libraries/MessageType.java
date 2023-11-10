package libraries;

import java.io.Serializable;

/**
 * The Message Type used on the PDU that contains all the messages needed for 
 * the login and signUp as well as the exceptions that the server will throw
 *
 * @author Andoni Sanz
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
