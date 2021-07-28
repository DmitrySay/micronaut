package com.solbeg.security;

import com.solbeg.model.User;
import io.micronaut.security.authentication.UserDetails;

import java.util.Collection;

public class JwtUserDetails extends UserDetails {
    private User user;

    public JwtUserDetails(User user, Collection<String> roles) {
        super(user.getEmail(), roles);
        this.user = user;
    }
}
