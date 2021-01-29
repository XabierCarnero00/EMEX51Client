/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.ExcepcionContraseñaNoCoincide;
import exceptions.ExcepcionEmailNoExiste;
import exceptions.ExcepcionPasswdIncorrecta;
import exceptions.ExcepcionUserNoExiste;
import model.User;

/**
 * Interface encapsulating methods for <code>User</code> Management for Emex51 project. 
 * @author xabig
 */
public interface UserInterface {
    /**
     * Returns a User with that login and password.
     * @param login the login of the User
     * @param password the password of the User
     * @return the User
     * @throws ExcepcionUserNoExiste
     * @throws ExcepcionPasswdIncorrecta
     * @throws BusinessLogicException 
     */
    public User login(String login, String password) throws ExcepcionUserNoExiste, ExcepcionPasswdIncorrecta, BusinessLogicException;
    
    /**
     * Makes a temporal password to the User 
     * and sends him an Email with it.
     * @param email the email of the User.
     * @throws ExcepcionEmailNoExiste 
     */
    public void temporalPass(String email) throws ExcepcionEmailNoExiste;
    
    /**
     * Changes the password of the User
     * @param newPass the new password to be set on the User
     * @param email the email of the User
     * @param TempPassw the temporal password set on the User
     * @throws ExcepcionContraseñaNoCoincide 
     */
    public void changePassword(String newPass, String email, String TempPassw) throws ExcepcionContraseñaNoCoincide;
    
}
