package com.example.demo.domain.appUser;


import com.example.demo.domain.appUser.dto.PublicUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController @RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
//    ADD YOUR ENDPOINT MAPPINGS HERE
private final UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) throws InstanceAlreadyExistsException {
        return userService.saveUser(user);
    }

    @GetMapping("")
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<PublicUserDTO> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
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
