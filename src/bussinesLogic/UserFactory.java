/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

/**
 *
 * @author Endika Ubierna Lopez
 */
public class UserFactory {
        
    public static UserManager getUserImp(){
        return new UserManagerImplementation();
    }
    
}
