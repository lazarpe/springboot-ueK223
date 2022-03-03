package com.example.demo.domain.user;
import com.example.demo.domain.role.RoleService;
import com.example.demo.domain.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.*;

@RestController @RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserSecurity userSecurity;

    /**
     * Saves a new user to the db with hibernate (everyone is allowed to create new users)
     * @param user The JSON object of the user entity
     * @return the saved user entity
     * @throws InstanceAlreadyExistsException will throw the exception if the users instance already exists
     */
    @PostMapping("/")
    public User saveUser(@RequestBody User user) throws InstanceAlreadyExistsException {
        return userService.saveUser(user);
    }

    /**
     * Gets all existing users (endpoint only accessible for admin role users)
     * @return a collection of all users
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * Every default user can search users by their username. If a user with a default role looks up himself he will see
     * sensitive info but nothing backend specific like roles. If the user looks up other users he will see public info
     * only like username and email. An admin role user can see absolutely all the info (including backend details like
     * roles)
     * @param username PathVariable used to find users by their name
     * @return User entity / modified DTO
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEFAULT')")
    @GetMapping("/username/{username}")
    public ResponseEntity<Object> findByUsername(@PathVariable String username) {
        if (userSecurity.hasRole(userSecurity.getCurrentlyLoggedInUser(), "ROLE_ADMIN")) {
            return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
        } else if (userSecurity.getCurrentlyLoggedInUser().getName().equals(username)) {
            return new ResponseEntity<>(
                    userMapper.convertUserToPrivateUserDTO(userService.findByUsername(username))
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    userMapper.convertUserToPublicUserDTO(userService.findByUsername(username))
                    , HttpStatus.OK);
        }
    }

    /**
     * Only admin role users can search users by their ID because this backend info is not available for the default
     * role users.
     * @param id id value from the url path
     * @return User entity (no DTO because an admin role user is allowed to see EVERYTHING)
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Only admin role users can add roles to users.
     * @param id value from the url path
     * @param roleId value from the url path
     * @return ResponseEntity based on dynamic behaviour of users interactions with the program
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/id/{id}/role/id/{roleId}")
    public ResponseEntity<Object> addRoleById(@PathVariable UUID id, @PathVariable UUID roleId) {
        try {
            return new ResponseEntity<>(userService.addRoleById(id, roleId), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>("user or role not found", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("user or role is null", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * admin users can delete user accounts by ID, which will delete the userprofile of the connected user automatically
     * because of the 1 to 1 relation with the userprofile.
     * @param id value from the url path
     * @return ResponseEntity which prints that a user (didn't) got deleted
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>("user deleted", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("user is null", HttpStatus.NOT_FOUND);
        }
    }
}
