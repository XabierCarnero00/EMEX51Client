/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;


/**
 * This class is a factory for {@link CreatureManager} interface.
 * @author Endika Ubierna Lopez.
 */
public class CreatureFactory {
    /**
     * This method returns an interface implemeting methods for {@link Creature} object management. 
     * @return An object implementing the interface {@link CreatureManager}.
     */
    public static CreatureInterface getCreatureInterface(){
        return new CreatureImplementation();
    }
}