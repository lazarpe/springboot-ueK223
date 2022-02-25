package com.example.demo.domain.authority;

import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AuthorityService {
  Authority save(Authority authority) throws InstanceAlreadyExistsException;
  Authority findByName(String name);
  Optional<Authority> findById(UUID id) throws InstanceNotFoundException;
  List<Authority> findAll();
  void updateNameByName(String name, String newName);
  void deleteById(UUID id);
}
