package com.classic.project.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UpdateUserDetails extends User {

    private String modifiableEmail;

    public UpdateUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.modifiableEmail = username;
    }

    public UpdateUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                                 boolean credentialsNonExpired,
                                 boolean accountNonLocked,
                                 Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.modifiableEmail = username;
    }

    public String getModifiableEmail() {
        return modifiableEmail;
    }

    public void setModifiableEmail(String modifiableEmail) {
        this.modifiableEmail = modifiableEmail;
    }


}
