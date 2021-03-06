package com.example.demo;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.role.RoleServiceImpl;
import com.example.demo.domain.userProfile.UserProfile;
import com.example.demo.domain.userProfile.UserProfileRepository;
import com.example.demo.domain.userProfile.UserProfileService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
//ApplicationListener used to run commands after startup
class AppStartupRunner implements ApplicationRunner {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserProfileService userProfileService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        RUN YOUR STARTUP CODE HERE
//        e.g. to add a user or role to the DB (only for testing)

//        Authorities
        Authority read_auth=new Authority(null,"READ");
        authorityRepository.save(read_auth);

//        Roles
        Role default_role = new Role(null, "DEFAULT",Arrays.asList(read_auth));
        roleRepository.save(default_role);

        User default_user = new User(null, "james","james.bond@mi6.com","bond", Set.of(default_role));
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "Zurich", "", null, "Default bio");

        userService.saveUser(default_user);
        userProfileService.saveUserProfile(userProfile);

        userService.addRoleToUser(default_user.getUsername(), default_role.getName());
    }
}

