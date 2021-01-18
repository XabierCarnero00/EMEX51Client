/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Sector data. The properties of this class are idSector,
 * name and type. It also contains relational fields for getting the
 * {@link Visitor} who visit the sector the {@link SectorContent} which are
 * storaged in the sector, this {@link SectorContent} can be {@link Creature} or
 * {@link Army} and the {@link Employee} who manage the sector.
 * @author Xabier Carnero, Endika Ubierna, Markel Lopez de Uralde.
 * @version 1.0
 * @since 01/12/2020
 */
@XmlRootElement
public class Sector implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id field of the Sector Entity. It is also the id value of the sector.
     */
    private Integer id;
    /**
     * Name field of the Sector Entity.
     */
    private String name;
    /**
     * List of {@link Visitor} belonging to the sector.
     */
    private Set<Visitor> visitors;
    /**
     * List of {@link EmployeeSectorManagement} belonging to the Sector.
     */
    private Set<EmployeeSectorManagement> employees;
    /**
     * List of {@link Criature} or {@link Army} belonging to the Sector.
     */
    private Set<SectorContent> sectorContent;
    /**
     * {@link Type} of the sector.
     */
    private SectorType type;

    /**
     * Class constructor.
     */
    public Sector() {
    }

    /**
     * Gets the id of the sector.
     * @return The id value.
     */
    public Integer getIdSector() {
        return id;
    }

    /**
     * Sets the id of the sector.
     * @param idSector The id of the sector.
     */
    public void setIdSector(Integer idSector) {
        this.id = idSector;
    }

    /**
     * Gets the name of the sector.
     * @return The name value
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the sector.
     * @param name The name of the sector.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets visitors of the sector.
     * @return A set of visitors
     */
    @XmlTransient
    public Set<Visitor> getVisitors() {
        return visitors;
    }

    /**
     * Sets the visitors of the sector.
     * @param visitors The visitors collection value.
     */
    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    /**
     * Gets the employees who manage the sector.
     * @return The employee collection value.
     */
    @XmlTransient
    public Set<EmployeeSectorManagement> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees who manage the sector.
     * @param empleados The employee collection value.
     */
    public void setEmployees(Set<EmployeeSectorManagement> empleados) {
        this.employees = empleados;
    }

    /**
     * Gets the type of the sector.
     * @return The type of the sector value.
     */
    public SectorType getType() {
        return type;
    }

    /**
     * Sets the type of the sector.
     * @param type The type value.
     */
    public void setType(SectorType type) {
        this.type = type;
    }

    /**
     * Gets a set of {@link Criature} or {@link Army} belonging to the sector.
     * @return The set of {@link Criature} or {@link Army} value.
     */
    @XmlTransient
    public Set<SectorContent> getSectorContent() {
        return sectorContent;
    }

    /**
     * Sets a set of {@link Criature} or {@link Army} belonging to the sector.
     * @param sectorContent The set of {@link Criature} or {@link Army} value.
     */
    public void setSectorContent(Set<SectorContent> sectorContent) {
        this.sectorContent = sectorContent;
    }

    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * This method compares two sector entities for equality. This
     * implementation compare id field value for equality.
     * @param object
     * @return True if objects are equals, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sector)) {
            return false;
        }
        Sector other = (Sector) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * This method returns a String representation for a sector entity instance.
     * @return The String representation for the Sector object.
     */
    @Override
    public String toString() {
        return name;
    }
}
