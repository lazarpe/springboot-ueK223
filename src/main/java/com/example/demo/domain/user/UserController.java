package com.example.demo.domain.user;


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
    public ResponseEntity<Object> save(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>("user already exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("")
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("user is null", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

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
