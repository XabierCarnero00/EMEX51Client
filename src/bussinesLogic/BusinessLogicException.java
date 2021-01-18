/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

/**
 * General error thrown in the Emex51 client application business tier.
 * @author Endika Ubierna Lopez, Xabier Carnero, Markel Lopez de Uralde.
 */
public class BusinessLogicException extends Exception{
    public BusinessLogicException(String msg){
        super(msg);
    }
}
