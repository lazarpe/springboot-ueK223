package com.example.demo.domain.appUser;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController @RequestMapping("/api")
@RequiredArgsConstructor
public class    UserController {
//    ADD YOUR ENDPOINT MAPPINGS HERE
private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> HomeTest(){
        return ResponseEntity.ok().body("Hello World");
    }

    @GetMapping("/user")
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/?username={username}")
    public User getUser(String username) {
        return userService.getUser(username);
    }

    @GetMapping("/user/?id={id}")
    public Optional<User> findById(@PathVariable UUID id) throws InstanceNotFoundException {
        return userService.findById(id);
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) throws InstanceAlreadyExistsException {
        return userService.saveUser(user);
    }

    @PutMapping("/user/?username={username}&roleName={roleName}")
    public void addRoleToUser(@PathVariable String username, @PathVariable String roleName) {
        userService.addRoleToUser(username, roleName);
    }

}
