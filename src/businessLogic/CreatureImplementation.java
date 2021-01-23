/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import businessLogic.CreatureInterface;
import clientREST.CreatureREST;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Creature;
import model.Sector;

/**
 * This class implements all {@link CreatureManager} interface methods using a Restful Web Client to access 
 * a JavaEE application server.
 * @author Endika Ubierna Lopez.
 */
public class CreatureImplementation implements CreatureInterface{
    /**
     * REST users web client
     */
    private CreatureREST webClient;
    /**
     * Logger object used to control activity from class CreatureManagerImplementation.
     */
    private static final Logger LOGGER=Logger.getLogger("CreatureManagerImplentation");
    /**
     * Create a CreatureManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public CreatureImplementation(){
        webClient=new CreatureREST();
    }
    /**
     * This method returns a Collection of {@link Creature}.
     * @return A List of {@link Creature}.
     * @throws ClientErrorException 
     */
    @Override
    public List<Creature> getAllCreatures() throws BusinessLogicException {
        List<Creature> creatures = null;
        try{
            LOGGER.info("Metodo getAllCreatures de la clase CreatureManagerImplementation.");
            creatures = webClient.findAllCreatures(new GenericType<List<Creature>>(){});
        }catch(ClientErrorException e){
            throw new BusinessLogicException(e.getMessage());
        }
        return creatures;
    }

    /**
     * This method returns a <code>Creature</code> with the same name as the one passed by parameter.
     * @param name The name of a {@link Creature}.
     * @return A creature object.
     * @throws BusinessLogicException
     */
    @Override
    public List <Creature> getCreatureByName(String name,Sector sector) throws BusinessLogicException {
        List <Creature> creature = null,creaturesSector = new ArrayList();;
        try{
            LOGGER.info("Metodo getCreatureByName de la clase CreatureManagerImplementation.");
            creature = webClient.findCreatureByName(new GenericType<List<Creature>>(){}, name);
            for(Creature c:creature){
                if(c.getSector().getName().equals(sector.getName()))
                    creaturesSector.add(c);
            }
            if(creature.isEmpty())
                throw new BusinessLogicException("No hay criaturas con el nombre "+name+" en el sector "+sector.getName());
        }catch(BusinessLogicException | ClientErrorException e){
            throw new BusinessLogicException("Error");
        }
        return creaturesSector;        
    }
    /**
     * This method returns a <code>Creature</code> with the same especie as the one passed by parameter.
     * @param especie The especie of a {@link Creature}.
     * @return A creature object.
     * @throws BusinessLogicException
     */
    @Override
    public List<Creature> getCreatureByEspecie(String especie,Sector sector) throws BusinessLogicException {
        List<Creature> creatures = null,creaturesSector = new ArrayList();
        try{
            LOGGER.info("Metodo getCreatureByEspecie de la clase CreatureManagerImplementation.");
            creatures = webClient.findCreatureByEspecie(new GenericType<List<Creature>>(){}, especie);
            for(Creature c:creatures){
                if(c.getSector().getName().equals(sector.getName()))
                    creaturesSector.add(c);
            }
            if(creaturesSector.isEmpty())
                throw new BusinessLogicException("No hay criaturas de la "+especie+" en el sector "+sector.getName());
        }catch(Exception e){
            throw new BusinessLogicException("Error");
        }
        return creaturesSector;        
    }
    /**
     * This method returns a List of {@link Creature} from a {@link Sector}.
     * @param sector A {@link Sector} name.
     * @return A List of Creatures.
     * @throws BusinessLogicException 
     */
    @Override
    public List<Creature> getCreatureBySector(String sector) throws BusinessLogicException {
        List<Creature> creatures = null;
        try{
            LOGGER.info("Metodo getCreatureBySector de la clase CreatureManagerImplementation.");
            creatures = webClient.findCreatureBySector(new GenericType<List<Creature>>(){}, sector);
        }catch(ClientErrorException e){
            throw new BusinessLogicException(e.getMessage());
        }
        return creatures;   
    }
    /**
     * This method updates data for an existing {@link Creature}.
     * @param creature The {@link Creature} object to be updated.
     * @throws BusinessLogicException 
     */
    @Override
    public void updateCreature(Creature creature) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo updateCreature de la clase CreatureManagerImplementation.");
            webClient.edit(creature);
        }catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE,"Exception updating creature");
            throw new BusinessLogicException("Error updating creature:\n"+ex.getMessage());
        }
    }

    /**
     * This method adds a new created <code>Creature</code>.
     * @param creature The {@link Creature} object to be added.
     * @throws BusinessLogicException 
     */
    @Override
    public void createCreature(Creature creature) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo createCreature de la clase CreatureManagerImplementation.");
            webClient.create(creature);
        }catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE,
                    "Exception creating creature");
            throw new BusinessLogicException("Error creating creature:\n"+ex.getMessage());
        }        
    }
    /**
     * This method finds if a <code>Creature</code> already exists.
     * @param name The name of a creature
     * @throws BusinessLogicException 
     * @throws CreaturaExistException 
     */
    @Override
    public void creatureNameIsRegistered(String name) throws BusinessLogicException, CreaturaExistException {
        List <Creature> creature = null;
        try{
            creature = this.webClient.findCreatureByName(new GenericType<List<Creature>>(){}, name);
            if(!creature.isEmpty())
                throw new CreaturaExistException("Ya existe una criatura con nombre "+name);
        }catch(ClientErrorException ex){
            throw new BusinessLogicException("Error finding creature:\n"+ex.getMessage());
        }
    }
    
}