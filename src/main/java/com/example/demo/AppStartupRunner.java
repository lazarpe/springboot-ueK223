package com.example.demo;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.userProfile.UserProfile;
import com.example.demo.domain.userProfile.UserProfileService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

//        Authorities (CRUD)
        Authority add_auth = new Authority(null, "CREATE");
        authorityRepository.save(add_auth);

        Authority read_auth = new Authority(null,"READ");
        authorityRepository.save(read_auth);

        Authority readAll_auth = new Authority(null, "READ-ALL");
        authorityRepository.save(readAll_auth);

        Authority update_auth = new Authority(null, "UPDATE");
        authorityRepository.save(update_auth);

        Authority delete_auth = new Authority(null, "DELETE");
        authorityRepository.save(delete_auth);

//       Roles
        Role default_role = new Role(null, "DEFAULT",Arrays.asList(read_auth));
        roleRepository.save(default_role);

        Role admin_role = new Role(null, "ADMIN", Arrays.asList(read_auth, add_auth, update_auth, delete_auth));
        roleRepository.save(admin_role);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
        User default_user = new User(null, "james","james.bond@mi6.com",passwordEncoder.encode("bond"), Set.of(default_role));
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "Zurich", "", null, "Default bio");
      
        userService.saveUser(default_user);
        userService.addRoleById(default_user.getId(), default_role.getId());

        UserProfile testUserProfile = new UserProfile("French Street", "sadadsada.png", null, "Test bio but keep it up so let's gooo", default_user);
        userProfileService.saveUserProfile(testUserProfile);

        User admin_user = new User(null, "boss", "boss@email.com", passwordEncoder.encode("bosspw"), Set.of(admin_role));
        UserProfile adminUserProfile = new UserProfile(UUID.randomUUID(), "Boss City", "", null, "I'm the boss");

        userService.saveUser(admin_user);
        userService.addRoleById(admin_user.getId(), admin_role.getId());
    }
}
