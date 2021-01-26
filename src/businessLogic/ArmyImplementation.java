/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.ArmyREST;
import exceptions.ExceptionArmyExiste;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Army;
import model.Sector;

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
    public List<Army> getAllArmys() throws BusinessLogicException {
        try {
            return armyRest.findAllArmys(new GenericType<List<Army>>() {
            });
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public List<Army> getArmysByName(String name) throws BusinessLogicException {
        try {
            List<Army> armys = getAllArmys();
            List<Army> armyReturn = new ArrayList();

            for (Army a : armys) {
                if (a.getName().contains(name)) {
                    armyReturn.add(a);
                }
            }
            return armyReturn;
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public List<Army> getArmysByAmmunition(Integer ammunition) throws BusinessLogicException {
        try {
            return armyRest.findArmyByMuniton(new GenericType<List<Army>>() {
            }, Integer.toString(ammunition));
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
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
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public List<Army> getArmysBySector(Sector sector) throws BusinessLogicException {
        List<Army> armys = getAllArmys();
        List<Army> armyReturn = new ArrayList();
        for (Army a : armys) {
            if (a.getSector().getIdSector().compareTo(sector.getIdSector()) == 0) {
                armyReturn.add(a);
            }
        }
        return armyReturn;
    }

    @Override
    public void editArmy(Army army) throws BusinessLogicException {
        //verifyArmy(army.getName());
        try {
            armyRest.edit(army);
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public void createArmy(Army army) throws BusinessLogicException, ExceptionArmyExiste {
        //Verificar que el Armamento con ese nombre no existe ya
        verifyArmy(army.getName());
        try {
            armyRest.create(army);
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public void deleteArmy(Army army) throws BusinessLogicException {
        try {
            army.setSector(null);
            armyRest.edit(army);
            armyRest.remove(army.getId().toString());
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    public void verifyArmy(String name) throws BusinessLogicException, ExceptionArmyExiste {
        List<Army> armys = getAllArmys();
        for (Army a : armys) {
            if (a.getName().equals(name)) {
                throw new ExceptionArmyExiste();
            }
        }
    }

}
