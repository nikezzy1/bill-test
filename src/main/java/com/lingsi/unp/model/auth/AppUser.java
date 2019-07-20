package com.lingsi.unp.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * APP用户信息
 */
@Component
public class AppUser implements UserDetails {

    private String userId;

    private String username;

    private String password;

    private Boolean enabled;

    private Boolean accountNonLocked;

    private List<AppRole> roles;
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AppRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleid()));
        }
        return authorities;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @JsonIgnore
    @Override
    //账户是否过期
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    @JsonIgnore
    @Override
    //认证是否过期
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AppRole> roles) {
        this.roles = roles;
    }

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }


}