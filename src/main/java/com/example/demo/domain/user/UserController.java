package com.example.demo.domain.user;

import com.example.demo.domain.role.RoleService;
import com.example.demo.domain.security.UserSecurity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final UserSecurity userSecurity;

    private final UserMapper userMapper;

    /**
     * Saves a new user to the db with hibernate (everyone is allowed to create new users)
     *
     * @param user The JSON object of the user entity
     * @return the saved user entity
     * @throws InstanceAlreadyExistsException will throw the exception if the users instance already exists
     */
    @Operation(summary = "")
    @PostMapping("/")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws InstanceAlreadyExistsException {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    /**
     * Gets all existing users (endpoint only accessible for admin role users)
     *
     * @return a collection of all users
     */
    @Operation(summary = "")
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
     *
     * @param username PathVariable used to find users by their name
     * @return User entity / modified DTO
     */
    @Operation(summary = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEFAULT')")
    @GetMapping("/username/{username}")
    public ResponseEntity<Object> findByUsername(@PathVariable String username) {
        if (userSecurity.hasRole(userSecurity.getCurrentlyLoggedInUser(), "ROLE_ADMIN")) {
            return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
        } else if (userSecurity.getCurrentlyLoggedInUser().getName().equals(username)) {
            return new ResponseEntity<>(
                    userMapper.convertUserToPrivateUserDTO(userService.findByUsername(username)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    userMapper.convertUserToPublicUserDTO(userService.findByUsername(username)), HttpStatus.OK);
        }
    }


    /**
     * Only admin role users can search users by their ID because this backend info is not available for the default
     * role users.
     *
     * @param uuid id value from the url path
     * @return User entity (no DTO because an admin role user is allowed to see EVERYTHING)
     */
    @Operation(summary = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{uuid}")
    public ResponseEntity<User> findById(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.findById(uuid), HttpStatus.OK);
    }


    /**
     * Only admin role users can add roles to users.
     *
     * @param uuid   value from the url path
     * @param roleId value from the url path
     * @throws InstanceNotFoundException
     * @return ResponseEntity based on dynamic behaviour of users interactions with the program
     */
    @Operation(summary = "")
    @PutMapping("/id/{uuid}/role/id/{roleId}")
    public ResponseEntity<Object> addRoleById(@PathVariable UUID uuid, @PathVariable UUID roleId) throws InstanceNotFoundException {
        return new ResponseEntity<>(userService.addRoleById(uuid, roleId), HttpStatus.OK);
    }


    /**
     * admin users can delete user accounts by username, which will delete the userprofile of the connected user automatically
     * because of the 1 to 1 relation with the userprofile.
     *
     * @param username value from the url path
     * @return ResponseEntity which prints that a user (didn't) get deleted
     */
    @Operation(summary = "")
    @DeleteMapping("/username/{username}")
    public ResponseEntity<String> deleteByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.deleteByUsername(username), HttpStatus.OK);
    }

    /**
     * admin users can delete user accounts by ID, which will delete the userprofile of the connected user automatically
     * because of the 1 to 1 relation with the userprofile.
     *
     * @param uuid value from the url path
     * @return ResponseEntity which prints that a user (didn't) get deleted
     */
    @Operation(summary = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{uuid}")
    public ResponseEntity<String> deleteById(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.deleteById(uuid), HttpStatus.OK);
    }
}
