/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

import clientREST.SectorREST;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Sector;

/**
 * This class implements all {@link SectorManager} interface methods using a Restful Web Client to access 
 * a JavaEE application server.
 * @author Endika Ubierna Lopez.
 */
public class SectorManagerImplementation implements SectorManager{
    /**
     * REST users web client
     */
    private SectorREST webClient;
    /**
     * Logger object used to control activity from class SectorManagerImplementation.
     */
    private static final Logger LOGGER=Logger.getLogger("SectorManagerImplentation");
    /**
     * Create a SectorManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public SectorManagerImplementation(){
        webClient=new SectorREST();
    }
    /**
     * This method returns a Collection of {@link Sector}.
     * @return A List of {@link Sector}.
     * @throws BusinessLogicException 
     */
    @Override
    public List<Sector> getAllSectors() throws BusinessLogicException {
        List<Sector> sectors = null;
        try{
            LOGGER.info("Metodo getAllSectors de la clase SectorManagerImplementation.");
            sectors = webClient.findAllSectors(new GenericType<List<Sector>>(){});
        }catch(Exception e){
            throw new BusinessLogicException(e.getMessage());
        }
        return sectors;       
    }
    /**
     * This method returns a <code>Sector</code> with the same name as the one passed by parameter.
     * @param name The name of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException 
     */
    @Override
    public List <Sector> getSectorsByName(String name) throws BusinessLogicException {
        List <Sector> sector = null;
        try{
            LOGGER.info("Metodo getSectorsByName de la clase SectorManagerImplementation.");
            sector = webClient.findSectorsByName(new GenericType<List<Sector>>(){}, name);
            if(sector.isEmpty())
                throw new BusinessLogicException("No hay sectores con el nombre "+name);
        }catch(Exception e){
            throw new BusinessLogicException(e.getMessage());
        }
        return sector;        
    }
    /**
     * This method returns a <code>Sector</code> with the same type as the one passed by parameter.
     * @param type The type of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException 
     */
    @Override
    public List <Sector> getSectorsByType(String type) throws BusinessLogicException {
        List<Sector> sector = null;
        try{
            LOGGER.info("Metodo getSectorsByType de la clase SectorManagerImplementation.");
            sector = webClient.findSectorsByType(new GenericType<List<Sector>>(){}, type);
        }catch(Exception e){
            throw new BusinessLogicException(e.getMessage());
        }
        return sector;        
    }
    /**
     * This method adds a new created <code>Sector</code>.
     * @param creature The {@link Sector} object to be added.
     * @throws BusinessLogicException 
     */
    @Override
    public void createSector(Sector sector) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo createSector de la clase SectorManagerImplementation.");
            webClient.create(sector);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,"Exception creating sector");
            throw new BusinessLogicException("Error creating sector:\n"+ex.getMessage());
        }         
    }
    /**
     * This method updates data for an existing {@link Sector}.
     * @param creature The {@link Sector} object to be updated.
     * @throws BusinessLogicException 
     */
    @Override
    public void updateSector(Sector sector) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo updateSector de la clase SectorManagerImplementation.");
            webClient.edit(sector);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,"Exception updating sector");
            throw new BusinessLogicException("Error updating sector:\n"+ex.getMessage());
        } 
    }
    /**
     * This method deletes data for an existing {@link Sector}.
     * @param creature The {@link Sector} object to be deleted.
     * @throws BusinessLogicException 
     */
    @Override
    public void deleteSector(Sector sector) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo deleteSector de la clase SectorManagerImplementation.");
            webClient.remove(Integer.toString(sector.getIdSector()));
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,"Exception deleting sector");
            throw new BusinessLogicException("Error deleting sector:\n"+ex.getMessage());
        } 
    }

    @Override
    public void sectorNameIsRegistered(String name) throws BusinessLogicException, SectorExistException {
        List <Sector> sectores = null;
        try{
            sectores = this.webClient.findSectorsByName(new GenericType<List<Sector>>(){}, name);
            if(!sectores.isEmpty())
                throw new SectorExistException("Ya existe una sector con nombre "+name);
        }catch(ClientErrorException ex){
            throw new BusinessLogicException("Error finding sector:\n"+ex.getMessage());
        }
    }
    
}