package com.example.demo.domain.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("userSecurity")
public class UserSecurity {

    /**
     * @return currently logged in user
     */
    public Authentication getCurrentlyLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * @param authentication
     * @param username
     * @return boolean based on compared usernames
     */
    public boolean hasUserName(Authentication authentication, String username) {
        return authentication.getName().equals(username);
    }

    /**
     * @param authentication
     * @param role
     * @return a boolean if the currently logged in user has a role from the function parameter
     */
    public boolean hasRole(Authentication authentication, String role) {
        return Arrays.toString(authentication.getAuthorities().toArray()).contains(role);
    }
}
