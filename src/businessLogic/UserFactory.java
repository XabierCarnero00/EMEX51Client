/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 * This class is a factory for {@link UserInterface} interface.
 * @author xabig
 */
public class UserFactory {
         /**
     * This method returns an interface implementing methods for {@link User}
     * object management.
     * @return An object implementing the interface {@link UserInterface}.
     */   
    public static UserInterface getUserImp(){
        return new UserImplementation();
    }
    
}
