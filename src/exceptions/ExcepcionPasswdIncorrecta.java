
package exceptions;

/**
 * Excepción que señala que la contraseña es incorrecta.
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class ExcepcionPasswdIncorrecta extends Exception {
    public ExcepcionPasswdIncorrecta (){
        super("Password incorrecto.");
    }    
}
