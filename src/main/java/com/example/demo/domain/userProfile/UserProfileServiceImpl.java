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
import java.util.NoSuchElementException;

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

    @Override
    public UserProfile saveUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile, String username) {
        User foundUser = findUser(username);
        return userProfileRepository.findByUserId(foundUser.getId()).map( foundUserProfile -> {
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

    @Override
    public String deleteById(String username) {
        User foundUser = findUser(username);
        userProfileRepository.deleteById(userProfileRepository.findByUserId(foundUser.getId()).orElseThrow().getId());
        return foundUser.getUsername() + " successfully deleted";
    }

    public User findUser(String username) {
        if(userService.findByUsername(username) == null){
            throw new NoSuchElementException("No user profile found by that name.");
        }
        return userService.findByUsername(username);
    }

    @Override
    public UserProfile getUserProfile(String username) {
        User foundUser = findUser(username);
        return userProfileRepository.findByUserId(foundUser.getId()).orElseThrow();
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
