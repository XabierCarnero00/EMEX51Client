/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 *
 * @author xabig
 */
public class EmployeeFactory {
    
    public static EmployeeInterface getEmployeeImp(){
        return new EmployeeImplementation();
    }
    
}
