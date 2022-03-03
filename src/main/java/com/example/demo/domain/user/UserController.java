package com.example.demo.domain.user;
import com.example.demo.domain.user.dto.PrivateUserDTO;
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
import javax.validation.Valid;
import java.util.*;

@RestController @RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final RoleService roleService;

    private final UserMapper userMapper;


    @PostMapping("/")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
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
        if (auth.getName().equals(username) || auth.getAuthorities().contains(roleService.findByRoleName("ADMIN"))) {
            return new ResponseEntity<>(userMapper.convertUserToPrivateUserDTO(userService.findByUsername(username)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userMapper.convertUserToPublicUserDTO(userService.findByUsername(username)), HttpStatus.OK);
        }
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<User> findById(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.findById(uuid), HttpStatus.OK);
    }

    @PutMapping("/id/{uuid}/role/id/{roleId}")
    public ResponseEntity<Object> addRoleById(@PathVariable UUID uuid, @PathVariable UUID roleId) throws InstanceNotFoundException {
        return new ResponseEntity<>(userService.addRoleById(uuid, roleId), HttpStatus.OK);
    }

    @DeleteMapping("/id/{uuid}")
    public ResponseEntity<String> deleteById(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.deleteById(uuid), HttpStatus.OK);
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<String> deleteByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.deleteByUsername(username), HttpStatus.OK);
    }
}
