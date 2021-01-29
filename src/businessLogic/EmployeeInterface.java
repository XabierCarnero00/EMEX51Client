/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.ExcepcionEmailYaExiste;
import exceptions.ExcepcionUserYaExiste;
import java.util.List;
import model.Employee;

/**
 * Interface encupsating methods for EmployeeInterface for Emex51 proyect.
 * @author Xabier Carnero
 */
public interface EmployeeInterface {

    /**
     * This method returns a Collection of all {@link Employee}.
     *
     * @return A List of {@link Employee}.
     * @throws BusinessLogicException
     */
    public List<Employee> getAllEmpoyees() throws BusinessLogicException;

    /**
     * This method returns a list of <code>Employee</code> with the same name as
     * the one passed by parameter.
     *
     * @param name The name of a {@link Employee}.
     * @return A List of Employees object.
     * @throws BusinessLogicException
     */
    public List<Employee> getEmployeesByName(String name) throws BusinessLogicException;

    /**
     * This method returns a list of <code>Employee</code> with the same email
     * as the one passed by parameter.
     *
     * @param email The email of a {@link Employee}.
     * @return A List of Employees object.
     * @throws BusinessLogicException
     */
    public List<Employee> getEmployeesByEmail(String email) throws BusinessLogicException;

    /**
     * This method returns a <code>Employee</code> with the same email as the
     * one passed by parameter.
     *
     * @param email The email of a {@link Employee}.
     * @return A List of Employees object.
     * @throws BusinessLogicException
     */
    public Employee getSingleEmployeeByEmail(String email) throws BusinessLogicException;

    /**
     * This method adds a new created <code>Employee</code>.
     *
     * @param employee The {@link Employee} object to be added.
     * @throws BusinessLogicException
     * @throws exceptions.ExcepcionUserYaExiste
     * @throws exceptions.ExcepcionEmailYaExiste
     */
    public void createEmployee(Employee employee) throws BusinessLogicException, ExcepcionUserYaExiste, ExcepcionEmailYaExiste;

    /**
     * This method updates data for an existing {@link Employee}.
     *
     * @param employee The {@link Employee} object to be updated.
     * @throws BusinessLogicException
     */
    public void updateEmployee(Employee employee) throws BusinessLogicException;

    /**
     * This method deletes data a existing {@link Employee}.
     *
     * @param id the id of Employee to delete
     * @throws BusinessLogicException
     */
    public void deleteEmployee(String id) throws BusinessLogicException;
}
