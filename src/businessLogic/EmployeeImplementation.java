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
 *
 * @author xabig
 */
public class EmployeeImplementation implements EmployeeInterface {

    EmployeeREST employeeRest = new EmployeeREST();

    @Override
    public List<Employee> getAllEmpoyees() throws BusinessLogicException {
        try {
            List<Employee> employees = new ArrayList();

            employees = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
            });

            return employees;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public List<Employee> getEmployeesByName(String name) throws BusinessLogicException {
        try {
            List<Employee> employees = new ArrayList();

            employees = employeeRest.findEmployeesByName(new GenericType<List<Employee>>() {
            }, name);

            return employees;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

    @Override
    public List<Employee> getEmployeesByEmail(String email) throws BusinessLogicException {
        try {
            List<Employee> employeesAux = new ArrayList();
            List<Employee> employees = new ArrayList();

            employeesAux = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
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

    @Override
    public Employee getSingleEmployeeByEmail(String email) throws BusinessLogicException {
        try {
            List<Employee> employees = new ArrayList();

            employees = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
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

    @Override
    public void updateEmployee(Employee employee) throws BusinessLogicException {
        try {
            employeeRest.edit(employee);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessLogicException(ex.getMessage());
        }
    }

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
