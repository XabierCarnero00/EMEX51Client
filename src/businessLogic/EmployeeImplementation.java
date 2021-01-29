/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.EmployeeREST;
import clientREST.UserREST;
import exceptions.ExcepcionEmailYaExiste;
import exceptions.ExcepcionUserYaExiste;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Employee;
import model.User;
import securityClient.ClavePublicaCliente;

/**
 * This class implements all {@link EmployeeInterface} interface methods using a Restful Web Client to access 
 * a JavaEE application server.
 * @author Xabier Carnero.
 */
public class EmployeeImplementation implements EmployeeInterface {

    EmployeeREST employeeRest = new EmployeeREST();

    /**
     * This method returns a Collection of all {@link Employee}.
     *
     * @return A List of {@link Employee}.
     * @throws BusinessLogicException
     */
    @Override
    public List<Employee> getAllEmpoyees() throws BusinessLogicException {
        try {
            List<Employee> employees = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
            });

            return employees;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    /**
     * This method returns a list of <code>Employee</code> with the same name as
     * the one passed by parameter.
     *
     * @param name The name of a {@link Employee}.
     * @return A List of Employees object.
     * @throws BusinessLogicException
     */
    @Override
    public List<Employee> getEmployeesByName(String name) throws BusinessLogicException {
        try {
            List<Employee> employees = employeeRest.findEmployeesByName(new GenericType<List<Employee>>() {
            }, name);

            return employees;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    /**
     * This method returns a list of <code>Employee</code> with the same email
     * as the one passed by parameter.
     *
     * @param email The email of a {@link Employee}.
     * @return A List of Employees object.
     * @throws BusinessLogicException
     */
    @Override
    public List<Employee> getEmployeesByEmail(String email) throws BusinessLogicException {
        try {
            List<Employee> employees = new ArrayList();
            List<Employee> employeesAux = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
            });

            for (int i = 0; i < employeesAux.size(); i++) {
                if (employeesAux.get(i).getEmail().contains(email)) {
                    employees.add(employeesAux.get(i));
                }
            }
            return employees;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    /**
     * This method adds a new created <code>Employee</code>.
     *
     * @param employee The {@link Employee} object to be added.
     * @throws BusinessLogicException
     * @throws exceptions.ExcepcionUserYaExiste
     * @throws exceptions.ExcepcionEmailYaExiste
     */
    @Override
    public void createEmployee(Employee employee) throws ExcepcionUserYaExiste, ExcepcionEmailYaExiste, BusinessLogicException {
        UserREST userRest = new UserREST();
        List<User> users = userRest.findAllUsers(new GenericType<List<User>>() {
        });
        for (User u : users) {
            if (u.getLogin().equals(employee.getLogin())) {
                throw new ExcepcionUserYaExiste();
            } else if (u.getEmail().equals(employee.getEmail())) {
                throw new ExcepcionEmailYaExiste();
            }
        }
        try {
            employee.setPassword(ClavePublicaCliente.cifrarTexto(employee.getPassword()));
            employeeRest.create(employee);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    /**
     * This method returns a <code>Employee</code> with the same email as the
     * one passed by parameter.
     *
     * @param email The email of a {@link Employee}.
     * @return A List of Employees object.
     * @throws BusinessLogicException
     */
    @Override
    public Employee getSingleEmployeeByEmail(String email) throws BusinessLogicException {
        try {
            List<Employee> employees = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
            });

            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getEmail().compareToIgnoreCase(email) == 0) {
                    return employees.get(i);
                }
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    /**
     * This method updates data for an existing {@link Employee}.
     *
     * @param employee The {@link Employee} object to be updated.
     * @throws BusinessLogicException
     */
    @Override
    public void updateEmployee(Employee employee) throws BusinessLogicException {
        try {
            employeeRest.edit(employee);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    /**
     * This method deletes data a existing {@link Employee}.
     *
     * @param id the id of Employee to delete
     * @throws BusinessLogicException
     */
    @Override
    public void deleteEmployee(String id) throws BusinessLogicException {
        try {
            employeeRest.remove(id);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

}
