/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.ExceptionArmyExiste;
import java.util.Date;
import java.util.List;
import model.Army;
import model.Sector;

/**
 *  Interface encupsating methods for ArmyInterface for Emex51 proyect.
 * @author Xabier Carnero Lopez
 */
public interface ArmyInterface {
        
    /**
     * Returns all Armys.
     * @return list of Armys
     * @throws businessLogic.BusinessLogicException
     * @throws exceptions.NoDataBaseException
     */
    public List<Army> getAllArmys() throws BusinessLogicException;
    
    /**
     * Returns Armys that contains that name
     * @param name name of the Armys returned
     * @return list of Armys
     * @throws businessLogic.BusinessLogicException
     */
    public List<Army> getArmysByName(String name) throws BusinessLogicException;
    
    /**
     * Returns armys with less ammo than the parameter ammunition
     * @param ammunition ammuninition minim
     * @return list of Armys
     * @throws businessLogic.BusinessLogicException
     */
    public List<Army> getArmysByAmmunition(Integer ammunition) throws BusinessLogicException;
    
    /**
     * Returns armys which had arrived at that date
     * @param date arrived date
     * @return list of Armys
     * @throws BusinessLogicException 
     */
    public List<Army> getArmysByDate(Date date) throws BusinessLogicException;
    
    /**
     * Returns the armys of the Sector. 
     * @param sector the sector of the armys
     * @return list of Armys
     * @throws BusinessLogicException 
     */
     public List<Army> getArmysBySector(Sector sector) throws BusinessLogicException;
    
    /**
     * Updates the Army send in the DataBase
     * @param army Army to be edit
     * @throws businessLogic.BusinessLogicException
     * @throws exceptions.ExceptionArmyExiste
     */
    public void editArmy(Army army) throws BusinessLogicException;
    
    /**
     * Creates a new Army in the DataBase
     * @param army the Army to be created
     * @throws BusinessLogicException 
     * @throws exceptions.ExceptionArmyExiste 
     */
    public void createArmy(Army army) throws BusinessLogicException, ExceptionArmyExiste;
    
    /**
     * Deletes the Army of the DataBase
     * @param army the Army to be deleted 
     * @throws BusinessLogicException 
     */
    public void deleteArmy(Army army) throws BusinessLogicException;
}
