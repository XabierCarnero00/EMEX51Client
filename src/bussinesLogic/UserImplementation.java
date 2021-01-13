/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

import clientREST.UserREST;
import model.Boss;
import model.Employee;
import model.User;

/**
 *
 * @author xabig
 */
public class UserImplementation implements UserInterface {

    @Override
    public User login(String login, String password) {
        Employee employee = new Employee();
        Boss boss = new Boss();
        UserREST userRest = new UserREST();

        try {
            return loginBoss(boss, userRest, login, password);
        } catch (Exception ex) {
            try {
                return loginEmpleado(employee, userRest, login, password);
            } catch (Exception ex2) {

            }
        }
        return null;

    }

    public Employee loginEmpleado(Employee employee, UserREST userRest, String login, String password) {

        employee = userRest.comprobateLogin(Employee.class, login, password);
        return employee;
    }

    public Boss loginBoss(Boss boss, UserREST userRest, String login, String password) {

        boss = userRest.comprobateLogin(Boss.class, login, password);
        return boss;
    }

}
