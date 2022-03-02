package com.example.demo.domain.userProfile;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.role.Role;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by lazar on 2/23/2022.
 * Project name: demo
 **/
public interface UserProfileService {
    UserProfile saveUserProfile(UserProfile userProfile) throws InstanceAlreadyExistsException;
    UserProfile getUserProfile(String username);
    // validation if user even exists
    Optional<UserProfile> findById(UUID id) throws InstanceNotFoundException;
    List<UserProfile> findAll();
}
