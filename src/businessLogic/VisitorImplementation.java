/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;
import clientREST.VisitorREST;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Visitor;

/**
 *
 * @author Markel
 */
public class VisitorImplementation implements VisitorManager{
    
    /**
     * REST users web client
     */
    private VisitorREST webClient;
    /**
     * Logger object used to control activity from class SectorManagerImplementation.
     */
    private static final Logger LOGGER=Logger.getLogger("SectorManagerImplentation");
    /**
     * Create a SectorManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public VisitorImplementation(){
        webClient=new VisitorREST();
    }
    /**
     * This method returns a Collection of {@link Sector}.
     * @return A List of {@link Sector}.
     * @throws BusinessLogicException 
     */
    @Override
    public List<Visitor> getAllVisitors() throws BusinessLogicException {
        List<Visitor> visitors = null;
        try{
            LOGGER.info("Metodo getAllVisitors de la clase VisitorManagerImplementation.");
            visitors = webClient.findAllVisitors(new GenericType<List<Visitor>>(){});
        }catch(Exception e){
            throw new BusinessLogicException(e.getMessage());
        }
        return visitors;       
    }
    /**
     * This method returns a <code>Sector</code> with the same name as the one passed by parameter.
     * @param name The name of a {@link Sector}.
     * @return A sector object.
     * @throws BusinessLogicException 
     */
    @Override
    public List <Visitor> getVisitorsByName(String fullName) throws BusinessLogicException {
        List <Visitor> sector = null;
        try{
            LOGGER.info("Metodo getVisitorsByName de la clase VisitorManagerImplementation.");
            sector = webClient.findVisitorsByName(new GenericType<List<Visitor>>(){}, fullName);
            if(sector.isEmpty())
                throw new BusinessLogicException("No hay visitantes con el nombre "+fullName);
        }catch(Exception e){
            throw new BusinessLogicException(e.getMessage());
        }
        return sector;        
    }
    
    /**
     * This method updates data for an existing {@link Sector}.
     * @param creature The {@link Sector} object to be updated.
     * @throws BusinessLogicException 
     */
    @Override
    public void updateVisitor(Visitor visitor) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo updateVisitor de la clase VisitorManagerImplementation.");
            webClient.edit(visitor);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,"Exception updating visitor");
            throw new BusinessLogicException("Error updating sector:\n"+ex.getMessage());
        } 
    }
    /**
     * This method deletes data for an existing {@link Sector}.
     * @param creature The {@link Sector} object to be deleted.
     * @throws BusinessLogicException 
     */
    @Override
    public void deleteVisitor(Visitor visitor) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo deleteVisitor de la clase VisitorManagerImplementation.");
            webClient.remove(Integer.toString(visitor.getId()));
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,"Exception deleting visitor");
            throw new BusinessLogicException("Error deleting visitor:\n"+ex.getMessage());
        } 
    }
    /**
     * This method return a date 
     * @param date
     * @return
     * @throws BusinessLogicException 
     */
    
    @Override
    public List<Visitor> getVisitorsByDate(Date date) throws BusinessLogicException {
        try {
            List<Visitor> armys = getAllVisitors();
            List<Visitor> armyReturn = new ArrayList();
            for (Visitor a : armys) {
                if (a.getFechaVisita().compareTo(date) == 0) {
                    armyReturn.add(a);
                }
            }
            return armyReturn;
        } catch (Exception ex) {
            Logger.getLogger(ArmyImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    
}