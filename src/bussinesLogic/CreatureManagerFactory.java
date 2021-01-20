/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;


/**
 * This class is a factory for {@link CreatureManager} interface.
 * @author Endika Ubierna Lopez.
 */
public class CreatureManagerFactory {
    /**
     * This method returns an interface implemeting methods for {@link Creature} object management. 
     * @return An object implementing the interface {@link CreatureManager}.
     */
    public static CreatureManager getCreatureManager(){
        return new CreatureManagerImplementation();
    }
}
