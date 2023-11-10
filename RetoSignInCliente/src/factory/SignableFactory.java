package factory;

import libraries.Signable;
import logic.SignableImplementation;

/**
 * The {@code SignableFactory} class is responsible for creating instances of
 * the {@link libraries.Signable} interface. It provides a method to obtain an
 * implementation of the {@code Signable} interface, allowing the application to
 * interact with the logic tier for user authentication and registration.
 * <p>
 * This factory follows the Factory Method design pattern, where the creation of
 * objects is delegated to a static method.
 * </p>
 * @author: Andoni Sanz
 */
public class SignableFactory {

    public static Signable getSignable() {
        //return the Implementation of Signable interface
        return new SignableImplementation();
    }
}
