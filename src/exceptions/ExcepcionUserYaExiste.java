
package exceptions;

/**
 * Exception thrown when a user already exists.
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class ExcepcionUserYaExiste extends Exception{
    public ExcepcionUserYaExiste (){
        super("El usuario ya existe.");
    }
}
