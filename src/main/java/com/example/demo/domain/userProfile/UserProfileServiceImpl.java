package com.example.demo.domain.userProfile;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    /**
     *
     * @param userProfile
     * @param
     */
    @Override
    public void updateUserProfile(UserProfile userProfile, String username) {
        User foundUser = userService.findByUsername(username);
            userProfileRepository.findByUserId(foundUser.getId()).map( foundUserProfile -> {
             foundUserProfile.setLocation(userProfile.getLocation());
             foundUserProfile.setBiography(userProfile.getBiography());
             foundUserProfile.setProfilePictureURL(userProfile.getProfilePictureURL());
             foundUserProfile.setDateOfBirth(userProfile.getDateOfBirth());
             return userProfileRepository.save(foundUserProfile);
            }).orElseGet(() -> {
                userProfile.setUser(foundUser);
                return userProfileRepository.save(userProfile);
            });
    }

    /**
     *
     * @param username
     * @throws InstanceNotFoundException
     */
    @Override
    public void deleteById(String username) throws InstanceNotFoundException {
        User foundUser = userService.findByUsername(username);
        userProfileRepository.deleteById(userProfileRepository.findByUserId(foundUser.getId()).get().getId());
    }

    //Currently can only get user profile through it's ID and not through user
    /**
     *
     * @param username
     * @return
     */
    @Override
    public Optional<UserProfile> getUserProfile(String username) {
        User foundUser = userService.findByUsername(username);
        return userProfileRepository.findByUserId(foundUser.getId());
    }

    @Override
    public List<UserProfile> findAllWithPagination(Integer page,Integer valuesPerPage) {
        Pageable sortedByName = PageRequest.of(page, valuesPerPage, Sort.by("user.username").ascending());
        return userProfileRepository.findAll(sortedByName).getContent();
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }
}
