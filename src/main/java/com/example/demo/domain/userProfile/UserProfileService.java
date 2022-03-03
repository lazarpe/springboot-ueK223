package com.example.demo.domain.userProfile;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileService {

    UserProfile saveUserProfile(UserProfile userProfile) throws InstanceAlreadyExistsException;

    Optional<UserProfile> getUserProfile(String username) throws InstanceNotFoundException;

    void updateUserProfile(UserProfile userProfile, String username);

    void deleteById(String username) throws InstanceNotFoundException;

    List<UserProfile> findAllWithPagination(Integer page, Integer valuesPerPage);

    List<UserProfile> findAll();
}
