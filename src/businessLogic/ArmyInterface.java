/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.List;
import model.Army;

/**
 *
 * @author xabig
 */
public interface ArmyInterface {
        
    /**
     * Returns all Armys.
     * @return list of Armys
     */
    public List<Army> getAllArmys();
    
    /**
     * Returns Armys that contains that name
     * @param name name of the Armys returned
     * @return list of Armys
     */
    public List<Army> getArmysByName(String name);
    
    /**
     * Returns armys with less ammo than the parameter ammunition
     * @param ammunition ammuninition minim
     * @return list of Armys
     */
    public List<Army> getArmysByAmmunition(Integer ammunition);
}
