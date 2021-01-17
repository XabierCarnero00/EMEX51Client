/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussinesLogic;

import clientREST.ArmyREST;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import model.Army;

/**
 *
 * @author xabig
 */
public class ArmyImplementation implements ArmyInterface{
    /**
     * The client REST service of Army. 
     */
    ArmyREST armyRest = new ArmyREST();
    
    /**
     * Returns all Armys.
     * @return list of armys
     */
    @Override
    public List<Army> getAllArmys(){
        return armyRest.findAllArmys(new GenericType<List<Army>>(){});
    }

}
