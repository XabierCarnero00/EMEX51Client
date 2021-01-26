/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 * 
 * @author Endika Ubierna Lopez
 */
public class UserFactory {
        
    public static UserInterface getUserImp(){
        return new UserImplementation();
    }
    
}
