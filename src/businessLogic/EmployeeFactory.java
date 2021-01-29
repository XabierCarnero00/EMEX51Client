/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 * Class for taking the Employee Interface with all the Business Methods.
 * 
 * @author Xabier Carnero
 */
public class EmployeeFactory {

    /**
     * This method returns an interface implemeting methods for {@link Employee}
     * object management.
     *
     * @return An object implementing the interface {@link Employee}.
     */
    public static EmployeeInterface getEmployeeImp() {
        return new EmployeeImplementation();
    }

}
