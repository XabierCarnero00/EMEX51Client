/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.SectorREST;
import exceptions.SectorExistException;
import exceptions.SectorNotExistException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import model.Sector;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * This class implements all {@link SectorManager} interface methods using a
 * Restful Web Client to access a JavaEE application server.
 *
 * @author Endika Ubierna Lopez.
 */
public class SectorImplementation implements SectorInterface {

    /**
     * REST users web client
     */
    private SectorREST webClient;
    /**
     * Logger object used to control activity from class
     * SectorManagerImplementation.
     */
    private static final Logger LOGGER = Logger.getLogger("SectorManagerImplentation");

    /**
     * Create a SectorManagerImplementation object. It constructs a web client
     * for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public SectorImplementation() {
        webClient = new SectorREST();
    }

    /**
     * This method returns a Collection of {@link Sector}.
     *
     * @return A List of {@link Sector}.
     * @throws BusinessLogicException
     */
    @Override
    public List<Sector> getAllSectors() throws BusinessLogicException {
        List<Sector> sectors = null;
        try {
            LOGGER.info("Metodo getAllSectors de la clase SectorManagerImplementation.");
            sectors = webClient.findAllSectors(new GenericType<List<Sector>>() {
            });
        } catch (Exception e) {
            throw new BusinessLogicException(e.getMessage());
        }
        return sectors;
    }

    /**
     * This method returns a <code>Sector</code> with the same name as the one
     * passed by parameter.
     *
     * @param name The name of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException
     */
    @Override
    public Sector getSectorsByName(String name) throws BusinessLogicException,SectorNotExistException {
        Sector sector = null;
        try {
            LOGGER.info("Metodo getSectorsByName de la clase SectorManagerImplementation.");
            sector = webClient.findSectorsByName(Sector.class, name);
            return sector;
        } catch (NotFoundException e) {
            throw new SectorNotExistException();
        }catch(WebApplicationException e){
            throw new BusinessLogicException(e.getMessage());
        }
    }

    /**
     * This method returns a <code>Sector</code> with the same type as the one
     * passed by parameter.
     *
     * @param type The type of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException
     */
    @Override
    public List<Sector> getSectorsByType(String type) throws BusinessLogicException {
        List<Sector> sector = null;
        try {
            LOGGER.info("Metodo getSectorsByType de la clase SectorManagerImplementation.");
            sector = webClient.findSectorsByType(new GenericType<List<Sector>>() {
            }, type);
        } catch (Exception e) {
            throw new BusinessLogicException(e.getMessage());
        }
        return sector;
    }

    /**
     * This method adds a new created <code>Sector</code>.
     *
     * @throws BusinessLogicException
     */
    @Override
    public void createSector(Sector sector) throws BusinessLogicException {
        try {
            LOGGER.info("Metodo createSector de la clase SectorManagerImplementation.");
            webClient.create(sector);
        } catch (ForbiddenException ex) {
            LOGGER.log(Level.SEVERE, "Exception creating sector");
            throw new BusinessLogicException(ex.getMessage());
        } 
    }

    /**
     * This method updates data for an existing {@link Sector}.
     *
     * @throws BusinessLogicException
     */
    @Override
    public void updateSector(Sector sector) throws BusinessLogicException {
        try {
            LOGGER.info("Metodo updateSector de la clase SectorManagerImplementation.");
            webClient.edit(sector);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception updating sector");
            throw new BusinessLogicException("Error updating sector:\n" + ex.getMessage());
        }
    }

    /**
     * This method deletes data for an existing {@link Sector}.
     *
     * @throws BusinessLogicException
     */
    @Override
    public void deleteSector(Sector sector) throws BusinessLogicException {
        try {
            LOGGER.info("Metodo deleteSector de la clase SectorManagerImplementation.");
            webClient.remove(Integer.toString(sector.getIdSector()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception deleting sector");
            throw new BusinessLogicException("Error deleting sector:\n" + ex.getMessage());
        }
    }

    @Override
    public void sectorNameIsRegistered(String name) throws BusinessLogicException, SectorExistException {
        Sector sector;
        try {
            sector = getSectorsByName(name);
            throw new SectorExistException();
        } catch (WebApplicationException ex) {
            throw new BusinessLogicException("Error finding sector:\n" + ex.getMessage());
        } catch (SectorNotExistException ex) {
            Logger.getLogger(SectorImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
