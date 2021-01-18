/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

import java.util.List;
import model.Creature;
import model.Sector;

/**
 * Interface encapsulating methods for <code>Creature</code> Management for Emex51 project. 
 * @author Endika Ubierna Lopez
 */
public interface CreatureManager {
    /**
     * This method returns a Collection of {@link Creature}.
     * @return A List of {@link Creature}.
     * @throws BusinessLogicException
     */
    public List<Creature> getAllCreatures() throws BusinessLogicException;
    /**
     * This method returns a <code>Creature</code> with the same name as the one passed by parameter.
     * @param name The name of a {@link Creature}.
     * @return A creature object.
     * @throws BusinessLogicException
     */
    public List <Creature> getCreatureByName(String name,Sector sector) throws BusinessLogicException;
    /**
     * This method returns a <code>Creature</code> with the same especie as the one passed by parameter.
     * @param especie The especie of a {@link Creature}.
     * @return A creature object.
     * @throws BusinessLogicException
     */
    public List<Creature> getCreatureByEspecie(String especie, Sector sector) throws BusinessLogicException;
    /**
     * This method updates data for an existing {@link Creature}.
     * @param creature The {@link Creature} object to be updated.
     * @throws BusinessLogicException 
     */
    public void updateCreature(Creature creature) throws BusinessLogicException;
    /**
     * This method adds a new created <code>Creature</code>.
     * @param creature The {@link Creature} object to be added.
     * @throws BusinessLogicException 
     */
    public void createCreature(Creature creature) throws BusinessLogicException;
    /**
     * This method returns a List of {@link Creature} from a {@link Sector}.
     * @param sector A {@link Sector} name.
     * @return A List of Creatures.
     * @throws BusinessLogicException 
     */
    public List<Creature> getCreatureBySector(String sector) throws BusinessLogicException;
    /**
     * This method verifies if there is an existing creature with the name.
     * @param name The name of the creature.
     * @throws BusinessLogicException Business logic tier exception.
     * @throws CreaturaExistException There is a creature registered with the same name.
     */
    public void creatureNameIsRegistered(String name) throws BusinessLogicException,CreaturaExistException;
}
