package com.example.demo.domain.user;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;

import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;


    @Override
//    This method is used for security authentication, use caution when changing this
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        else {
//          Construct a valid set of Authorities (needs to implement Granted Authorities)
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
                role.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getName())));
            });
//            return a spring internal user object that contains authorities and roles
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public User saveUser(User user) throws InstanceAlreadyExistsException{
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new InstanceAlreadyExistsException("User already exists");
        }
        else {
            return userRepository.save(user);
        }
    }

    @Override
    public User addRoleById(UUID id, UUID roleId) throws InstanceNotFoundException {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User is null");
        }
        User user = optionalUser.get();
        if (!roleRepository.existsById(roleId)) {
            throw new InstanceNotFoundException("Role not found");
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            throw new NoSuchElementException("Role is null");
        }
        Set<Role> roles = user.getRoles();
        roles.add(optionalRole.get());
        return user;
    }

    @Override
    public User findByUsername(String username) { return userRepository.findByUsername(username); }

    @Override
    public Optional<User> findById(UUID id) throws InstanceNotFoundException {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        }
        else {
            throw new InstanceNotFoundException("User not found");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }


}
