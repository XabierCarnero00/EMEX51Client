package businessLogic;

import exceptions.SectorExistException;
import java.util.List;
import model.Sector;

/**
 * Interface encapsulating methods for <code>Sector</code> Management for Emex51 project. 
 * @author Endika Ubierna Lopez.
 */
public interface SectorInterface {
    /**
     * This method returns a Collection of {@link Sector}.
     * @return A List of {@link Sector}.
     * @throws BusinessLogicException 
     */
    public List<Sector> getAllSectors() throws BusinessLogicException;
    /**
     * This method returns a <code>Sector</code> with the same name as the one passed by parameter.
     * @param name The name of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException 
     */
    public List <Sector> getSectorsByName(String name) throws BusinessLogicException;
    /**
     * This method returns a <code>Sector</code> with the same type as the one passed by parameter.
     * @param type The type of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException 
     */
    public List <Sector> getSectorsByType(String type) throws BusinessLogicException;
    /**
     * This method adds a new created <code>Sector</code>.
     * @param sector
     * @throws BusinessLogicException 
     */
    public void createSector(Sector sector) throws BusinessLogicException;
    /**
     * This method updates data for an existing {@link Sector}.
     * @param sector
     * @throws BusinessLogicException 
     */
    public void updateSector(Sector sector) throws BusinessLogicException;
    /**
     * This method deletes data for an existing {@link Sector}.
     * @param sector
     * @throws BusinessLogicException 
     */
    public void deleteSector(Sector sector) throws BusinessLogicException;
    /**
     * This method verifies if there is an existing sector with the name.
     * @param name The name of the sector.
     * @throws BusinessLogicException Business logic tier exception.
     * @throws SectorExistException There is a sector registered with the same name.
     */
    public void sectorNameIsRegistered(String name) throws BusinessLogicException,SectorExistException;
}