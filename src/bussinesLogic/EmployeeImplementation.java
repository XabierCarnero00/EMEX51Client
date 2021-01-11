/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

import clientREST.EmployeeREST;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import model.Employee;

/**
 *
 * @author xabig
 */
public class EmployeeImplementation implements EmployeeInterface{

    @Override
    public List<Employee> getAllEmpoyees() {
        List<Employee> employees = new ArrayList();
        
        EmployeeREST employeeRest = new EmployeeREST();
        
        employees =  employeeRest.findAllEmployees(new GenericType<List<Employee>>(){});
        
        return employees;
    }
    
}
