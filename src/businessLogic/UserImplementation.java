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
import java.util.logging.Logger;
import javax.ws.rs.ForbiddenException;
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
 * @author Markel Lopez de Uralde, Endika Ubierna, Xabier Carnero
 */
public class UserImplementation implements UserInterface {
    /**
     * Logger object used to control activity from class SectorManagerImplementation.
     */
    private static final Logger LOGGER=Logger.getLogger("UserImplentation");
    /**
     * REST users web client
     */
    UserREST userRest = new UserREST();
    /**
     * This method treats a login operation.
     * @param login Users login
     * @param password Users password
     * @return An user
     * @throws ExcepcionUserNoExiste
     * @throws ExcepcionPasswdIncorrecta
     * @throws BusinessLogicException 
     */
    @Override
    public User login(String login, String password) throws ExcepcionUserNoExiste, ExcepcionPasswdIncorrecta, BusinessLogicException {
        LOGGER.info("Metodo loginUser de la clase UserImplementation.");
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
    /**
     * This method sends an email to the user for password recovery. Before finds if the user exists.
     * @param email The users email.
     * @throws ExcepcionEmailNoExiste 
     */
    @Override
    public void temporalPass(String email) throws ExcepcionEmailNoExiste,BusinessLogicException {
        LOGGER.info("Metodo sendPassword de la clase UserManagerImplementation.");
        try {
            userRest.sendMail(User.class, email);
        } catch (ForbiddenException ex) {
            throw new ExcepcionEmailNoExiste();
        }catch (InternalServerErrorException e){
            throw  new BusinessLogicException("Error");
        }
    }
    /**
     * This method sends the old and new password of the user for password change.
     * @param email The users email.
     * @param tempPass The temporal current password
     * @param newPass The new password
     * @throws ExcepcionContraseñaNoCoincide 
     */
    @Override
    public void changePassword(String email, String tempPass, String newPass) throws ExcepcionContraseñaNoCoincide {
        LOGGER.info("Metodo editChangePassword de la clase UserManagerImplementation.");
        try {
            newPass = ClavePublicaCliente.cifrarTexto(newPass);
            userRest.changePassword(User.class, email, tempPass, newPass);
        } catch (WebApplicationException ex) {
            throw new ExcepcionContraseñaNoCoincide();
        }
    }

}
