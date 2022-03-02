package com.example.demo.domain.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    public boolean hasUserName(Authentication authentication, String username) {
        return authentication.getName().equals(username);
    }
}
