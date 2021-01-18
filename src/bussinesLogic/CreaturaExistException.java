/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

/**
 * Exception thrown when a new <code>Creature</code> is created and the name is already registered. 
 * @author Endika Ubierna Lopez
 */
public class CreaturaExistException extends Exception{
    public CreaturaExistException(String msg){
        super(msg);
    }
}
