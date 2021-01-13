
package excepciones;

/**
 * Excepción que señala que el usuario no existe.
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class ExcepcionUserNoExiste extends Exception{
    public ExcepcionUserNoExiste (){
        super("El usuario no existe.");
    }
    
    
}
