package com.example.demo.domain.role;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    void addAuthorityToRole( String rolename, String authorityname);

    String saveRole(Role role) throws InstanceAlreadyExistsException;
    void udpateRole(Role role, UUID id) throws InstanceNotFoundException;
    void deleteRole(UUID id);
    Role getRole(String roleName);
    Optional<Role> findById(UUID id) throws InstanceNotFoundException;
    Role findByRoleName(String rolename) throws InstanceNotFoundException;
    List<Role> findAll();
}
