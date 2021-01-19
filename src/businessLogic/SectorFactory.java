/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 * This class is a factory for {@link SectorManager} interface.
 *
 * @author Endika Ubierna Lopez.
 */
public class SectorFactory {

    /**
     * This method returns an interface implemeting methods for {@link Sector}
     * object management.
     *
     * @return An object implementing the interface {@link SectorManager}.
     */
    public static SectorInterface getSector() {
        return new SectorImplementation();
    }
}
