/**
 * This package contains the logic implementation for user authentication and registration.
 * The {@link logic.SignableImplementation} class provides the core
 * functionality for interacting with a server through socket communication to
 * handle user login and registration. It also includes exception classes such
 * as {@link exceptions.CredentialsException},
 * {@link exceptions.EmailAlreadyExistsException}, and
 * {@link exceptions.ServerErrorException} to manage different scenarios during
 * the authentication and registration process.
 * <p>
 * The communication details, including the server's IP address and port number,
 * are configured through a properties file retrieved using
 * {@link java.util.ResourceBundle}.
 * </p>
 *
 * @author Jagoba Bartolomé
 * @author Ander Goirigolzarri Iturburu
 * @author Andoni Sanz
 */
package logic;
