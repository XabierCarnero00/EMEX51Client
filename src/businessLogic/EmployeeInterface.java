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
 *
 * @author xabig
 */
public interface EmployeeInterface {
    
    public List<Employee> getAllEmpoyees() throws BusinessLogicException;
    
    public List<Employee> getEmployeesByName(String name) throws BusinessLogicException;
    
    public List<Employee> getEmployeesByEmail(String email) throws BusinessLogicException;
    
    public Employee getSingleEmployeeByEmail(String email) throws BusinessLogicException;
    
    public void createEmployee(Employee employee) throws BusinessLogicException, ExcepcionUserYaExiste, ExcepcionEmailYaExiste;
    
    public void updateEmployee(Employee employee) throws BusinessLogicException;
    
    public void deleteEmployee(String id) throws BusinessLogicException;
}
