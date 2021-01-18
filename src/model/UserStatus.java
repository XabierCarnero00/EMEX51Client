
package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This enumeration is for users status. Includes the values ENABLED and DISABLED 
 * @since 23/11/2020
 * @version 1.0
 * @author Xabier Carnero, Endika Ubierna, Markel Lopez de Uralde.
 */
@XmlRootElement
public enum UserStatus{
    /**
     * The user is enabled.
     */
    ENABLED,
    /**
     * The user is disabled.
     */
    DISABLED;
}
