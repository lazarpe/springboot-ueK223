package com.example.demo.domain.userProfile;

import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by lazar on 2/23/2022.
 * Project name: demo
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserServiceImpl userService;

    /**
     * Saves a userprofile only if the user exists. There can be no profile without a user (with account details)
     * @param userProfile
     * @return UserProfile
     * @throws InstanceAlreadyExistsException
     */
    @Override
    public UserProfile saveUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    //Response can be improved
    @Override
    public void updateUserProfile(UserProfile userProfile, UUID uuid) {
            userProfileRepository.findById(uuid).map( foundUserProfile -> {
             foundUserProfile.setLocation(userProfile.getLocation());
             foundUserProfile.setBiography(userProfile.getBiography());
             foundUserProfile.setProfilePictureURL(userProfile.getProfilePictureURL());
             foundUserProfile.setDateOfBirth(userProfile.getDateOfBirth());
             return userProfileRepository.save(foundUserProfile);
            }).orElseGet(() -> {
                userProfile.setId(uuid);
                return userProfileRepository.save(userProfile);
            });
    }

    @Override
    public void deleteById(UUID id) throws InstanceNotFoundException {
        userProfileRepository.deleteById(id);
    }

    //Currently can only get user profile through it's ID and not through user
    @Override
    public Optional<UserProfile> getUserProfile(UUID uuid) {
        return userProfileRepository.findById(uuid);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }
}
