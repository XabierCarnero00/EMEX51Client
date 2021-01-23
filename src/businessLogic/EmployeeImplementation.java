/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.EmployeeREST;
import exceptions.ExcepcionEmailYaExiste;
import exceptions.ExcepcionUserYaExiste;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import model.Employee;
import securityClient.ClavePublicaCliente;

/**
 *
 * @author xabig
 */
public class EmployeeImplementation implements EmployeeInterface {

    EmployeeREST employeeRest = new EmployeeREST();

    @Override
    public List<Employee> getAllEmpoyees() {
        List<Employee> employees = new ArrayList();

        employees = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
        });

        return employees;
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        List<Employee> employees = new ArrayList();

        employees = employeeRest.findEmployeesByName(new GenericType<List<Employee>>() {
        }, name);

        return employees;
    }

    @Override
    public List<Employee> getEmployeesByEmail(String email) {
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
    }

    @Override
    public void createEmployee(Employee employee) throws ExcepcionUserYaExiste, ExcepcionEmailYaExiste {
        employee.setPassword(ClavePublicaCliente.cifrarTexto(employee.getPassword()));
        employeeRest.create(employee);
    }

    @Override
    public Employee getSingleEmployeeByEmail(String email) {
        List<Employee> employees = new ArrayList();

        employees = employeeRest.findAllEmployees(new GenericType<List<Employee>>() {
        });

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmail().compareToIgnoreCase(email) == 0) {
                return employees.get(i);
            }
        }

        return null;
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeRest.edit(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRest.remove(id);
    }

}
