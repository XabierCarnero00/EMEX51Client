/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import clientREST.UserREST;
import exceptions.ExcepcionEmailNoExiste;
import java.util.logging.Logger;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import model.Boss;
import model.Employee;
import model.User;
import model.UserPrivilege;
import model.Visitor;

/**
 *
 * @author Endika Ubierna Lopez
 */
public class UserImplementation implements UserInterface {
    /**
     * Logger object used to control activity from class SectorManagerImplementation.
     */
    private static final Logger LOGGER=Logger.getLogger("UserManagerImplentation");
    /**
     * REST users web client
     */
    private UserREST webClient;
    /**
     * Create a SectorManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public UserImplementation(){
        webClient=new UserREST();
    }
    /**
     * Logger object used to control activity from class SectorManagerImplementation.
     */
    @Override
    public User findUsersByLogin(String login) throws BusinessLogicException {
        User user = null;
        try{
            LOGGER.info("Metodo getUserByLogin de la clase UserManagerImplementation.");
            user = webClient.findUsersByLogin(User.class, login);
        }catch(Exception e){
            throw new BusinessLogicException(e.getMessage());
        }
        return user;        
    }

    @Override
    public void editChangePassword(User user, String oldPass, String newPass) throws BusinessLogicException {
        try{
            LOGGER.info("Metodo editChangePassword de la clase UserManagerImplementation.");
            webClient.editChangePassword(user,oldPass,newPass);
        }catch(WebApplicationException e){
            throw new BusinessLogicException(e.getMessage());
        }    
    }

    @Override
    public void loginUser(String login, String password) throws BusinessLogicException {
        LOGGER.info("Metodo loginUser de la clase UserManagerImplementation.");
        User user = null;
        try{
           user = findUsersByLogin(login);
           if(user.getPrivilege()==UserPrivilege.BOSS)
               user = webClient.loginUser(Boss.class, login, password);
           else if (user.getPrivilege()==UserPrivilege.EMPLOYEE)
               user = webClient.loginUser(Employee.class, login, password);
           else
               user = webClient.loginUser(Visitor.class, login, password);
        }catch(ForbiddenException e){
            throw new BusinessLogicException(e.getMessage());
        }
    }

    @Override
    public void sendPassword(User user) throws BusinessLogicException,ExcepcionEmailNoExiste {
        LOGGER.info("Metodo sendPassword de la clase UserManagerImplementation.");
        try{
            webClient.editForgotPassword(user);
        }catch(ForbiddenException e){
            throw  new ExcepcionEmailNoExiste();
        }catch(WebApplicationException e){
            throw new BusinessLogicException(e.getMessage());
        }        
    }

    @Override
    public void edit(Object requestEntity) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T find(Class<T> responseType, String id) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object requestEntity) throws BusinessLogicException {
    }

    @Override
    public <T> T findAllEmployees() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String id) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
