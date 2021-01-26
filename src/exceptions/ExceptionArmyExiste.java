/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author xabig
 */
public class ExceptionArmyExiste extends Exception {
    public ExceptionArmyExiste() {
        super("Ya existe un Armamento con ese nombre");
    }
}
