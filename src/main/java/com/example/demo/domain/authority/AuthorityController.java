package com.example.demo.domain.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorityController {

  private final AuthorityService authorityService;

  @PostMapping("/authority/?name={name}")
  public Authority save(@PathVariable Authority authority) throws InstanceAlreadyExistsException {
    return authorityService.save(authority);
  }

  @GetMapping("/authority")
  public List<Authority> findAll() {
    return authorityService.findAll();
  }

  @GetMapping("/authority/?id={id}")
  public Optional<Authority> findById(@PathVariable UUID id) throws InstanceNotFoundException {
    return authorityService.findById(id);
  }

  @GetMapping("/authority/?name={name}")
  public Authority findByName(@PathVariable String name) {
    return authorityService.findByName(name);
  }

  @PutMapping("/authority/?name={name}&newName={newName}")
  public Authority changeName(@PathVariable String name, @PathVariable String newName) {
    return authorityService.changeName(name, newName);
  }

  @DeleteMapping("/?id={id}")
  public void deleteById(@PathVariable UUID id) {
    authorityService.deleteById(id);
  }
}
