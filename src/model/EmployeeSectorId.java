/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * {@link Embeddable} Class that contains the primary keys of the entities
 * {@link Employee} and {@link Sector}
 * @author Endika Ubierna, Markel Lopez de Uralde, Xabier Carnero
 * @version 1.0
 * @since 01/12/2020
 */
//Esta clase contiene las claves primaria de empleado y sector. Es parte de lo que hay que hacer en una relación N:M con atributos
//Da igual el nombre de los atributos lo importante es que el tipo en este caso integer sea igual que el de la pk de empleado y sector.
//Esta clase será luego embebida por employeesectormanagement
public class EmployeeSectorId implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id field of the {@link Employee} entity.
     */
    private Integer employeeId;
    /**
     * Id field of the {@link Sector} entity.
     */
    private Integer sectorId;

    /**
     * Gets the {@link Employee} id.
     * @return The id value.
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the {@link Employee} id.
     * @param employeeId The id value.
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the {@link  Sector} id.
     * @return The id value.
     */
    public Integer getSectorId() {
        return sectorId;
    }

    /**
     * Sets the {@link Sector} id.
     * @param sectorId The id value.
     */
    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        //Si idArmamento es nulo devuelve 0 sino devuelve su valor numérico.
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        hash += (sectorId != null ? sectorId.hashCode() : 0);
        return hash;
    }

    /**
     * This method compares two employeeSectorId objects for equality. This
     * implementation compare id field value for equality, in this case the id
     * is a composite id.
     * @param obj The object to compare to.
     * @return True if objects are equals, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        //Comprobar que el objeto recibido como parámetro es una instancia de arma
        if (!(obj instanceof EmployeeSectorId)) {
            return false;
        }
        EmployeeSectorId other = (EmployeeSectorId) obj;
        if ((this.employeeId == null && other.employeeId != null)
                || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        if ((this.sectorId == null && other.sectorId != null)
                || (this.sectorId != null && !this.sectorId.equals(other.sectorId))) {
            return false;
        }
        return true;
    }

    /**
     * This method returns a String representation for a employeeSectorId
     * instance.
     * @return The String representation for the sectorContent object.
     */
    @Override
    public String toString() {
        return "EmployeeSectorId{" + "employeeId=" + employeeId + ", sectorId=" + sectorId + '}';
    }

}
