package com.example.demo;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.userprofile.UserProfile;
import com.example.demo.domain.userprofile.UserProfileService;
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
        Authority addAuth = new Authority(null, "CREATE");
        authorityRepository.save(addAuth);

        Authority readAuth = new Authority(null,"READ");
        authorityRepository.save(readAuth);

        Authority readAllAuth = new Authority(null, "READ-ALL");
        authorityRepository.save(readAllAuth);

        Authority updateAuth = new Authority(null, "UPDATE");
        authorityRepository.save(updateAuth);

        Authority deleteAuth = new Authority(null, "DELETE");
        authorityRepository.save(deleteAuth);

//       Roles
        Role defaultRole = new Role(null, "DEFAULT", List.of(readAuth));
        roleRepository.save(defaultRole);

        Role admin_role = new Role(null, "ADMIN", Arrays.asList(read_auth, add_auth, update_auth, delete_auth));
        roleRepository.save(admin_role);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
      
        //Test User
        User defaultUser = new User(null, "james","james.bond@mi6.com",passwordEncoder.encode("bond"), Set.of(defaultRole));
      
        userService.saveUser(defaultUser);
        userService.addRoleById(defaultUser.getId(), defaultRole.getId());

        UserProfile testUserProfile = new UserProfile("French Street", "sadadsada.png", null, "Test bio but keep it up so let's gooo", defaultUser);
        userProfileService.saveUserProfile(testUserProfile);

      
        //Admin
        User adminUser = new User(null, "boss", "boss@email.com", passwordEncoder.encode("bosspw"), Set.of(adminRole));
      
        userService.saveUser(admin_user);
        userService.addRoleById(admin_user.getId(), admin_role.getId());

        UserProfile adminUserProfile = new UserProfile("Boss ave", "boss-baby.png", null, "Who is the boss ?", admin_user);
        userProfileService.saveUserProfile(adminUserProfile);
    }
}
