/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.ArmyREST;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.WebApplicationException;
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
    public List<Army> getArmysByAmmunition(Integer ammunition) {
        return armyRest.findArmyByMuniton(new GenericType<List<Army>>() {
        }, Integer.toString(ammunition));
    }

    public List<Army> getArmysByDate(Date date) throws BusinessLogicException {
        try {
            List<Army> armys = getAllArmys();
            List<Army> armyReturn = new ArrayList();
            for (Army a : armys) {
                if (a.getArrivalDate().compareTo(date) == 0) {
                    armyReturn.add(a);
                }
            }
            return armyReturn;
        } catch (WebApplicationException ex) {
            throw new BusinessLogicException("Error getting armys by date");
        }
    }

    @Override
    public void editArmy(Army army) throws BusinessLogicException {
        try {
            armyRest.edit(army);
        } catch (WebApplicationException ex) {
            throw new BusinessLogicException("Error editing army");
        }
    }

    @Override
    public void createArmy(Army army) throws BusinessLogicException {
        try {
            armyRest.create(army);
        } catch (WebApplicationException ex) {
            throw new BusinessLogicException("Error creating army");
        }
    }

}
