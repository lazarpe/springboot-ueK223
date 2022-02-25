package com.example.demo.domain.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by lazar on 2/24/2022.
 * Project name: demo
 **/

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    /**
     * Get all saved roles from DB.
     *
     * @return All roles as ResponseEntity
     */
    @GetMapping("/")
    public ResponseEntity<Collection<Role>> fetchAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    /**
     * Returns a ResponseEntity which gets filtered by the ID of the role the user sends with the get mapping.
     *
     * @param id
     * @return Role information by ID
     * @throws InstanceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Role>> fetchRoleById(@PathVariable UUID id) throws InstanceNotFoundException {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    /**
     * Returns a ResponseEntity which gets filtered by the name of the role the user sends with the get mapping.
     *
     * @param rolename
     * @return Role information by role name
     * @throws InstanceNotFoundException
     */
    @GetMapping("/search/{rolename}")
    public ResponseEntity<Role> fetchRoleByName(@PathVariable String rolename) throws InstanceNotFoundException {
        return new ResponseEntity<>(roleService.findByRoleName(rolename), HttpStatus.OK);
    }

    /**
     * Saves a new role on post mapping.
     *
     * @param role
     * @return New ResponseEntity with type string, that will print an operation specific message if the saving
     * was successful or not.
     * @throws InstanceAlreadyExistsException
     */
    @PostMapping("/")
    public ResponseEntity<String> saveRole(@RequestBody Role role) throws InstanceAlreadyExistsException {
        return new ResponseEntity<String>(roleService.saveRole(role), HttpStatus.OK);
    }

    /**
     * Update an existing role from DB by comparing the ID of the roles.
     */
    @PutMapping("/{id}")
    public void updateRole(@RequestBody Role role, @PathVariable UUID id) throws InstanceNotFoundException {
        roleService.udpateRole(role, id);
        ResponseEntity.ok().body("Role updated");
    }

    /**
     * Delete an existing role from DB by searching for it's ID.
     */
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        ResponseEntity.ok().body("Role Deleted");
    }
}
