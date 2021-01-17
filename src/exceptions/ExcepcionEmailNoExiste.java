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
public class ExcepcionEmailNoExiste extends Exception {  
    public ExcepcionEmailNoExiste (){
        super("No existe ningun usuario con ese email");
    }    
}
