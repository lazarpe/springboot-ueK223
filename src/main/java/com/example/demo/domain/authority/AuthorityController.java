package com.example.demo.domain.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    private final ResponseEntity<Object> authorityNotFound = new ResponseEntity<>("authority not found", HttpStatus.NOT_FOUND);
    private final ResponseEntity<Object> authorityIsNull = new ResponseEntity<>("authority is null", HttpStatus.NOT_FOUND);

    @PostMapping("/name/{name}")
    public ResponseEntity<Object> save(@PathVariable String name) {
        try {
            return new ResponseEntity<>(authorityService.save(new Authority(null, name)), HttpStatus.CREATED);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>("authority \"" + name + "\" already exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("")
    public List<Authority> findAll() {
        return authorityService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        try {
            Optional<Authority> authority = authorityService.findById(id);
            if (authority.isEmpty()) {
                return authorityIsNull;
            }
            return new ResponseEntity<>(authority.get(), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return authorityNotFound;
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        if (authorityService.findByName(name) != null) {
            return new ResponseEntity<>(authorityService.findByName(name), HttpStatus.OK);
        } else {
            return authorityIsNull;
        }
    }

    @PutMapping("/name/{name}/{newName}")
    public ResponseEntity<Object> updateNameByName(@PathVariable String name, @PathVariable String newName) {
        try {
            return new ResponseEntity<>(authorityService.updateNameByName(name, newName), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return authorityNotFound;
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>("authority \"" + newName + "\" already exists", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
        try {
            authorityService.deleteById(id);
            return new ResponseEntity<>("authority deleted", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return authorityNotFound;
        }
    }
}
