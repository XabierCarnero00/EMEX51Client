/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when a pair of passwords are not the same.
 * @author xabig
 */
public class ExcepcionContraseñaNoCoincide extends Exception{

    public ExcepcionContraseñaNoCoincide() {
        super("Las contraseñas no coinciden.");
    }
}
