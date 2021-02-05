
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when a sector don´t exist
 * @author endika
 */
public class SectorNotExistException extends Exception{
    public SectorNotExistException(){
        super("The sector don´t exist");
    }    
}