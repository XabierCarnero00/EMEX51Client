/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when a sector already exists
 * @author endika
 */
public class SectorExistException extends Exception{
    public SectorExistException(){
        super("The sector already exists");
    }    
    
}
