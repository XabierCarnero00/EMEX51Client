package model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity JPA class for user data. The properties of this class are id, login ,
 * name, email, status, privilege, password, last password change, last access.
 * @since 23/11/2020
 * @version 1.0
 * @author Endika Ubierna, Markel Lopez de Uralde, Xabier Carnero.
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id field of the Criature Entity. It is also the id value of the criature.
     */
    private Integer id;
    /**
     * The login value for the user.
     */
    private String login;
    /**
     * The email of the user.
     */
    private String email;
    /**
     * The name of the user.
     */
    private String fullName;
    /**
     * {@link UserStatus} of the user.
     */
    private UserStatus status = UserStatus.ENABLED;
    /**
     * {@link UserPrivilege} of the user.
     */
    private UserPrivilege privilege;
    /**
     * Password of the user.
     */
    private String password;
    /**
     * Last access date of the user
     */
    private Date lastAccess;
    /**
     * Last password change date of the user.
     */
    private Date lastPasswordChange;

    /**
     * Class constructor.
     */
    public User() {

    }

    /**
     * Class constructor with two parameters.
     * @param login The login value.
     * @param password The password value.
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Class constructor with four parameters
     * @param login The login value.
     * @param email The email value.
     * @param fullName The name value.
     * @param password The password value.
     */
    public User(String login, String email, String fullName, String password) {
        this.login = login;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        status = UserStatus.ENABLED;
    }

    /**
     * Gets the login of the user.
     * @return The login value.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login of the user.
     * @param login The login value.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the email value.
     * @return The email value.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email The email value.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the name of the user.
     * @return The name value.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the name of the user.
     * @param fullName The name value.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the {@link UserStatus} of the user.
     * @return The {@link UserStatus} value.
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Sets the {@link UserStatus} of the user.
     * @param status The {@link UserStatus} value.
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Gets the {@link UserPrivilege} of the user.
     * @return The {@link UserPrivilege} value.
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }
    
    /**
     * Sets the {@link UserPrivilege} of the user.
     * @return The {@link UserPrivilege} value.
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     * Gets the id of the user.
     * @return The id value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the user.
     * @param id The id value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the password of the user
     * @return The password value.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password The password value.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the last access date of the user into the application. The Date is
     * registered at the time the user exits the application.
     * @return The date value.
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * Sets the last access date of the user into the application. The Date is
     * registered at the time the user exits the application.
     * @param lastAccess The date value.
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    /**
     * Gets the last time the user changed the password.
     * @return The date value.
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Sets the last time the user changed the password.
     * @param lastPasswordChange The date value.
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
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
     * This method compares two user entities for equality. This implementation
     * compare id field value for equality.
     * @param object
     * @return True if objects are equals, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sector)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * This method returns a String representation for a user entity instance.
     * @return The String representation for the Sector object.
     */
    @Override
    public String toString() {
        return "User{" + "idUser=" + id + '}';
    }

}