/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Employee data. This class inherits of the superclass
 * User. The properties of this class are wage and job. It also contains
 * relational fields for getting the {@link Sector} managed and the
 * {@link Visitor} management and a reference to his {@link Boss}.
 * @since 23/11/2020
 * @version 1.0
 * @author Xabier Carnero, Endika Ubierna, Markel Lopez de Uralde.
 */
@XmlRootElement
public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Wage of the employee.
     */
    private float wage;
    /**
     * Position of the employee.
     */
    private String job;
    /**
     * List of {@link Sector} where the employee works.
     */
    private Set<EmployeeSectorManagement> sectorsManaged;
    /**
     * List of {@link Visitor} the employee manages.
     */
    private Set<Visitor> visitors;
    /**
     * The Boss of the employee.
     */
    private Boss boss;

    /**
     * Class constructor.
     */
    public Employee() {
    }

    /**
     * Gets the wages of the employee.
     * @return The Boss value.
     */
    public float getWage() {
        return wage;
    }

    /**
     * Sets the wages of the employee.
     * @param Salario The Boss value.
     */
    public void setWage(float Salario) {
        this.wage = Salario;
    }

    /**
     * Gets the position of the employee.
     * @return The position value.
     */
    public String getJob() {
        return job;
    }

    /**
     * Sets the position of the employee.
     * @param puesto The position value.
     */
    public void setJob(String puesto) {
        this.job = puesto;
    }

    /**
     * Gets a list of {@link Sector} managed by the employee.
     * @return The list Sector value.
     */
    public Set<EmployeeSectorManagement> getSectors() {
        return sectorsManaged;
    }

    /**
     * Sets a list of {@link Sector} managed by the employee.
     * @param sector The list {@link Sector} value.
     */
    public void setSectors(Set<EmployeeSectorManagement> sectors) {
        this.sectorsManaged = sectors;
    }

    /**
     * Gets a list of {@link Visitor} managed by the employee.
     * @return The list {@link Visitor} value.
     */
    @XmlTransient
    public Set<Visitor> getVisitors() {
        return visitors;
    }

    /**
     * Sets a list of {@link Visitor} managed by the employee.
     * @param visitante The list {@link Visitor} value.
     */
    public void setVisitors(Set<Visitor> visitantes) {
        this.visitors = visitantes;
    }

    /**
     * Gets the {@link Boss} of the employee.
     * @return The {@link Boss} value.
     */
    @XmlTransient
    public Boss getBoss() {
        return boss;
    }

    /**
     * Sets the {@link Boss} of the employee.
     * @param jefe The {@link Boss} value.
     */
    public void setBoss(Boss jefe) {
        this.boss = jefe;
    }
}
