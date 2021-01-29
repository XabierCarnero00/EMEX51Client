/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 * Class for taking the Army Interface with all the Business Methods.
 *
 * @author Xabier Carnero.
 */
public class ArmyFactory {

    /**
     * This method returns an interface implemeting methods for {@link Army}
     * object management.
     *
     * @return An object implementing the interface {@link ArmyInterface}.
     */
    public static ArmyInterface getArmyImp() {
        return new ArmyImplementation();
    }

}
