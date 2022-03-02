package com.example.demo.domain.user;


import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    User addRoleById(UUID id, UUID roleId) throws InstanceNotFoundException;
    User findByUsername(String username);
    Optional<User> findById(UUID id) throws InstanceNotFoundException;
    List<User> findAll();
    void deleteById(UUID id);
}
