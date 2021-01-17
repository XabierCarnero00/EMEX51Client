/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

/**
 *
 * @author xabig
 */
public class ArmyFactory {
        
    public static ArmyInterface getArmyImp(){
        return new ArmyImplementation();
    }
    
}
