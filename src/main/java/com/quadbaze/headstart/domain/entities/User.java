package com.quadbaze.headstart.domain.entities;

import com.quadbaze.headstart.domain.base.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
@Entity
@Table(name = "users")
public class User extends AuditableEntity {

    @Column(name="active")
    private boolean active = false;

    @Size(min=6, max=256)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(min=6, max=1024)
    @Column(name = "password", nullable = false, length=1024)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logged_in_at")
    private Date loggedInAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logged_out_at")
    private Date loggedOutAt;

    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.MERGE}, targetEntity = Role.class)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName="id"))
    private List<Role> roles = new ArrayList<>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Date getLoggedInAt() {
        return loggedInAt;
    }

    public void setLoggedInAt(Date loggedInAt) {
        this.loggedInAt = loggedInAt;
    }

    public Date getLoggedOutAt() {
        return loggedOutAt;
    }

    public void setLoggedOutAt(Date loggedOutAt) {
        this.loggedOutAt = loggedOutAt;
    }

    @Override
    public int hashCode() {
        return hashCoder()
                .append(this.getId())
                .append(this.getUsername())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        if (obj == this) return true;
        return equalizer()
                .append(this.getId(), ((User)obj).getId())
                .append(this.getUsername(), ((User)obj).getUsername())
                .append(this.getRoles(), ((User)obj).getRoles())
                .isEquals();
    }

}
