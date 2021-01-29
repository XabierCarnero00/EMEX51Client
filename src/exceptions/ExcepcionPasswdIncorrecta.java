
package exceptions;

/**
 * Excepción thrown when an users password is incorrect.
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class ExcepcionPasswdIncorrecta extends Exception {
    public ExcepcionPasswdIncorrecta (){
        super("Password incorrecto.");
    }    
}
