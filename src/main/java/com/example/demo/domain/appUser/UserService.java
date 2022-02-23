package com.example.demo.domain.appUser;


import com.example.demo.domain.role.Role;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    Optional<User> findById(UUID id) throws InstanceNotFoundException;
    List<User> findAll();
}
