package com.example.demo.domain.userProfile;

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

    Optional<UserProfile> getUserProfile(String username) throws InstanceNotFoundException;

    void updateUserProfile(UserProfile userProfile, String username);

    void deleteById(String username) throws InstanceNotFoundException;

    List<UserProfile> findAll();
}
