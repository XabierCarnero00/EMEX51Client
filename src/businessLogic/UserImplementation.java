/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.UserREST;
import exceptions.ExcepcionContraseñaNoCoincide;
import exceptions.ExcepcionEmailNoExiste;
import exceptions.ExcepcionUserNoExiste;
import javax.ws.rs.WebApplicationException;
import model.Boss;
import model.Employee;
import model.User;
import securityClient.ClavePublicaCliente;

/**
 *
 * @author xabig
 */
public class UserImplementation implements UserInterface {

    UserREST userRest = new UserREST();

    @Override
    public User login(String login, String password) throws ExcepcionUserNoExiste {
        Employee employee = new Employee();
        Boss boss = new Boss();

        try {
            return loginBoss(boss, login, password);
        } catch (Exception ex) {
            try {
                return loginEmpleado(employee, login, password);
            } catch (Exception ex2) {
                throw new ExcepcionUserNoExiste();
            }
        }
    }

    public Employee loginEmpleado(Employee employee, String login, String password) {

        employee = userRest.comprobateLogin(Employee.class, login, password);
        return employee;
    }

    public Boss loginBoss(Boss boss, String login, String password) {

        boss = userRest.comprobateLogin(Boss.class, login, password);
        return boss;
    }

    @Override
    public void temporalPass(String email) throws ExcepcionEmailNoExiste {
        try {
            userRest.sendMail(User.class, email);
        } catch (WebApplicationException ex) {
            throw new ExcepcionEmailNoExiste();
        }
    }

    @Override
    public void changePassword(String email, String tempPass, String newPass) throws ExcepcionContraseñaNoCoincide {
        try {
            newPass = ClavePublicaCliente.cifrarTexto(newPass);
            userRest.changePassword(User.class, email, tempPass, newPass);
        } catch (WebApplicationException ex) {
            throw new ExcepcionContraseñaNoCoincide();
        }
    }

}
