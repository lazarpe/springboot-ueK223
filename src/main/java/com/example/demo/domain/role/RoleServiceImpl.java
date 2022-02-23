package com.example.demo.domain.role;

import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void addAuthorityToRole( String rolename, String authorityname){
    Authority authority = authorityRepository.findByName(authorityname);
    Role role = roleRepository.findByName(rolename);
    role.getAuthorities().add(authority);
    }}

