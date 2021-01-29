
package exceptions;

/**
 * Excepción thrown when an user exists.
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class ExcepcionUserNoExiste extends Exception{
    public ExcepcionUserNoExiste (){
        super("El usuario no existe.");
    }    
}
