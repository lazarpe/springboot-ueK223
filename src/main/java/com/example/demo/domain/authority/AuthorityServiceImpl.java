package com.example.demo.domain.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor @Transactional
public class AuthorityServiceImpl implements AuthorityService {

  @Autowired
  private final AuthorityRepository authorityRepository;

  @Override
  public Authority save(Authority authority) throws InstanceAlreadyExistsException {
    if (authorityRepository.findByName(authority.getName()) != null) {
      throw new InstanceAlreadyExistsException("Authority already exists");
    } else {
      return authorityRepository.save(authority);
    }
  }

  @Override
  public Authority findByName(String name) {
    return authorityRepository.findByName(name);
  }

  @Override
  public Optional<Authority> findById(UUID id) throws InstanceNotFoundException {
    if (authorityRepository.existsById(id)) {
      return authorityRepository.findById(id);
    } else {
      throw new InstanceNotFoundException("Authority not found");
    }
  }

  @Override
  public List<Authority> findAll() {
    return authorityRepository.findAll();
  }

  @Override
  public Authority updateNameByName(String name, String newName) {
    authorityRepository.updateNameByName(name, newName);
    return authorityRepository.findByName(newName);
  }

  @Override
  public void deleteById(UUID id) {
    authorityRepository.deleteById(id);
  }
}
