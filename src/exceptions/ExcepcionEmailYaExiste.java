/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when the email already exists.
 * @author xabig
 */
public class ExcepcionEmailYaExiste extends Exception{
    public ExcepcionEmailYaExiste(){
        super("El email ya existe.");
    }
}
