/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Boss data. This class inherits from de class User. The
 * property of this class is the wage of the boss.It also contains a relational
 * field, a set of {@link Employee} managed by the Boss.
 * @since 23/11/2020
 * @version 1.0
 * @author Xabier Carnero, Endika Ubierna, Markel Lopez de Uralde.
 */
@XmlRootElement
public class Boss extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The Boss of the boss.
     */
    private float wage;
    /**
     * The list of {@link Employee} of the boss.
     */
    private Set<Employee> employees;

    /**
     * Class constructor.
     */
    public Boss() {
    }
    
    public Boss(String login, String email, String fullName, String password) {
        super(login, email, fullName, password);
        this.wage = 3500;
        super.setPrivilege(UserPrivilege.BOSS);
        super.setLastAccess(Date.valueOf(LocalDate.now()));
        super.setLastPasswordChange(Date.valueOf(LocalDate.now()));
    }

    /**
     * Gets the wages of the boss.
     * @return The Boss value.
     */
    public float getWage() {
        return wage;
    }

    /**
     * Sets the wages of the boss.
     * @param wage The Boss value.
     */
    public void setWage(float wage) {
        this.wage = wage;
    }

    /**
     * Gets the list of {@link Employee} of the boss.
     * @return The Set of {@link Employee} value.
     */
    @XmlTransient
    public Set<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of {@link Employee} of the boss.
     * @param employees The Set of {@link Employee} value.
     */
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
