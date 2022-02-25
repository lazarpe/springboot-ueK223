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

  @PostMapping("")
  public ResponseEntity<?> save(@RequestBody Authority authority) {
    try {
      return new ResponseEntity<>(authorityService.save(authority), HttpStatus.CREATED);
    } catch (InstanceAlreadyExistsException e) {
      return new ResponseEntity<>("authority already exists", HttpStatus.CONFLICT);
    }
  }

  @GetMapping("")
  public List<Authority> findAll() {
    return authorityService.findAll();
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<?> findById(@PathVariable UUID id) {
    try {
      Optional<Authority> authority = authorityService.findById(id);
      return new ResponseEntity<>(authority.orElse(null), HttpStatus.OK);
    } catch (InstanceNotFoundException e) {
      return new ResponseEntity<>("authority not found", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/name/{name}")
  public Authority findByName(@PathVariable String name) {
    return authorityService.findByName(name);
  }

  @PutMapping("/name/{name}/{newName}")
  public Authority updateNameByName(@PathVariable String name, @PathVariable String newName) {
    return authorityService.updateNameByName(name, newName);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<?> deleteById(@PathVariable UUID id) {
    try {
      authorityService.deleteById(id);
      return new ResponseEntity<>("authority deleted", HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      return new ResponseEntity<>("authority not found", HttpStatus.NOT_FOUND);
    }
  }
}
