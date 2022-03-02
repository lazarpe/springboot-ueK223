package com.example.demo.domain.role;

import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void addAuthorityToRole( String rolename, String authorityname) {
        Authority authority = authorityRepository.findByName(authorityname);
        Role role = roleRepository.findByName(rolename);
        role.getAuthorities().add(authority);
    }

    @Override
    public String saveRole(Role role) throws InstanceAlreadyExistsException {
        try {
            roleRepository.saveAndFlush(role);
            return "Role saved";
        } catch (Exception InstanceAlreadyExistsException) {
            return "Role already exists";
        }
    }

    @Override
    public void udpateRole(Role role, UUID id) throws InstanceNotFoundException {
        try {
            roleRepository.findById(id).map(currentRole -> {
                currentRole.setName(role.getName());
                currentRole.getAuthorities().clear();
                currentRole.setAuthorities(role.getAuthorities());
                return roleRepository.save(currentRole);
            }).orElseGet(() -> {
                role.setId(id);
                return roleRepository.save(role);
            });
        } catch (Exception e) {
            System.out.println("Something wrong with the new data.");
            //e.printStackTrace();
        }
    }

    @Override
    public void deleteRole(UUID id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Deleting didn't work");
            e.printStackTrace();
        }
    }

    @Override
    public Role getRole(String roleName) {
        return null;
    }

    @Override
    public Optional<Role> findById(UUID id) throws InstanceNotFoundException {
        return roleRepository.findById(id);
    }

    @Override
    public Role findByRoleName(String rolename) throws InstanceNotFoundException {
        return roleRepository.findByName(rolename);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

