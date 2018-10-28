package com.quadbaze.headstart.services.authentication;

import com.quadbaze.headstart.domain.entities.Role;
import com.quadbaze.headstart.domain.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Olatunji O. Longe: Created on (14/08/2018)
 */

public class AuthenticationUserPrincipal implements UserDetails {

    private User user;

    public AuthenticationUserPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        if(user != null){
            for(Role role : user.getRoles()){
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleType().name());
                if(!authList.contains(authority)){
                    authList.add(authority);
                }
            }
        }
        return authList;
    }

    @Override
    public String getPassword() {
        if (user != null){
            return user.getPassword();
        }
        return "";
    }

    @Override
    public String getUsername() {
        return user != null ? user.getUsername() : "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return getAuthenticationPrincipalStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getAuthenticationPrincipalStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getAuthenticationPrincipalStatus();
    }

    @Override
    public boolean isEnabled() {
        return getAuthenticationPrincipalStatus();
    }

    private boolean getAuthenticationPrincipalStatus(){
        return user != null && user.isActive();
    }
}
