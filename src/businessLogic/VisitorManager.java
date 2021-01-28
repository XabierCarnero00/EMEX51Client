/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Date;
import java.util.List;
import model.Visitor;

/**
 *
 * @author markel
 */
public interface VisitorManager {
    /**
     * This method returns a Collection of {@link Sector}.
     * @return A List of {@link Sector}.
     * @throws BusinessLogicException 
     */
    public List<Visitor> getAllVisitors() throws BusinessLogicException;
    /**
     * This method returns a <code>Visitor</code> with the same name as the one passed by parameter.
     * @param name The name of a {@link Visitor}.
     * @return A sector object.
     * @throws BusinessLogicException 
     */
    public List <Visitor> getVisitorsByName(String name) throws BusinessLogicException;
    /**
     * This method updates data for an existing {@link Sector}.
     * @param visitor The {@link Sector} object to be updated.
     * @throws BusinessLogicException 
     */
    public void updateVisitor(Visitor visitor) throws BusinessLogicException;
    
    public void createVisitor(Visitor visitor) throws BusinessLogicException;
    /**
     * This method deletes data for an existing {@link Sector}.
     * @param visitor The {@link Sector} object to be deleted.
     * @throws BusinessLogicException 
     */
    public void deleteVisitor(Visitor visitor) throws BusinessLogicException;
    /**
     * Returns visitors which had arrived at that date
     * @param date arrived date
     * @return list of Visitors
     * @throws BusinessLogicException 
     * @throws exceptions.NoDataBaseException 
     */
    public List<Visitor> getVisitorsByDate(Date date) throws BusinessLogicException;
    
    
}