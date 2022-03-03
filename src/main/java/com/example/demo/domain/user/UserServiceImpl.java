package com.example.demo.domain.user;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
//    This method is used for security authentication, use caution when changing this
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);

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

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User addRoleById(UUID uuid, UUID roleId) throws InstanceNotFoundException {
        User user = findById(uuid);
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
    public User findByUsername(String username) {
        if(userRepository.findByUsername(username) == null) {
            throw new NoSuchElementException("No user found with that username.");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String deleteById(UUID uuid) {
        User foundUser = findById(uuid);
        userRepository.deleteById(foundUser.getId());
        return foundUser.getUsername() + " successfully deleted";
    }

    @Override
    public String deleteByUsername(String username) {
        User foundUser = findByUsername(username);
        userRepository.deleteById(foundUser.getId());
        return foundUser.getUsername() + " successfully deleted";
    }

}
