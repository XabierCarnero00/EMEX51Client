/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.ArmyREST;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import model.Army;

/**
 *
 * @author xabig
 */
public class ArmyImplementation implements ArmyInterface {

    /**
     * The client REST service of Army.
     */
    ArmyREST armyRest = new ArmyREST();

    /**
     * Returns all Armys.
     *
     * @return list of armys
     */
    @Override
    public List<Army> getAllArmys() {
        return armyRest.findAllArmys(new GenericType<List<Army>>() {
        });
    }

    @Override
    public List<Army> getArmysByName(String name) {
        List<Army> armys = getAllArmys();
        List<Army> armyReturn = new ArrayList();

        for (Army a : armys) {
            if (a.getName().contains(name)) {
                armyReturn.add(a);
            }
        }

        return armyReturn;
    }
    
    @Override
    public List<Army> getArmysByAmmunition(Integer ammunition){
        return armyRest.findArmyByMuniton(new GenericType<List<Army>>(){}, Integer.toString(ammunition));
    }

}
