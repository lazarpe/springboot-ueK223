package com.example.demo.domain.appUser;


import com.example.demo.domain.appUser.dto.PublicUserDTO;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    User addRoleById(UUID id, UUID roleId) throws InstanceNotFoundException;
    PublicUserDTO findByUsername(String username);
    Optional<User> findById(UUID id) throws InstanceNotFoundException;
    List<User> findAll();
    void deleteById(UUID id);
}
