package com.example.demo.domain.userProfile;

import com.example.demo.domain.appUser.UserRepository;
import com.example.demo.domain.appUser.UserServiceImpl;
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
    public UserProfile saveUserProfile(UserProfile userProfile) throws InstanceAlreadyExistsException {
        /*if (userRepository.findByUsername(user.getUsername()) != null) {
            userProfileRepository.save(new UserProfile());
        } else {
            throw new InstanceAlreadyExistsException("User already exists");
        }*/
        return null;
    }

    @Override
    public void deleteById(String id) throws InstanceNotFoundException {
        userProfileRepository.deleteById(UUID.fromString(id));
    }

    //Currently can only get user profile through it's ID and not through user
    @Override
    public Optional<UserProfile> getUserProfile(String uuid) {
        return userProfileRepository.findById(UUID.fromString(uuid));
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }
}
