/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity JPA class for Army data. This class inherits from de class
 * SectorContent. The property of this class is the ammunition.
 * @author Endika Ubierna, Markel Lopez de Uralde, Xabier Carnero
 * @version 1.0
 * @since 01/12/2020
 */
@XmlRootElement
public class Army extends SectorContent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The quantity of ammunition of the army.
     */
    private Integer ammunition;

    /**
     * Class constructor.
     */
    public Army() {
        super();
    }
    /**
     * Gets the quantity of ammunition of the army.
     * @return The ammunition value.
     */
    public int getAmmunition() {
        return ammunition;
    }
    /**
     * Sets the quantity of ammunition of the army.
     * @param ammunition The ammunition value.
     */
    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

}
