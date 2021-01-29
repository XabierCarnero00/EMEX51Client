/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.UserREST;
import exceptions.ExcepcionContraseñaNoCoincide;
import exceptions.ExcepcionEmailNoExiste;
import exceptions.ExcepcionPasswdIncorrecta;
import exceptions.ExcepcionUserNoExiste;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
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
    public User login(String login, String password) throws ExcepcionUserNoExiste, ExcepcionPasswdIncorrecta, BusinessLogicException {
        Employee employee = new Employee();
        Boss boss = new Boss();
        User user;

        try {
            user = userRest.comprobateUserType(User.class, login);
            if (user.getLogin().equals("Boss")) {
                return boss = userRest.comprobateLogin(Boss.class, login, password);
            } else if(user.getLogin().equals("Employee")) {
                return employee = userRest.comprobateLogin(Employee.class, login, password);
            } else {
                throw new ExcepcionUserNoExiste();
            }
        } catch (NotFoundException ex) {
            throw new ExcepcionUserNoExiste();
        } catch (NotAuthorizedException ex){
            throw new ExcepcionPasswdIncorrecta();
        } catch (InternalServerErrorException ex){
            throw new BusinessLogicException(ex.getMessage());
        }
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
