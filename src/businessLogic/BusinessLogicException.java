/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 * Exception thrown in the businees tier of the project.
 * @author endika
 */
public class BusinessLogicException extends Exception{
    /**
     * Business logic tier exception.
     * @param msg The message shown.
     */
    public BusinessLogicException(String msg){
        super(msg);
    }
}