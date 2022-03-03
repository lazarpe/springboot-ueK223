package com.example.demo.domain.user;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.UUID;

public interface UserService {

    User saveUser(User user);

    User addRoleById(UUID uuid, UUID roleId) throws InstanceNotFoundException;

    User findByUsername(String username);

    User findById(UUID uuid);

    List<User> findAll();

    String deleteById(UUID uuid);

    String deleteByUsername(String username);
}
