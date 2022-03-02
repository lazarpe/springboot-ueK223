package com.example.demo.domain.appUser;


import com.example.demo.domain.appUser.dto.PrivateUserDTO;
import com.example.demo.domain.appUser.dto.PublicUserDTO;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.*;

@RestController @RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
//    ADD YOUR ENDPOINT MAPPINGS HERE
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) throws InstanceAlreadyExistsException {
        return userService.saveUser(user);
    }

    // andMatcher for this endpoint
    @GetMapping("")
    public ResponseEntity<Collection<PrivateUserDTO>> findAll() {
        List<PrivateUserDTO> listOfPrivateUserDTOs = new ArrayList<>();
        for (User user : userService.findAll()) {
            listOfPrivateUserDTOs.add(userMapper.convertUserToPrivateUserDTO(user));
        }
        return new ResponseEntity<>(listOfPrivateUserDTOs, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) throws InstanceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("ROLE: " + roleService.findByRoleName("ADMIN"));
        if (auth.getName().equals(username) || auth.getAuthorities().contains(roleService.findByRoleName("ADMIN"))) {
            System.out.println("CRAZY NAME: " + auth.getName());
            return new ResponseEntity<>(userMapper.convertUserToPrivateUserDTO(userService.findByUsername(username)), HttpStatus.OK);
        } else {
            System.out.println("NOT SO CRAZY NAME: " + auth.getName());
            return new ResponseEntity<>(userMapper.convertUserToPublicUserDTO(userService.findByUsername(username)), HttpStatus.OK);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{id}/role/id/{roleId}")
    public ResponseEntity<?> addRoleById(@PathVariable UUID id, @PathVariable UUID roleId) {
        try {
            return new ResponseEntity<>(userService.addRoleById(id, roleId), HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>("user or role not found", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("user or role is null", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>("user deleted", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("user is null", HttpStatus.NOT_FOUND);
        }
    }
}
