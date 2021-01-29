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
 * This class implements all {@link ArmyInterface} interface methods using a
 * Restful Web Client to access a JavaEE application server.
 *
 * @author Xabier Carnero.
 */
public class ArmyImplementation implements ArmyInterface {

    /**
     * The client REST service of Army.
     */
    ArmyREST armyRest = new ArmyREST();

    /**
     * Returns all Armys.
     *
     * @return list of Armys
     * @throws businessLogic.BusinessLogicException
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

    /**
     * Returns Armys that contains that name
     *
     * @param name name of the Armys returned
     * @return list of Armys
     * @throws businessLogic.BusinessLogicException
     */
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

    /**
     * Returns armys with less ammo than the parameter ammunition
     *
     * @param ammunition ammuninition minim
     * @return list of Armys
     * @throws businessLogic.BusinessLogicException
     */
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

    /**
     * Returns armys which had arrived at that date
     *
     * @param date arrived date
     * @return list of Armys
     * @throws BusinessLogicException
     */
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

    /**
     * Returns the armys of the Sector.
     *
     * @param sector the sector of the armys
     * @return list of Armys
     * @throws BusinessLogicException
     */
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

    /**
     * Updates the Army send in the DataBase
     *
     * @param army Army to be edit
     * @throws businessLogic.BusinessLogicException
     */
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

    /**
     * Creates a new Army in the DataBase
     *
     * @param army the Army to be created
     * @throws BusinessLogicException
     * @throws exceptions.ExceptionArmyExiste
     */
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

    /**
     * Deletes the Army of the DataBase
     *
     * @param army the Army to be deleted
     * @throws BusinessLogicException
     */
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

    /**
     * Verifies that the Army you send is not in the DataBase yet.
     *
     * @param name the name of the Army to verify
     * @throws BusinessLogicException
     * @throws ExceptionArmyExiste
     */
    public void verifyArmy(String name) throws BusinessLogicException, ExceptionArmyExiste {
        List<Army> armys = getAllArmys();
        for (Army a : armys) {
            if (a.getName().equals(name)) {
                throw new ExceptionArmyExiste();
            }
        }
    }

}
