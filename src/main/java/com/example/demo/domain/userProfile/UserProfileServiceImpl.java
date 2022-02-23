package com.example.demo.domain.userProfile;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserRepository;
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
    public UserProfile getUserProfile(String username) {
        return null;
    }

    @Override
    public Optional<UserProfile> findById(UUID id) throws InstanceNotFoundException {
        return Optional.empty();
    }

    @Override
    public List<UserProfile> findAll() {
        return null;
    }
}
