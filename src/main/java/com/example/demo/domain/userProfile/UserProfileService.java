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
    Optional<UserProfile> getUserProfile(String uuid) throws InstanceNotFoundException;
    // validation if user even exists
    void deleteById(String id) throws InstanceNotFoundException;
    List<UserProfile> findAll();
}
