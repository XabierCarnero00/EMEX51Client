/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity JPA class for SectorContent data. The properties of this class are id,
 * name, arrivalDate. It also contains relational fields for getting the
 * {@link Sector} where the content are storaged.
 * @author Endika Ubierna, Markel Lopez de Uralde, Xabier Carnero
 * @version 1.0
 * @since 01/12/2020
 */
@XmlRootElement
public class SectorContent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id field of the Criature Entity. It is also the id value of the criature.
     */
    private Integer id;
    /**
     * Name of the army.
     */
    private String name;
    /**
     * {@link Sector} where the army is stored.
     */
    private Sector sector;
    /**
     * Arrival Date of the army to the facilities.
     */
    private Date arrivalDate;

    /**
     * Class constructor
     */
    public SectorContent() {
    }

    /**
     * Gets the id of the content. Can be a {@link Creature} or {@link Army}
     * instance.
     * @return The id value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the content. Can be a {@link Creature} or {@link Army}
     * instance.
     * @param id The id value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the content. Can be a {@link Creature} or {@link Army}
     * instance.
     * @return The name value.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the content. Can be a {@link Creature} or {@link Army}
     * instance.
     * @param name The name value.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the {@link Sector} where the content is storaged.
     * @return The {@link Sector} value.
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Sets the {@link Sector} where the content is storaged.
     * @param sector The {@link Sector} value.
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    /**
     * Sets the date where the content arrived to the area. This content can be
     * a {@link Creature} or {@link Army} instance.
     * @return The date value.
     */
    public Date getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the date where the content arrived to the area. This content can be
     * a {@link Creature} or {@link Army} instance.
     * @param arrivalDate The date value.
     */
    public void setArrivalDate(Date arrivalDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        this.arrivalDate = arrivalDate;
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
     * This method compares two SectorContent entities for equality. This
     * implementation compare id field value for equality.
     * @param object
     * @return True if objects are equals, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SectorContent)) {
            return false;
        }
        SectorContent other = (SectorContent) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * This method returns a String representation for a sectorcontent entity
     * instance.
     * @return The String representation for the sectorContent object.
     */
    @Override
    public String toString() {
        return "sectorContent.Sectorcontent[ id=" + id + " name=" + name + " ]";
    }

}
