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
    
    public List<Employee> getAllEmpoyees();
    
    public List<Employee> getEmployeesByName(String name);
    
    public List<Employee> getEmployeesByEmail(String email);
    
    public Employee getSingleEmployeeByEmail(String email);
    
    public void createEmployee(Employee employee)throws ExcepcionUserYaExiste, ExcepcionEmailYaExiste;
    
    public void updateEmployee(Employee employee);
    
    public void deleteEmployee(String id);
}
