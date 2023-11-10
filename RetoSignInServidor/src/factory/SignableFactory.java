package factory;

import libraries.Signable;
import model.SignableImplementation;

/**
 * SignableImplementation factory that returns a new instance
 * @author Andoni Sanz
 */
public class SignableFactory {
    
    public static synchronized Signable getSignable(){
        //return the Implementation of Signable interface
        return new SignableImplementation();
    }
}
