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
 *
 * @author xabig
 */
public interface UserInterface {
    public User findUsersByLogin(String login) throws BusinessLogicException;
    public void editChangePassword(User user, String oldPass, String newPass) throws BusinessLogicException;
    public void loginUser(String login, String password) throws BusinessLogicException;
    public void sendPassword(User user) throws BusinessLogicException;
    public void edit(Object requestEntity) throws BusinessLogicException;
    public <T> T find(Class<T> responseType, String id) throws BusinessLogicException;
    public void create(Object requestEntity) throws BusinessLogicException;
    public <T> T findAllEmployees() throws BusinessLogicException;
    public void remove(String id) throws BusinessLogicException;
}